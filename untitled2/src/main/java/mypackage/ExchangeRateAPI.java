package mypackage;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExchangeRateAPI {

    private final String key;

    public ExchangeRateAPI(final String key) {
        this.key = key;
    }

    public JSONObject exchange() throws IOException, ParseException {
        final String urlStr = "https://v6.exchangerate-api.com/v6/" + key + "/latest/BGN";
        final URL url = new URL(urlStr);
        final HttpURLConnection request = (HttpURLConnection) url.openConnection();
        final JSONParser jp = new JSONParser();
        final JSONObject root = (JSONObject) jp.parse(new InputStreamReader((InputStream) request.getContent()));
        return root;
    }
}
