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
    YouTubePlayerView youtubePlayerView;
    Button button;

    YouTubePlayer.OnInitializedListener onInitializedListener;

    public BroadcastReceiver mMessageReceiverTest = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("data");
            Toast.makeText(getApplicationContext(), "Received",
                    Toast.LENGTH_LONG).show();

//            textView.setText(mellow.activate() + "....hello");
//            System.out.println(mellow.activate() + "....hello");
//            Toast.makeText(getApplicationContext(), mellow.activate(),
//                    Toast.LENGTH_LONG).show();
        }
    };

    public MainActivity() throws IOException, JSONException {
    }

    TextView tv;

    //Youtube Player
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);

        AsyncCaller mellow = new AsyncCaller(this);
        mellow.execute();

        button = (Button) findViewById(R.id.bn);
        youtubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player_view);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiverTest,
                new IntentFilter("custom-event-name"));

        Intent mServiceIntent = new Intent(getApplicationContext(), MyService.class);
        mServiceIntent.setData(Uri.parse("https://3bb4690e.ngrok.io"));
        getApplicationContext().startService(mServiceIntent);

        mMessageReceiverTest.onReceive(getBaseContext(), mServiceIntent);

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("1rdSjaohWkI");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youtubePlayerView.initialize(COnfig.API_KEY, onInitializedListener);
            }
        });
    }


    private class AsyncCaller extends AsyncTask<Void, String, Void>
    {
        MainActivity ma;
        String rightCookie;

        public AsyncCaller() {
            super();
        }

        public AsyncCaller(MainActivity ma) {
            super();
            this.ma = ma;
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
                json = readJsonFromUrl("https://3bb4690e.ngrok.io"); //original link >>> https://3bb4690e.ngrok.io
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            rightCookie = json.toString();
            this.publishProgress(rightCookie);
            System.out.println(rightCookie + "this worked");
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values){
            super.onProgressUpdate(values);
            System.out.println(rightCookie + "asdf");
            //78 and 79 are very special, maybe you can get them to work :) set text in asyncTask https://www.quandl.com/api/v3/datasets/ODA/PBARL_USD.json?api_key=E-f4bGCzj_cGGgo-6RsR
            TextView textView = (TextView) ma.findViewById(R.id.textView);
            textView.setText(rightCookie + "....hello");
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
//            TextView textView = (TextView) findViewById(R.id.textView);
//            textView.setText(rightCookie + "....hello");

            //this method will be running on UI thread
        }


        private String readAll(Reader rd) throws IOException {
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            return sb.toString();
        }

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
