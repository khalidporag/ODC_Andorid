package com.purebasicv2.app.activity.user;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;
import com.purebasicv2.app.CallBacks;
import com.purebasicv2.app.R;
import com.purebasicv2.app.SmsBroadcastReceiver;
import com.purebasicv2.app.activity.HomeActivity;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.model.UserData;
import com.purebasicv2.app.utils.AppConstants;
import com.purebasicv2.app.utils.RequestHandler;
import com.purebasicv2.app.utils.SharedPrefManager;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginVerify extends AppCompatActivity implements SmsBroadcastReceiver.SmsBroadcastReceiverListener {

    private Button btnRegSendOTP;
    private boolean otpSend = false;
    private int otpCode = 0;
    private CountDownTimer otpSendCounter;
    private Random randomOtp;
    String user_id, from_where, mobile_no;
    AVLoadingIndicatorView loading;
    SharedPrefManager sharedPrefManager;

    private static final int REQ_USER_CONSENT = 200;
    SmsBroadcastReceiver smsBroadcastReceiver;
    OtpView otpView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_veirfy);

        if (getIntent().hasExtra(AppConstants.USER_ID_TAG))
            user_id = getIntent().getExtras().getString(AppConstants.USER_ID_TAG);
        if (getIntent().hasExtra(AppConstants.FROM_WHERE))
            from_where = getIntent().getExtras().getString(AppConstants.FROM_WHERE);
        if (getIntent().hasExtra(AppConstants.MOBILE))
            mobile_no = getIntent().getExtras().getString(AppConstants.MOBILE);
        Log.d("Volley ", user_id + " " + from_where);

        otpView = findViewById(R.id.otp_view);
        btnRegSendOTP = (Button) findViewById(R.id.btnRegSendOTP);
        TextView tvOTPNumber = findViewById(R.id.tvOTPNumber);
        final TextView otpStatus = findViewById(R.id.tvOtpError);
        loading = findViewById(R.id.avLoadingRegSG1);
        sharedPrefManager = SharedPrefManager.getInstance(this);

        otpSendCounter = new CountDownTimer(30 * 1000, 1000) {
            @Override
            public void onTick(long millis) {
                btnRegSendOTP.setEnabled(false);
                btnRegSendOTP.setText("Resend in " + String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));
            }

            @Override
            public void onFinish() {
                Log.d("otpSendCounter", "onFinish: ");
                btnRegSendOTP.setText("Send OTP Again");
                btnRegSendOTP.setEnabled(true);
                otpSend = false;
            }
        };
        otpSendCounter.start();

        randomOtp = new Random();
        btnRegSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendVerification();
            }
        });

        otpView.setItemCount(4);
        otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                Log.d("onOtpCompleted=>", otp);
                otpVerification(otpView.getText().toString().trim());
            }
        });
        tvOTPNumber.setText(mobile_no);

//        if (getCountry.equals("Bangladesh")){
//            tvOTPNumber.setText(getPhone);
//            sendOtp(getPhone);
//        } else {
//            tvOTPNumber.setText(getEmail);
//            sendMail(getPhone);
//        }

        startSMSListener();

    }


    private void sendOtp(String getPhone) {
        if (!otpSend) {
            otpCode = randomOtp.nextInt(900000 - 100000) + 100000;
            otpSendCounter.start();
            Log.d("otpCode", "Code: " + otpCode);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, CallBacks.otpLink(getPhone, otpCode), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (obj.getInt("status_code") == 200) {
                            otpSend = true;
                            otpSendCounter.start();
                        } else {
                            SweetAlertDialog pDialog = new SweetAlertDialog(LoginVerify.this, SweetAlertDialog.ERROR_TYPE);
                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog.setTitleText(" ");
                            pDialog.setContentText(obj.getString("error_message"));
                            pDialog.setCancelable(true);
                            pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    btnRegSendOTP.setText("Send OTP Again");
                                    btnRegSendOTP.setEnabled(true);
                                    otpSend = false;
                                }
                            });
                            pDialog.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onErrorResponse(VolleyError error) {
                    new ErrorMe(LoginVerify.this, "Something Went Wrong!" + error);
                }
            });
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }
    }


    private void sendMail(String getPhone) {
        if (!otpSend) {
            otpCode = randomOtp.nextInt(900000 - 100000) + 100000;
            otpSendCounter.start();
            Log.d("otpCode", "Code: " + otpCode);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.MAIL_API + "?mobile=" + getPhone + "&code=" + otpCode, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (!obj.getBoolean("error")) {
                            otpSend = true;
                            otpSendCounter.start();
                        } else {
                            SweetAlertDialog pDialog = new SweetAlertDialog(LoginVerify.this, SweetAlertDialog.ERROR_TYPE);
                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog.setTitleText(" ");
                            pDialog.setContentText(obj.getString("message"));
                            pDialog.setCancelable(true);
                            pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    btnRegSendOTP.setText("Send OTP Again");
                                    btnRegSendOTP.setEnabled(true);
                                    otpSend = false;
                                }
                            });
                            pDialog.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onErrorResponse(VolleyError error) {
                    new ErrorMe(LoginVerify.this, "Something Went Wrong!" + error);
                }
            });
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }
    }

    private void otpVerification(final String otp) {
        loading.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.OTP_VERIFICATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("VollySuccess", response);
                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("success")) {
                                try {
                                    JSONObject jsonObject = obj.getJSONObject("data");
                                    Gson gson = new Gson();
                                    Type type = new TypeToken<UserData>() {
                                    }.getType();
                                    UserData loginResponse = gson.fromJson(jsonObject.toString(), type);
                                    System.out.println(loginResponse.getApiToken());
                                    System.out.println(loginResponse.getStudentInfo().toString());
                                    sharedPrefManager.setUserInfo(loginResponse);
                                    sharedPrefManager.userLoginAdd(
                                            loginResponse.getStudentInfo().getId(),
                                            loginResponse.getStudentInfo().getMobile(), loginResponse.getStudentInfo().getPassword(),
                                            true);
                                    System.out.println("apikey "+AppConstants.API_KEY);
                                    AppConstants.API_KEY=loginResponse.getApiKey();
                                    System.out.println("apikey "+AppConstants.API_KEY);
                                    finishAffinity();
                                    startActivity(new Intent(LoginVerify.this, HomeActivity.class));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
//                                            FragmentTransaction fr_left = getFragmentManager().beginTransaction();
//                                            fr_left.setCustomAnimations(R.animator.fr_left, R.animator.fr_right, R.animator.fr_left, R.animator.fr_right);
//                                            fr_left.replace(R.id.fragment_container_sgp, fragment, "GONext");
//                                            fr_left.addToBackStack(null);
//                                            fr_left.commit();
                            } else {
                                SweetAlertDialog pDialog = new SweetAlertDialog(LoginVerify.this, SweetAlertDialog.ERROR_TYPE);
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
                new ErrorMe(LoginVerify.this, "Something Went Wrong!" + error);
            }
        }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> arguments = new HashMap<String, String>();
                arguments.put("otp", otp);
                arguments.put("user_id", user_id);
                arguments.put("from_page", from_where);
                return arguments;
            }
        };
        RequestHandler.getInstance(LoginVerify.this).addToRequestQueue(stringRequest);
    }

    private void resendVerification() {
        loading.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.RESEND_OTP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("VollySuccess", response);
                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("success")) {
                                if (otpSendCounter != null) {
                                    otpSendCounter.cancel();
                                    otpSendCounter.start();
                                }
                                Toast.makeText(LoginVerify.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                            } else {
                                SweetAlertDialog pDialog = new SweetAlertDialog(LoginVerify.this, SweetAlertDialog.ERROR_TYPE);
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
                new ErrorMe(LoginVerify.this, "Something Went Wrong!" + error);
            }
        }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> arguments = new HashMap<String, String>();
                arguments.put("user_id", user_id);
                return arguments;
            }
        };
        RequestHandler.getInstance(LoginVerify.this).addToRequestQueue(stringRequest);
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
                otpView.setText(otp);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }
}