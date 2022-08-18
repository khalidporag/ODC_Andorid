package com.purebasicv2.app.adapter.exam;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.purebasicv2.app.R;
import com.purebasicv2.app.model.ExamList;
import com.purebasicv2.app.model.ResultList;
import com.purebasicv2.app.ongoingbatch.batchcontent.AdapterCourseContentDetails1;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {

    private Context context;
    private List<ResultList.Question> resultLists;
    //  private List<BatchRoot.Batch> BatchListFiltered;
    private ResultAdapter.ResultAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvQuesCount,tvDetails;
        public TextView tvQues;
        LinearLayout llCbOptions,llRadioOptions;
        TextView cbOption1,cbOption2,cbOption3,cbOption4,cbOption5;
        RadioButton rbOption1,rbOption2,rbOption3,rbOption4,rbOption5;
        RecyclerView rvCorrectAns;



        public MyViewHolder(View view) {
            super(view);
            tvQuesCount = view.findViewById(R.id.tvQuesCount);
            tvQues = view.findViewById(R.id.tvQuestion);
            llCbOptions = view.findViewById(R.id.llCbOptions);
            tvDetails = view.findViewById(R.id.tvDetails);
            rvCorrectAns = view.findViewById(R.id.rvCorrectAnswer);
            llRadioOptions = view.findViewById(R.id.llRadioOptions);
            cbOption1 = view.findViewById(R.id.cbOption1);
            cbOption2 = view.findViewById(R.id.cbOption2);
            cbOption3 = view.findViewById(R.id.cbOption3);
            cbOption4 = view.findViewById(R.id.cbOption4);
            cbOption5 = view.findViewById(R.id.cbOption5);

            rbOption1 = view.findViewById(R.id.rbOption1);
            rbOption1 = view.findViewById(R.id.rbOption1);
            rbOption1 = view.findViewById(R.id.rbOption3);
            rbOption1 = view.findViewById(R.id.rbOption4);
            rbOption1 = view.findViewById(R.id.rbOption5);
        }
    }

    public ResultAdapter(Context context, List<ResultList.Question> resultLists, ResultAdapter.ResultAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.resultLists = resultLists;
        // this.BatchListFiltered = BatchItems;
    }

    @NonNull
    @Override
    public ResultAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_result_list, parent, false);
        return new ResultAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ResultAdapter.MyViewHolder holder, final int position) {
        final ResultList.Question data = resultLists.get(position);
        try {
            if (data.getQuestion() != null) {
                holder.tvQuesCount.setText(""+(position+1)+".");
                holder.tvQues.setText(data.getQuestion());
                holder.cbOption1.setText(data.getOptions().get(0).getOption());
                holder.cbOption2.setText(data.getOptions().get(1).getOption());
                holder.cbOption3.setText(data.getOptions().get(2).getOption());
                holder.cbOption4.setText(data.getOptions().get(3).getOption());
                holder.cbOption5.setText(data.getOptions().get(4).getOption());

                if (data.getOptions().get(0).getCorrectOrNot()>0){
                    holder.cbOption1.setBackgroundColor(Color.GREEN);
                }else if(data.getOptions().get(0).getIs_answered()>0){
                    holder.cbOption1.setBackgroundColor(Color.RED);
                }

                if (data.getOptions().get(1).getCorrectOrNot()>0){
                    holder.cbOption2.setBackgroundColor(Color.GREEN);
                }else if(data.getOptions().get(1).getIs_answered()>0){
                    holder.cbOption2.setBackgroundColor(Color.RED);
                }

                if (data.getOptions().get(2).getCorrectOrNot()>0){
                    holder.cbOption3.setBackgroundColor(Color.GREEN);
                }else if(data.getOptions().get(1).getIs_answered()>0){
                    holder.cbOption3.setBackgroundColor(Color.RED);
                }

                if (data.getOptions().get(3).getCorrectOrNot()>0){
                    holder.cbOption4.setBackgroundColor(Color.GREEN);
                }else if(data.getOptions().get(3).getIs_answered()>0){
                    holder.cbOption4.setBackgroundColor(Color.RED);
                }

                if (data.getOptions().get(4).getCorrectOrNot()>0){
                    holder.cbOption5.setBackgroundColor(Color.GREEN);
                }else if(data.getOptions().get(4).getIs_answered()>0){
                    holder.cbOption5.setBackgroundColor(Color.RED);
                }

                holder.tvDetails.setText("Details- "+resultLists.get(position).getDetailss());

                if (data.getCorrect_answer().size()>0){
                    holder.rvCorrectAns.setVisibility(View.VISIBLE);
                    holder.rvCorrectAns.setLayoutManager(new LinearLayoutManager(holder.rvCorrectAns.getContext()));
                    CorrectAnswerAdapter correctAnswerAdapter = new CorrectAnswerAdapter(context, data.getCorrect_answer());
                    holder.rvCorrectAns.setAdapter(correctAnswerAdapter);
                    holder.rvCorrectAns.getAdapter().notifyDataSetChanged();
                }else{
                    holder.rvCorrectAns.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return resultLists.size();
    }

    public interface ResultAdapterListener {
        void onExamSelected(ResultList.Question examList, int whatToDo);
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
