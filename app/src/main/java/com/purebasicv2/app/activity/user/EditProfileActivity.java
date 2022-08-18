package com.purebasicv2.app.activity.user;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.purebasicv2.app.R;
import com.purebasicv2.app.activity.base.BaseActivity;
import com.purebasicv2.app.app.CustomHeaderInt;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.utils.RequestHandler;
import com.purebasicv2.app.utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EditProfileActivity extends BaseActivity {

    private TextInputEditText etrRegisterFullName,etrRegisterEmail,etrRegisterMedicalorCollege,etrRegisterMedicalorCollegeBatch
            ,etrRegisterMoblie,etrGender,etrCountry,etrQualification,etrBmdc;
    private TextInputEditText etrRegisterSession,etrRegisterFacebookId,etrRegisterAddress,etrRegisterBioDate;
    private ProgressBar proEditProfile;
    private ProgressDialog progressDialog;
    JSONObject jsonObj;
    String student_id,api_token="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        new CustomHeaderInt(this, "Edit Profile");
        student_id=String.valueOf(getSharedPrefManager().getUserInfo().getStudentInfo().getStudentId());
        api_token=getSharedPrefManager().getUserInfo().getApiToken();

        proEditProfile = findViewById(R.id.proEditProfile);
        etrRegisterFullName = findViewById(R.id.etrRegisterFullName);
        etrRegisterEmail = findViewById(R.id.etrRegisterEmail);
        etrRegisterBioDate = findViewById(R.id.etrRegisterBioDate);
        etrRegisterMedicalorCollege = findViewById(R.id.etrRegisterMedicalorCollege);
        etrRegisterMedicalorCollegeBatch = findViewById(R.id.etrRegisterMedicalorCollegeBatch);
        etrRegisterSession = findViewById(R.id.etrRegisterSession);
        etrRegisterFacebookId = findViewById(R.id.etrRegisterFacebookId);
        etrRegisterAddress = findViewById(R.id.etrRegisterAddress);
        etrBmdc = findViewById(R.id.etrBmdc);

        etrRegisterMoblie = findViewById(R.id.etrRegisterMoblie);
        etrGender = findViewById(R.id.etrGender);
        etrCountry = findViewById(R.id.etrCountry);
        etrQualification = findViewById(R.id.etrQualification);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Updating..");

        if (getIntent().hasExtra("data")){
            try {
                jsonObj = new JSONObject(getIntent().getStringExtra("data"));
                setProfileData(jsonObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
//        else{
//            parseProfileData();
//        }

        findViewById(R.id.btnProfileUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullName =  etrRegisterFullName.getText().toString().trim();
                final String email =  etrRegisterEmail.getText().toString().trim();
                final String mobile =  etrRegisterMoblie.getText().toString().trim();
                final String gender =  etrGender.getText().toString().trim();
                final String medicaldental =  etrRegisterMedicalorCollege.getText().toString().trim();
                final String medicaldentalBatch =  etrRegisterMedicalorCollegeBatch.getText().toString().trim();
                final String qualification =  etrQualification.getText().toString().trim();
                final String session =  etrRegisterSession.getText().toString().trim();
                final String facebook =  etrRegisterFacebookId.getText().toString().trim();
                final String address =  etrRegisterAddress.getText().toString().trim();
                final String bio =  etrRegisterBioDate.getText().toString().trim();
                final String bmdc =  etrBmdc.getText().toString().trim();

                if (vaFullName() && vaEmail() && vaBio() && vaMedicalCollege() && vaMedicalCollegeBatch() && vaSession() && vaFacebook() && vaAddress()){
                    progressDialog.show();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.PROFILE_UPDATE+student_id+"?api_test=false&api_token="+api_token, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getBoolean("success")) {
                                    showSuccess(jsonObject.getString("message"));
                                } else {
                                    showError(jsonObject.getString("message"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                        }
                    },new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.hide();
                            Toast.makeText(EditProfileActivity.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            String api_token=getSharedPrefManager().getUserHash();
                      //      params.put("mobile", SharedPrefManager.getInstance(EditProfileActivity.this).getMobile());
                            params.put("name", fullName);
                            params.put("api_token", api_token);
                            params.put("BMDC", bmdc);
                            params.put("birth", bio);
                            params.put("medical", medicaldental);
                            params.put("batch", medicaldentalBatch);
                            params.put("qualification", qualification);
                            params.put("sessionn", session);
                            params.put("fb", facebook);
                            params.put("gender", gender);
                            params.put("address", address);
                            return params;
                        }
                    };
                    RequestHandler.getInstance(EditProfileActivity.this).addToRequestQueue(stringRequest);
                }
            }
        });
    }

    private void setProfileData(JSONObject jsonObject) {
        try {
            etrRegisterFullName.setText(jsonObject.getString("name"));
            etrRegisterEmail.setText(jsonObject.getString("email"));
            etrRegisterSession.setText(jsonObject.getString("sessionn"));
            etrRegisterMedicalorCollege.setText(jsonObject.getString("medical"));
            etrRegisterMedicalorCollegeBatch.setText(jsonObject.getString("batch"));
            etrRegisterFacebookId.setText(jsonObject.getString("fb"));
            etrRegisterAddress.setText(jsonObject.getString("address"));
            etrRegisterBioDate.setText(jsonObject.getString("birth"));
            etrRegisterMoblie.setText(jsonObject.getString("mobile"));
            etrGender.setText(jsonObject.getString("gender"));
            etrCountry.setText(jsonObject.getString("country"));
            etrQualification.setText(jsonObject.getString("qualification"));
            etrBmdc.setText(jsonObject.getString("BMDC"));

        } catch (JSONException e) {
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
                .setContentText(text)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        finish();
                    }
                })
                .show();
    }

    private void parseProfileData() {
        proEditProfile.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.USER_PROFILE+ SharedPrefManager.getInstance(this).getUsernameHash(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getBoolean("error")) {
                        JSONArray arrayName = obj.getJSONArray("datas");
                        JSONObject jsonObject = arrayName.getJSONObject(0);

                        etrRegisterFullName.setText(jsonObject.getString("name"));
                        etrRegisterEmail.setText(jsonObject.getString("email"));
                        etrRegisterSession.setText(jsonObject.getString("session"));
                        etrRegisterMedicalorCollege.setText(jsonObject.getString("medical"));
                        etrRegisterMedicalorCollegeBatch.setText(jsonObject.getString("medical_batch"));
                        etrRegisterFacebookId.setText(jsonObject.getString("fb"));
                        etrRegisterAddress.setText(jsonObject.getString("address"));
                        etrRegisterBioDate.setText(jsonObject.getString("bio"));

                    } else {
                        Toast.makeText(EditProfileActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                proEditProfile.setVisibility(View.INVISIBLE);
            }
        }, new Response.ErrorListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onErrorResponse(VolleyError error) {
                proEditProfile.setVisibility(View.INVISIBLE);
                new ErrorMe(EditProfileActivity.this, "Something Went Wrong!"+error);
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private boolean vaFullName() {
        String input = etrRegisterFullName.getText().toString().trim();
        if (input.isEmpty()) {
            etrRegisterFullName.setError("Full Name is empty!");
            return false;
        }  else {
            etrRegisterFullName.setError(null);
            return true;
        }
    }

    private boolean vaEmail() {
        String input = etrRegisterEmail.getText().toString().trim();
        if (input.isEmpty()) {
            etrRegisterEmail.setError("Email is empty!");
            return false;
        }  else {
            etrRegisterEmail.setError(null);
            return true;
        }
    }

    private boolean vaBio() {
        String input = etrRegisterBioDate.getText().toString().trim();
        if (input.isEmpty()) {
            etrRegisterBioDate.setError("Date of Birth is empty!");
            return false;
        }  else {
            etrRegisterBioDate.setError(null);
            return true;
        }
    }


    private boolean vaMedicalCollege() {
        String input = etrRegisterMedicalorCollege.getText().toString().trim();
        if (input.isEmpty()) {
            etrRegisterMedicalorCollege.setError("Empty!");
            return false;
        }  else {
            etrRegisterMedicalorCollege.setError(null);
            return true;
        }
    }

    private boolean vaMedicalCollegeBatch() {
        String input = etrRegisterMedicalorCollegeBatch.getText().toString().trim();
        if (input.isEmpty()) {
            etrRegisterMedicalorCollegeBatch.setError("Empty!");
            return false;
        }  else {
            etrRegisterMedicalorCollegeBatch.setError(null);
            return true;
        }
    }

    private boolean vaSession() {
        String input = etrRegisterSession.getText().toString().trim();
        if (input.isEmpty()) {
            etrRegisterSession.setError("Session is empty!");
            return false;
        }  else {
            etrRegisterSession.setError(null);
            return true;
        }
    }

    private boolean vaFacebook() {
        String input = etrRegisterFacebookId.getText().toString().trim();
        if (input.isEmpty()) {
            etrRegisterFacebookId.setError("Facebook ID is empty!");
            return false;
        }  else {
            etrRegisterFacebookId.setError(null);
            return true;
        }
    }

    private boolean vaAddress() {
        String input = etrRegisterAddress.getText().toString().trim();
        if (input.isEmpty()) {
            etrRegisterAddress.setError("Address is empty!");
            return false;
        }  else {
            etrRegisterAddress.setError(null);
            return true;
        }
    }
}