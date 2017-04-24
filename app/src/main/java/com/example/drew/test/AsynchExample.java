package com.example.drew.test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Drew on 4/22/2017.
 */

public class AsynchExample extends Activity {

    private String rightCookie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public String activate(){
        new AsyncCaller().execute();
        return rightCookie;
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        new AsyncCaller().execute();
    }

    private class AsyncCaller extends AsyncTask<Void, Void, Void>
    {
        public AsyncCaller() {
            super();
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //this method will be running on UI thread
        }
        @Override
        protected Void doInBackground(Void... params) {

            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
            JSONObject json = new JSONObject();
            try {
                json = readJsonFromUrl("https://3bb4690e.ngrok.io");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            rightCookie = json.toString();
            System.out.println(rightCookie + "this worked");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
//            Toast.makeText(getApplicationContext(), rightNipple.toString() + "Fish",
//                    Toast.LENGTH_LONG).show();
            //this method will be running on UI thread
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
