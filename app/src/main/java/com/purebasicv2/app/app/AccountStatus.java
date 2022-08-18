package com.purebasicv2.app.app;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purebasicv2.app.R;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.utils.RequestHandler;
import com.purebasicv2.app.utils.SharedPrefManager;
import cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountStatus {
    public AccountStatus(final Context ctx){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.API_USER_DATA+ SharedPrefManager.getInstance(ctx).getUsernameHash()+"&column=status", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getBoolean("error")){
                        if (Integer.parseInt(obj.getString("status")) == 0) {
                                final Dialog dialog = new Dialog(ctx);
                                dialog.setCancelable(false);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.block_layout);
                                dialog.show();
                        } else if (Integer.parseInt(obj.getString("status")) == 3) {
                            SweetAlertDialog pDialog = new SweetAlertDialog(ctx, SweetAlertDialog.WARNING_TYPE);
                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog.setTitleText("Account In-Active!");
                            pDialog.setContentText("We're Reviewing Your Account");
                            pDialog.setCancelable(false);
                            pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    System.exit(0);
                                }
                            });
                            pDialog.show();
                        }
                    } else {
                        Toast.makeText(ctx, obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new ErrorMe(ctx, error.getMessage());
            }
        });
        RequestHandler.getInstance(ctx).addToRequestQueue(stringRequest);
    }
}
