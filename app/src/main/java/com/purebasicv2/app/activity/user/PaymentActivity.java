package com.purebasicv2.app.activity.user;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.purebasicv2.app.BuildConfig;
import com.purebasicv2.app.LoginActivity;
import com.purebasicv2.app.R;
import com.purebasicv2.app.activity.base.BaseActivity;
import com.purebasicv2.app.app.CustomHeaderInt;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.utils.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PaymentActivity extends BaseActivity implements View.OnClickListener {

    private int[] images = {R.drawable.pay_bkash, R.drawable.pay_rocket, R.drawable.pay_nagad, R.drawable.pay_brac, R.drawable.pay_city_bank};
    private ImageView imgPayInstruct;
    private EditText etrPaymentAmount, etrPaymentTrxId;
    private LinearLayout lytPayInfo;
    private String methodName, getPhone, paymentState, batchId, batchName, amount;
    private int paidAmount, source;
    private ProgressDialog progressDialog;
    private Constants.PaymentMethod getMethod;
    private TextInputEditText etBatchName, etAmount, etMerchant, etTrxId;
    private TextInputLayout tilBatchName, tilAmount, tilMerchant, tilTrxId;
    private final int RES_CODE = 2344;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_main);
        new CustomHeaderInt(this, "Payment");
        imgPayInstruct = findViewById(R.id.imgPayInstruct);
        final TextView tvPayBatchName = findViewById(R.id.tvPayBatchName);
        final TextView tvPayAmount = findViewById(R.id.tvPayAmount);
        lytPayInfo = findViewById(R.id.lytPayInfo);
        etrPaymentAmount = findViewById(R.id.etrPaymentAmount);
        etrPaymentTrxId = findViewById(R.id.etrPaymentTrxId);
        final Button btnPayNow = findViewById(R.id.btnPay);

        etAmount = findViewById(R.id.etAmount);
        etTrxId = findViewById(R.id.etTrxId);
        etBatchName = findViewById(R.id.etBatchName);
        etMerchant = findViewById(R.id.etMerchant);
        tilAmount = findViewById(R.id.tilAmount);
        tilTrxId = findViewById(R.id.tilTrxId);
        tilMerchant = findViewById(R.id.tilMerchant);
        getIntentData();
        etMerchant.setOnClickListener(this);
        btnPayNow.setOnClickListener(this);
//        getPhone = getIntent().getStringExtra("PAY_USER");
//        getMethod = (Constants.PaymentMethod) getIntent().getSerializableExtra("PAY_METHOD");
//        source = getIntent().getIntExtra("PAY_SOURCE", 0);

//        if (source == 0){
//            new CustomHeaderInt(this, getIntent().getStringExtra("PAY_METHOD_NAME"));
//            paymentState = "Amount Due";
//        } else if (source == 1){
//            new CustomHeaderInt(this, getIntent().getStringExtra("PAY_METHOD_NAME"), new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    finishAffinity();
//                    startActivity(new Intent(PaymentActivity.this, LoginActivity.class));
//                }
//            });
//            paymentState = "Payment Amount";
//        }


        //loadMethod(getMethod);

    }

    private void getIntentData() {
        try {
            if (getIntent().hasExtra("batch_id")) {
                batchId = getIntent().getExtras().getString("batch_id");
            }
            if (getIntent().hasExtra("amount")) {
                amount = getIntent().getExtras().getString("amount");
                etAmount.setText(amount);
            }
            if (getIntent().hasExtra("batch_name")) {
                batchName = getIntent().getExtras().getString("batch_name");
                etBatchName.setText(batchName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitPayment() {
        showDialog();
        String api_token=getSharedPrefManager().getUserInfo().getApiToken();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.NEW_PAYMENT+"&api_token="+api_token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            hideDialog();
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("success")) {
                                Toast.makeText(PaymentActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                showError(jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            hideDialog();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                Toast.makeText(PaymentActivity.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("batch_id", String.valueOf(batchId));
                params.put("mar", etMerchant.getText().toString());
                params.put("transaction", etTrxId.getText().toString());
                params.put("amount", etAmount.getText().toString());
                params.put("student_id",
                        String.valueOf(getSharedPrefManager().getUserInfo().getStudentInfo().getStudentId()));
                return params;
            }
        };
        RequestHandler.getInstance(PaymentActivity.this).addToRequestQueue(stringRequest);
    }

    public void showError(String text) {
        new SweetAlertDialog(PaymentActivity.this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(" ")
                .setContentText(text)
                .show();
    }

    public void showSuccess(String text) {
        new SweetAlertDialog(PaymentActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(" ")
                .setContentText(text)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        finishAffinity();
                        if (source == 1) {
                            startActivity(new Intent(PaymentActivity.this, LoginActivity.class));
                        } else if (source == 0) {
                            startActivity(new Intent(PaymentActivity.this, ProfileActivity.class));
                        }
                    }
                })
                .show();
    }

    private void validateInputs() {
        if (vaAmount() && vaTransID() && vaMerchant()) {
            submitPayment();
        }
    }

    private void loadMethod(Constants.PaymentMethod method) {
        if (lytPayInfo.getVisibility() == View.GONE) {
            lytPayInfo.setVisibility(View.VISIBLE);
        }
        switch (method) {
            case BKASH:
                imgPayInstruct.setImageResource(images[0]);
                methodName = "Bkash";
                break;
            case ROCKET:
                imgPayInstruct.setImageResource(images[1]);
                methodName = "Rocket";
                break;
            case NAGAD:
                imgPayInstruct.setImageResource(images[2]);
                methodName = "Nagad";
                break;
            case DBBl:
                imgPayInstruct.setImageDrawable(null);
                methodName = "DBBL";
                break;
            case BRAC_BANK:
                imgPayInstruct.setImageResource(images[3]);
                methodName = "BRAC BANK";
                break;
            case CITY_BANK:
                imgPayInstruct.setImageResource(images[4]);
                methodName = "City Bank";
                break;
        }
    }

    private boolean vaAmount() {
        String input = etAmount.getText().toString().trim();
        if (input.isEmpty()) {
            tilAmount.setError("Amount is empty!");
            return false;
        } else {
            tilAmount.setError(null);
            return true;
        }
    }

    private boolean vaTransID() {
        String input = etTrxId.getText().toString().trim();
        if (input.isEmpty()) {
            etTrxId.setError("Transaction Id is empty!");
            return false;
        } else {
            etTrxId.setError(null);
            return true;
        }
    }

    private boolean vaMerchant() {
        String input = etMerchant.getText().toString().trim();
        if (input.isEmpty()) {
            tilMerchant.setError("Merchant name is empty!");
            return false;
        } else {
            tilMerchant.setError(null);
            return true;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.etMerchant:
                startActivityForResult(new Intent(this, PaymentMethodActivity.class), RES_CODE);
                break;
            case R.id.btnPay:
                validateInputs();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RES_CODE) {
                try {
//                    String appID,id;
//                    appID=BuildConfig.APPLICATION_ID;
//                    id= getCallingActivity().getPackageName();
//                    if (getCallingActivity() != null && getCallingActivity().getPackageName().equals(BuildConfig.APPLICATION_ID))
//                    {
                        String merchant = data.getExtras().getString("PAY_METHOD_NAME");
                        etMerchant.setText(merchant);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}