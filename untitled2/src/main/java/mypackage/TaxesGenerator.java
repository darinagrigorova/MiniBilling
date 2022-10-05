package mypackage;

import mypackage.model.InvoiceLine;
import mypackage.model.Taxes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TaxesGenerator {

    InvoiceLine invoiceLine;

    int taxesSize;

    final BigDecimal currencyValue;
    static final String NAME = "Standing charge";
    static final String UNIT = "days";

    public TaxesGenerator(final InvoiceLine invoiceLine, final int taxesSize, final BigDecimal currencyValue) {
        this.invoiceLine = invoiceLine;
        this.taxesSize = taxesSize;
        this.currencyValue = currencyValue;
    }

    public Taxes generate() {

        final List<Integer> index = new ArrayList<>();
        index.add(invoiceLine.getIndex());
        final int quantityDays = (int) invoiceLine.getStart().toLocalDate().atStartOfDay()
                .until(invoiceLine.getEnd().toLocalDate().atTime(23, 59, 59), ChronoUnit.DAYS);
        final BigDecimal priceInTaxes = new BigDecimal("1.6").setScale(2, RoundingMode.HALF_UP);
        BigDecimal amountInTaxes = priceInTaxes.multiply(BigDecimal.valueOf(quantityDays))
                .setScale(2, RoundingMode.HALF_UP);
        amountInTaxes = amountInTaxes.multiply(currencyValue).setScale(2, RoundingMode.HALF_UP);
        return new Taxes(taxesSize + 1, index, NAME, quantityDays, UNIT, priceInTaxes, amountInTaxes);
    }
}

