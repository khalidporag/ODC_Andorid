package com.purebasicv2.app.activity.exam.result;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.purebasicv2.app.adapter.exam.ResultAdapter;
import com.purebasicv2.app.app.CustomHeaderInt;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.model.ResultList;
import com.purebasicv2.app.utils.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends BaseActivity implements ResultAdapter.ResultAdapterListener {


    private RecyclerView rvResult;
    private ResultAdapter resultAdapter;
    private List<ResultList.Question> resultLists;
    private int pageIndex = 0;
    private boolean isAutoLoad = true;
    private ProgressBar proLoadMoreRC;
    private TextView tvNoData;
    private ProgressDialog progressDialog;
    int type;
    String batch_id, exam_title = "", student_id,modeltest_id;
    Button btnTotal,btnObtained;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initView();
    }

    private void initView() {
        if (getIntent() != null) {
            modeltest_id = getIntent().getStringExtra("MODELTEST_ID");
        }
        student_id = String.valueOf(getSharedPrefManager().getUserInfo().getStudentInfo().getStudentId());

        new CustomHeaderInt(this, "Result");

        showDialog();

        resultLists = new ArrayList<>();
        tvNoData = findViewById(R.id.tvNoData);
        proLoadMoreRC = findViewById(R.id.proLoadMoreRC);
        btnTotal = findViewById(R.id.btnTotalMarks);
        btnObtained = findViewById(R.id.btnObtainMarks);
        rvResult = findViewById(R.id.rvResult);

        rvResult.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        resultAdapter = new ResultAdapter(ResultActivity.this, resultLists, ResultActivity.this);
        rvResult.setAdapter(resultAdapter);
        loadResult();
    }

    private void loadResult() {
        String api_token=getSharedPrefManager().getUserInfo().getApiToken();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.EXAM_RESULT + "&modeltest_id="+modeltest_id+ "&student_id="+student_id+"&api_token="+api_token, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
//                                pageIndex = response.getInt("nextPage");
//                                isAutoLoad = response.getBoolean("scroll");


                               // JSONArray jsonArray = response.getJSONArray("data");
                                Gson gson = new Gson();
                                Type listType = new TypeToken<ResultList>() {
                                }.getType();
                                ResultList convertedList = gson.fromJson(response.toString(), listType);
                                resultLists.clear();
                                resultLists.addAll(convertedList.getData().getExamInfo().getQuestions());
                                resultAdapter.notifyDataSetChanged();
                                btnTotal.setText(""+convertedList.getData().getResult().getTotal_mark());
                                btnObtained.setText(""+convertedList.getData().getResult().getPoint());

                            } else {
                                tvNoData.setVisibility(View.VISIBLE);
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
                new ErrorMe(ResultActivity.this, "Something Went Wrong!" + error);
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(request);

    }

    @Override
    public void onExamSelected(ResultList.Question examList, int whatToDo) {

    }
}