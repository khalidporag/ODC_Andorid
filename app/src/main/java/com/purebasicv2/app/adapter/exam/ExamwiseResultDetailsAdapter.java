package com.purebasicv2.app.adapter.exam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.purebasicv2.app.R;
import com.purebasicv2.app.model.ExamwiseResultDetailsResponse;

import java.util.ArrayList;

public class ExamwiseResultDetailsAdapter extends RecyclerView.Adapter<ExamwiseResultDetailsAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<ExamwiseResultDetailsResponse.ResultData> examInfos;
    private ExamwiseResultDetailsAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(ExamwiseResultDetailsResponse.ResultData position);
    }

    public void setOnItemClickListener(ExamwiseResultDetailsAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public ExamwiseResultDetailsAdapter(Context context, ArrayList<ExamwiseResultDetailsResponse.ResultData> exampleList,ExamwiseResultDetailsAdapter.OnItemClickListener listener) {
        mContext = context;
        examInfos = exampleList;
        mListener=listener;
    }

    @Override
    public ExamwiseResultDetailsAdapter.ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.adapter_exam_wise_result_details_new, parent, false);
        return new ExamwiseResultDetailsAdapter.ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExamwiseResultDetailsAdapter.ExampleViewHolder holder, int position) {
        ExamwiseResultDetailsResponse.ResultData examInfo = examInfos.get(position);
    //    holder.tvId.setText(""+examInfo.getStudentId());
        try {
            if (examInfo.getStudents()!=null) {
                holder.tvName.setText("" + examInfo.getStudents().getName());
                holder.txtStudentId.setText("Student id: "+examInfo.getStudentId());
                holder.tvObtained.setText("" + examInfo.getPoint());
                holder.tvPos.setText("" + examInfo.getRank());
                holder.tvDate.setText("" + examInfo.getStudents().getUpdatedAt());
            }
            else{
                holder.llMain.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return examInfos.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView txtStudentId,tvName,tvObtained,tvPos,tvDate;
        public LinearLayout llMain;
        public ExampleViewHolder(View itemView) {
            super(itemView);
        //    tvId = (TextView) itemView.findViewById(R.id.tvId);
            tvName = (TextView) itemView.findViewById(R.id.txtName);
            txtStudentId = (TextView) itemView.findViewById(R.id.txtStudentId);
            tvObtained = (TextView) itemView.findViewById(R.id.txtObtained);
            tvPos = (TextView) itemView.findViewById(R.id.txtPos);
            tvDate = (TextView) itemView.findViewById(R.id.txtDate);
            llMain = (LinearLayout) itemView.findViewById(R.id.llMain);
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