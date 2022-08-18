package com.purebasicv2.app.activity;

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
import com.purebasicv2.app.adapter.CategoryLectureAdapter;
import com.purebasicv2.app.app.CustomHeaderInt;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.model.CategoryLectureItems;
import com.purebasicv2.app.utils.RequestHandler;
import com.purebasicv2.app.utils.SharedPrefManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class CategoryViewActivity extends AppCompatActivity implements CategoryLectureAdapter.OnItemClickListener {

    private RecyclerView rvCategoryLectures;
    private CategoryLectureAdapter categoryLectureAdapter;
    private ArrayList<CategoryLectureItems> categoryLectureItems;
    private int pageIndex = 0;
    private boolean isAutoLoad = true;
    private ProgressBar proLoadMoreRC;
    private TextView tvNoData;
    private ProgressDialog progressDialog;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);

        String getCategoryName = getIntent().getStringExtra("CATEGORY_NAME");
        final String getCategorySlug = getIntent().getStringExtra("CATEGORY_SLUG");
        new CustomHeaderInt(this, getCategoryName);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        tvNoData = findViewById(R.id.tvNoData);
        proLoadMoreRC = findViewById(R.id.proLoadMoreRC);
        rvCategoryLectures = findViewById(R.id.rvCategoryLectures);
        rvCategoryLectures.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        categoryLectureItems = new ArrayList<>();
        parseLecture(getCategorySlug);

        rvCategoryLectures.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    if (isAutoLoad) {
                        getAutoLoad(getCategorySlug);
                    }
                }
            }
        });

        tvNoData.setText("No lecture found");
    }

    private void parseLecture(String category){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.CATEGORY_LECTURE_SHEET+SharedPrefManager.getInstance(this).getUsernameHash()+"&category="+category+"&page=0", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                pageIndex = response.getInt("nextPage");
                                isAutoLoad = response.getBoolean("scroll");

                                JSONArray jsonArray = response.getJSONArray("lecture");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject hit = jsonArray.getJSONObject(i);

                                    int getId = hit.getInt("id");
                                    String getTitle = hit.getString("title");
                                    String getDate = hit.getString("date_time");
                                    String type = hit.getString("member_type");
                                    categoryLectureItems.add(new CategoryLectureItems(getId, getTitle, getDate, type));
                                }
                                categoryLectureAdapter = new CategoryLectureAdapter(CategoryViewActivity.this, categoryLectureItems);
                                rvCategoryLectures.setAdapter(categoryLectureAdapter);
                                categoryLectureAdapter.setOnItemClickListener(CategoryViewActivity.this);
                            } else {
                                isAutoLoad = response.getBoolean("scroll");
                            }

                            if (categoryLectureItems.size() <= 0){
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
                new ErrorMe(CategoryViewActivity.this, "Something Went Wrong!"+error);
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(request);
    }

    private void getAutoLoad(String category) {
        proLoadMoreRC.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.CATEGORY_LECTURE_SHEET+SharedPrefManager.getInstance(this).getUsernameHash()+"&category="+category+"&page="+pageIndex, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                pageIndex = response.getInt("nextPage");
                                isAutoLoad = response.getBoolean("scroll");
                                JSONArray jsonArray = response.getJSONArray("lecture");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject hit = jsonArray.getJSONObject(i);

                                    int getId = hit.getInt("id");
                                    String getTitle = hit.getString("title");
                                    String getDate = hit.getString("date_time");
                                    String type = hit.getString("member_type");
                                    categoryLectureItems.add(new CategoryLectureItems(getId, getTitle, getDate, type));
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
        if (categoryLectureItems.size() > 0){
            CategoryLectureItems items = categoryLectureItems.get(position);
            Intent intent = new Intent(CategoryViewActivity.this, LectureViewActivity.class);
            intent.putExtra("LECTURE_TITLE", items.getName());
            intent.putExtra("LECTURE_ID", items.getId());
            startActivity(intent);
        }
    }
}
