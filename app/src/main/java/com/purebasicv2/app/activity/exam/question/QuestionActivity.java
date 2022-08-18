package com.purebasicv2.app.activity.exam.question;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.purebasicv2.app.R;
import com.purebasicv2.app.activity.base.BaseActivity;
import com.purebasicv2.app.activity.exam.result.ResultActivity;
import com.purebasicv2.app.app.CustomHeaderInt;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.model.Answer;
import com.purebasicv2.app.model.QuestionList;
import com.purebasicv2.app.utils.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.ContentValues.TAG;

public class QuestionActivity extends BaseActivity {


    private RecyclerView rvAllExamList;
    //  private ExamListAdapter examListAdapter;
    private List<QuestionList> questionLists;
    private List<Answer> answerList;
    private List<Integer> skipQuesList;
    private int pageIndex = 0;
    private boolean isAutoLoad = true;
    private ProgressBar proLoadMoreRC;
    private TextView tvNoData;
    private ProgressDialog progressDialog;
    String exam_id = "", student_id;
    TextView tvQuesCount, tvQuestion, tvTimer;
    CheckBox cbOption1, cbOption2, cbOption3, cbOption4, cbOption5;
    RadioButton rbOption1, rbOption2, rbOption3, rbOption4, rbOption5;
    RadioGroup rgOptions;
    LinearLayout llRadioOptions, llCbOptions, llTimer, llQuesMain, llQues, btnLayout;;
    Button btnNext, btnSkip, btnFinish;
    int currentIndex = 0, skipIndex;
    boolean allFinished = false;
    String molestedExamType, moledtedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        initView();
        parseQuestions();
    }


    private void parseQuestions() {
        String api_token = getSharedPrefManager().getUserInfo().getApiToken();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.QUESTION_LIST + exam_id + "?" + "api_test=false&student_id=" + student_id + "&api_token" + api_token, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.get("test_info") != null) {

                                JSONObject object = new JSONObject(response.get("test_info").toString());
                                molestedExamType = object.getString("exam_type");
                                moledtedID = object.getString("id");
                                String time = object.getString("ex_time");
                                JSONArray jsonArray = response.getJSONArray("questions");
                                Gson gson = new Gson();
                                Type listType = new TypeToken<ArrayList<QuestionList>>() {
                                }.getType();
                                List<QuestionList> convertedList = gson.fromJson(jsonArray.toString(), listType);
                                questionLists.clear();
                                questionLists.addAll(convertedList);
                                llQuesMain.setVisibility(View.VISIBLE);
                                showNextQuestion(currentIndex);
                                setAnswerListFirstTime();

                                if (time != null) {
                                    startTimer(Integer.parseInt(time));
                                }


                            } else if (response.get("messege") != null) {
                                tvNoData.setVisibility(View.VISIBLE);
                                tvTimer.setText("" + response.get("messege"));
                                btnLayout.setVisibility(View.GONE);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            try {
                                tvTimer.setText("" + response.get("message"));
                                btnLayout.setVisibility(View.GONE);
                            } catch (JSONException jsonException) {
                                jsonException.printStackTrace();
                            }

                        }
//                        proLoadMoreRC.setVisibility(View.INVISIBLE);
                        hideDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                new ErrorMe(QuestionActivity.this, "Something Went Wrong!" + error);
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(request);
    }


    private void submitQuestion() {
        showDialog();
        String api_token = getSharedPrefManager().getUserInfo().getApiToken();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.EXAM_SUBMIT + "&api_token=" + api_token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("VollySuccess", response);
                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("success")) {
                                try {
                                    hideDialog();
                                    JSONObject jsonObject = obj.getJSONObject("data");
                                    Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
                                    intent.putExtra("MODELTEST_ID", "" + exam_id);
                                    startActivity(intent);
                                    finish();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
//                                            FragmentTransaction fr_left = getFragmentManager().beginTransaction();
//                                            fr_left.setCustomAnimations(R.animator.fr_left, R.animator.fr_right, R.animator.fr_left, R.animator.fr_right);
//                                            fr_left.replace(R.id.fragment_container_sgp, fragment, "GONext");
//                                            fr_left.addToBackStack(null);
//                                            fr_left.commit();
                            } else {
                                hideDialog();
                                SweetAlertDialog pDialog = new SweetAlertDialog(QuestionActivity.this, SweetAlertDialog.ERROR_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText(" ");
                                pDialog.setContentText(obj.getString("message"));
                                pDialog.setCancelable(true);
                                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                    }
                                });
                                pDialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("VollyError", e.getMessage());
                        }
                        hideDialog();
                    }
                }, new Response.ErrorListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                new ErrorMe(QuestionActivity.this, "Something Went Wrong!" + error);
            }
        }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> arguments = new HashMap<String, String>();
                arguments.put("modeltest_exam_type", molestedExamType);
                arguments.put("student_id", student_id);
                arguments.put("modeltest_id", moledtedID);
                String answerJson = new Gson().toJson(answerList);
                arguments.put("question", answerJson);
                Log.d(TAG, "AnswerList " + answerJson);

                return arguments;
            }
        };
        RequestHandler.getInstance(QuestionActivity.this).addToRequestQueue(stringRequest);
    }

    private void showNextQuestion(int index) {

        try {

            if (questionLists.get(index).getQuestion() != null) {
                clearView();
                tvQuesCount.setText("" + (index + 1) + ".");
                tvQuestion.setText(questionLists.get(index).getQuestion());
                if (questionLists.get(index).getIsMulti() == 1) {
                    llCbOptions.setVisibility(View.VISIBLE);
                    llRadioOptions.setVisibility(View.GONE);
                    llQues.setBackgroundColor(ContextCompat.getColor(QuestionActivity.this, R.color.colorYellowLight));

                    cbOption1.setText(questionLists.get(index).getOptions().get(0).getOption());
                    cbOption2.setText(questionLists.get(index).getOptions().get(1).getOption());
                    cbOption3.setText(questionLists.get(index).getOptions().get(2).getOption());
                    cbOption4.setText(questionLists.get(index).getOptions().get(3).getOption());
                    cbOption5.setText(questionLists.get(index).getOptions().get(4).getOption());
                } else {
                    llCbOptions.setVisibility(View.GONE);
                    llRadioOptions.setVisibility(View.VISIBLE);
                    llQues.setBackgroundColor(ContextCompat.getColor(QuestionActivity.this, R.color.green));

                    rbOption1.setText(questionLists.get(index).getOptions().get(0).getOption());
                    rbOption2.setText(questionLists.get(index).getOptions().get(1).getOption());
                    rbOption3.setText(questionLists.get(index).getOptions().get(2).getOption());
                    rbOption4.setText(questionLists.get(index).getOptions().get(3).getOption());
                    rbOption5.setText(questionLists.get(index).getOptions().get(4).getOption());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT);
        }

    }

    public void setAnswerListFirstTime() {
        try {
            if (questionLists.size() > 0) {
                for (int i = 0; i < questionLists.size(); i++) {
                  //  List<Integer> option = new ArrayList<>();
                    answerList.add(new Answer(questionLists.get(i).getId()));
                  //  answerMap.put(questionLists.get(i).getId(), option);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("AnswerMap", answerList.toString());
    }

    private void setAnswer(int index) {
        try {

            List<Integer> option = new ArrayList<>();
            if (questionLists.get(index).getIsMulti() == 1) {
                if (cbOption1.isChecked()) {
                    option.add(questionLists.get(index).getOptions().get(0).getId());
                }
                if (cbOption2.isChecked()) {
                    option.add(questionLists.get(index).getOptions().get(1).getId());
                }
                if (cbOption3.isChecked()) {
                    option.add(questionLists.get(index).getOptions().get(2).getId());
                }
                if (cbOption4.isChecked()) {
                    option.add(questionLists.get(index).getOptions().get(3).getId());
                }
                if (cbOption5.isChecked()) {
                    option.add(questionLists.get(index).getOptions().get(4).getId());
                }
            } else {
                if (rbOption1.isChecked()) {
                    option.add(questionLists.get(index).getOptions().get(0).getId());
                }
                if (rbOption2.isChecked()) {
                    option.add(questionLists.get(index).getOptions().get(1).getId());
                }
                if (rbOption3.isChecked()) {
                    option.add(questionLists.get(index).getOptions().get(2).getId());
                }
                if (rbOption4.isChecked()) {
                    option.add(questionLists.get(index).getOptions().get(3).getId());
                }
                if (rbOption5.isChecked()) {
                    option.add(questionLists.get(index).getOptions().get(4).getId());
                }

            }

            for (int i=0;i<answerList.size();i++){
                if (answerList.get(i).getQuestionId()==questionLists.get(index).getId() && option.size()>0){
                    answerList.get(i).setOption(option);
                    System.out.println("Answerset "+questionLists.get(index)+" \t"+index+"\t "+answerList.get(i).getQuestionId());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //    private void setAnswer(int index){
//
//        try {
//            if (answerList.size()<questionLists.size()) {
//                Answer answer = new Answer();
//                answer.setQuestionId(questionLists.get(index).getId());
//                List<Integer> option = new ArrayList<>();
//                if (questionLists.get(index).getIsMulti()==1) {
//                    if (cbOption1.isChecked()) {
//                        option.add(questionLists.get(index).getOptions().get(0).getId());
//                    }
//                    if (cbOption2.isChecked()) {
//                        option.add(questionLists.get(index).getOptions().get(1).getId());
//                    }
//                    if (cbOption3.isChecked()) {
//                        option.add(questionLists.get(index).getOptions().get(2).getId());
//                    }
//                    if (cbOption4.isChecked()) {
//                        option.add(questionLists.get(index).getOptions().get(3).getId());
//                    }
//                    if (cbOption5.isChecked()) {
//                        option.add(questionLists.get(index).getOptions().get(4).getId());
//                    }
//                }
//                else{
//                    if (rbOption1.isChecked()) {
//                        option.add(questionLists.get(index).getOptions().get(0).getId());
//                    }
//                    if (rbOption2.isChecked()) {
//                        option.add(questionLists.get(index).getOptions().get(1).getId());
//                    }
//                    if (rbOption3.isChecked()) {
//                        option.add(questionLists.get(index).getOptions().get(2).getId());
//                    }
//                    if (rbOption4.isChecked()) {
//                        option.add(questionLists.get(index).getOptions().get(3).getId());
//                    }
//                    if (rbOption5.isChecked()) {
//                        option.add(questionLists.get(index).getOptions().get(4).getId());
//                    }
//                }
//                answer.setOption(option);
//                answerList.add(answer);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    private void clearView() {

        tvQuesCount.setText(null);
        tvQuestion.setText(null);
        cbOption1.setText(null);
        cbOption2.setText(null);
        cbOption3.setText(null);
        cbOption4.setText(null);
        cbOption5.setText(null);
        cbOption1.setChecked(false);
        cbOption2.setChecked(false);
        cbOption3.setChecked(false);
        cbOption4.setChecked(false);
        cbOption5.setChecked(false);

        rgOptions.clearCheck();
        rbOption1.setText(null);
        rbOption2.setText(null);
        rbOption3.setText(null);
        rbOption4.setText(null);
        rbOption5.setText(null);
//        rbOption1.setChecked(false);
//        rbOption2.setChecked(false);
//        rbOption3.setChecked(false);
//        rbOption4.setChecked(false);
//        rbOption5.setChecked(false);


    }

    private void initView() {

        new CustomHeaderInt(this, "Exam");

        showDialog();
        if (getIntent() != null) {
            exam_id = getIntent().getStringExtra("exam_id");
        }
        student_id = String.valueOf(getSharedPrefManager().getUserInfo().getStudentInfo().getStudentId());

        questionLists = new ArrayList<>();
        skipQuesList = new ArrayList<>();
        answerList = new ArrayList<>();
        tvNoData = findViewById(R.id.tvNoData);
        tvQuesCount = findViewById(R.id.tvQuesCount);
        tvQuestion = findViewById(R.id.tvQuestion);
        cbOption1 = findViewById(R.id.cbOption1);
        cbOption2 = findViewById(R.id.cbOption2);
        cbOption3 = findViewById(R.id.cbOption3);
        cbOption4 = findViewById(R.id.cbOption4);
        cbOption5 = findViewById(R.id.cbOption5);
        rgOptions = findViewById(R.id.rgOptions);
        rbOption1 = findViewById(R.id.rbOption1);
        rbOption2 = findViewById(R.id.rbOption2);
        rbOption3 = findViewById(R.id.rbOption3);
        rbOption4 = findViewById(R.id.rbOption4);
        rbOption5 = findViewById(R.id.rbOption5);
        llRadioOptions = findViewById(R.id.llRadioOptions);
        llCbOptions = findViewById(R.id.llCbOptions);
        btnNext = findViewById(R.id.btnNext);
        btnSkip = findViewById(R.id.btnSkip);
        btnFinish = findViewById(R.id.btnFinish);
        tvTimer = findViewById(R.id.tvTimer);
        llTimer = findViewById(R.id.llTimer);
        llQues = findViewById(R.id.llQues);
        llQuesMain = findViewById(R.id.llQuesMain);
        btnLayout = findViewById(R.id.btnLayout);
        llQuesMain.setVisibility(View.GONE);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!allFinished && questionLists.size() - 1 > currentIndex) {
                        setAnswer(currentIndex);
                        ++currentIndex;
                        showNextQuestion(currentIndex);

                        if (skipQuesList.contains(currentIndex)) {
                            skipQuesList.remove(currentIndex);
                        }
                    } else if (skipQuesList.size() > 0) {
                        // btnSkip.setEnabled(false);
                        allFinished = true;
                        setAnswer(currentIndex);
                        int tempIndex = skipQuesList.get(0);
                        if (tempIndex == currentIndex) {
                            skipQuesList.remove(skipQuesList.get(0));
                            if (skipQuesList.size() > 0) {
                                showNextQuestion(skipQuesList.get(0));
                                skipQuesList.remove(skipQuesList.get(0));
                            }

                        } else {
                            currentIndex=skipQuesList.get(0);
                            showNextQuestion(skipQuesList.get(0));
                            skipQuesList.remove(skipQuesList.get(0));
                        }

                    } else {
                        if (!allFinished) {
                            if (currentIndex > questionLists.size() - 1) {
                                btnNext.setEnabled(false);
                            } else {
                                setAnswer(currentIndex);
                                btnNext.setEnabled(false);
                                btnSkip.setEnabled(false);
                            }
                        } else {

                            setAnswer(currentIndex);
                            btnNext.setEnabled(false);
                            btnSkip.setEnabled(false);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!allFinished) {
                        if (questionLists.size() - 1 > currentIndex) {
                            skipQuesList.add(currentIndex);
                            showNextQuestion(++currentIndex);
                        } else {
                            allFinished = true;
                            skipQuesList.add(currentIndex);
                            currentIndex = skipQuesList.get(0);
                            showNextQuestion(skipQuesList.get(0));
                            skipQuesList.remove(skipQuesList.get(0));
                        }
                    } else {
                        if (skipQuesList.contains(currentIndex) && skipQuesList.size() > 0) {
                            skipQuesList.remove(skipQuesList.indexOf(currentIndex));
                            int tempInted = currentIndex;
                            currentIndex = skipQuesList.get(0);
                            skipQuesList.add(tempInted);
                            showNextQuestion(currentIndex);
                        } else {
                            if (skipQuesList.size() > 0) {
                                skipQuesList.add(currentIndex);
                                currentIndex = skipQuesList.get(0);
                                showNextQuestion(currentIndex);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    setAnswer(currentIndex);
                    submitQuestion();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void startTimer(int minutes) {
        new CountDownTimer(minutes * 60000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                tvTimer.setText("TIME : " + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            public void onFinish() {
                setAnswer(currentIndex);
                submitQuestion();
            }
        }.start();
    }


}