package com.purebasicv2.app.adapter.exam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.purebasicv2.app.R;
import com.purebasicv2.app.model.ExamHistoryResponse;

import java.util.ArrayList;


public class ExamHistoryAdapter extends RecyclerView.Adapter<ExamHistoryAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<ExamHistoryResponse.ExamInfo> examInfos;
    private ExamHistoryAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(ExamHistoryResponse.ExamInfo position);
    }

    public void setOnItemClickListener(ExamHistoryAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public ExamHistoryAdapter(Context context, ArrayList<ExamHistoryResponse.ExamInfo> exampleList,ExamHistoryAdapter.OnItemClickListener listener) {
        mContext = context;
        examInfos = exampleList;
        mListener=listener;
    }

    @Override
    public ExamHistoryAdapter.ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.adapter_exam_history, parent, false);
        return new ExamHistoryAdapter.ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExamHistoryAdapter.ExampleViewHolder holder, int position) {
        try {
            ExamHistoryResponse.ExamInfo examInfo = examInfos.get(position);
            if (examInfo.getModeltest()!=null)
            holder.tvExamName.setText(""+examInfo.getModeltest().getName());
            holder.tvTotalMark.setText(""+examInfo.getTotalQuestions());
            holder.tvObtainedMark.setText(""+examInfo.getPoint());
            holder.tvDate.setText(""+examInfo.getCreatedAt());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return examInfos.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView tvExamName,tvDate,tvTotalMark,tvObtainedMark;
        Button btnSolveSheet;
        public LinearLayout llExam;
        public ExampleViewHolder(View itemView) {
            super(itemView);
            tvExamName = (TextView) itemView.findViewById(R.id.tvExamName);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvTotalMark = (TextView) itemView.findViewById(R.id.tvTotalMark);
            tvObtainedMark = (TextView) itemView.findViewById(R.id.tvObtainedMark);
            btnSolveSheet = (Button) itemView.findViewById(R.id.btnSolveSheet);
            // llExam = (TextView) itemView.findViewById(R.id.tvModelTextTime);
            btnSolveSheet.setOnClickListener(new View.OnClickListener() {
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