package com.purebasicv2.app.adapter.exam;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.purebasicv2.app.R;
import com.purebasicv2.app.model.ExamList;
import com.purebasicv2.app.utils.AppConstants;

import java.util.List;

public class ExamListAdapter extends RecyclerView.Adapter<ExamListAdapter.MyViewHolder> {

    private Context context;
    private List<ExamList> examLists;
  //  private List<BatchRoot.Batch> BatchListFiltered;
    private ExamListAdapter.ExamListAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvExamName;
        public TextView tvTotalMinutes;
        public TextView tvBatchName;
        public TextView tvTotalMark;
        public TextView tvCourseFee;
        public TextView tvDueAmount;
        public TextView tvStatus;
        public LinearLayoutCompat layoutDueAmount;
        public Button btnStartExam;

        public MyViewHolder(View view) {
            super(view);
            tvExamName = view.findViewById(R.id.tvExamName);
            tvTotalMinutes = view.findViewById(R.id.tvTotalMinutes);
            tvBatchName = view.findViewById(R.id.tvBatchName);
            tvTotalMark = view.findViewById(R.id.tvTotalMark);

            btnStartExam = view.findViewById(R.id.btnStartExam);
            btnStartExam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        if (examLists.get(getAdapterPosition()).getAlreadyTaken()) {
                            listener.onExamResultSelected(examLists.get(getAdapterPosition()), AppConstants.GO_TO_ALL_SUBJECT);
                        } else {
                            listener.onExamSelected(examLists.get(getAdapterPosition()), AppConstants.GO_TO_ALL_SUBJECT);

                        }
                    }
                }
            });
        }
    }

    public ExamListAdapter(Context context, List<ExamList> examLists, ExamListAdapter.ExamListAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.examLists = examLists;
       // this.BatchListFiltered = BatchItems;
    }

    @NonNull
    @Override
    public ExamListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_exam_list, parent, false);
        return new ExamListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExamListAdapter.MyViewHolder holder, final int position) {
        final ExamList examList = examLists.get(position);
        try {
            if (examList.getName() != null) {
                holder.tvExamName.setText(examList.getName());
                holder.tvTotalMinutes.setText(examList.getExTime()+ " minutes");
                holder.tvBatchName.setText(examList.getBatchName());
                holder.tvTotalMark.setText("Total Mark: "+examList.getExamInMinutes().toString());
                if (examList.getAlreadyTaken()){
                    holder.btnStartExam.setText("See Result");
                    holder.btnStartExam.setBackground(ContextCompat.getDrawable(context, R.drawable.goto_exam_button_color));
                    holder.btnStartExam.setTextColor(Color.parseColor("#f9a90a"));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return examLists.size();
    }

    public interface ExamListAdapterListener {
        void onExamSelected(ExamList examList, int whatToDo);
        void onExamResultSelected(ExamList examList, int whatToDo);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void addAll(List<ExamList> examLists) {
//        BatchListFiltered = new ArrayList<>();
        //BatchListFiltered = (batchList);
        notifyDataSetChanged();
    }
}
