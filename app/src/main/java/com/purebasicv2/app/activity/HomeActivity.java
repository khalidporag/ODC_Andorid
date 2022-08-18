package com.purebasicv2.app.activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.purebasicv2.app.CallBacks;
import com.purebasicv2.app.ChapterandLecture.ChapterLectureActivity;
import com.purebasicv2.app.R;
import com.purebasicv2.app.activity.base.BaseActivity;
import com.purebasicv2.app.activity.exam.exam_list.AllExamActivity;
import com.purebasicv2.app.activity.exam.exam_list.ExamCategoryActivity;
import com.purebasicv2.app.activity.exam_history.ExamHistoryActivity;
import com.purebasicv2.app.activity.model_test.ModelTestActivity;
import com.purebasicv2.app.activity.model_test_result.ModelTestResultList;
import com.purebasicv2.app.activity.offline.OfflineVideos;
import com.purebasicv2.app.activity.user.PaymentActivity;
import com.purebasicv2.app.activity.user.PaymentHistoryActivity;
import com.purebasicv2.app.activity.user.ProfileActivity;
import com.purebasicv2.app.adapter.BatchAdapter;
import com.purebasicv2.app.adapter.CategoryAdapter;
import com.purebasicv2.app.app.AccountStatus;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.model.BatchRoot;
import com.purebasicv2.app.model.CategoryItems;
import com.purebasicv2.app.model.CategoryRoot;
import com.purebasicv2.app.ongoingbatch.OngoingBatchViewActivity;
import com.purebasicv2.app.utils.AppConstants;
import com.purebasicv2.app.utils.RequestHandler;
import com.purebasicv2.app.utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeActivity extends BaseActivity implements BatchAdapter.BatchAdapterListener,
        CategoryAdapter.CategoryAdapterListener,
        BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private NavigationView navigationView;
    private TextView tvNavName, nav_mobile,nav_id;
    private ImageView nav_pp;
    private CategoryAdapter categoryAdapter;
    private List<CategoryItems> categoryItems;
    private TextView tvNoticePublic, tvNoticeBatch;
    private SharedPrefManager sharedPrefManager;
    private BatchAdapter batchAdapter;
    private List<BatchRoot.Batch> batchRootList;
    LinearLayout layoutBatch, layoutSubjects;
    LinearLayoutCompat tvBatch, tvSubjects;
    private String batch_id = "",api_token="";
    private String subject_id = "";
    private String student_id="0";
    private Button btnBack, btnAddCourse;
    private BatchRoot.Batch batch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CallBacks.checkLogIn(this);
        intToolBar();

        View navView = navigationView.getHeaderView(0);
        tvNavName = navView.findViewById(R.id.nav_name);
        nav_mobile = navView.findViewById(R.id.nav_mobile);
        nav_id = navView.findViewById(R.id.nav_id);
        nav_pp = navView.findViewById(R.id.nav_pp);

        tvBatch = findViewById(R.id.tvBatch);
        tvSubjects = findViewById(R.id.tvSubjects);
        layoutBatch = findViewById(R.id.layoutBatch);
        layoutSubjects = findViewById(R.id.layoutSubjects);
        btnBack = findViewById(R.id.btnBack);
        btnAddCourse = findViewById(R.id.btnAddCourse);
        btnAddCourse.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        EditText etrHomeCategorySearch = findViewById(R.id.etrHomeCategorySearch);
        RecyclerView rvHomeCategory = findViewById(R.id.rvHomeCategory);
        RecyclerView rvHomeSubjects = findViewById(R.id.rvHomeSubjects);
        rvHomeCategory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvHomeSubjects.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        batchRootList = new ArrayList<>();
        batchAdapter = new BatchAdapter(this, batchRootList, this);
        rvHomeCategory.setAdapter(batchAdapter);

        categoryItems = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(this, categoryItems, this);
        rvHomeSubjects.setAdapter(categoryAdapter);

//        etrHomeCategorySearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Log.d("5656", "afterTextChanged: " + charSequence.toString());
//                categoryAdapter.getFilter().filter(charSequence.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });
        api_token=getSharedPrefManager().getUserInfo().getApiToken();

        BottomNavigationView navigation = findViewById(R.id.navigationBottom);
        navigation.setOnNavigationItemSelectedListener(this);
        parseProfileData();
        getEnrolledBatch();
    }


    private void categoryItems() {
        categoryItems.add(new CategoryItems("Anatomy", "Anatomy", R.drawable.anatomy));
        categoryItems.add(new CategoryItems("Physiology", "Physiology", R.drawable.physiology));
        categoryItems.add(new CategoryItems("Oral_Anatomy", "Oral Anatomy", R.drawable.oral_anatomy));
        categoryItems.add(new CategoryItems("Oral_Physiology", "Oral Physiology", R.drawable.oral_physiology));
        categoryItems.add(new CategoryItems("General_Pathology", "General Pathology", R.drawable.genarel_pathology));
        categoryItems.add(new CategoryItems("Microbiology", "Microbiology", R.drawable.microbiology));
        categoryItems.add(new CategoryItems("Immunology", "Immunology", R.drawable.immunology));
        categoryItems.add(new CategoryItems("Oral_Pathology ", "Oral Pathology ", R.drawable.oral_pathology));
        categoryItems.add(new CategoryItems("Periodontology", "Periodontology", R.drawable.periodontology));
        categoryItems.add(new CategoryItems("Biochemistry", "Biochemistry", R.drawable.biochemistry));
        categoryItems.add(new CategoryItems("Pharmacology", "Pharmacology", R.drawable.pharmacology));
        categoryItems.add(new CategoryItems("Dentistry", "Clinical Dentisty", R.drawable.dentistry));
        categoryItems.add(new CategoryItems("Workshop", "Workshop", R.drawable.workshop));
//        categoryItems.add(new CategoryItems("Clinical", "Clinical", R.drawable.clinical));
//        categoryItems.add(new CategoryItems("INBDE", "INBDE", R.drawable.inbde));
//        categoryItems.add(new CategoryItems("NDBE", "NDBE", R.drawable.physiology));
//        categoryItems.add(new CategoryItems("ADC", "ADC", R.drawable.adcpng));
        categoryItems.add(new CategoryItems("Overseas", "Overseas", R.drawable.overseas));
//        categoryItems.add(new CategoryItems("Prometric_HAAD", "Prometric & HAAD", R.drawable.prometric_haad));
//        categoryItems.add(new CategoryItems("ORE", "ORE", R.drawable.ore));
    }


    @Override
    public void onBackPressed() {
        if (layoutSubjects.getVisibility() == View.VISIBLE) {
            alterView(false);
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Exit App")
                    .setMessage("Are you sure you want to exit app?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("Rate Us", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            CallBacks.rateUs(HomeActivity.this);
                        }
                    })
                    .show();
        }
    }


    void intToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        navigationView = findViewById(R.id.navigation_view);
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                //drawerLayout.closeDrawers();
                switch (id) {
                    case R.id.nav_home:
                        //drawerLayout.closeDrawers();
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/drive/u/0/folders/1-C9srGsbjl6uO1eYeCls1R9gvl7m-0N9")));
                        break;

                    case R.id.nav_profile:
                        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                        break;

                    case R.id.nav_lv_exam:
                        openExamType(1);
                        break;

                    case R.id.nav_lv_bcs:
                        openExamType(2);
                        break;

                    case R.id.nav_lv_exam_history:
                        startActivity(new Intent(HomeActivity.this, ExamHistoryActivity.class));
                        break;

                    case R.id.nav_lv_model_test_result:
                        startActivity(new Intent(HomeActivity.this, ModelTestResultList.class));
                        break;

                    case R.id.nav_lv_offline_video:
                        startActivity(new Intent(HomeActivity.this, OfflineVideos.class));
                        break;

                    case R.id.nav_lv_enilkhet:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.enilkhet.com/")));
                        break;

                    case R.id.nav_lv_workshop:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://workshop.purebasic.com.bd/")));
                        break;

                    case R.id.nav_lv_diagnostic:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.odchealthcares.com/")));
                        break;

                    case R.id.book_list:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://purebasic.com.bd/contents/website/pdf/book_list.pdf")));
                        break;
                    case R.id.book_download:
                        startActivity(new Intent(HomeActivity.this,BookDownloadActivity.class));
                        break;

                    case R.id.dentube:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/drive/u/0/folders/1Vs7ziwEhWAb4T5zLr8cfB4j7OPcHPRLQ")));
                        break;

                    case R.id.guidelines:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://purebasic.com.bd/contents/website/pdf/guidelines.pdf")));
                        break;

                    case R.id.chat:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/pure.basic/")));
                        break;

                    case R.id.download:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://purebasic.com.bd/books")));
                        break;

                    case R.id.PureBasicWebsite:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://purebasic.com.bd/")));
                        break;

                    case R.id.paymentHistory:
                        startActivity(new Intent(HomeActivity.this, PaymentHistoryActivity.class));
                        break;

                    case R.id.DrSarwerBiplob:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.drsarwerbiplob.com/")));
                        break;

                    case R.id.notice:
                        startActivity(new Intent(HomeActivity.this, NoticeActivity.class));
                        break;

                    case R.id.contact:
                        startActivity(new Intent(HomeActivity.this, ActivityAbout.class));
                        break;

                    case R.id.nav_rate:
                        CallBacks.rateUs(HomeActivity.this);
                        break;

                    case R.id.nav_louout:
                        finishAffinity();
                        SharedPrefManager.getInstance(HomeActivity.this).logout();
                        break;

                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_bottom_notice:
                startActivity(new Intent(HomeActivity.this, NoticeActivity.class));
                break;
//                case R.id.navigation_bottom_profile:
//                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
//                break;

            case R.id.navigation_bottom_live_exam:
               // openExamType(1);
                openExamActivity(1,"0");
                break;

            case R.id.navigation_bottom_shop:
                CallBacks.openMyWebView(HomeActivity.this, "Book Store", "https://books.purebasic.com.bd/");
                break;
        }
        return true;
    }

    @Override
    public void onBatchSelected(BatchRoot.Batch items, int whatToDo) {
        batch_id = items.batch_id + "";
        batch = items;
        if (whatToDo==AppConstants.GO_TO_EXAM_BY_BATCH){
            openExamActivity(2,batch_id);
        }
        else {
            getSubjects(items.batch_id + "");
        }

//        if (items != null && categoryAdapter.getItemCount() > 0){
//            Intent intent = new Intent(HomeActivity.this, CategoryViewActivity.class);
//            intent.putExtra("CATEGORY_NAME", items.getName());
//            intent.putExtra("CATEGORY_SLUG", items.getSlug());
//            startActivity(intent);
//        }
    }

    @Override
    public void onPayNowButtonClicked(BatchRoot.Batch item, int whatToDo) {
        Intent payIntent = new Intent(HomeActivity.this, PaymentActivity.class);
        payIntent.putExtra("batch_id", ""+item.getBatch_id());
        payIntent.putExtra("amount", ""+item.getPayable());
        payIntent.putExtra("batch_name", ""+item.getCourse().getPlan());
        startActivity(payIntent);
    }

    private void parseProfileData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.USER_PROFILE + SharedPrefManager.getInstance(this).getUsernameHash(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getBoolean("error")) {
                        JSONArray arrayName = obj.getJSONArray("datas");
                        JSONObject jsonObject = arrayName.getJSONObject(0);
                        tvNavName.setText(jsonObject.getString("name"));
                        nav_mobile.setText(jsonObject.getString("mobile"));
                        nav_id.setText(getSharedPrefManager().getUserInfo().getStudentInfo().getmGeneratedSId());
                        String notice_public = jsonObject.getString("notice_public");
                        String notice_batch = jsonObject.getString("notice_batch");

//                        try {
//                            if (jsonObject.getString("photo")!=null)
//                                Glide.with(HomeActivity.this).load(jsonObject.getString("photo")).into(nav_pp);
//                        } catch (Exception e){
//                            Toast.makeText(HomeActivity.this, "Profile Picture Problem", Toast.LENGTH_SHORT).show();
//                        }
//                        if (notice_public != null && !notice_public.isEmpty()) {
//                            tvNoticePublic.setSelected(true);
//                            tvNoticePublic.setText(notice_public);
//                        } else {
//                            tvNoticePublic.setVisibility(View.GONE);
//                        }
//
//                        if (notice_batch != null && !notice_batch.isEmpty()) {
//                            tvNoticeBatch.setSelected(true);
//                            tvNoticeBatch.setText(notice_batch);
//                        } else {
//                            tvNoticeBatch.setVisibility(View.GONE);
//                        }
                    } else {
                        Toast.makeText(HomeActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, "No Connection", Toast.LENGTH_SHORT).show();
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void openExamType(int type) {
        Intent intent = new Intent(HomeActivity.this, ModelTestActivity.class);
        switch (type) {
            case 1:
                intent.putExtra("EXAM_TITLE", "Live Exam");
                intent.putExtra("EXAM_TYPE", type);
                break;
            case 2:
                intent.putExtra("EXAM_TITLE", "BCS Exam");
                intent.putExtra("EXAM_TYPE", type);
                break;
        }
        startActivity(intent);
    }
    private void openExamActivity(int type,String batch_id) {
        switch (type) {
            case 1:
                Intent intent = new Intent(HomeActivity.this, ExamCategoryActivity.class);
                intent.putExtra("EXAM_TITLE", "ALL Exam");
                intent.putExtra("EXAM_TYPE", type);
                intent.putExtra("BATCH_ID", batch_id);
                startActivity(intent);
                break;
            case 2:
                Intent i = new Intent(HomeActivity.this, AllExamActivity.class);
                i.putExtra("EXAM_TITLE", "Exam By Batch");
                i.putExtra("EXAM_TYPE", type);
                i.putExtra("BATCH_ID", batch_id);
                startActivity(i);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new AccountStatus(this);
    }

    private void getEnrolledBatch() {
        showDialog();
        student_id=String.valueOf(getSharedPrefManager().getUserInfo().getStudentInfo().getStudentId());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.ENROLLED_BATCH + "&" + "student_id="+student_id+"&api_token="+api_token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideDialog();
                        try {
                            Log.d("VollySuccess", response);
                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("success")) {
                                Gson gson = new Gson();
                                Type type = new TypeToken<BatchRoot>() {
                                }.getType();
                                BatchRoot batchRoot = gson.fromJson(obj.toString(), type);
                                batchRootList.clear();
                                batchRootList.addAll(batchRoot.getData());
                                batchAdapter.notifyDataSetChanged();
                                alterView(false);
//                                batchAdapter.addAll(batchRoot.getData());
                            } else {
                                alterView(false);
                                showError(obj.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                alterView(false);
                new ErrorMe(HomeActivity.this, "Something Went Wrong!" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void getSubjects(String batchId) {
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.SUBJECTS + "&" + "batch_id=" + batchId+"&api_token="+api_token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideDialog();
                        try {
                            Log.d("VollySuccess", response);
                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("success")) {
                                Gson gson = new Gson();
                                Type type = new TypeToken<CategoryRoot>() {
                                }.getType();
                                CategoryRoot categoryRoot = gson.fromJson(obj.toString(), type);
                                categoryItems.clear();
                                categoryItems.addAll(categoryRoot.getData());
                                categoryAdapter.notifyDataSetChanged();
                                alterView(true);
//                                batchAdapter.addAll(batchRoot.getData());
                            } else {
                                alterView(true);
                                showError(obj.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                alterView(true);
                new ErrorMe(HomeActivity.this, "Something Went Wrong!" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onCategorySelected(CategoryItems contact) {
        Intent intent = new Intent(HomeActivity.this, ChapterLectureActivity.class);
        intent.putExtra("batch_id", batch_id);
        intent.putExtra("batch", (Serializable) batch);
        intent.putExtra("subject", (Serializable) contact);
        intent.putExtra("subject_id", contact.getId() + "");
        startActivity(intent);
    }

    private void alterView(boolean isSubject) {
        if (isSubject) {
            layoutSubjects.setVisibility(View.VISIBLE);
            tvSubjects.setVisibility(View.VISIBLE);
            layoutBatch.setVisibility(View.GONE);
            tvBatch.setVisibility(View.GONE);
        } else {
            layoutSubjects.setVisibility(View.GONE);
            tvSubjects.setVisibility(View.GONE);
            layoutBatch.setVisibility(View.VISIBLE);
            tvBatch.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                if (layoutSubjects.getVisibility() == View.VISIBLE)
                    alterView(false);
                break;

            case R.id.btnAddCourse:
                startActivity(new Intent(this, OngoingBatchViewActivity.class));
                break;
        }
    }
}
