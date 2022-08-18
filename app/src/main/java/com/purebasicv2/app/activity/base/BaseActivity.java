package com.purebasicv2.app.activity.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.purebasicv2.app.R;
import com.purebasicv2.app.utils.SharedPrefManager;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BaseActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatusBar();
        sharedPrefManager = SharedPrefManager.getInstance(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
    }

    public static void hideKeyBoard(Activity context) {
        try {
            View view = context.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showError(String text) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(" ")
                .setContentText(text)
                .show();
    }

    public void showSuccess(String text) {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(" ")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        navigateToPaymentActivity();
                        sweetAlertDialog.dismiss();
                    }
                })
                .setContentText(text)
                .show();
    }

    public SharedPrefManager getSharedPrefManager() {
        return sharedPrefManager;
    }

    protected void showDialog() {
        progressDialog.show();
    }

    protected void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
        }
    }

    protected void navigateToPaymentActivity() {

    }
}
