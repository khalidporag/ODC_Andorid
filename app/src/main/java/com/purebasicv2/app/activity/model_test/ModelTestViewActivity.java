package com.purebasicv2.app.activity.model_test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.purebasicv2.app.CallBacks;
import com.purebasicv2.app.R;
import com.purebasicv2.app.adapter.model_test.ModelTestQuestionAdapter;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.app.ExamOverAlert;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.model.ModelTestOptionItems;
import com.purebasicv2.app.model.ModelTestQuestionItems;
import com.purebasicv2.app.utils.RequestHandler;
import com.purebasicv2.app.utils.SharedPrefManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ModelTestViewActivity extends AppCompatActivity implements ModelTestQuestionAdapter.OnItemClickListener, ModelTestQuestionAdapter.OnItemClickListenerMulti {

    private TextView tvLiveExamRemainTime,tvLiveExamTotalQuestion,tvLiveExamTotalAnswered;
    private CountDownTimer examTimeCounter;
    private RecyclerView rvLiveExamMain;
    private List<ModelTestQuestionItems> questionItems;
    private ModelTestQuestionAdapter modelTestQuestionAdapter;
    private ProgressBar proLiveExam;
    private int totalQuestion=0,answered=0,getId;
    private String getName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_test_view);

        getName = getIntent().getStringExtra("MODEL_TEST_NAME");
        getId = getIntent().getIntExtra("MODEL_TEST_ID", 0);
        int getTime = getIntent().getIntExtra("MODEL_TEST_TIME", 0);
        ((TextView) findViewById(R.id.tvLiveExamName)).setText(getName);

        tvLiveExamTotalQuestion = findViewById(R.id.tvLiveExamTotalQuestion);
        tvLiveExamTotalAnswered = findViewById(R.id.tvLiveExamTotalAnswered);
        proLiveExam = findViewById(R.id.proLiveExam);
        tvLiveExamRemainTime = findViewById(R.id.tvLiveExamRemainTime);
        rvLiveExamMain = findViewById(R.id.rvLiveExamMain);
        //rvLiveExamMain.setItemViewCacheSize(20);
        rvLiveExamMain.setLayoutManager(new LinearLayoutManager(this));
        rvLiveExamMain.setNestedScrollingEnabled(false);

        questionItems = new ArrayList<>();

        //TimeUnit.MINUTES.toMillis(getTime)
        examTimeCounter = new CountDownTimer(TimeUnit.MINUTES.toMillis(getTime), 1000) {
            @Override
            public void onTick(long millis) {
                tvLiveExamRemainTime.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));
            }

            @Override
            public void onFinish() {
                if(!ModelTestViewActivity.this.isFinishing()) {
                    new ExamOverAlert(ModelTestViewActivity.this, getName, getId);
                }
            }
        };


        ModelTestQuestionAdapter.setOnItemClickListenerMulti(this);
        ModelTestQuestionAdapter.setOnItemClickListener(this);

        if (getTime == 0){
            CallBacks.showError(ModelTestViewActivity.this, "Exam Time 0 is Invalid", false);
        } else {
            parseQuestion(getId);

            findViewById(R.id.btnModelTestFinish).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new ExamOverAlert(ModelTestViewActivity.this, getName, getId);
                }
            });
        }
    }


    private void parseQuestion(int mdl) {
        proLiveExam.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.MODEL_TEST_QUESTIONS+SharedPrefManager.getInstance(this).getUsernameHash()+"&mdl="+mdl+"&page=0", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")){
                                totalQuestion = response.getInt("total");
                                tvLiveExamTotalQuestion.setText(String.format("Question: %d", totalQuestion));

                                JSONArray questionArray = response.getJSONArray("questions");
                                for (int i = 0; i < questionArray.length(); i++) {
                                    JSONObject questionHit = questionArray.getJSONObject(i);
                                    int id = questionHit.getInt("id");
                                    int is_multi = questionHit.getInt("is_multi");
                                    String getTitle = questionHit.getString("question");


                                    List<ModelTestOptionItems> options = new ArrayList<>();

                                    JSONArray optionArray = questionHit.getJSONArray("options");
                                    for (int oi = 0; oi < optionArray.length(); oi++) {
                                        JSONObject option = optionArray.getJSONObject(oi);
                                        int op_id = option.getInt("option_id");
                                        int correct_or_not = option.getInt("correct_or_not");
                                        String optionName = option.getString("option");
                                        options.add(new ModelTestOptionItems(op_id, optionName, correct_or_not));
                                    }
                                    questionItems.add(new ModelTestQuestionItems(id, getTitle, is_multi, options));
                                }

                                modelTestQuestionAdapter = new ModelTestQuestionAdapter(questionItems);
                                rvLiveExamMain.setAdapter(modelTestQuestionAdapter);
                                examTimeCounter.start();
                                Toast.makeText(ModelTestViewActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {

                                if (response.getString("error_type").equals("PREMIUM")){
                                    CallBacks.premiumAlert(ModelTestViewActivity.this);
                                } else {
                                    CallBacks.showError(ModelTestViewActivity.this, response.getString("message"), false);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        proLiveExam.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                new ErrorMe(ModelTestViewActivity.this, "Something Went Wrong!" + error);
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(request);
    }

    @SuppressLint("DefaultLocale")
    public void updateAnswered(){
        answered++;
        tvLiveExamTotalAnswered.setText(String.format("Answered: %d", answered));
    }


    @Override
    public void onItemClick(int optionId, int questionId) {
        Log.d("ExamTouch", " optionID: "+optionId+" questionId: "+questionId);
        String submitData = "&opid="+optionId+"&qid="+questionId+"&mdlId="+getId+"&type=0";
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.OPTION_SUBMIT+SharedPrefManager.getInstance(this).getUsernameHash()+submitData, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")){
                                updateAnswered();
                            }
                            Toast.makeText(ModelTestViewActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                new ErrorMe(ModelTestViewActivity.this, "Something Went Wrong!" + error);
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(request);
    }

    @Override
    public void onItemClickMultiOption(int questionId, List<Integer> options) {
        String result = myJoin(options, ",");
        String submitData = "&opid="+result+"&qid="+questionId+"&mdlId="+getId+"&type=1";
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.OPTION_SUBMIT+SharedPrefManager.getInstance(this).getUsernameHash()+submitData, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")){
                                updateAnswered();
                            }
                            Toast.makeText(ModelTestViewActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                new ErrorMe(ModelTestViewActivity.this, "Something Went Wrong!" + error);
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(request);
    }

    public static String myJoin(List<Integer> arr, String separator) {
        if (null == arr || 0 == arr.size()) return "";
        StringBuilder sb = new StringBuilder(256);
        sb.append(arr.get(0));
        //if (arr.length == 1) return sb.toString();
        for (int i = 1; i < arr.size(); i++) sb.append(separator).append(arr.get(i));
        return sb.toString();
    }
}
