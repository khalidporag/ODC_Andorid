package com.purebasicv2.app.activity.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.purebasicv2.app.R;
import com.purebasicv2.app.activity.base.BaseActivity;
import com.purebasicv2.app.app.CustomHeaderInt;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.utils.RequestHandler;
import com.purebasicv2.app.utils.SharedPrefManager;
import com.purebasicv2.app.utils.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProfilePictureChange extends BaseActivity {

    private ImageView upload;
    private Bitmap screenshotGet;
    private ProgressDialog progressDialog;
    private String user,student_id;
    JSONObject jsonObj;
    String fullName,bio,session,facebook,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture_change);
        student_id=String.valueOf(getSharedPrefManager().getUserInfo().getStudentInfo().getStudentId());

        new CustomHeaderInt(this, getString(R.string.change_picture));

        if (SharedPrefManager.getInstance(this).isLoggedIn()){
            user =SharedPrefManager.getInstance(this).getMobile();
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Uploading Picture..");
            upload = findViewById(R.id.imgUploadProfileImage);
            Button btnChangeProfilePicture = (Button) findViewById(R.id.btnChangeProfilePicture);
            btnChangeProfilePicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (screenshotGet != null) {
                        uploadBitmap(screenshotGet);
                    } else {
                        Toast.makeText(ProfilePictureChange.this, "Select Profile Image!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Profile Image!"), 100);
                }
            });
        } else {
            finish();
        }

        if (getIntent().hasExtra("data")){
            try {
                jsonObj = new JSONObject(getIntent().getStringExtra("data"));
                fullName=jsonObj.getString("name");
                bio=jsonObj.getString("birth");
                facebook=jsonObj.getString("fb");
                session=jsonObj.getString("sessionn");
                address=jsonObj.getString("address");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    private void uploadBitmap(final Bitmap screenshot) {
        String api_token=getSharedPrefManager().getUserInfo().getApiToken();

        progressDialog.show();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Constants.PROFILE_UPDATE+student_id+"?api_test=false&api_token="+api_token, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                try {
                    JSONObject obj = new JSONObject(new String(response.data));
                    Log.d("6565", "" + response);
                    if (obj.getBoolean("success")) {
                        if(!((Activity) ProfilePictureChange.this).isFinishing()) {
                            new SweetAlertDialog(ProfilePictureChange.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText(" ")
                                    .setContentText(obj.getString("message"))
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                          //  startActivity(new Intent(ProfilePictureChange.this, ProfileActivity.class));
                                            finish();
                                        }
                                    })
                                    .show();
                        }
                    } else {
                        Toast.makeText(ProfilePictureChange.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "s" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String api_token=getSharedPrefManager().getUserHash();
                params.put("name", fullName);
                params.put("api_token", api_token);
//                params.put("birth", bio);
//                params.put("sessionn", session);
//                params.put("fb", facebook);
//                params.put("address", address);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                if (screenshot != null) {
                    params.put("photo", new DataPart("avatar_" + user+".jpeg", getFileDataFromDrawable(screenshot)));
                }
                return params;
            }
        };
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(volleyMultipartRequest);
    }

    

    private byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap mb = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                screenshotGet = sampleResize(mb);
                upload.setImageBitmap(screenshotGet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap sampleResize(Bitmap btm) {
        return Bitmap.createScaledBitmap(btm, 450, 450, false);
    }

    
}