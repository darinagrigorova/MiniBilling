package mypackage;

import mypackage.model.Invoice;
import mypackage.model.Price;
import mypackage.model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;

public class JsonGenerator {

    private final Invoice invoice;
    private final String folderPath;

    private final String currency;

    private final User user;

    public JsonGenerator(final Invoice invoice, final String folderPath, final String currency, final User user) {
        this.invoice = invoice;
        this.folderPath = folderPath;
        this.currency = currency;
        this.user = user;
    }

    JSONObject json = new JSONObject();

    JSONArray lines = new JSONArray();

    JSONArray vatLines = new JSONArray();

    JSONArray taxesLines = new JSONArray();

    String documentNumber = Invoice.getDocumentNumber();



    public JSONObject generateJson() throws NoSuchFieldException, IllegalAccessException {
        //        final String consumer = invoice.getConsumer();
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssXXX");
        final List<Price> prices = user.getPriceList().getPrices();
        for (int i = 0; i < prices.size(); i++) {
            final JSONObject newLine = new JSONObject();
            final JSONObject newTaxesLine = new JSONObject();
            orderJson(json);
            orderJson(newLine);
            orderJson(newTaxesLine);

            json.put("documentDate", invoice.getDocumentDate());
            json.put("documentNumber", Integer.parseInt(documentNumber) - 1);
            json.put("consumer", user.getName());
            json.put("reference", user.getRef());
            json.put("totalAmount", invoice.getTotalAmount().toString() + " " + currency);
            json.put("totalAmountWithVat", invoice.getTotalAmountWithVat().toString() + " " + currency);
            //          Invoice new line
            final int index = invoice.getLines().get(i).getIndex();
            newLine.put("index", index);
            final BigDecimal quantity = invoice.getLines().get(i).getQuantity();
            newLine.put("quantity", quantity);
            final String lineStart = invoice.getLines().get(i).getStart().atZone(ZoneId.of("GMT"))
                    .format(dateTimeFormatter);
            newLine.put("lineStart", lineStart);
            final String lineEnd = invoice.getLines().get(i).getEnd().atZone(ZoneId.of("GMT"))
                    .format(dateTimeFormatter);
            newLine.put("lineEnd", lineEnd);
            final String product = invoice.getLines().get(i).getProduct();
            newLine.put("product", product);
            final BigDecimal price = invoice.getLines().get(i).getPrice();
            newLine.put("price", price);
            final int priceList = invoice.getLines().get(i).getPriceList();
            newLine.put("priceList", priceList);
            final BigDecimal amount = invoice.getLines().get(i).getAmount();
            newLine.put("amount", amount.toString() + " " + currency);
            lines.put(newLine);
            //          Line in taxes
            final int indexInTaxes = invoice.getTaxesLines().get(i).getIndex();
            newTaxesLine.put("index", indexInTaxes);
            final List<Integer> linesInTaxes = invoice.getTaxesLines().get(i).getLines();
            newTaxesLine.put("lines", linesInTaxes);
            final String name = invoice.getTaxesLines().get(i).getName();
            newTaxesLine.put("name", name);
            final int quantityInTaxes = invoice.getTaxesLines().get(i).getQuantity();
            newTaxesLine.put("quantity", quantityInTaxes);
            final String unit = invoice.getTaxesLines().get(i).getUnit();
            newTaxesLine.put("unit", unit);
            final BigDecimal priceInTaxes = invoice.getTaxesLines().get(i).getPrice();
            newTaxesLine.put("price", priceInTaxes);
            final BigDecimal amountInTaxes = invoice.getTaxesLines().get(i).getAmount();
            newTaxesLine.put("amount", amountInTaxes.toString() + " " + currency);
            taxesLines.put(newTaxesLine);
        }
        //          Line in vat
        for (int j = 0; j < invoice.getVatsLines().size(); j++) {
            final JSONObject newVatLine = new JSONObject();
            orderJson(newVatLine);
            final int indexInVat = invoice.getVatsLines().get(j).getIndex();
            newVatLine.put("index", indexInVat);
            final int linesInVat = invoice.getVatsLines().get(j).getLines();
            newVatLine.put("lines", linesInVat);
            final int taxesInVat = invoice.getVatsLines().get(j).getTaxes();
            newVatLine.put("taxes", taxesInVat);
            final int taxedAmountPercentage = invoice.getVatsLines().get(j).getTaxedAmountPercentage();
            newVatLine.put("taxedAmountPercentage", taxedAmountPercentage);
            final int percentage = invoice.getVatsLines().get(j).getPercentage();
            newVatLine.put("percentage", percentage);
            final BigDecimal amountInVat = invoice.getVatsLines().get(j).getAmount();
            newVatLine.put("amount", amountInVat.toString() + " " + currency);
            vatLines.put(newVatLine);
        }
        json.put("lines", lines);
        json.put("taxes", taxesLines);
        json.put("vat", vatLines);
        return json;
    }

    public void orderJson(final JSONObject json) throws NoSuchFieldException, IllegalAccessException {
        final Field changeMap = json.getClass().getDeclaredField("map");
        changeMap.setAccessible(true);
        changeMap.set(json, new LinkedHashMap<>());
        changeMap.setAccessible(false);
    }

}
