package com.purebasicv2.app.activity.user;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.purebasicv2.app.R;
import com.purebasicv2.app.activity.base.BaseActivity;
import com.purebasicv2.app.adapter.PaymentHistoryAdapter;
import com.purebasicv2.app.app.CustomHeaderInt;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.model.PaymentHistoryResponse;
import com.purebasicv2.app.utils.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PaymentHistoryActivity extends BaseActivity implements PaymentHistoryAdapter.OnItemClickListener{


    private RecyclerView rvPayment;
    private PaymentHistoryAdapter paymentHistoryAdapter;
    private ArrayList<PaymentHistoryResponse.Data> paymentHistoryResponse;
    private TextView tvNoData;
    String student_id;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        initView();
    }

    private void initView() {

        student_id=String.valueOf(getSharedPrefManager().getUserInfo().getStudentInfo().getStudentId());

        new CustomHeaderInt(this, "Payment History");

        showDialog();

        paymentHistoryResponse = new ArrayList<>();
        tvNoData = findViewById(R.id.tvNoData);
        rvPayment = findViewById(R.id.rvPaymentHistory);

        rvPayment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        paymentHistoryAdapter = new PaymentHistoryAdapter(PaymentHistoryActivity.this, paymentHistoryResponse, PaymentHistoryActivity.this);
        rvPayment.setAdapter(paymentHistoryAdapter);
        loadPaymentHistory();
    }

    private void loadPaymentHistory() {
        String api_token=getSharedPrefManager().getUserInfo().getApiToken();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.PAYMENT_HISTORY+"&student_id="+student_id+"&api_token="+api_token, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                Gson gson = new Gson();
                                Type listType = new TypeToken<PaymentHistoryResponse>() {
                                }.getType();
                                PaymentHistoryResponse convertedList = gson.fromJson(response.toString(), listType);
                                paymentHistoryResponse.clear();
                                paymentHistoryResponse.addAll(convertedList.getData());
                                paymentHistoryAdapter.notifyDataSetChanged();

                            } else {
                                tvNoData.setVisibility(View.VISIBLE);
                                tvNoData.setText("No Data Found");
                            }

                            if (paymentHistoryResponse.size() <= 0){
                                tvNoData.setVisibility(View.VISIBLE);
                                tvNoData.setText("No Data Found");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        hideDialog();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                new ErrorMe(PaymentHistoryActivity.this, "Something Went Wrong!"+error);
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(request);
    }

    @Override
    public void onItemClick(PaymentHistoryResponse.Data position) {

    }
}