package com.purebasicv2.app.activity.youtubevideo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.purebasicv2.app.CallBacks;
import com.purebasicv2.app.R;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

public class YoutubeVideoViewActiviyt extends AppCompatActivity {


    PlayerView playerView;
    SimpleExoPlayer player;
    boolean playWhenReady = true;
    int currentWindow = 0;
    long playBackPosition = 0;
    ProgressBar progressBar;
    RelativeLayout rlLayoutToolbar;
    private ImageView btFullScreen;

    boolean flagFullScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_video_view_activiyt);

        playerView = findViewById(R.id.exoPlayerView);
        progressBar = findViewById(R.id.progress_bar_exo_player);
        rlLayoutToolbar = findViewById(R.id.id2);
        btFullScreen = (ImageView) playerView.findViewById(R.id.bt_fullscreen);


        initPlayer();

        btFullScreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (flagFullScreen) {
                    btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen));
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    playerView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, CallBacks.dpToPixel(YoutubeVideoViewActiviyt.this, 200)));
                    flagFullScreen = false;
                    rlLayoutToolbar.setVisibility(View.VISIBLE);
                    return;
                }
                btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen_exit));
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                playerView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                flagFullScreen = true;
                rlLayoutToolbar.setVisibility(View.GONE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        });
    }

    private void initPlayer() {
        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        playYoutubeVideo();
    }

    private void playYoutubeVideo() {

        progressBar.setVisibility(View.VISIBLE);
        new YouTubeExtractor(this) {
            @Override
            protected void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta videoMeta) {

                int videoTag = 137;
                int audioTag = 140;
                if (ytFiles == null) {
                    // Something went wrong we got no urls. Always check this.
                    Toast.makeText(YoutubeVideoViewActiviyt.this, "YTFiles Returning null", Toast.LENGTH_SHORT).show();
                    return;
                }
                MediaSource audioSource = new ProgressiveMediaSource
                        .Factory(new DefaultHttpDataSource.Factory())
                        .createMediaSource(MediaItem.fromUri(ytFiles.get(audioTag).getUrl()));
                MediaSource videoSource = new ProgressiveMediaSource
                        .Factory(new DefaultHttpDataSource.Factory())
                        .createMediaSource(MediaItem.fromUri(ytFiles.get(videoTag).getUrl()));

                player.setMediaSource(new MergingMediaSource
                                (true,
                                        videoSource,
                                        audioSource),
                        true
                );
                player.prepare();
                player.setPlayWhenReady(playWhenReady);
                player.seekTo(currentWindow, playBackPosition);

                progressBar.setVisibility(View.GONE);
//                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//                rlLayoutToolbar.setVisibility(View.GONE);

            }
        }.extract("https://www.youtube.com/embed/5PwU12NfqSE", false, true);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(Util.SDK_INT>=24){
//            initPlayer();
//        }
//    }

    @Override
    protected void onStop() {
        super.onStop();
        if(Util.SDK_INT>=24){
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playBackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            player.setPlayWhenReady(false);
            player.getPlaybackState();
        }
    }

    @Override
    protected void onDestroy() {
        releasePlayer();
        super.onDestroy();
    }


    private void hideSystemUI() {
        playerView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        if((Util.SDK_INT<24 || player==null)){
            initPlayer();
            hideSystemUI();
        }
    }
}