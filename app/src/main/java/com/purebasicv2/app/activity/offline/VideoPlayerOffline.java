package com.purebasicv2.app.activity.offline;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.purebasicv2.app.R;
import com.purebasicv2.app.constant.Constants;

import java.io.File;


public class VideoPlayerOffline extends AppCompatActivity {

    private ImageView btFullScreen;
    boolean flagFullScreen = false;
    private ProgressBar progress_bar_exo_player;
    private SimpleExoPlayer simpleExoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_player_view);

        String getVideo = getIntent().getStringExtra("VIDEO_SOURCE");

        PlayerView exoPlayerView = (PlayerView) findViewById(R.id.exoPlayerOfflineView);
        progress_bar_exo_player = (ProgressBar) findViewById(R.id.progress_bar_exo_player_offline);
        btFullScreen = (ImageView) exoPlayerView.findViewById(R.id.bt_fullscreen);
        ImageView btnDeleteVideo = exoPlayerView.findViewById(R.id.btnDeleteVideo);
//        ArrowDownloadButton btnDownloadVideo = (ArrowDownloadButton) exoPlayerView.findViewById(R.id.btnDownloadVideo);
//        btnDownloadVideo.setVisibility(View.GONE);
//        btnDeleteVideo.setVisibility(View.VISIBLE);

        String videoPath = Environment.getExternalStorageDirectory().toString()+"/"+Constants.DOWNLOAD_FOLDER+"/"+getVideo;

        final File getVideoFileInfo = new File(videoPath);
        if (getVideo == null || getVideo.isEmpty()){
            Toast.makeText(this, "Video Source Not Found", Toast.LENGTH_SHORT).show();
            return;
        } else if (!getVideoFileInfo.exists()) {
            Toast.makeText(this, "Video Not Exist", Toast.LENGTH_SHORT).show();
            return;
        }


     //   simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter())), new DefaultLoadControl());
        MediaSource mediaSource = buildMediaSource(Uri.parse(videoPath));
        exoPlayerView.setPlayer(simpleExoPlayer);
        exoPlayerView.setKeepScreenOn(true);
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.addListener(new Player.EventListener() {
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
            }

            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            }

            public void onLoadingChanged(boolean isLoading) {
            }

            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == 2) {
                    progress_bar_exo_player.setVisibility(View.VISIBLE);
                } else if (playbackState == 3) {
                    progress_bar_exo_player.setVisibility(View.GONE);
                }
            }

            public void onRepeatModeChanged(int repeatMode) {
            }

            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
            }

            public void onPlayerError(ExoPlaybackException error) {
            }

            public void onPositionDiscontinuity(int reason) {
            }

            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            }

            public void onSeekProcessed() {
            }
        });

        btFullScreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (flagFullScreen) {
                    btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen));
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    flagFullScreen = false;
                    return;
                }
                btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen_exit));
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                flagFullScreen = true;
            }
        });

        btnDeleteVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!getVideoFileInfo.exists()) {
                    Toast.makeText(VideoPlayerOffline.this, "Video Not Exist", Toast.LENGTH_SHORT).show();
                } else {
                    if (getVideoFileInfo.delete()) {
                        finish();
                        Toast.makeText(VideoPlayerOffline.this, "Video Deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(VideoPlayerOffline.this, "Video Delete Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setCancelable(true);
        LayoutInflater inflater = getLayoutInflater();
        View dialogViewVideoSpeed = inflater.inflate(R.layout.lyt_video_speed, null);
        dialogBuilder.setView(dialogViewVideoSpeed);
        final AlertDialog alertDialogVideoSpeed = dialogBuilder.create();
        alertDialogVideoSpeed.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogViewVideoSpeed.findViewById(R.id.btnSpeedSlow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogVideoSpeed.dismiss();
                PlaybackParameters param = new PlaybackParameters(0.75f, 0.75f);
                simpleExoPlayer.setPlaybackParameters(param);
            }
        });

        dialogViewVideoSpeed.findViewById(R.id.btnSpeedNormal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogVideoSpeed.dismiss();
                PlaybackParameters param = new PlaybackParameters(1f, 1f);
                simpleExoPlayer.setPlaybackParameters(param);

            }
        });

        dialogViewVideoSpeed.findViewById(R.id.btnSpeedFast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogVideoSpeed.dismiss();
                PlaybackParameters param = new PlaybackParameters(1.75f, 1.75f);
                simpleExoPlayer.setPlaybackParameters(param);
            }
        });

        exoPlayerView.findViewById(R.id.btnVideoSpeed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogVideoSpeed.show();
            }
        });


    }


    private MediaSource buildMediaSource(Uri uri){
        return new ExtractorMediaSource(uri, new DefaultDataSourceFactory(this,"ua"), new DefaultExtractorsFactory(),null,null);
    }

/*    private MediaSource buildMediaSourceNew(Uri uri,int buildType){
        DataSource.Factory datasourceFactroy = new DefaultDataSourceFactory(context, Util.getUserAgent(context,"Your App Name"));
        return new ExtractorMediaSource.Factory(datasourceFactroy).createMediaSource(uri);
    }*/

    public void onPause() {
        super.onPause();
        simpleExoPlayer.setPlayWhenReady(false);
        simpleExoPlayer.getPlaybackState();
    }

    @Override
    protected void onDestroy() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
        super.onDestroy();
    }


}
