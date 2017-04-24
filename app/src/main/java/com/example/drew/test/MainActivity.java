package com.example.drew.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONException;
import java.io.IOException;


public class MainActivity extends YouTubeBaseActivity {
    YouTubePlayerView youtubePlayerView;
    Button button;

    YouTubePlayer.OnInitializedListener onInitializedListener;

    public BroadcastReceiver mMessageReceiverTest = new BroadcastReceiver() {

        public void onReceive(Context context,Intent intent) {
            // Get extra data included in the Intent

            String message = intent.getStringExtra("data");
            TextView textView = (TextView) findViewById(R.id.textView);
            Toast.makeText(getApplicationContext(), "Received",
                    Toast.LENGTH_LONG).show();
            AsynchExample mellow = new AsynchExample();
            textView.setText(mellow.activate() + "....hello");
            System.out.println(mellow.activate() + "....hello");
            Toast.makeText(getApplicationContext(), mellow.activate(),
                    Toast.LENGTH_LONG).show();
        }
    };

    public MainActivity() throws IOException, JSONException {
    }

    //Youtube Player
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    /*public LocalBroadcastManager mMessageReceiver = new LocalBroadcastManager() {

        public void onReceive(Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("data");
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText(message);
        }
    };*/
    }
