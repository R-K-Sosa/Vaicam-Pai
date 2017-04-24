package com.example.drew.test;

import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;


public class MyService extends IntentService {

    public static Object get;
    public static String rightCookie = "cookie";
    public static SuperFishy superfish;

    public SuperFishy getSuperfish() {
        return superfish;
    }

    public MyService() {
        super(MyService.class.getName());
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            URL url = new URL("https://3bb4690e.ngrok.io");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
//                    Toast.makeText(getApplicationContext(), line,
//                            Toast.LENGTH_LONG).show();
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                Intent intention = new Intent("custom-event-name");
                JSONObject json = null;
                try {
                    json = readJsonFromUrl("https://3bb4690e.ngrok.io");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(json.toString());
                rightCookie = json.toString();
                try {
                    System.out.println(json.get("app_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                rightCookie = "no working";
                superfish = new SuperFishy();
                superfish.setFish(rightCookie);
                intent.putExtra("data", rightCookie);
//                Toast.makeText(getApplicationContext(), "Help",
//                        Toast.LENGTH_LONG).show();
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            rightCookie = "the url is broken";
//            Toast.makeText(getApplicationContext(), "the url is broken",
//                    Toast.LENGTH_LONG).show();
        }
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