package mypackage;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.math.BigDecimal;

public class CurrencyGenerator {

    private final String currency;

    private final String key;

    public CurrencyGenerator(final String currency, final String key) {
        this.currency = currency;
        this.key = key;
    }

    public BigDecimal generateCurrency() throws IOException, ParseException {
        final ExchangeRateAPI exchangeRateAPI = new ExchangeRateAPI(key);
        final JSONObject exchange = exchangeRateAPI.exchange();
        final JSONObject conversionRates = (JSONObject) exchange.get("conversion_rates");
        final BigDecimal currencyValue = new BigDecimal(String.valueOf(conversionRates.get(currency)));
        return currencyValue;
    }
}
