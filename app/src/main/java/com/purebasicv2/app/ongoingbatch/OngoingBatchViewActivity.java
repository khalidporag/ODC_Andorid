package com.purebasicv2.app.ongoingbatch;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.purebasicv2.app.R;
import com.purebasicv2.app.activity.base.BaseActivity;
import com.purebasicv2.app.activity.user.PaymentActivity;
import com.purebasicv2.app.app.CustomHeaderInt;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.model.OngoingBatch;
import com.purebasicv2.app.model.QuestionList;
import com.purebasicv2.app.ongoingbatch.batchcontent.BatchCourseContent;
import com.purebasicv2.app.utils.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OngoingBatchViewActivity extends BaseActivity implements OngoingBatchAdapter.OngoingBatchAdapterListener {

    private RecyclerView rvOngoing;
    private OngoingBatchAdapter ongoingBatchAdapter;
    private ArrayList<OngoingBatch> ongoingBatchArrayList;
    private int pageIndex = 0;
    private boolean isAutoLoad = true;
    private ProgressBar proLoadMoreRC;
    private TextView tvNoData;
    private ProgressDialog progressDialog;
    private OngoingBatch ongoingBatch = new OngoingBatch();
    String  api_token="";
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoing_batch);
        new CustomHeaderInt(this, "Ongoing Batches");
        tvNoData = findViewById(R.id.tvNoData);
        rvOngoing = findViewById(R.id.rvOngoingBatch);
        rvOngoing.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ongoingBatchArrayList = new ArrayList<>();
        ongoingBatchAdapter = new OngoingBatchAdapter(this, ongoingBatchArrayList, this);
        rvOngoing.setAdapter(ongoingBatchAdapter);
        api_token=getSharedPrefManager().getUserInfo().getApiToken();

        getOngoingBatch();
    }

    private void getOngoingBatch() {
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.ONGOING_BATCH+"&api_token="+api_token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("VollySuccess", response);
                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("success")) {
                                JSONArray jsonArray = obj.getJSONArray("data");

                                Gson gson = new Gson();
                                Type type = new TypeToken<ArrayList<OngoingBatch>>() {
                                }.getType();
                                ArrayList<OngoingBatch> convertedList = gson.fromJson(jsonArray.toString(), type);
                                ongoingBatchArrayList.clear();
                                ongoingBatchArrayList.addAll(convertedList);
                                notifyDataSetChanged();
                                hideDialog();
                            } else {
                                notifyDataSetChanged();
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
                notifyDataSetChanged();
                new ErrorMe(OngoingBatchViewActivity.this, "Something Went Wrong!" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onBatchEnrolledClick(final OngoingBatch batch) {
        try {
            this.ongoingBatch = batch;
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle("Enroll")
                    .setMessage("Are you sure you want to enroll the batch?")
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            enrollBatch(batch.batch_id + "");
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCourseContentClick(OngoingBatch ongoingBatch) {
        Intent intent = new Intent(this, BatchCourseContent.class);
        intent.putExtra("batch_id", ongoingBatch.batch_id + "");
        startActivity(intent);
    }

    private void notifyDataSetChanged() {
        ongoingBatchAdapter.notifyDataSetChanged();
        if (ongoingBatchArrayList.size() > 0) {
            tvNoData.setVisibility(View.GONE);
        } else {
            tvNoData.setVisibility(View.VISIBLE);
        }
    }

    private void enrollBatch(final String batchId) {
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.REQUEST_ENROLL_BATCH+"&api_token="+api_token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideDialog();
                        try {
                            Log.d("VollySuccess", response);
                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("success")) {
                                showSuccess(obj.getString("message"));
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
                new ErrorMe(OngoingBatchViewActivity.this, "Something Went Wrong!" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("student_id", getSharedPrefManager().getUserInfo().getStudentInfo().getStudentId() + "");
                params.put("batch_id", batchId);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    protected void navigateToPaymentActivity() {
        super.navigateToPaymentActivity();
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra("batch_id", ongoingBatch.batch_id + "");
        intent.putExtra("batch_name", ongoingBatch.title + "");
        startActivity(intent);
        finish();
        System.out.println("PAYMENT");
    }
}
