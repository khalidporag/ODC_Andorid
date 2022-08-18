package com.purebasicv2.app;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.google.android.material.textfield.TextInputLayout;
import com.purebasicv2.app.activity.base.BaseActivity;
import com.purebasicv2.app.activity.forgot_password.ForgotPasswordOtpRequest;
import com.purebasicv2.app.activity.signup.SignUpMain;
import com.purebasicv2.app.activity.user.LoginVerify;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.utils.AppConstants;
import com.purebasicv2.app.utils.RequestHandler;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends BaseActivity {

    private EditText etrLoginPhone, etrLoginPassword;
    private ProgressDialog progressDialog;
    private TextInputLayout tilLoginPhone, tilLoginPassword;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        etrLoginPhone = findViewById(R.id.etrLoginPhone);
        etrLoginPassword = findViewById(R.id.etrLoginPassword);

        tilLoginPhone = findViewById(R.id.tilLoginPhone);
        tilLoginPassword = findViewById(R.id.tilLoginPassword);


        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpMain.class));
            }
        });
        findViewById(R.id.tvForgot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordOtpRequest.class));
            }
        });




//        TextView loginForget = findViewById(R.id.loginForget);
//        loginForget.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this, ForgetPassword.class));
//            }
//        });


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
    }


    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }

    private void userLogin() {

        final String phone = etrLoginPhone.getText().toString().trim();
        final String password = etrLoginPassword.getText().toString().trim();

        if (validatePhone() && validatePassword()) {
            progressDialog.show();
            hideKeyBoard(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.LOGIN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        Log.d("VollySuccess", response);
                        JSONObject obj = new JSONObject(response);
                        if (obj.getBoolean("success")) {
                            JSONObject jsonObject = obj.getJSONObject("data");
                            Intent intent = new Intent(LoginActivity.this, LoginVerify.class);
                            intent.putExtra(AppConstants.USER_ID_TAG, jsonObject.getString("user_id"));
                            intent.putExtra(AppConstants.FROM_WHERE, AppConstants.FROM_LOGIN);
                            intent.putExtra(AppConstants.MOBILE, etrLoginPhone.getText().toString());
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
                    progressDialog.dismiss();
                    new ErrorMe(LoginActivity.this, "Something Went Wrong!" + error);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", phone);
                    params.put("password", password);
                    return params;
                }
            };
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }
    }


    private boolean validatePhone() {
        String input = etrLoginPhone.getText().toString().trim();

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

    private boolean validatePassword() {
        String input = etrLoginPassword.getText().toString().trim();
        if (input.isEmpty()) {
            tilLoginPassword.setError("Password is empty!");
            tilLoginPassword.startAnimation(shakeError());
            return false;
        } else {
            tilLoginPassword.setError(null);
            return true;
        }
    }

    public void showError(String text) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(" ")
                .setContentText(text)
                .show();
    }

}
