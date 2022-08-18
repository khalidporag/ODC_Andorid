package com.purebasicv2.app.activity.forgot_password;

import androidx.annotation.Nullable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.material.textfield.TextInputLayout;
import com.purebasicv2.app.LoginActivity;
import com.purebasicv2.app.R;
import com.purebasicv2.app.SmsBroadcastReceiver;
import com.purebasicv2.app.activity.base.BaseActivity;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.utils.AppConstants;
import com.purebasicv2.app.utils.RequestHandler;
import com.purebasicv2.app.utils.SharedPrefManager;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ResetPassword extends BaseActivity implements SmsBroadcastReceiver.SmsBroadcastReceiverListener{

    EditText etrResetPassword,etrOtp;
    Button btnResetPass;
    AVLoadingIndicatorView loading;
    SharedPrefManager sharedPrefManager;
    private TextInputLayout tilOtp,tilResetPassword;
    Context mContext;
    String user_id;
    private static final int REQ_USER_CONSENT = 200;
    SmsBroadcastReceiver smsBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        if (getIntent().hasExtra(AppConstants.USER_ID_TAG))
            user_id = getIntent().getExtras().getString(AppConstants.USER_ID_TAG);

        etrResetPassword = findViewById(R.id.etrResetPassword);
        etrOtp = findViewById(R.id.etrOtp);
        btnResetPass = findViewById(R.id.btnResetPass);
        loading = findViewById(R.id.avLoading);
        sharedPrefManager = SharedPrefManager.getInstance(this);
        tilOtp = findViewById(R.id.tilOtp);
        tilResetPassword = findViewById(R.id.tilResetPassword);
        mContext=ResetPassword.this;

        startSMSListener();
        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

    }

    private boolean validateOtp(){
         String otp=etrOtp.getText().toString().trim();

         if (otp.isEmpty()){
             tilOtp.setError("OTP is empty!");
             tilOtp.startAnimation(shakeError());
             return false;
         }else {
             tilResetPassword.setError(null);
             return true;
         }
    }
    private boolean validatePassword() {
        String input = etrResetPassword.getText().toString().trim();
        if (input.isEmpty()) {
            tilResetPassword.setError("Password is empty!");
            tilResetPassword.startAnimation(shakeError());
            return false;
        } else {
            tilResetPassword.setError(null);
            return true;
        }
    }
    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }

    private void resetPassword() {
        final String otp=etrOtp.getText().toString().trim();
        final String newPassword=etrResetPassword.getText().toString().trim();
        if (validateOtp() && validatePassword()) {
            loading.setVisibility(View.VISIBLE);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.RESET_PASS,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.d("VollySuccess", response);
                                JSONObject obj = new JSONObject(response);
                                if (obj.getBoolean("success")) {

                                        finishAffinity();
                                        startActivity(new Intent(mContext, LoginActivity.class));


                                } else {
                                    SweetAlertDialog pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE);
                                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                    pDialog.setTitleText(" ");
                                    pDialog.setContentText(obj.getString("message"));
                                    pDialog.setCancelable(true);
                                    pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                        }
                                    });
                                    pDialog.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("VollyError", e.getMessage());
                            }
                            loading.setVisibility(View.INVISIBLE);
                        }
                    }, new Response.ErrorListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onErrorResponse(VolleyError error) {
                    loading.setVisibility(View.INVISIBLE);
                    new ErrorMe(mContext, "Something Went Wrong!" + error);
                }
            }

            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> arguments = new HashMap<String, String>();
                    arguments.put("otp", otp);
                    arguments.put("user_id", user_id);
                    arguments.put("new_password", newPassword);
                    return arguments;
                }
            };
            RequestHandler.getInstance(mContext).addToRequestQueue(stringRequest);
        }
    }

    public void startSMSListener() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        client.startSmsUserConsent(null);
    }

    @Override
    public void onSuccess(Intent intent) {

    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_USER_CONSENT){

            if ((resultCode == RESULT_OK) && (data != null)){

                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                getOtpFromMessage(message);


            }


        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(smsBroadcastReceiver);
    }

    private void getOtpFromMessage(String message) {

        Pattern otpPattern = Pattern.compile("-?\\d+");
        Matcher matcher = otpPattern.matcher(message);
        while (matcher.find()) {
            try {
                String otp=matcher.group();
                etrOtp.setText(otp);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }
    private void registerBroadcastReceiver(){

        smsBroadcastReceiver = new SmsBroadcastReceiver();

        smsBroadcastReceiver.smsBroadcastReceiverListener = new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
            @Override
            public void onSuccess(Intent intent) {

                startActivityForResult(intent,REQ_USER_CONSENT);

            }

            @Override
            public void onFailure() {

            }
        };

        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsBroadcastReceiver,intentFilter);

    }
}