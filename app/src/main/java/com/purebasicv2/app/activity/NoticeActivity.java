
package com.purebasicv2.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.purebasicv2.app.R;
import com.purebasicv2.app.activity.base.BaseActivity;
import com.purebasicv2.app.activity.exam.result.ExamwiseResultActivity;
import com.purebasicv2.app.adapter.NoticeAdapter;
import com.purebasicv2.app.adapter.exam.ExamwiseResultAdapter;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.model.ExamwiseResultResponse;
import com.purebasicv2.app.model.NoticeResponse;
import com.purebasicv2.app.ongoingbatch.OngoingBatchRoot;
import com.purebasicv2.app.utils.RequestHandler;
import com.purebasicv2.app.utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NoticeActivity extends BaseActivity implements NoticeAdapter.OnItemClickListener {

    private TextView tvNoticePublic,tvNoticeBatch;
    private TextView tvNavName, nav_mobile;

    private RecyclerView rvNotice;
    private NoticeAdapter noticeAdapter;
    private ArrayList<NoticeResponse.Datum> notices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notice);
        tvNoticePublic = findViewById(R.id.tvNoticePublic);
        tvNoticeBatch = findViewById(R.id.tvNoticeBatch);
        rvNotice = findViewById(R.id.rvNotice);

        notices = new ArrayList<>();

        rvNotice.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        noticeAdapter = new NoticeAdapter(NoticeActivity.this, notices,this);
        rvNotice.setAdapter(noticeAdapter);

        ImageView ib = (ImageView) findViewById(R.id.header_back);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //parseProfileData();
        getNotice();
    }

    private void getNotice() {
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.NOTICE+"student_id="+getSharedPrefManager().getUserInfo().getStudentInfo().getStudentId() , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getBoolean("success")) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<NoticeResponse>() {
                        }.getType();
                        NoticeResponse noticeResponse = gson.fromJson(obj.toString(), type);
                        System.out.println(notices);

                        notices.clear();
                        notices.addAll(noticeResponse.getData());
                        noticeAdapter.notifyDataSetChanged();

                        hideDialog();

                    } else {
                        Toast.makeText(NoticeActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    hideDialog();
                }
            }
        }, new Response.ErrorListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NoticeActivity.this, "No Connection", Toast.LENGTH_SHORT).show();
                hideDialog();
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }



    private void parseProfileData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.USER_PROFILE+ SharedPrefManager.getInstance(this).getUsernameHash(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getBoolean("error")) {
                        JSONArray arrayName = obj.getJSONArray("datas");
                        JSONObject jsonObject = arrayName.getJSONObject(0);
//                        tvNavName.setText(jsonObject.getString("name"));
//                        nav_mobile.setText(jsonObject.getString("mobile"));
                        String notice_public = jsonObject.getString("notice_public");
                        String notice_batch = jsonObject.getString("notice_batch");
                        if (notice_public != null && !notice_public.isEmpty()){
                            tvNoticePublic.setText(notice_public);
                        }

                        if (notice_batch != null && !notice_batch.isEmpty()){
                            tvNoticeBatch.setText(notice_batch);
                        }
                    } else {
                        Toast.makeText(NoticeActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NoticeActivity.this, "No Connection", Toast.LENGTH_SHORT).show();
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onItemClick(int position) {

    }
}