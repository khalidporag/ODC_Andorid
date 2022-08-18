package com.purebasicv2.app.ongoingbatch.batchcontent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.purebasicv2.app.R;
import com.purebasicv2.app.activity.base.BaseActivity;
import com.purebasicv2.app.app.CustomHeaderInt;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.model.CourseContentResponse;
import com.purebasicv2.app.utils.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchCourseContent extends BaseActivity {

    String batch_id="";
    TextView tvTitle;
    RelativeLayout layoutPromoVideo;
    public WebView webview;
    ProgressBar progressbar;
    RecyclerView rvCourseContent;
    Context mContext;
    Button btnEnroll;
    private AdapterCourseContent adapterCourseContent;
    private ArrayList<CourseContentResponse.LectureDetail> lectureDetailArrayList;
    String api_token="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_course_content);

        initView();
        getIntentData();

    }

    private void initView() {
        new CustomHeaderInt(this, "Course Content");
        tvTitle=findViewById(R.id.tvTitle);
        btnEnroll=findViewById(R.id.btnEnroll);
        layoutPromoVideo=findViewById(R.id.layoutPromoVideo);
        webview=findViewById(R.id.myWebView);
        progressbar=findViewById(R.id.progressbar);
        rvCourseContent=findViewById(R.id.rvCourseContent);
        mContext=BatchCourseContent.this;

        btnEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enrollBatch(batch_id);
            }
        });

        rvCourseContent.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        lectureDetailArrayList = new ArrayList<>();
        adapterCourseContent = new AdapterCourseContent(this, lectureDetailArrayList);
        rvCourseContent.setAdapter(adapterCourseContent);

        api_token=getSharedPrefManager().getUserInfo().getApiToken();

    }


    private void getIntentData() {
        try {
            if (getIntent().hasExtra("batch_id")) {
                batch_id = getIntent().getExtras().getString("batch_id");
                getContent(batch_id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    List<String> subjectList=new ArrayList<>();
    List<String> chapList=new ArrayList<>();
    List<String> lecList=new ArrayList<>();
    private void getContent(String batch_id) {
            showDialog();
        String api_token=getSharedPrefManager().getUserInfo().getApiToken();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.COURSE_CONTENT+batch_id+"?api_test=false&api_token="+api_token,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.d("VollySuccess", response);
                                JSONObject obj = new JSONObject(response);
                                if (obj.getBoolean("success")) {
                                    Gson gson = new Gson();
                                    Type listType = new TypeToken<CourseContentResponse>() {}.getType();
                                    CourseContentResponse convertedList = gson.fromJson(response.toString(), listType);
                                    lectureDetailArrayList.addAll(convertedList.getData().getLectureDetails());
                                    setData(convertedList);
                                    adapterCourseContent.notifyDataSetChanged();
                                    hideDialog();

                                } else {
                              //      notifyDataSetChanged();
                                    showError(obj.getString("message"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideDialog();
                  //  notifyDataSetChanged();
                    new ErrorMe(mContext, "Something Went Wrong!" + error);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    return params;
                }
            };
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void setData(CourseContentResponse convertedList) {

        if (convertedList.getData().getBatchInfo().getPromotionVideo()!= null && !convertedList.getData().getBatchInfo().getPromotionVideo().trim().isEmpty()) {
            layoutPromoVideo.setVisibility(View.VISIBLE);
            initWebViewSettings();
            loadUrl(convertedList.getData().getBatchInfo().getPromotionVideo());
        } else {
            layoutPromoVideo.setVisibility(View.GONE);
        }
        tvTitle.setText(convertedList.getData().getBatchInfo().getTitle());

    }


    private void initWebViewSettings() {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setAppCacheMaxSize(0);
        webview.getSettings().setAllowFileAccess(false);
        webview.getSettings().setAppCacheEnabled(false);
        webview.getSettings().setAllowFileAccessFromFileURLs(false);
        webview.getSettings().setAllowUniversalAccessFromFileURLs(false);

        webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                progressbar.setVisibility(View.GONE);
                if (view.getTitle().contentEquals("")) {
                    progressbar.setVisibility(View.VISIBLE);
                    view.reload();
                }
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                // System.out.println("Error " + errorResponse.getData().toString() + " " + request);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                // System.out.println("Error " + error + " " + request);
            }
        });
    }

    private void loadUrl(String url) {
        try {
            // String iframe="<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/5PwU12NfqSE\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";
            String iframe_part1="<iframe width=\"100%\" height=\"100%\"";
            String iframe_part2="src=\""+url+"\" ";
            String iframe_part3="frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";
            String iframe_final=iframe_part1+iframe_part2+iframe_part3;
            webview.loadData(iframe_final, "text/html", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enrollBatch(final String batchId) {
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.REQUEST_ENROLL_BATCH+"&api_token="+api_token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideDialog();
                        try {
                            Log.d("VollySuccess", response);
                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("success")) {
                                showSuccess(obj.getString("message"));
                            } else {
                                showError(obj.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                new ErrorMe(mContext, "Something Went Wrong!" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("student_id", getSharedPrefManager().getUserInfo().getStudentInfo().getStudentId() + "");
                params.put("batch_id", batchId);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}