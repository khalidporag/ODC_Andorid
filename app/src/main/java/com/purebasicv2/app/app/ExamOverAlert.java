package com.purebasicv2.app.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import com.purebasicv2.app.R;
import com.purebasicv2.app.activity.exam_history.ExamHistoryViewActivity;

public class ExamOverAlert extends AlertDialog {

    public ExamOverAlert(final Context context, final String mdlName, final int mdlId) {
        super(context);
        Builder dialogBuilder = new Builder(context);
        dialogBuilder.setCancelable(false);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.lyt_exam_over, null);
        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        dialogView.findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                ((Activity) context).finish();
            }
        });

        dialogView.findViewById(R.id.btnLiveExamViewResult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                ((Activity) context).finish();
                context.startActivity(new Intent(context, ExamHistoryViewActivity.class).putExtra("MODEL_TEST_NAME", mdlName).putExtra("MODEL_TEST_ID", mdlId));
            }
        });
    }
}
