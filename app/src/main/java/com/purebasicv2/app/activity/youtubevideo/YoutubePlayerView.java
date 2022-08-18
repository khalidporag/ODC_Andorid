package com.purebasicv2.app.activity.youtubevideo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.purebasicv2.app.R;
import com.purebasicv2.app.utils.AppConstants;
import com.purebasicv2.app.utils.SharedPrefManager;

public class YoutubePlayerView extends YouTubeBaseActivity implements View.OnClickListener{


    LinearLayout videoControls;
    ImageView exoFfwd,exoRew,playVideo,pauseVideo;
    private Handler mHandler = null;
    private YouTubePlayer mPlayer;
    public  String VIDEO_ID = "";
    TextView mPlayTimeTextView;
    SeekBar mSeekBar;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_youtube_player_view);


        final String api_key = getApiKey();
        System.out.println("apikey "+AppConstants.API_KEY);

        initView();
        // Get reference to the view of Video player
        YouTubePlayerView ytPlayer = (YouTubePlayerView) findViewById(R.id.ytPlayer);

        try {
            ytPlayer.initialize(
                    api_key,
                    new YouTubePlayer.OnInitializedListener() {
                        // Implement two methods by clicking on red
                        // error bulb inside onInitializationSuccess
                        // method add the video li nk or the playlist
                        // link that you want to play In here we
                        // also handle the play and pause
                        // functionality
                        @Override
                        public void onInitializationSuccess(
                                YouTubePlayer.Provider provider,
                                YouTubePlayer youTubePlayer, boolean wasRestored) {

                            if (null == youTubePlayer) return;
                            mPlayer=youTubePlayer;

                            // Start buffering
                            if (!wasRestored) {
                                youTubePlayer.cueVideo(VIDEO_ID);
                            }
                            displayCurrentTime();

                            mPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                            videoControls.setVisibility(View.VISIBLE);
                            // Add listeners to YouTubePlayer instance
                            youTubePlayer.setPlayerStateChangeListener(mPlayerStateChangeListener);
                            youTubePlayer.setPlaybackEventListener(mPlaybackEventListener);
                        }

                        // Inside onInitializationFailure
                        // implement the failure functionality
                        // Here we will show toast
                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                            YouTubeInitializationResult
                                                                    youTubeInitializationResult) {
                            Toast.makeText(getApplicationContext(), "Video player Failed "+youTubeInitializationResult.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getApiKey() {
        sharedPrefManager = SharedPrefManager.getInstance(this);

        AppConstants.API_KEY=sharedPrefManager.getUserInfo().getApiKey();
        return sharedPrefManager.getUserInfo().getApiKey();

    }

    private void initView() {

        try {
            VIDEO_ID = getIntent().getExtras().getString("videoid");
        } catch (Exception e) {
            e.printStackTrace();
        }

        videoControls = findViewById(R.id.video_control);
        exoFfwd= findViewById(R.id.exo_ffwd);
        exoRew= findViewById(R.id.exo_rew);
        playVideo=findViewById(R.id.play_video);
        pauseVideo=findViewById(R.id.pause_video);
        mPlayTimeTextView=findViewById(R.id.play_time);

        mSeekBar = (SeekBar) findViewById(R.id.video_seekbar);
        mSeekBar.setOnSeekBarChangeListener(mVideoSeekBarChangeListener);

        exoRew.setOnClickListener(this);
        exoFfwd.setOnClickListener(this);
        playVideo.setOnClickListener(this);
        pauseVideo.setOnClickListener(this);

        mHandler = new Handler();
    }

    public void setPlayPauseIcon(){
        try {
            if (null != mPlayer && !mPlayer.isPlaying()) {
                mPlayer.play();
                playVideo.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_pause));
            }else{
                mPlayer.pause();
                playVideo.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_play));
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
                case R.id.play_video:
                  setPlayPauseIcon();
                break;
            case R.id.pause_video:
                try {
                    if (null != mPlayer && mPlayer.isPlaying())
                        mPlayer.pause();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.exo_ffwd:
                try {
                    long lengthPlayed = ((mPlayer.getCurrentTimeMillis())+10000);
                    int nextPlay= ((int) (lengthPlayed+10000));
                    mPlayer.seekToMillis(nextPlay);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case R.id.exo_rew:
                try {
                    long lengthPlayed1 = (mPlayer.getCurrentTimeMillis());
                    mPlayer.seekToMillis((int) (lengthPlayed1-10000));
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    YouTubePlayer.PlaybackEventListener mPlaybackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
            mHandler.removeCallbacks(runnable);
            displayCurrentTime();
        //    mHandler2.removeCallbacks(mUpdateTimeTask);
        }

        @Override
        public void onPlaying() {
            mHandler.postDelayed(runnable, 100);
            //mHandler2.postDelayed(mUpdateTimeTask, 1000);
            displayCurrentTime();
        }

        @Override
        public void onSeekTo(int arg0) {
            mHandler.postDelayed(runnable, 100);
    //        mHandler2.postDelayed(mUpdateTimeTask,10000);

        }

        @Override
        public void onStopped() {
            mHandler.removeCallbacks(runnable);
  //          mHandler2.removeCallbacks(mUpdateTimeTask);
        }
    };

    YouTubePlayer.PlayerStateChangeListener mPlayerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
            setPlayPauseIcon();
        }

        @Override
        public void onVideoStarted() {
            displayCurrentTime();
        }
    };
    SeekBar.OnSeekBarChangeListener mVideoSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            long lengthPlayed = (mPlayer.getDurationMillis() * seekBar.getProgress()) / 100;
            mPlayer.seekToMillis((int) lengthPlayed);
        }
    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            displayCurrentTime();
            int progress = mPlayer.getCurrentTimeMillis() * 100 / mPlayer.getDurationMillis();
            mSeekBar.setProgress(progress);
            mHandler.postDelayed(this, 100);
        }
    };


    private void displayCurrentTime() {
        if (null == mPlayer) return;
        String formattedTime = formatTime(mPlayer.getDurationMillis() - mPlayer.getCurrentTimeMillis());
        mPlayTimeTextView.setText(formattedTime);
    }

    private String formatTime(int millis) {
        int seconds = millis / 1000;
        int minutes = seconds / 60;
        int hours = minutes / 60;

        return (hours == 0 ? "--:" : hours + ":") + String.format("%02d:%02d", minutes % 60, seconds % 60);
    }


}