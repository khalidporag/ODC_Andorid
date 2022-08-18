package com.purebasicv2.app.activity.exam.result;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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
import com.purebasicv2.app.adapter.exam.ExamwiseResultDetailsAdapter;
import com.purebasicv2.app.app.CustomHeaderInt;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.model.ExamwiseResultDetailsResponse;
import com.purebasicv2.app.utils.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ExamwiseResultDetailsActivity extends BaseActivity implements ExamwiseResultDetailsAdapter.OnItemClickListener{

    private RecyclerView rvExamwiseResult;
    private ExamwiseResultDetailsAdapter examwiseResultAdapter;
    private ArrayList<ExamwiseResultDetailsResponse.ResultData> examInfos;
    private int pageIndex = 0,lastPage,currentPage=0;
    private boolean isAutoLoad = true;
    private ProgressBar proLoadMoreRC;
    private TextView tvNoData;
    private ProgressDialog progressDialog;
    String nextPageUrl="",api_token="";
    LinearLayout tableHeaderLayout;
    int exam_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examwise_result_details);


        initView();
    }

    private void initView() {

        new CustomHeaderInt(this, "Exam Wise Result Details");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        api_token=getSharedPrefManager().getUserInfo().getApiToken();

        if (getIntent() != null) {
            exam_id = getIntent().getIntExtra("id", 0);
        }

        tvNoData = findViewById(R.id.tvNoData);
        proLoadMoreRC = findViewById(R.id.proLoadMoreRC);
        rvExamwiseResult = findViewById(R.id.rvExamwiseResult);
        tableHeaderLayout = findViewById(R.id.tableHeaderLayout);
        tableHeaderLayout.setVisibility(View.GONE);
        examInfos = new ArrayList<>();

        rvExamwiseResult.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        examwiseResultAdapter = new ExamwiseResultDetailsAdapter(ExamwiseResultDetailsActivity.this, examInfos,ExamwiseResultDetailsActivity.this);
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
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.EXAM_WISE_RESULT_DETAILS+exam_id+"?api_test=false&api_token="+api_token, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {

                                Gson gson = new Gson();
                                Type listType = new TypeToken<ExamwiseResultDetailsResponse>() {
                                }.getType();
                                ExamwiseResultDetailsResponse convertedList = gson.fromJson(response.toString(), listType);
                                examInfos.clear();
                                examInfos.addAll(convertedList.getData().getData());
                                examwiseResultAdapter.notifyDataSetChanged();
                                nextPageUrl = convertedList.getData().getNextPageUrl();
                                currentPage = convertedList.getData().getCurrentPage();
                                lastPage = convertedList.getData().getLastPage();
                                pageIndex=currentPage+1;
                                //tableHeaderLayout.setVisibility(View.VISIBLE);

                                if (currentPage>=lastPage){
                                    isAutoLoad=false;
                                }

                            } else {
                                if (currentPage>=lastPage){
                                    isAutoLoad=false;
                                }
                            }

                            if (examInfos.size() <= 0){
                                tableHeaderLayout.setVisibility(View.GONE);
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
                new ErrorMe(ExamwiseResultDetailsActivity.this, "Something Went Wrong!"+error);
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(request);
    }

    private void getAutoLoad() {
        proLoadMoreRC.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.EXAM_WISE_RESULT_DETAILS+exam_id+"?api_test=false&page="+pageIndex+"&api_token="+api_token, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                Gson gson = new Gson();
                                Type listType = new TypeToken<ExamwiseResultDetailsResponse>() {}.getType();
                                ExamwiseResultDetailsResponse convertedList = gson.fromJson(response.toString(), listType);
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
    public void onItemClick(ExamwiseResultDetailsResponse.ResultData examInfo) {
        //Toast.makeText(ExamwiseResultDetailsActivity.this,"Clicked "+examInfo.getName(),Toast.LENGTH_LONG);
    }
}