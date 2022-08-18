package com.purebasicv2.app.activity.user;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.purebasicv2.app.R;
import com.purebasicv2.app.app.CustomHeaderInt;
import com.purebasicv2.app.constant.Constants;

public class PaymentMethodActivity extends AppCompatActivity {

    private String getPhone,methodName;
    private int source;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        new CustomHeaderInt(this, "Payment Method");
//        getPhone = getIntent().getStringExtra("PAY_USER");
//        source = getIntent().getIntExtra("PAY_SOURCE", 0);
//
//        if (source == 0){
//            new CustomHeaderInt(this, "Payment Method");
//        } else if (source == 1){
//            new CustomHeaderInt(this, "Payment Method", new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    finishAffinity();
//                    startActivity(new Intent(PaymentMethodActivity.this, LoginActivity.class));
//                }
//            });
//        }

    }

    private void loadMethod(Constants.PaymentMethod method){
        Intent intent = new Intent();
        intent.putExtra("PAY_METHOD_NAME", methodName);
        setResult(RESULT_OK,intent);
        finish();
//        Intent payIntent = new Intent(PaymentMethodActivity.this, PaymentActivity.class);
//        payIntent.putExtra("PAY_USER",getPhone);
//        payIntent.putExtra("PAY_SOURCE", source);
//        payIntent.putExtra("PAY_METHOD", method);
//        payIntent.putExtra("PAY_METHOD_NAME", methodName);
//        startActivity(payIntent);
    }

    public void callThisMethod(View view) {
        if (view.getId() == R.id.btnPayBkash){
            methodName = "Bkash";
            loadMethod(Constants.PaymentMethod.BKASH);
        } else if (view.getId() == R.id.btnPayRocket){
            methodName = "Rocket";
            loadMethod(Constants.PaymentMethod.ROCKET);
        }else if (view.getId() == R.id.btnPayNagad){
            methodName = "Nagad";
            loadMethod(Constants.PaymentMethod.NAGAD);
        }else if (view.getId() == R.id.btnPayCityBank){
            methodName = "City Bank";
            loadMethod(Constants.PaymentMethod.CITY_BANK);
        }else if (view.getId() == R.id.btnPayBracBank){
            methodName = "BRAC BANK";
            loadMethod(Constants.PaymentMethod.BRAC_BANK);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}