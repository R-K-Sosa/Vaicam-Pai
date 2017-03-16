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

        button.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                youtubePlayerView.initialize(COnfig.API_KEY, onInitializedListener);
            }
        });
    }

    // Clarifai
    //final ClarifaiCLient client = new ClarifaiBuilder("ypmwcZNi4rAV9zvI2Aqmisk-_Mz0Hz4goTw59uwg", "Zbj-YeJeANKe6Skd9K1NxTmDojGV6VLAisJiv6DB").buildSync();
}
