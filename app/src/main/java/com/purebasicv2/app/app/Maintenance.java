package com.purebasicv2.app.app;


import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.utils.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class Maintenance {
    public Maintenance(final Context ctx){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.APP_API+"?mp=[0]"+"&column=maintance", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getBoolean("error")){
                        if (Integer.parseInt(obj.getString("maintance")) == 1 && Integer.parseInt(obj.getString("maintance")) != 0) {
                            AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                            alertDialog.setTitle(" ");
                            alertDialog.setCancelable(false);
                            alertDialog.setMessage("App Under Maintenance. We will be back soon");
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "CLOSE",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            //((Activity) mCtx).finish();
                                            System.exit(0);
                                        }
                                    });
                            alertDialog.show();
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
                Toast.makeText(ctx, "Connection Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestHandler.getInstance(ctx).addToRequestQueue(stringRequest);
    }
}
