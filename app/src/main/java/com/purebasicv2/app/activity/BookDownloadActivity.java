package com.purebasicv2.app.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.purebasicv2.app.R;
import com.purebasicv2.app.activity.base.BaseActivity;
import com.purebasicv2.app.adapter.BookDownloadAdapter;
import com.purebasicv2.app.app.CustomHeaderInt;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.model.BookDownloadResponse;
import com.purebasicv2.app.utils.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookDownloadActivity extends BaseActivity implements BookDownloadAdapter.OnItemClickListener {


    String batch_id="";
    RecyclerView rvBookList;
    Context mContext;
    private BookDownloadAdapter bookListAdapter;
    private ArrayList<BookDownloadResponse.Data> bookLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        initView();
        getBookData();
    }

    private void initView() {
        new CustomHeaderInt(this, "Book Download");

        rvBookList=findViewById(R.id.rvBookList);
        mContext= BookDownloadActivity.this;

        rvBookList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        bookLists = new ArrayList<>();
        bookListAdapter = new BookDownloadAdapter(this, bookLists,this);
        rvBookList.setAdapter(bookListAdapter);
    }

    private void getBookData() {
        showDialog();
        String api_token=getSharedPrefManager().getUserInfo().getApiToken();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.BOOKLIST+"&api_token="+api_token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideDialog();
                        try {
                            Log.d("VollySuccess", response);
                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("success")) {

                                Gson gson = new Gson();
                                Type listType = new TypeToken<BookDownloadResponse>() {
                                }.getType();
                                BookDownloadResponse convertedList = gson.fromJson(response.toString(), listType);
                                bookLists.clear();
                                bookLists.addAll(convertedList.getData());
                                bookListAdapter.notifyDataSetChanged();

                        } else {
                                //      notifyDataSetChanged();
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
                //  notifyDataSetChanged();
                new ErrorMe(mContext, "Something Went Wrong!" + error);
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
    public void onItemClick(BookDownloadResponse.Data bookData) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(bookData.getUrl())));

    }
}