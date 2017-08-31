package drew.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by RoSo on 4/7/17.
 * Source: http://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
 */

public class JsonReader {

    String left;

    public JsonReader() throws JSONException, IOException{
        JSONObject json = readJsonFromUrl("https://105ba16f.ngrok.io/");
        System.out.println(json.toString());
        left = json.toString();
        System.out.println(json.get("id"));
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

}

