package com.purebasicv2.app.activity.signup;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.*;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purebasicv2.app.CallBacks;
import com.purebasicv2.app.app.ErrorMe;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import org.json.JSONException;
import org.json.JSONObject;

import com.purebasicv2.app.R;
import com.purebasicv2.app.utils.RequestHandler;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class SGP_2_abandoned extends Fragment {


    private Button btnRegSendOTP;
    private boolean otpSend=false;
    private int otpCode=0;
    private CountDownTimer otpSendCounter;
    private Random randomOtp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root_view = inflater.inflate(R.layout.sgp_2, container, false);

        Bundle arguments = getArguments();
        final String getPhone = arguments.getString("R_PHONE");
        final String getPassword = arguments.getString("R_PASSWORD");

        root_view.findViewById(R.id.btnBack).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });


        OtpView otpView = root_view.findViewById(R.id.otp_view);
        btnRegSendOTP = (Button) root_view.findViewById(R.id.btnRegSendOTP);
        TextView tvOTPNumber = root_view.findViewById(R.id.tvOTPNumber);
        final TextView otpStatus = root_view.findViewById(R.id.tvOtpError);
        tvOTPNumber.setText(getPhone);

        otpSendCounter = new CountDownTimer(120 * 1000, 1000) {
            @Override
            public void onTick(long millis) {
                btnRegSendOTP.setEnabled(false);
                btnRegSendOTP.setText("Resend in "+String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));
            }

            @Override
            public void onFinish() {
                Log.d("otpSendCounter", "onFinish: ");
                btnRegSendOTP.setText("Send OTP Again");
                btnRegSendOTP.setEnabled(true);
                otpSend = false;
            }
        };

        randomOtp = new Random();
        btnRegSendOTP.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                sendOtp(getPhone);
            }
        });

        otpView.setItemCount(6);
        otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                Log.d("onOtpCompleted=>", otp);
                if (otpCode == Integer.parseInt(otp)){
                    otpStatus.setVisibility(View.INVISIBLE);

                    final SGP_3 fragment = new SGP_3();
                    Bundle arguments = new Bundle();
                    arguments.putString( "R_PHONE", getPhone);
                    arguments.putString( "R_PASSWORD", getPassword);
                    arguments.putInt( "R_OTP", otpCode);
                    fragment.setArguments(arguments);

                    FragmentTransaction fr_left = getFragmentManager().beginTransaction();
                    fr_left.setCustomAnimations(R.animator.fr_left, R.animator.fr_right, R.animator.fr_left, R.animator.fr_right);
                    fr_left.replace(R.id.fragment_container_sgp, fragment, "GONext");
                    fr_left.addToBackStack(null);
                    fr_left.commit();
                } else {
                    otpStatus.setVisibility(View.VISIBLE);
                }
            }
        });

        sendOtp(getPhone);

        return root_view;
    }

    private void sendOtp(String getPhone){
        if (!otpSend){
            otpCode = randomOtp.nextInt(900000 - 100000) + 100000;
            otpSendCounter.start();
            Log.d("otpCode", "Code: "+otpCode);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, CallBacks.otpLink(getPhone, otpCode), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (obj.getInt("status_code") == 200) {
                            otpSend = true;
                            otpSendCounter.start();
                        } else {
                            SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE);
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
                    new ErrorMe(getActivity(), "Something Went Wrong!"+error);
                }
            });
            RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
        }
    }

}