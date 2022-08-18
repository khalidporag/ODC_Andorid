package com.purebasicv2.app.activity;


import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fenjuly.library.ArrowDownloadButton;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.purebasicv2.app.CallBacks;
import com.purebasicv2.app.R;
import com.purebasicv2.app.app.CustomHeaderInt;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.utils.RequestHandler;
import com.purebasicv2.app.utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LectureViewActivity extends AppCompatActivity {

    private String videoId = null;
    private ProgressDialog progressDialog;
    private ImageView btFullScreen;
    boolean flagFullScreen = false;
    private PlayerView exoPlayerView;
    private ProgressBar progress_bar_exo_player;
    private SimpleExoPlayer simpleExoPlayer;
    private DownloadManager downloadManager;
    private boolean downloading = false;
    private ArrowDownloadButton btnDownloadVideo;
    private MediaSource mediaSource;
    private String getTitle, getVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_view);

        getTitle = getIntent().getStringExtra("LECTURE_TITLE");
        getVideo = getIntent().getStringExtra("LECTURE_LINK");
//        int getId = getIntent().getIntExtra("LECTURE_ID", 0);
        new CustomHeaderInt(this, getTitle);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        ((TextView) findViewById(R.id.tvLectureViewTitle)).setText(getTitle);
//        parseLecture(getId);


        exoPlayerView = (PlayerView) findViewById(R.id.exoPlayerView);
        progress_bar_exo_player = (ProgressBar) findViewById(R.id.progress_bar_exo_player);
        btFullScreen = (ImageView) exoPlayerView.findViewById(R.id.bt_fullscreen);
       // btnDownloadVideo = (ArrowDownloadButton) exoPlayerView.findViewById(R.id.btnDownloadVideo);
        DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        DefaultLoadControl loadControl = new DefaultLoadControl();
        DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(this);

//        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
//                renderersFactory, trackSelector, loadControl);
        exoPlayerView.setPlayer(simpleExoPlayer);
        exoPlayerView.setKeepScreenOn(true);
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
                    exoPlayerView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, CallBacks.dpToPixel(LectureViewActivity.this, 200)));
                    flagFullScreen = false;
                    return;
                }
                btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen_exit));
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                exoPlayerView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                flagFullScreen = true;
            }
        });

        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        btnDownloadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!downloading) {
                    if (videoId != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(LectureViewActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            } else {
                                downloadVideo();
                            }
                        } else {
                            downloadVideo();
                        }
                    } else {
                        Toast.makeText(LectureViewActivity.this, "No Source Found!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        exoPlayerView.findViewById(R.id.btnVideoSpeed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        if (getVideo != null && simpleExoPlayer != null) {
//            mediaSource = ExtractorMediaSource.Factory()
//            mediaSource = new ExtractorMediaSource(Uri.parse(getVideo), new DefaultHttpDataSourceFactory("purebasic_exoplayer_video"), new DefaultExtractorsFactory(), null, null);
//            simpleExoPlayer.prepare(mediaSource);
            // Measures bandwidth during playback. Can be null if not required.
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            // Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                   "purebasic_exoplayer_video", bandwidthMeter);
            // This is the MediaSource representing the media to be played.
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(getVideo));
            // Prepare the player with the source.
            simpleExoPlayer.prepare(videoSource);
            simpleExoPlayer.setPlayWhenReady(true);
        }


    }


    private void parseLecture(int lecture) {
        progressDialog.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.LECTURE_VIEW + SharedPrefManager.getInstance(this).getUsernameHash() + "&lecture=" + lecture, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                JSONArray jsonArray = response.getJSONArray("lecture_data");
                                JSONObject hit = jsonArray.getJSONObject(0);
                                final String pdf = hit.getString("pdf");
                                videoId = hit.getString("video_id");
                                final String links = hit.getString("links");
                                final String v_link = hit.getString("v_link");

                                Log.d("videoId", "ID-1: " + videoId);

                                findViewById(R.id.btnLectureViewPdf).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(pdf)));
                                        } catch (Exception e) {
                                            Toast.makeText(LectureViewActivity.this, "Something is wrong!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                findViewById(R.id.btnLectureViewLink).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(links)));
                                        } catch (Exception e) {
                                            Toast.makeText(LectureViewActivity.this, "Something is wrong!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                findViewById(R.id.btnLectureViewLinkTwo).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(v_link)));
                                        } catch (Exception e) {
                                            Toast.makeText(LectureViewActivity.this, "Something is wrong!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                if (videoId != null && !videoId.isEmpty() && simpleExoPlayer != null) {
                                    mediaSource = new ExtractorMediaSource(Uri.parse(videoId), new DefaultHttpDataSourceFactory("purebasic_exoplayer_video"), new DefaultExtractorsFactory(), null, null);
                                    simpleExoPlayer.prepare(mediaSource);
                                }
                            } else {
                                if (response.getString("error_type").equals("PREMIUM")) {
                                    CallBacks.premiumAlert(LectureViewActivity.this);
                                } else {
                                    CallBacks.showError(LectureViewActivity.this, response.getString("message"), false);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                new ErrorMe(LectureViewActivity.this, "Something Went Wrong!" + error);
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(request);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri, new DefaultDataSourceFactory(this, "ua"), new DefaultExtractorsFactory(), null, null);
    }

/*    private MediaSource buildMediaSourceNew(Uri uri,int buildType){
        DataSource.Factory datasourceFactroy = new DefaultDataSourceFactory(context, Util.getUserAgent(context,"Your App Name"));
        return new ExtractorMediaSource.Factory(datasourceFactroy).createMediaSource(uri);
    }*/


    private void downloadVideo() {
        Log.d("downloadCalled", "downloadVideo: 0000000000");
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(videoId));
        request.setTitle(getTitle);
        request.setDescription("PureBasic Offline Video Download");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Constants.DOWNLOAD_FOLDER, getTitle);
        request.allowScanningByMediaScanner();
        showDownloadProgress(downloadManager.enqueue(request));
    }

    private void showDownloadProgress(final long downloadId) {
        new Thread() {
            @Override
            public void run() {
                do {
                    downloading = true;
                    final Cursor c;
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(downloadId);
                    c = downloadManager.query(query);
                    if (c.moveToFirst()) {
                        final int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));//Get download status
                        final long bytes_downloaded = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                        final long bytes_total = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                        final long download_percentage;
                        if (bytes_total > 0) {
                            download_percentage = (bytes_downloaded * 100L) / bytes_total;
                        } else {
                            download_percentage = 0;
                        }
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                if (status == DownloadManager.STATUS_RUNNING) {
                                    downloading = true;
                                    Log.d("downloading", "run: 333333333333333333333");
                                    btnDownloadVideo.setProgress(download_percentage);
                                } else if (status == DownloadManager.STATUS_FAILED) {
                                    downloading = false;
                                    Log.d("downloading", "run: 000000000000000000000000");
                                    Toast.makeText(LectureViewActivity.this, "Download Failed!", Toast.LENGTH_SHORT).show();
                                    btnDownloadVideo.reset();
                                } else if (status == DownloadManager.STATUS_SUCCESSFUL) {
                                    downloading = false;
                                    Log.d("downloading", "run: 11111111111111111111111111");
                                    btnDownloadVideo.setProgress(100);
                                    btnDownloadVideo.setEnabled(false);
                                    Toast.makeText(LectureViewActivity.this, "Video Downloaded", Toast.LENGTH_SHORT).show();
                                } else {
                                    downloading = true;
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            btnDownloadVideo.startAnimating();
                                            btnDownloadVideo.setEnabled(false);
                                        }
                                    });
                                    Log.d("downloading", "run-start: 444444444444444444");
                                }
                            }
                        });
                    } else {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                downloading = false;
                                btnDownloadVideo.reset();
                                Log.d("downloading", "run: 5555555555555555555555");
                            }
                        });
                    }
                    c.close();
                } while (downloading);
            }
        }.start();
    }


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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    downloadVideo();
                }
            }
        }
    }

}
