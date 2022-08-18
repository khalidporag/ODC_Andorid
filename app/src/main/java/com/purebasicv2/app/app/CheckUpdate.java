package com.purebasicv2.app.app;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.purebasicv2.app.CallBacks;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.utils.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckUpdate {

    public CheckUpdate(final Context ctx){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.APP_API+"?mp=[0]", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getBoolean("error")){
                        JSONArray arrayName = obj.getJSONArray("datas");
                        JSONObject jsonObject = arrayName.getJSONObject(0);
                        int force = jsonObject.getInt("forcem");
                        String appVersion = jsonObject.getString("version");
                        final String dlink = jsonObject.getString("dlink");

                        if (!appVersion.equals(CallBacks.appVersionName(ctx))) {
                            AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                            alertDialog.setTitle("New Version Available!");
                            if (force == 1){alertDialog.setCancelable(false);}
                            alertDialog.setMessage("You're App Is Old. Please Update To Newer Version "+ appVersion);
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "UPDATE",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(dlink));
                                            ctx.startActivity(intent);
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
