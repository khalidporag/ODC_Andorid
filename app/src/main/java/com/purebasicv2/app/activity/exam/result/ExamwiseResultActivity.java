package com.purebasicv2.app.activity.exam.result;

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
import com.purebasicv2.app.activity.base.BaseActivity;
import com.purebasicv2.app.adapter.exam.ExamwiseResultAdapter;
import com.purebasicv2.app.app.CustomHeaderInt;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.model.ExamwiseResultResponse;
import com.purebasicv2.app.utils.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ExamwiseResultActivity extends BaseActivity implements ExamwiseResultAdapter.OnItemClickListener{

    private RecyclerView rvExamwiseResult;
    private ExamwiseResultAdapter examwiseResultAdapter;
    private ArrayList<ExamwiseResultResponse.ExamInfo> examInfos;
    private int pageIndex = 0,lastPage,currentPage=0;
    private boolean isAutoLoad = true;
    private ProgressBar proLoadMoreRC;
    private TextView tvNoData;
    private ProgressDialog progressDialog;
    String nextPageUrl="";
    String api_token="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examwise_result);

        initView();
    }

    private void initView() {

        new CustomHeaderInt(this, "Exam Wise Result");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        api_token=getSharedPrefManager().getUserInfo().getApiToken();

        tvNoData = findViewById(R.id.tvNoData);
        proLoadMoreRC = findViewById(R.id.proLoadMoreRC);
        rvExamwiseResult = findViewById(R.id.rvExamwiseResult);
        examInfos = new ArrayList<>();

        rvExamwiseResult.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        examwiseResultAdapter = new ExamwiseResultAdapter(ExamwiseResultActivity.this, examInfos,ExamwiseResultActivity.this);
        rvExamwiseResult.setAdapter(examwiseResultAdapter);
        parseExam();

        rvExamwiseResult.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.EXAM_WISE_RESULT+"&api_token="+api_token, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {

                                Gson gson = new Gson();
                                Type listType = new TypeToken<ExamwiseResultResponse>() {
                                }.getType();
                                ExamwiseResultResponse convertedList = gson.fromJson(response.toString(), listType);
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
                new ErrorMe(ExamwiseResultActivity.this, "Something Went Wrong!"+error);
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(request);
    }

    private void getAutoLoad() {
        proLoadMoreRC.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.EXAM_WISE_RESULT+"&page="+pageIndex+"&api_token="+api_token, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                Gson gson = new Gson();
                                Type listType = new TypeToken<ExamwiseResultResponse>() {}.getType();
                                ExamwiseResultResponse convertedList = gson.fromJson(response.toString(), listType);
                                examInfos.addAll(convertedList.getData().getData());

                                nextPageUrl = convertedList.getData().getNextPageUrl();
                                currentPage = convertedList.getData().getCurrentPage();
                                lastPage = convertedList.getData().getLastPage();
                                pageIndex=currentPage+1;

                                if (currentPage>=lastPage){
                                    isAutoLoad=false;
                                }
                                rvExamwiseResult.getAdapter().notifyDataSetChanged();
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
    public void onItemClick(ExamwiseResultResponse.ExamInfo examInfo) {
        Intent intent=new Intent(ExamwiseResultActivity.this,ExamwiseResultDetailsActivity.class);
        intent.putExtra("id",examInfo.getId());
        startActivity(intent);
    }
}