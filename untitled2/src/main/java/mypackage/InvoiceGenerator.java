package mypackage;

import mypackage.model.Invoice;
import mypackage.model.InvoiceLine;
import mypackage.model.Price;
import mypackage.model.Taxes;
import mypackage.model.User;
import mypackage.model.Vat;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InvoiceGenerator {

    private final User user;
    private final Collection<Measurement> measurements;
    private final Collection<Price> prices;
    private final String yearMonthStr;

    private final String currency;

    private final String key;


    public InvoiceGenerator(final User user, final Collection<Measurement> measurements, final Collection<Price> prices,
                            final String yearMonthStr, final String currency, final String key) {
        this.user = user;
        this.measurements = measurements;
        this.prices = prices;
        this.yearMonthStr = yearMonthStr;
        this.currency = currency;
        this.key = key;
    }

    BigDecimal totalAmount = BigDecimal.ZERO;
    BigDecimal totalAmountWithVat = BigDecimal.ZERO;

    public Invoice generate() throws IOException, ParseException {
        final ProportionalMeasurementDistributor proportionalMeasurementDistributor = new ProportionalMeasurementDistributor(
                measurements, prices);
        final List<QuantityPricePeriod> distribute = proportionalMeasurementDistributor.distribute();
        final LocalDateTime yearMonthLocalDate = localDateTimeToReport();
        final List<InvoiceLine> invoiceLines = new ArrayList<>();
        final List<Vat> vatLines = new ArrayList<>();
        final List<Taxes> taxesLines = new ArrayList<>();
        final CurrencyGenerator currencyGenerator = new CurrencyGenerator(currency, key);
        final BigDecimal currencyValue = currencyGenerator.generateCurrency();
        final List<Vat> vatList = new ArrayList<>();

        int index = 1;

        for (final QuantityPricePeriod qpp : distribute) {
            final LocalDateTime end = qpp.getEnd();
            if (0 <= yearMonthLocalDate.compareTo(end)) {
                //              Invoice Line
                final BigDecimal quantity = qpp.getQuantity();
                final LocalDateTime start = qpp.getStart();
                ZonedDateTime gmt = start.atZone(ZoneId.of("GMT"));
                final String product = qpp.getProduct();
                final BigDecimal price = qpp.getPrice();
                final int priceList = user.getPriceListNumber();
                BigDecimal amount = qpp.getQuantity().multiply(qpp.getPrice())
                        .setScale(2, RoundingMode.HALF_UP);
                amount = amount.multiply(currencyValue).setScale(2, RoundingMode.HALF_UP);
                totalAmount = totalAmount.add(amount);
                final InvoiceLine invoiceLine = new InvoiceLine(index, quantity, start, end, product, price, priceList,
                        amount);
                invoiceLines.add(invoiceLine);
                //              Taxes line
                final TaxesGenerator taxesGenerator = new TaxesGenerator(invoiceLine, taxesLines.size(), currencyValue);
                final Taxes taxes = taxesGenerator.generate();
                taxesLines.add(taxes);
                final BigDecimal amountInTaxes = taxes.getAmount();
                totalAmount = totalAmount.add(amountInTaxes);
                //              Vat Line
                final VatGenerator vatGenerator = new VatGenerator(index, amount, amountInTaxes);
                vatList.addAll(vatGenerator.generateVats());
                for (final Vat vat : vatList) {
                    vatLines.add(vat);
                    totalAmountWithVat = totalAmountWithVat.add(vat.getAmount());
                }
                vatList.clear();
                index++;
            }
        }
        totalAmountWithVat = totalAmountWithVat.add(totalAmount);
        final LocalDateTime documentDate = LocalDateTime.now();
        final String documentNumber = Invoice.getDocumentNumber();

        return new Invoice(documentDate, documentNumber, user.getName(), totalAmount, totalAmountWithVat, invoiceLines,
                vatLines,
                taxesLines);
    }

    public LocalDateTime localDateTimeToReport() {
        final YearMonth yearMonth = YearMonth.parse(yearMonthStr, DateTimeFormatter.ofPattern("yy-MM"));
        final LocalDateTime yearMonthLocalDate = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        return yearMonthLocalDate;
    }

}
