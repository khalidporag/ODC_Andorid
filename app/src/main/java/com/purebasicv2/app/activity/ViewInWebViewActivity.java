package com.purebasicv2.app.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.purebasicv2.app.R;
import com.purebasicv2.app.activity.base.BaseActivity;
import com.purebasicv2.app.app.CustomHeaderInt;


/**
 * Created by Naim on 7/2/2018.
 */

public class ViewInWebViewActivity extends BaseActivity {
    Toolbar toolbar;
    TextView title;
    private String policyType = "0";
    private String fileName = "";
    private String fileUrl = "";
    WebView webview;
    ProgressBar progressBar;
    RelativeLayout rlAppbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_webview);


        try {
            policyType = getIntent().getExtras().getString("nType");
            fileName = getIntent().getExtras().getString("nTitle");
            fileUrl = getIntent().getExtras().getString("nPath");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initToolBar(fileName);
        initComponents();
        initWebViewSettings();
        loadUrl(fileUrl, policyType);


        // webview.loadData(myHtmlString, "text/html", "UTF-8");
    }


    private void initToolBar(String subject) {
            new CustomHeaderInt(this, subject);
    }

    private void initComponents() {
        webview = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        rlAppbar = (RelativeLayout) findViewById(R.id.id2);
        //initFont();
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
                progressBar.setVisibility(View.GONE);
                if (view.getTitle().contentEquals("")) {
                    progressBar.setVisibility(View.VISIBLE);
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

    private void loadUrl(String url, String type) {
        try {
            if (type.contentEquals("pdf") || type.contentEquals("doc") || type.contentEquals("docx")) {
                webview.loadUrl(url);
            }
            else if (type.contentEquals("video")){
               // String iframe="<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/5PwU12NfqSE\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";
                rlAppbar.setVisibility(View.GONE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                webview.loadData(url, "text/html", null);
            }
            else {
                webview.loadUrl(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
