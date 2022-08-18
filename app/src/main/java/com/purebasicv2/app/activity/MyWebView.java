package com.purebasicv2.app.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

import com.purebasicv2.app.R;
import com.purebasicv2.app.app.CustomHeaderInt;
import com.wang.avi.AVLoadingIndicatorView;

public class MyWebView extends AppCompatActivity {

    private WebView myWeb;
    private AVLoadingIndicatorView avLoading;

    @SuppressLint({"SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);

        String pageName = getIntent().getStringExtra("PAGE_NAME");
        String pageUrl = getIntent().getStringExtra("PAGE_URL");

        new CustomHeaderInt(this, pageName);


        avLoading = findViewById(R.id.avLoading);

        myWeb = findViewById(R.id.myWebView);
        WebSettings mWebSettings = myWeb.getSettings();
        mWebSettings.setDefaultTextEncodingName("utf-8");
        myWeb.getSettings().setAllowFileAccess( true );
        myWeb.getSettings().setAppCacheEnabled( true );
        myWeb.getSettings().setJavaScriptEnabled( true );
        myWeb.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        myWeb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //myWeb.addJavascriptInterface(new WebAppInterface(MyWebView.this), "Android");
        //mWebSettings.setSavePassword(true);
        //mWebSettings.setEnableSmoothTransition(true);
        mWebSettings.setLoadWithOverviewMode(true);
        myWeb.loadUrl(pageUrl);
        mWebSettings.setSupportZoom(false);
        mWebSettings.setBuiltInZoomControls(false);
        mWebSettings.setDisplayZoomControls(false);
        myWeb.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT );


        myWeb.setWebChromeClient(new WebChromeClient());
        myWeb.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView mview, String url, Bitmap favicon) {
                super.onPageStarted(mview, url, favicon);
                avLoading.setVisibility(View.VISIBLE);
            }
            public void onPageFinished(WebView mview, String url) {
                avLoading.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView mview, int errorCode, String description, String failingUrl) {
                avLoading.setVisibility(View.GONE);
                try {
                    mview.stopLoading();
                } catch (Exception ignored) {
                }
                try {
                    mview.clearView();
                } catch (Exception ignored) {
                }
                View lyt_failed = findViewById(R.id.lyt_failed);
                myWeb.setVisibility(View.GONE);
                lyt_failed.setVisibility(View.VISIBLE);
                super.onReceivedError(mview, errorCode, description, failingUrl);
            }
        });

    }

}

