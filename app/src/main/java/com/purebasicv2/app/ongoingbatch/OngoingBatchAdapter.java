package com.purebasicv2.app.ongoingbatch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.purebasicv2.app.R;
import com.purebasicv2.app.model.OngoingBatch;

import java.util.List;

public class OngoingBatchAdapter extends RecyclerView.Adapter<OngoingBatchAdapter.MyViewHolder> {
    private Context context;
    private List<OngoingBatch> ongoingBatchList;
    private OngoingBatchAdapterListener listener;

    interface OngoingBatchAdapterListener {
        void onBatchEnrolledClick(OngoingBatch batch);

        void onCourseContentClick(OngoingBatch ongoingBatch);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvF1, tvF2, tvF3, tvF4, tvF5, tvF6, tvF7, tvF8, tvF9, tvF10, tvFLink;
        public LinearLayout layoutF1, layoutF2, layoutF3, layoutF4, layoutF5, layoutF6, layoutF7,
                layoutF8, layoutF9, layoutF10, layoutFLink, layoutExpand;
        public Button btnViewMore, btnEnroll,btnCourseContent;
        public WebView webview;
        public ProgressBar progressBar;
        public RelativeLayout layoutPromoVideo;
        public TextView tvTotalStudent,tvExamTime,tvTotalVideo,tvTotalExam;
        public ImageView ivCover;
        public MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvF1 = view.findViewById(R.id.tvF1);
            tvF2 = view.findViewById(R.id.tvF2);
            tvF3 = view.findViewById(R.id.tvF3);
            tvF4 = view.findViewById(R.id.tvF4);
            tvF5 = view.findViewById(R.id.tvF5);
            tvF6 = view.findViewById(R.id.tvF6);
            tvF7 = view.findViewById(R.id.tvF7);
            tvF8 = view.findViewById(R.id.tvF8);
            tvF9 = view.findViewById(R.id.tvF9);
            tvF10 = view.findViewById(R.id.tvF10);
            tvFLink = view.findViewById(R.id.tvFLink);
            webview = view.findViewById(R.id.myWebView);
            progressBar = view.findViewById(R.id.progressbar);
            tvTotalStudent = view.findViewById(R.id.tvTotalStudent);
            tvExamTime = view.findViewById(R.id.tvExamTime);
            tvTotalVideo = view.findViewById(R.id.tvTotalVideo);
            tvTotalExam = view.findViewById(R.id.tvTotalExam);
            ivCover = view.findViewById(R.id.ivCover);

            layoutF1 = view.findViewById(R.id.layoutF1);
            layoutF2 = view.findViewById(R.id.layoutF2);
            layoutF3 = view.findViewById(R.id.layoutF3);
            layoutF4 = view.findViewById(R.id.layoutF4);
            layoutF5 = view.findViewById(R.id.layoutF5);
            layoutF6 = view.findViewById(R.id.layoutF6);
            layoutF7 = view.findViewById(R.id.layoutF7);
            layoutF8 = view.findViewById(R.id.layoutF8);
            layoutF9 = view.findViewById(R.id.layoutF9);
            layoutF10 = view.findViewById(R.id.layoutF10);
            layoutFLink = view.findViewById(R.id.layoutFLink);
            layoutExpand = view.findViewById(R.id.layoutExpand);
            layoutPromoVideo = view.findViewById(R.id.layoutPromoVideo);
            btnEnroll = view.findViewById(R.id.btnEnroll);
            btnViewMore = view.findViewById(R.id.btnViewMore);
            btnCourseContent = view.findViewById(R.id.btnCourseContent);

            btnCourseContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCourseContentClick(ongoingBatchList.get(getAdapterPosition()));

                }
            });
            btnEnroll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onBatchEnrolledClick(ongoingBatchList.get(getAdapterPosition()));
                }
            });

            tvFLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ongoingBatchList.get(getAdapterPosition()).getLink() != null && !ongoingBatchList.get(getAdapterPosition()).getLink().trim().isEmpty()) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        browserIntent.setData(Uri.parse(ongoingBatchList.get(getAdapterPosition()).getLink()));
                        context.startActivity(browserIntent);
                    }
                }
            });
            btnViewMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        OngoingBatch batch = ongoingBatchList.get(getAdapterPosition());
                        if (batch.isExpanded()) {
                            batch.setExpanded(false);
                        } else {
                            batch.setExpanded(true);
                        }
                        ongoingBatchList.set(getAdapterPosition(), batch);
                        notifyItemChanged(getAdapterPosition());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public OngoingBatchAdapter(Context context, List<OngoingBatch> ongoingBatches, OngoingBatchAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.ongoingBatchList = ongoingBatches;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_onbatch_row_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        try {
            final OngoingBatch ongoingBatch = ongoingBatchList.get(position);
            holder.tvTitle.setText(ongoingBatch.getTitle());
            if (ongoingBatch.isExpanded()) {
                holder.btnViewMore.setText("View Less");
                holder.layoutExpand.setVisibility(View.VISIBLE);
            } else {
                holder.btnViewMore.setText("View More");
                holder.layoutExpand.setVisibility(View.GONE);
            }
            bind(holder, ongoingBatch);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void bind(MyViewHolder holder, OngoingBatch batch) {
        if (batch.getFild_1() != null && !batch.getFild_1().trim().isEmpty()) {
            holder.layoutF1.setVisibility(View.VISIBLE);
            holder.tvF1.setText(batch.getFild_1());
        } else {
            holder.layoutF1.setVisibility(View.GONE);
        }

        if (batch.getFild_2() != null && !batch.getFild_2().trim().isEmpty()) {
            holder.layoutF2.setVisibility(View.VISIBLE);
            holder.tvF2.setText(batch.getFild_2());
        } else {
            holder.layoutF2.setVisibility(View.GONE);
        }

        if (batch.getFild_3() != null && !batch.getFild_3().trim().isEmpty()) {
            holder.layoutF3.setVisibility(View.VISIBLE);
            holder.tvF3.setText(batch.getFild_3());
        } else {
            holder.layoutF3.setVisibility(View.GONE);
        }

        if (batch.getFild_4() != null && !batch.getFild_4().trim().isEmpty()) {
            holder.layoutF4.setVisibility(View.VISIBLE);
            holder.tvF4.setText(batch.getFild_4());
        } else {
            holder.layoutF4.setVisibility(View.GONE);
        }

        if (batch.getFild_5() != null && !batch.getFild_5().trim().isEmpty()) {
            holder.layoutF5.setVisibility(View.VISIBLE);
            holder.tvF5.setText(batch.getFild_5());
        } else {
            holder.layoutF5.setVisibility(View.GONE);
        }

        if (batch.getFild_6() != null && !batch.getFild_6().trim().isEmpty()) {
            holder.layoutF6.setVisibility(View.VISIBLE);
            holder.tvF6.setText(batch.getFild_6());
        } else {
            holder.layoutF6.setVisibility(View.GONE);
        }

        if (batch.getFild_7() != null && !batch.getFild_7().trim().isEmpty()) {
            holder.layoutF7.setVisibility(View.VISIBLE);
            holder.tvF7.setText(batch.getFild_7());
        } else {
            holder.layoutF7.setVisibility(View.GONE);
        }

        if (batch.getFild_8() != null && !batch.getFild_8().trim().isEmpty()) {
            holder.layoutF8.setVisibility(View.VISIBLE);
            holder.tvF8.setText(batch.getFild_8());
        } else {
            holder.layoutF8.setVisibility(View.GONE);
        }

        if (batch.getFild_9() != null && !batch.getFild_9().trim().isEmpty()) {
            holder.layoutF9.setVisibility(View.VISIBLE);
            holder.tvF9.setText(batch.getFild_9());
        } else {
            holder.layoutF9.setVisibility(View.GONE);
        }

        if (batch.getFild_10() != null && !batch.getFild_10().trim().isEmpty()) {
            holder.layoutF10.setVisibility(View.VISIBLE);
            holder.tvF10.setText(batch.getFild_10());
        } else {
            holder.layoutF10.setVisibility(View.GONE);
        }

        if (batch.getLink() != null && !batch.getLink().trim().isEmpty()) {
            holder.layoutFLink.setVisibility(View.VISIBLE);
         //   holder.tvFLink.setText(batch.getLink());
            holder.tvFLink.setPaintFlags(holder.tvFLink.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        } else {
            holder.tvFLink.setVisibility(View.GONE);
        }

        if (batch.getCover_image()!=null && !batch.getCover_image().trim().isEmpty()){
            holder.ivCover.setVisibility(View.VISIBLE);
            Glide.with(context).load(batch.getCover_image()).into(holder.ivCover);
        }else{
            holder.ivCover.setVisibility(View.GONE);
        }

//        if (batch.getPromotion_video() != null && !batch.getPromotion_video().trim().isEmpty()) {
//            holder.layoutPromoVideo.setVisibility(View.VISIBLE);
//            initWebViewSettings(holder);
//            loadUrl(holder,batch.getPromotion_video());
//        } else {
//            holder.layoutPromoVideo.setVisibility(View.GONE);
//        }

        holder.tvTotalVideo.setText(""+batch.getTotal_video()+" Video");
        holder.tvTotalStudent.setText(""+batch.getTotal_student());
        holder.tvTotalExam.setText(""+batch.getTotal_exam()+" Exams");
        holder.tvExamTime.setText(""+batch.getTotal_time());
    }

    @Override
    public int getItemCount() {
        return ongoingBatchList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }





    private void initWebViewSettings(final MyViewHolder holder) {
        holder.webview.getSettings().setJavaScriptEnabled(true);
        holder.webview.getSettings().setBuiltInZoomControls(true);
        holder.webview.getSettings().setDisplayZoomControls(true);
        holder.webview.getSettings().setLoadWithOverviewMode(true);
        holder.webview.getSettings().setUseWideViewPort(true);
        holder.webview.getSettings().setAppCacheMaxSize(0);
        holder.webview.getSettings().setAllowFileAccess(false);
        holder.webview.getSettings().setAppCacheEnabled(false);
        holder.webview.getSettings().setAllowFileAccessFromFileURLs(false);
        holder.webview.getSettings().setAllowUniversalAccessFromFileURLs(false);

        holder.webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                holder.progressBar.setVisibility(View.GONE);
                if (view.getTitle().contentEquals("")) {
                    holder.progressBar.setVisibility(View.VISIBLE);
                    view.reload();
                }
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                // System.out.println("Error " + errorResponse.getData().toString() + " " + request);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                // System.out.println("Error " + error + " " + request);
            }
        });
    }

    private void loadUrl(MyViewHolder holder,String url) {
        try {
            // String iframe="<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/5PwU12NfqSE\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";
            String iframe_part1="<iframe width=\"100%\" height=\"100%\"";
            String iframe_part2="src=\""+url+"\" ";
            String iframe_part3="frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";
            String iframe_final=iframe_part1+iframe_part2+iframe_part3;
            holder.webview.loadData(iframe_final, "text/html", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}