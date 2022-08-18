package com.purebasicv2.app;

import androidx.appcompat.app.*;
import android.content.*;
import android.os.*;
import com.purebasicv2.app.activity.HomeActivity;
import com.purebasicv2.app.activity.base.BaseActivity;
import com.purebasicv2.app.utils.AppConstants;
import com.purebasicv2.app.utils.SharedPrefManager;

public class Splash extends BaseActivity {

    //Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharedPrefManager.getInstance(Splash.this).isLoggedIn()){
                    finish();
                    startActivity(new Intent(Splash.this, HomeActivity.class));
                    return;
                } else {
                    startActivity(new Intent(Splash.this, LoginActivity.class));
                }

            }
        }, 2000);
    }
}


