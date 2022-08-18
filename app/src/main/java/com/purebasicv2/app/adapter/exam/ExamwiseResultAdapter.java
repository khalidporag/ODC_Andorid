package com.purebasicv2.app.adapter.exam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.purebasicv2.app.R;
import com.purebasicv2.app.model.ExamwiseResultResponse;

import java.util.ArrayList;


public class ExamwiseResultAdapter extends RecyclerView.Adapter<ExamwiseResultAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<ExamwiseResultResponse.ExamInfo> examInfos;
    private ExamwiseResultAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(ExamwiseResultResponse.ExamInfo position);
    }

    public void setOnItemClickListener(ExamwiseResultAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public ExamwiseResultAdapter(Context context, ArrayList<ExamwiseResultResponse.ExamInfo> exampleList,ExamwiseResultAdapter.OnItemClickListener listener) {
        mContext = context;
        examInfos = exampleList;
        mListener=listener;
    }

    @Override
    public ExamwiseResultAdapter.ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.adapter_exam_wise_result, parent, false);
        return new ExamwiseResultAdapter.ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExamwiseResultAdapter.ExampleViewHolder holder, int position) {
        ExamwiseResultResponse.ExamInfo examInfo = examInfos.get(position);
        holder.tvExamName.setText(examInfo.getName());
    }

    @Override
    public int getItemCount() {
        return examInfos.size();
    }

        public class ExampleViewHolder extends RecyclerView.ViewHolder {
            public TextView tvExamName;
            public LinearLayout llExam;
            public ExampleViewHolder(View itemView) {
                super(itemView);
                tvExamName = (TextView) itemView.findViewById(R.id.tvExamName);
               // llExam = (TextView) itemView.findViewById(R.id.tvModelTextTime);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                mListener.onItemClick(examInfos.get(getAdapterPosition()));
                            }
                        }
                    }
                });
            }
        }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }
}