package com.purebasicv2.app.activity.exam.exam_list;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.purebasicv2.app.R;
import com.purebasicv2.app.activity.base.BaseActivity;
import com.purebasicv2.app.activity.exam.result.ExamwiseResultActivity;
import com.purebasicv2.app.app.CustomHeaderInt;

public class ExamCategoryActivity extends BaseActivity implements View.OnClickListener{


    private TextView tvNoData;
    private ProgressDialog progressDialog;
    int type;
    String batch_id,exam_title="",student_id;
    LinearLayout llRegularExam,llExamPlus,llExamWiseResult,llExamHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_category);

        initView();
    }

    private void initView() {
        if (getIntent() != null) {
            exam_title = getIntent().getStringExtra("EXAM_TITLE");
            batch_id = getIntent().getStringExtra("BATCH_ID");
            type = getIntent().getIntExtra("EXAM_TYPE", 0);
        }
        student_id=String.valueOf(getSharedPrefManager().getUserInfo().getStudentInfo().getStudentId());

        new CustomHeaderInt(this, "Exam");

        llRegularExam=findViewById(R.id.llRegular);
        llExamPlus=findViewById(R.id.llExamPlus);
        llExamWiseResult=findViewById(R.id.llExamWiseResult);
        llExamHistory=findViewById(R.id.llExamHistory);

        llRegularExam.setOnClickListener(this);
        llExamPlus.setOnClickListener(this);
        llExamWiseResult.setOnClickListener(this);
        llExamHistory.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llRegular :
                Intent intent = new Intent(ExamCategoryActivity.this, AllExamActivity.class);
                intent.putExtra("EXAM_TITLE", "Regular Exam");
                intent.putExtra("EXAM_TYPE", 1);
                intent.putExtra("BATCH_ID", batch_id);
                startActivity(intent);
                break;

            case R.id.llExamPlus:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://103.125.255.78/")));
                break;

            case R.id.llExamWiseResult:
                startActivity(new Intent(this, ExamwiseResultActivity.class));
                break;

            case R.id.llExamHistory:

                startActivity(new Intent(this, ExamHistoryActivity.class));
                break;
        }
    }
}