package com.purebasicv2.app.activity.exam.question;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.purebasicv2.app.R;
import com.purebasicv2.app.model.ExamList;
import com.purebasicv2.app.utils.AppConstants;

import java.util.List;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.MyViewHolder> {

    private Context context;
    private List<ExamList> examLists;
  //  private List<BatchRoot.Batch> BatchListFiltered;
    private QuestionListAdapter.ExamListAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvExamName,tvTotalMinutes;
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
                    if (listener != null)
                        listener.onExamSelected(examLists.get(getAdapterPosition()), AppConstants.GO_TO_ALL_SUBJECT);
                }
            });
        }
    }

    public QuestionListAdapter(Context context, List<ExamList> examLists, QuestionListAdapter.ExamListAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.examLists = examLists;
       // this.BatchListFiltered = BatchItems;
    }

    @NonNull
    @Override
    public QuestionListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_exam_list, parent, false);
        return new QuestionListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuestionListAdapter.MyViewHolder holder, final int position) {
        final ExamList examList = examLists.get(position);
        try {
            if (examList.getName() != null) {
                holder.tvExamName.setText(examList.getName());
                holder.tvTotalMinutes.setText(examList.getExTime()+ " minutes");
                holder.tvBatchName.setText(examList.getBatchName());
                holder.tvTotalMark.setText("Total Mark: "+examList.getExamInMinutes().toString());


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
