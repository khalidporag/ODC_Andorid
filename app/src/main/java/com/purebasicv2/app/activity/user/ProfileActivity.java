package com.purebasicv2.app.activity.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.purebasicv2.app.R;
import com.purebasicv2.app.activity.base.BaseActivity;
import com.purebasicv2.app.app.CustomHeaderInt;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.utils.RequestHandler;
import com.purebasicv2.app.utils.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends BaseActivity {

    private ProgressBar proLoad;
    private TextView userStatus,userBatch,userPaid,userDue,userName,userPhone,userEmail,userBmdc,userMedical,userQualification;
    private TextView userGender,userPosition,userBio,userSession,userAddress,userFb;
    private Button btnProfilePay;
    private String batchName,student_id;
    private ImageView profileImageCircle;
    JSONObject profileData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        student_id=String.valueOf(getSharedPrefManager().getUserInfo().getStudentInfo().getStudentId());

        new CustomHeaderInt(this, "Profile");

        profileImageCircle = findViewById(R.id.profileImageCircle);
        proLoad = findViewById(R.id.proProfile);

        userName = findViewById(R.id.userName);
        userStatus = findViewById(R.id.userStatus);

        userPhone = findViewById(R.id.userPhone);
        userEmail = findViewById(R.id.userEmail);

        userBatch = findViewById(R.id.userBatch);
        userPaid = findViewById(R.id.userPaid);
        userDue = findViewById(R.id.userDue);
        btnProfilePay = findViewById(R.id.btnProfilePay);


        userGender = findViewById(R.id.userGender);
        userPosition = findViewById(R.id.userPosition);
        userBio = findViewById(R.id.userBio);
        userSession = findViewById(R.id.userSession);
        userFb = findViewById(R.id.userFb);
        userAddress = findViewById(R.id.userAddress);

        userBmdc = findViewById(R.id.userBmdc);
        userMedical = findViewById(R.id.userMedical);
        userQualification = findViewById(R.id.userQualification);




        findViewById(R.id.btnProfileEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ProfileActivity.this, EditProfileActivity.class);
                i.putExtra("data",profileData.toString());
                startActivity(i);
            }
        });

        btnProfilePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent payIntent = new Intent(ProfileActivity.this, PaymentMethodActivity.class);
                payIntent.putExtra("PAY_USER", SharedPrefManager.getInstance(ProfileActivity.this).getMobile());
                payIntent.putExtra("PAY_SOURCE", 0);
                startActivity(payIntent);
            }
        });

        findViewById(R.id.btnChangeProfilePicture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ProfileActivity.this, ProfilePictureChange.class);
                i.putExtra("data",profileData.toString());
                startActivity(i);
            }
        });
    }

    /*private void parseProfileData() {
        proLoad.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.USER_PROFILE+SharedPrefManager.getInstance(this).getUsernameHash(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getBoolean("error")) {
                        JSONArray arrayName = obj.getJSONArray("datas");
                        JSONObject jsonObject = arrayName.getJSONObject(0);

                        userName.setText(jsonObject.getString("name"));
                        if (jsonObject.getInt("is_approve") == 1){
                            userStatus.setTextColor(getResources().getColor(R.color.greenDeep));
                            userStatus.setText("Approved");
                        } else {
                            userStatus.setText("Pending");
                        }

                        userPhone.setText(jsonObject.getString("mobile"));
                        userEmail.setText(jsonObject.getString("email"));
                        batchName = jsonObject.getString("batch");
                        userBatch.setText(batchName);
                        userPaid.setText(String.format("Paid %s", jsonObject.getString("paid")));

                        if (jsonObject.getDouble("due") > 0){
                            userDue.setText(String.format("Due ৳%s", jsonObject.getString("due")));
                            btnProfilePay.setVisibility(View.VISIBLE);
                        } else {
                            userDue.setVisibility(View.GONE);
                            btnProfilePay.setVisibility(View.GONE);
                        }

                        userGender.setText("Gender: "+jsonObject.getString("gender"));
                        userPosition.setText("Position: "+jsonObject.getString("position"));
                        userBio.setText("Birth Date: "+jsonObject.getString("bio"));
                        userSession.setText("Session: "+jsonObject.getString("session"));
                        userFb.setText("Facebook: "+jsonObject.getString("fb"));
                        userAddress.setText("Address: "+jsonObject.getString("address"));

                        try {
                            Glide.with(ProfileActivity.this).load(jsonObject.getString("photo")).into(profileImageCircle);
                        } catch (Exception e){
                            Toast.makeText(ProfileActivity.this, "Profile Picture Problem", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ProfileActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                proLoad.setVisibility(View.INVISIBLE);
            }
        }, new Response.ErrorListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onErrorResponse(VolleyError error) {
                proLoad.setVisibility(View.INVISIBLE);
                new ErrorMe(ProfileActivity.this, "Something Went Wrong!"+error);
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }*/

    private void parseProfileData() {
        proLoad.setVisibility(View.VISIBLE);
        String api_token=getSharedPrefManager().getUserInfo().getApiToken();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.PROFILE_INFO+student_id+"?api_test=false&api_token="+api_token, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getBoolean("success")) {
                        JSONObject jsonObject = obj.getJSONObject("data");
                        profileData=jsonObject;

                        userName.setText(jsonObject.getString("name"));
                        if (jsonObject.getInt("is_approve") == 1){
                            userStatus.setTextColor(getResources().getColor(R.color.greenDeep));
                            userStatus.setText("Approved");
                        } else {
                            userStatus.setText("Pending");
                        }

                        userPhone.setText(jsonObject.getString("mobile"));
                        userEmail.setText(jsonObject.getString("email"));
                        batchName = jsonObject.getString("batch");
                        userBatch.setText(batchName);
                        userPaid.setText(String.format("Paid %s", jsonObject.getString("taka")));

                        userDue.setVisibility(View.GONE);
                        btnProfilePay.setVisibility(View.GONE);

//                        if (jsonObject.getDouble("due") > 0){
//                            userDue.setText(String.format("Due ৳%s", jsonObject.getString("due")));
//                            btnProfilePay.setVisibility(View.VISIBLE);
//                        } else {
//                            userDue.setVisibility(View.GONE);
//                            btnProfilePay.setVisibility(View.GONE);
//                        }

                        userGender.setText("Gender: "+jsonObject.getString("gender"));
                        userPosition.setText("Position: "+jsonObject.getString("position"));
                        userBio.setText("Birth Date: "+jsonObject.getString("birth"));
                        userSession.setText("Session: "+jsonObject.getString("sessionn"));
                        userFb.setText("Facebook: "+jsonObject.getString("fb"));
                        userAddress.setText("Address: "+jsonObject.getString("address"));
                        userBmdc.setText("BMDC: "+jsonObject.getString("BMDC"));
                        userMedical.setText("Medical: "+jsonObject.getString("medical"));
                        userQualification.setText("Qualification: "+jsonObject.getString("qualification"));
                        try {
                            if (jsonObject.getString("photo")!=null)
                            Glide.with(ProfileActivity.this).load(jsonObject.getString("photo")).into(profileImageCircle);
                        } catch (Exception e){
                            Toast.makeText(ProfileActivity.this, "Profile Picture Problem", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ProfileActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                proLoad.setVisibility(View.INVISIBLE);
            }
        }, new Response.ErrorListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onErrorResponse(VolleyError error) {
                proLoad.setVisibility(View.INVISIBLE);
                new ErrorMe(ProfileActivity.this, "Something Went Wrong!"+error);
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        parseProfileData();
    }
}
