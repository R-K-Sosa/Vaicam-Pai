package drew.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;


public class MainActivity extends YouTubeBaseActivity {
    // declare our objects
    YouTubePlayerView youtubePlayerView;
    Button button;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    //our broadcast receiever, recieves data from MyService.java
    public BroadcastReceiver mMessageReceiverTest = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("data");
            Toast.makeText(getApplicationContext(), "Received",
                    Toast.LENGTH_LONG).show();

        }
    };

    //Constructor
    public MainActivity() throws IOException, JSONException {
    }

    TextView tv;

    //Youtube Player
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);

        // starts our asynchronous task,
        AsyncCaller mellow = new AsyncCaller(this);
        mellow.execute();

        //creates the play button for the video stream
        button = (Button) findViewById(R.id.bn);
        youtubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player_view);

        // receives the broadcast from our Service
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiverTest,
                new IntentFilter("custom-event-name"));


        //configures our service to parse our api
        Intent mServiceIntent = new Intent(getApplicationContext(), MyService.class);
        mServiceIntent.setData(Uri.parse("https://3bb4690e.ngrok.io"));
        getApplicationContext().startService(mServiceIntent);

        mMessageReceiverTest.onReceive(getBaseContext(), mServiceIntent);


        // interacts with the youtube java api library, loads stream, displays it
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("1rdSjaohWkI");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        };


        // grab our api key from COnfig.java
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youtubePlayerView.initialize(COnfig.API_KEY, onInitializedListener);
            }
        });
    }


    // our asynchronous task
    private class AsyncCaller extends AsyncTask<Void, String, Void>
    {
        MainActivity ma;
        String rightCookie;

        public AsyncCaller() {
            super();
        }

        //constructor
        public AsyncCaller(MainActivity ma) {
            super();
            this.ma = ma;
        }


        // runs at the start of the app
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //this method will be running on UI thread
        }
        @Override
        protected Void doInBackground(Void... params) {

            // grabs json data using json library
            JSONObject json = new JSONObject();
            try {
                json = readJsonFromUrl("https://3bb4690e.ngrok.io"); //original link >>> https://3bb4690e.ngrok.io
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //converts the json array in object form to a string.
            rightCookie = json.toString();
            // publishes to onProgressUpdate
            this.publishProgress(rightCookie);
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values){
            super.onProgressUpdate(values);
            // gets the progress published by doInBackground, sets it as a textview
            TextView textView = (TextView) ma.findViewById(R.id.textView);
            textView.setText(rightCookie);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }

        // reads in data, sets it as a string, called by readjsonfromurl
        private String readAll(Reader rd) throws IOException {
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            return sb.toString();
        }


        // method that reads from our api, turns it into the json object.
        public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
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



}
