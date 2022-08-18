package com.purebasicv2.app.activity.forgot_password;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;
import com.purebasicv2.app.R;
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

public class ForgotPasswordOtpRequest extends BaseActivity {

    EditText etrResetPhone;
    Button btnResetOTP;
    AVLoadingIndicatorView loading;
    SharedPrefManager sharedPrefManager;
    private TextInputLayout tilLoginPhone;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_otp_request);

        etrResetPhone = findViewById(R.id.etrResetPhone);
        btnResetOTP = (Button) findViewById(R.id.btnResetOTP);
        loading = findViewById(R.id.avLoadingRegSG1);
        sharedPrefManager = SharedPrefManager.getInstance(this);
        tilLoginPhone = findViewById(R.id.tilLoginPhone);
        mContext=ForgotPasswordOtpRequest.this;


        btnResetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetOtpRequest();
            }
        });
    }


    private void resetOtpRequest() {
        final String phone = etrResetPhone.getText().toString().trim();
        if (validatePhone()){
            showDialog();
            hideKeyBoard(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.FORGOT_PASS_OTP_REQ, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    hideDialog();
                    try {
                        Log.d("VollySuccess", response);
                        JSONObject obj = new JSONObject(response);
                        if (obj.getBoolean("success")) {
                            JSONObject jsonObject = obj.getJSONObject("data");
                            Intent intent = new Intent(mContext, ResetPassword.class);
                            intent.putExtra(AppConstants.USER_ID_TAG, jsonObject.getString("user_id"));
                            startActivity(intent);
                            finish();
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
                    params.put("email_or_mobile", phone);
                    return params;
                }
            };
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }
    }

    private boolean validatePhone() {
        String input = etrResetPhone.getText().toString().trim();

        if (input.isEmpty()) {
            tilLoginPhone.setError("Phone No is empty!");
            return false;
        } else if (input.length() < 5) {
            tilLoginPhone.setError("Invalid phone number!");
            return false;
        } else {
            tilLoginPhone.setError(null);
            return true;
        }
    }

}