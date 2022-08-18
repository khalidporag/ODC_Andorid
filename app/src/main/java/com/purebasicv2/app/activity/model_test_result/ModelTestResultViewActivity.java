package com.purebasicv2.app.activity.model_test_result;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.purebasicv2.app.CallBacks;
import com.purebasicv2.app.R;
import com.purebasicv2.app.adapter.ModelTestMeritAdapter;
import com.purebasicv2.app.app.CustomHeaderInt;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.model.ModelTestResultMerit;
import com.purebasicv2.app.utils.RequestHandler;
import com.purebasicv2.app.utils.SharedPrefManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ModelTestResultViewActivity extends AppCompatActivity {


    private RecyclerView rvModelTestMerit;
    private List<ModelTestResultMerit> modelTestResultMerits;
    private ModelTestMeritAdapter modelTestMeritAdapter;
    private ProgressBar proModelTestMerit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_test_result_view);

        String getName = getIntent().getStringExtra("MODEL_TEST_NAME");
        int getId = getIntent().getIntExtra("MODEL_TEST_ID", 0);
        ((TextView) findViewById(R.id.tvLiveExamName)).setText(getName);

        new CustomHeaderInt(this, "");

        proModelTestMerit = findViewById(R.id.proModelTestMerit);
        rvModelTestMerit = findViewById(R.id.rvModelTestMerit);
        rvModelTestMerit.setLayoutManager(new LinearLayoutManager(this));
        rvModelTestMerit.setNestedScrollingEnabled(false);
        modelTestResultMerits = new ArrayList<>();

        parseQuestion(getId);

    }


    private void parseQuestion(int mdl) {
        proModelTestMerit.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.MODEL_TEST_MERIT_VIEW+SharedPrefManager.getInstance(this).getUsernameHash()+"&mdl="+mdl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")){
                                JSONArray questionArray = response.getJSONArray("students");
                                for (int i = 0; i < questionArray.length(); i++) {
                                    JSONObject questionHit = questionArray.getJSONObject(i);
                                    int serial = questionHit.getInt("serial");
                                    String name = questionHit.getString("name");
                                    String point = questionHit.getString("point");
                                    modelTestResultMerits.add(new ModelTestResultMerit(serial,name,point));
                                }
                                modelTestMeritAdapter = new ModelTestMeritAdapter(ModelTestResultViewActivity.this, modelTestResultMerits);
                                rvModelTestMerit.setAdapter(modelTestMeritAdapter);
                            } else {
                                CallBacks.showError(ModelTestResultViewActivity.this, response.getString("message"), false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        proModelTestMerit.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                new ErrorMe(ModelTestResultViewActivity.this, "Something Went Wrong!" + error);
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(request);
    }


}
