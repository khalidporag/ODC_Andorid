package com.purebasicv2.app.activity.exam.exam_list;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.purebasicv2.app.R;
import com.purebasicv2.app.activity.ViewInWebViewActivity;
import com.purebasicv2.app.activity.base.BaseActivity;
import com.purebasicv2.app.adapter.exam.ExamHistoryAdapter;
import com.purebasicv2.app.app.CustomHeaderInt;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.model.ExamHistoryResponse;
import com.purebasicv2.app.utils.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ExamHistoryActivity extends BaseActivity implements ExamHistoryAdapter.OnItemClickListener{


    private RecyclerView rvExamHistory;
    private ExamHistoryAdapter examwiseResultAdapter;
    private ArrayList<ExamHistoryResponse.ExamInfo> examInfos;
    private int pageIndex = 0,lastPage,currentPage=0;
    private boolean isAutoLoad = true;
    private ProgressBar proLoadMoreRC;
    private TextView tvNoData;
    private ProgressDialog progressDialog;
    String nextPageUrl="",student_id,api_token="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_history);
        initView();
    }

    private void initView() {

        new CustomHeaderInt(this, "Exam Wise Result");
        student_id=String.valueOf(getSharedPrefManager().getUserInfo().getStudentInfo().getStudentId());

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        api_token=getSharedPrefManager().getUserInfo().getApiToken();


        tvNoData = findViewById(R.id.tvNoData);
        proLoadMoreRC = findViewById(R.id.proLoadMoreRC);
        rvExamHistory = findViewById(R.id.rvExamHistory);
        examInfos = new ArrayList<>();

        rvExamHistory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        examwiseResultAdapter = new ExamHistoryAdapter(ExamHistoryActivity.this, examInfos,ExamHistoryActivity.this);
        rvExamHistory.setAdapter(examwiseResultAdapter);
        parseExam();

        rvExamHistory.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    if (isAutoLoad) {
                        getAutoLoad();
                    }
                    else{
                        //  Toast.makeText(ExamwiseResultActivity.this,"No more data availabel",Toast.LENGTH_LONG);
                    }
                }
            }
        });

        tvNoData.setText("You Didn't Joined Any Exam");
    }

    private void parseExam(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.EXAM_HISTORY+"&student_id="+student_id+"&api_token="+api_token, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {

                                Gson gson = new Gson();
                                Type listType = new TypeToken<ExamHistoryResponse>() {
                                }.getType();
                                ExamHistoryResponse convertedList = gson.fromJson(response.toString(), listType);
                                examInfos.clear();
                                examInfos.addAll(convertedList.getData().getData());
                                examwiseResultAdapter.notifyDataSetChanged();
                                nextPageUrl = convertedList.getData().getNextPageUrl();
                                currentPage = convertedList.getData().getCurrentPage();
                                lastPage = convertedList.getData().getLastPage();
                                pageIndex=currentPage+1;

                                if (currentPage>=lastPage){
                                    isAutoLoad=false;
                                }

                            } else {
                                if (currentPage>=lastPage){
                                    isAutoLoad=false;
                                }
                            }

                            if (examInfos.size() <= 0){
                                tvNoData.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        proLoadMoreRC.setVisibility(View.INVISIBLE);
                        if (progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                new ErrorMe(ExamHistoryActivity.this, "Something Went Wrong!"+error);
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(request);
    }

    private void getAutoLoad() {
        proLoadMoreRC.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.EXAM_HISTORY+"&student_id="+student_id+"&page="+pageIndex+"&api_token="+api_token, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                Gson gson = new Gson();
                                Type listType = new TypeToken<ExamHistoryResponse>() {
                                }.getType();
                                ExamHistoryResponse convertedList = gson.fromJson(response.toString(), listType);
                                examInfos.addAll(convertedList.getData().getData());

                                nextPageUrl = convertedList.getData().getNextPageUrl();
                                currentPage = convertedList.getData().getCurrentPage();
                                lastPage = convertedList.getData().getLastPage();
                                pageIndex=currentPage+1;

                                if (currentPage>=lastPage){
                                    isAutoLoad=false;
                                }
                                rvExamHistory.getAdapter().notifyDataSetChanged();
                            } else {
                                if (currentPage>=lastPage){
                                    isAutoLoad=false;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        proLoadMoreRC.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(request);
    }

    @Override
    public void onItemClick(ExamHistoryResponse.ExamInfo examInfo) {
        Intent intent=new Intent(ExamHistoryActivity.this, ViewInWebViewActivity.class);
        intent.putExtra("nType", "pdf");
        intent.putExtra("nTitle",  "Solve Shet -Pdf");
        intent.putExtra("nPath", examInfo.getModeltest().getSolveShet());
        startActivity(intent);
    }



}