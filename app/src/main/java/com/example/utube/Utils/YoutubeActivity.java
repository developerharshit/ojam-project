package com.example.utube.Utils;

import android.os.Bundle;
import android.widget.Toast;

import com.example.utube.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

public class YoutubeActivity extends YouTubeBaseActivity {

    YouTubePlayerFragment youTubePlayerFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        String videoId="";
        if(getIntent().hasExtra("videoId")){
            videoId=getIntent().getStringExtra("videoId");
        }

        youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_player_view);
        final String finalVideoId = videoId;
        youTubePlayerFragment.initialize(YoutubeConfig.getAPI_key(), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);
                youTubePlayer.setFullscreen(true);
                youTubePlayerFragment.setRetainInstance(true);
                youTubePlayer.loadVideo(finalVideoId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(YoutubeActivity.this, "initialisation failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}