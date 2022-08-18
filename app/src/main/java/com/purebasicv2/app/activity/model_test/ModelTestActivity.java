package com.purebasicv2.app.activity.model_test;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.purebasicv2.app.R;
import com.purebasicv2.app.adapter.ModelTestAdapter;
import com.purebasicv2.app.app.CustomHeaderInt;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.model.ModelTestItems;
import com.purebasicv2.app.utils.RequestHandler;
import com.purebasicv2.app.utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ModelTestActivity extends AppCompatActivity implements ModelTestAdapter.OnItemClickListener {

    private RecyclerView rvCategoryLectures;
    private ModelTestAdapter modelTestAdapter;
    private ArrayList<ModelTestItems> modelTestItems;
    private int pageIndex = 0;
    private boolean isAutoLoad = true;
    private ProgressBar proLoadMoreRC;
    private TextView tvNoData;
    private ProgressDialog progressDialog;
    private int getType;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);

        String getTitle = getIntent().getStringExtra("EXAM_TITLE");
        getType = getIntent().getIntExtra("EXAM_TYPE", 0);

        new CustomHeaderInt(this, getTitle);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        tvNoData = findViewById(R.id.tvNoData);
        proLoadMoreRC = findViewById(R.id.proLoadMoreRC);
        rvCategoryLectures = findViewById(R.id.rvCategoryLectures);
        rvCategoryLectures.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        modelTestItems = new ArrayList<>();

        rvCategoryLectures.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    if (isAutoLoad) {
                        getAutoLoad();
                    }
                }
            }
        });

        tvNoData.setText("No Exam Available");
    }

    private void parseExam(){
        modelTestItems.clear();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.MODEL_TEST_LIST+SharedPrefManager.getInstance(this).getUsernameHash()+"&page=0&type="+getType, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                pageIndex = response.getInt("nextPage");
                                isAutoLoad = response.getBoolean("scroll");

                                JSONArray jsonArray = response.getJSONArray("model_test");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject hit = jsonArray.getJSONObject(i);

                                    int getId = hit.getInt("id");
                                    String getTitle = hit.getString("name");
                                    int getTime = hit.getInt("exam_time");
                                    modelTestItems.add(new ModelTestItems(getId, getTitle, getTime));
                                }
                                modelTestAdapter = new ModelTestAdapter(ModelTestActivity.this, modelTestItems);
                                rvCategoryLectures.setAdapter(modelTestAdapter);
                                modelTestAdapter.setOnItemClickListener(ModelTestActivity.this);
                            } else {
                                isAutoLoad = response.getBoolean("scroll");
                            }

                            if (modelTestItems.size() <= 0){
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
                new ErrorMe(ModelTestActivity.this, "Something Went Wrong!"+error);
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(request);
    }

    private void getAutoLoad() {
        proLoadMoreRC.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.MODEL_TEST_LIST+SharedPrefManager.getInstance(this).getUsernameHash()+"&page="+pageIndex+"&type="+getType, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                pageIndex = response.getInt("nextPage");
                                isAutoLoad = response.getBoolean("scroll");
                                JSONArray jsonArray = response.getJSONArray("model_test");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject hit = jsonArray.getJSONObject(i);

                                    int getId = hit.getInt("id");
                                    String getTitle = hit.getString("name");
                                    int getTime = hit.getInt("exam_time");
                                    modelTestItems.add(new ModelTestItems(getId, getTitle, getTime));
                                }
                                rvCategoryLectures.getAdapter().notifyDataSetChanged();
                            } else {
                                isAutoLoad = response.getBoolean("scroll");
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
    public void onItemClick(int position) {
        if (modelTestItems.size() > 0){
            ModelTestItems items = modelTestItems.get(position);
            Intent intent = new Intent(ModelTestActivity.this, ModelTestViewActivity.class);
            intent.putExtra("MODEL_TEST_ID", items.getId());
            intent.putExtra("MODEL_TEST_NAME", items.getName());
            intent.putExtra("MODEL_TEST_TIME", items.getExam_time());
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        parseExam();
    }
}
