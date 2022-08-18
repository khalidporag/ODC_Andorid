package com.purebasicv2.app.activity.exam_history;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.purebasicv2.app.adapter.exam_result.ExamResultQuestionAdapter;
import com.purebasicv2.app.app.CustomHeaderInt;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.model.ModelTestResultOptionItems;
import com.purebasicv2.app.model.ModelTestResultQuestionItems;
import com.purebasicv2.app.utils.RequestHandler;
import com.purebasicv2.app.utils.SharedPrefManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ExamHistoryViewActivity extends AppCompatActivity {

    private RecyclerView rvLiveExamResult;
    private List<ModelTestResultQuestionItems> questionItems;
    private ExamResultQuestionAdapter examQuestionAdapter;
    private ProgressBar proLiveExamResult;
    private TextView tvExamResultQuestion,tvExamResultPoint,tvExamResultRightAnswer;
    private TextView tvExamResultWrongAnswer,tvExamResultAnswered,tvExamResultUnAnswered;
    private Button btnSolveSheet001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_test_result);

        String getName = getIntent().getStringExtra("MODEL_TEST_NAME");
        int getId = getIntent().getIntExtra("MODEL_TEST_ID", 0);
        new CustomHeaderInt(this, getName);

        tvExamResultQuestion = findViewById(R.id.tvExamResultQuestion);
        tvExamResultPoint = findViewById(R.id.tvExamResultPoint);
        tvExamResultRightAnswer = findViewById(R.id.tvExamResultRightAnswer);
        tvExamResultWrongAnswer = findViewById(R.id.tvExamResultWrongAnswer);
        tvExamResultAnswered = findViewById(R.id.tvExamResultAnswered);
        tvExamResultUnAnswered = findViewById(R.id.tvExamResultUnAnswered);
        btnSolveSheet001 = findViewById(R.id.btnSolveSheet001);

        proLiveExamResult = findViewById(R.id.proLiveExamResult);
        rvLiveExamResult = findViewById(R.id.rvLiveExamResult);
        rvLiveExamResult.setItemViewCacheSize(20);
        rvLiveExamResult.setLayoutManager(new LinearLayoutManager(this));
        questionItems = new ArrayList<>();
        parseQuestion(getId);
    }


    private void parseQuestion(int mdl) {
        proLiveExamResult.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.MODEL_TEST_QUESTIONS_RESULT+SharedPrefManager.getInstance(this).getUsernameHash()+"&mdl="+mdl, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")){

                                JSONArray resultArray = response.getJSONArray("model_test_result");
                                JSONObject resultHit = resultArray.getJSONObject(0);
                                tvExamResultQuestion.setText("Question: "+ resultHit.getString("total_questions"));
                                tvExamResultPoint.setText("Marks: "+ resultHit.getString("point"));
                                tvExamResultAnswered.setText("Answered: "+ resultHit.getString("answered_questions"));
                                tvExamResultUnAnswered.setText("Unanswered: "+ resultHit.getString("unanswered_questions"));
                                tvExamResultRightAnswer.setText("Right: "+ resultHit.getString("right_answers"));
                                tvExamResultWrongAnswer.setText("Wrong: "+ resultHit.getString("wrong_answers"));
                                final String solveLink = resultHit.getString("solve_sheet");

                                if (solveLink != null){
                                    btnSolveSheet001.setVisibility(View.VISIBLE);
                                }
                                btnSolveSheet001.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(solveLink)));
                                        } catch (Exception e){
                                            Toast.makeText(ExamHistoryViewActivity.this, "Something is wrong!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                JSONArray questionArray = response.getJSONArray("questions");
                                for (int i = 0; i < questionArray.length(); i++) {
                                    JSONObject questionHit = questionArray.getJSONObject(i);
                                    int id = questionHit.getInt("id");
                                    int is_multi = questionHit.getInt("is_multi");
                                    String getTitle = questionHit.getString("question");
                                    String details = questionHit.getString("details");

                                    List<ModelTestResultOptionItems> options = new ArrayList<>();
                                    JSONArray optionArray = questionHit.getJSONArray("options");
                                    for (int oi = 0; oi < optionArray.length(); oi++) {
                                        JSONObject option = optionArray.getJSONObject(oi);
                                        String optionName = option.getString("option");
                                        int correct_or_not = option.getInt("correct_or_not");
                                        boolean answered = option.getBoolean("answered");
                                        options.add(new ModelTestResultOptionItems(optionName, correct_or_not, answered));
                                    }
                                    questionItems.add(new ModelTestResultQuestionItems(id, getTitle, is_multi, details, options));
                                }

                                examQuestionAdapter = new ExamResultQuestionAdapter(questionItems);
                                rvLiveExamResult.setAdapter(examQuestionAdapter);
                            } else {
                                CallBacks.showError(ExamHistoryViewActivity.this, response.getString("message"), false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        proLiveExamResult.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                new ErrorMe(ExamHistoryViewActivity.this, "Something Went Wrong!" + error);
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(request);
    }

}
