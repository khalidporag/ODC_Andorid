package com.purebasicv2.app.activity.exam.exam_list;

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
import com.purebasicv2.app.activity.exam.question.QuestionActivity;
import com.purebasicv2.app.activity.exam.result.ResultActivity;
import com.purebasicv2.app.adapter.exam.ExamListAdapter;
import com.purebasicv2.app.app.CustomHeaderInt;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.model.ExamList;
import com.purebasicv2.app.utils.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AllExamActivity extends BaseActivity implements ExamListAdapter.ExamListAdapterListener{

    private RecyclerView rvAllExamList;
    private ExamListAdapter examListAdapter;
    private List<ExamList> examRootLists;
    private int pageIndex = 0;
    private boolean isAutoLoad = true;
    private ProgressBar proLoadMoreRC;
    private TextView tvNoData;
    private ProgressDialog progressDialog;
    int type;
    String batch_id,exam_title="",student_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_all);

        initView();

    }

    private void initView() {
        if (getIntent() != null) {
            exam_title = getIntent().getStringExtra("EXAM_TITLE");
            batch_id = getIntent().getStringExtra("BATCH_ID");
            type = getIntent().getIntExtra("EXAM_TYPE", 0);
        }
        student_id=String.valueOf(getSharedPrefManager().getUserInfo().getStudentInfo().getStudentId());

        new CustomHeaderInt(this, exam_title);

        showDialog();

        examRootLists = new ArrayList<>();
        tvNoData = findViewById(R.id.tvNoData);
        proLoadMoreRC = findViewById(R.id.proLoadMoreRC);
        rvAllExamList = findViewById(R.id.rvAllExamList);

        rvAllExamList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        examListAdapter = new ExamListAdapter(AllExamActivity.this, examRootLists, AllExamActivity.this);
        rvAllExamList.setAdapter(examListAdapter);
        loadExam();
    }

    private void loadExam() {
        if (type==1){
            parseExam();
        }else{
            parseExamByBatch();
        }
    }

    private void parseExam(){
        String api_token=getSharedPrefManager().getUserInfo().getApiToken();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.EXAM_LIST_ALL+"&type="+exam_title+"&student_id="+student_id+"&api_token="+api_token, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
//                                pageIndex = response.getInt("nextPage");
//                                isAutoLoad = response.getBoolean("scroll");

                                JSONArray jsonArray = response.getJSONArray("data");
                                Gson gson = new Gson();
                                Type listType = new TypeToken<ArrayList<ExamList>>() {
                                }.getType();
                                List<ExamList> convertedList = gson.fromJson(jsonArray.toString(), listType);
                                examRootLists.clear();
                                examRootLists.addAll(convertedList);
                                examListAdapter.notifyDataSetChanged();

                            } else {
                               // isAutoLoad = response.getBoolean("scroll");
                            }

                            if (examRootLists.size() <= 0){
                                tvNoData.setText("No Data Found");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        proLoadMoreRC.setVisibility(View.INVISIBLE);
                        hideDialog();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                new ErrorMe(AllExamActivity.this, "Something Went Wrong!"+error);
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(request);
    }

    private void parseExamByBatch(){
        String api_token=getSharedPrefManager().getUserInfo().getApiToken();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.EXAM_BY_BATCH+batch_id+"?api_test=false"+"&student_id="+student_id+"&api_token="+api_token, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
//                                pageIndex = response.getInt("nextPage");
//                                isAutoLoad = response.getBoolean("scroll");

                                JSONArray jsonArray = response.getJSONArray("data");
                                Gson gson = new Gson();
                                Type listType = new TypeToken<ArrayList<ExamList>>() {
                                }.getType();
                                List<ExamList> convertedList = gson.fromJson(jsonArray.toString(), listType);
                                examRootLists.clear();
                                examRootLists.addAll(convertedList);
                                examListAdapter.notifyDataSetChanged();
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    JSONObject hit = jsonArray.getJSONObject(i);
//
//                                    int getId = hit.getInt("id");
//                                    String getTitle = hit.getString("name");
//                                    int getTime = hit.getInt("exam_time");
//                                    modelTestItems.add(new ModelTestItems(getId, getTitle, getTime));
//                                }
//                                examListAdapter = new ExamListAdapter(AllExamActivity.this, modelTestItems);
//                                rvAllExamList.setAdapter(examListAdapter);
//                                examListAdapter.setOnItemClickListener(ExamHistoryActivity.this);
                            } else {
                                // isAutoLoad = response.getBoolean("scroll");
                            }

                            if (examRootLists.size() <= 0){
                                tvNoData.setText("No Data Found");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        proLoadMoreRC.setVisibility(View.INVISIBLE);
                        hideDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                new ErrorMe(AllExamActivity.this, "Something Went Wrong!"+error);
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(request);
    }

    @Override
    public void onExamSelected(ExamList examList, int whatToDo) {
        Intent intent = new Intent(AllExamActivity.this, QuestionActivity.class);
       // Intent intent = new Intent(AllExamActivity.this, ResultActivity.class);
        intent.putExtra("exam_id", ""+examList.getId());
        startActivity(intent);
    }

    @Override
    public void onExamResultSelected(ExamList examList, int whatToDo) {
        Intent intent = new Intent(AllExamActivity.this, ResultActivity.class);
        intent.putExtra("MODELTEST_ID", ""+examList.getBatch().get(0).getPivot().getModeltestId());
        startActivity(intent);
    }
}