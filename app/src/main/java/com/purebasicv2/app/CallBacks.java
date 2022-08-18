package com.purebasicv2.app;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import com.purebasicv2.app.activity.MyWebView;
import com.purebasicv2.app.activity.user.PaymentMethodActivity;
import com.purebasicv2.app.utils.SharedPrefManager;


import cn.pedant.SweetAlert.SweetAlertDialog;

public class CallBacks {



    public static String otpLink(String mobile, int msg){
        return "https://smsplus.sslwireless.com/api/v3/send-sms?api_token=PUREBASIC-e245d74c-71fa-41b6-91d2-e4dfb175027e&sid=PUREBASICNON&sms=Verification%20Code%20"+msg+"&msisdn="+mobile+"&csms_id=12345678";
    }

    public static void checkLogIn(Context context){
        if (!SharedPrefManager.getInstance(context).isLoggedIn()) {
            ((Activity) context).finishAffinity();
            context.startActivity(new Intent(context, LoginActivity.class));
            return;
        }
    }

    public static void openMyWebView(Context context, String name, String url){
        context.startActivity(new Intent(context, MyWebView.class).putExtra("PAGE_NAME",  name).putExtra("PAGE_URL", url));
    }

    public static int dpToPixel(Context context, int dpAmount){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpAmount, context.getResources().getDisplayMetrics());
    }

    public static void showError(final Context context, String text, boolean cancelable){
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        sweetAlertDialog.setTitleText(" ");
        sweetAlertDialog.setContentText(text);
        sweetAlertDialog.setCancelable(cancelable);
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                ((Activity) context).finish();
            }
        });
        sweetAlertDialog.show();
    }

    public static void premiumAlert(final Context context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setCancelable(false);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.lyt_premium_alert, null);
        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        dialogView.findViewById(R.id.alertClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                ((Activity) context).finish();
            }
        });

        dialogView.findViewById(R.id.gotoPayment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                ((Activity) context).finish();
                Intent payIntent = new Intent(context, PaymentMethodActivity.class);
                payIntent.putExtra("PAY_USER", SharedPrefManager.getInstance(context).getMobile());
                payIntent.putExtra("PAY_SOURCE", 1);
                context.startActivity(payIntent);
            }
        });
    }


    public static String appVersionName(Context mCtx){
        PackageInfo pinfo = null;
        try {
            pinfo = mCtx.getPackageManager().getPackageInfo(mCtx.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pinfo.versionName;
    }

    public static void rateUs(Context mCtx){
        try{
            mCtx.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id="+mCtx.getPackageName())));
        }
        catch (ActivityNotFoundException e){
            mCtx.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id="+mCtx.getPackageName())));
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }


}