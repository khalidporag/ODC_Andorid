package com.purebasicv2.app.app;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.purebasicv2.app.R;

public class CustomHeaderInt {
    private Context context;
    public CustomHeaderInt(Context context){
        this.context = context;

    }

    public CustomHeaderInt(final Context ctx, String title){
        context =ctx;
        ((Activity) ctx).findViewById(R.id.header_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) ctx).finish();
            }
        });

        ((TextView) ((Activity) ctx).findViewById(R.id.header_title)).setText(title);
    }

    public CustomHeaderInt(final Context ctx, String title, View.OnClickListener onClickListener){
        context =ctx;
        ((Activity) ctx).findViewById(R.id.header_back).setOnClickListener(onClickListener);
        ((TextView) ((Activity) ctx).findViewById(R.id.header_title)).setText(title);
    }


    public CustomHeaderInt(View view, String title, View.OnClickListener onClickListener){
        view.findViewById(R.id.header_back).setOnClickListener(onClickListener);
        ((TextView) view.findViewById(R.id.header_title)).setText(title);
    }

    public void statusBar(int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = ((Activity) context).getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(context.getResources().getColor(color));
        }
    }
}
