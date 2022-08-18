package com.purebasicv2.app.app;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.purebasicv2.app.R;

public class ErrorMe extends AlertDialog {

    public ErrorMe(final Context context, String msg) {
        super(context);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setCancelable(true);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.lyt_error_me, null);
        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        //TextView tvErrorText = dialogView.findViewById(R.id.tvErrorText);
        //tvErrorText.setText(msg);
        Button quit = dialogView.findViewById(R.id.error_exit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        Log.d("ErrorAndroidMan", "Context"+context+ ":::Msg"+msg);
    }

}
