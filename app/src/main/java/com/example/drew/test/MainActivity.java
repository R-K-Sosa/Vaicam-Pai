package com.example.drew.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeBaseActivity {
    YouTubePlayerView youtubePlayerView;
    Button button;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();

    //Youtube Player
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.bn);
        youtubePlayerView = (YouTubePlayerView)findViewById(R.id.youtube_player_view);
        onInitializedListener = new YouTubePlayer.OnInitializedListener(){
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
            youTubePlayer.loadVideo("wRFs1_BL9zw");
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        };

        editText = (EditText) findViewById(R.id.Name);
        editText2 = (EditText) findViewById(R.id.part_nr);

        button.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                youtubePlayerView.initialize(COnfig.API_KEY, onInitializedListener);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Send(View view) {
        new Create_Part().execute();
    }

    class Create_Part extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Sending part to the database...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String String_name = editText.getText().toString();
            String Int_Part = editText2.getText().toString();

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("Name", String_name));
            params.add(new BasicNameValuePair("part_nr", Int_Part));

            JSONObject json = jsonParser.makeHttpRequest("RaspberryPi_IP/db_create.php", "POST", params);

            try {
                int success = json.getInt("success");

                if(success == 1){
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url){
            pDialog.dismiss();
        }

    // Clarifai
    //final ClarifaiClient client = new ClarifaiBuilder("ypmwcZNi4rAV9zvI2Aqmisk-_Mz0Hz4goTw59uwg", "Zbj-YeJeANKe6Skd9K1NxTmDojGV6VLAisJiv6DB").buildSync();
}}
