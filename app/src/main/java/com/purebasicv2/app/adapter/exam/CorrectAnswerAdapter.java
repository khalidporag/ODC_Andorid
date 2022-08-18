package com.purebasicv2.app.adapter.exam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.purebasicv2.app.R;
import com.purebasicv2.app.model.CourseContentResponse;
import com.purebasicv2.app.ongoingbatch.batchcontent.AdapterCourseContentDetails2;

import java.util.List;

public class CorrectAnswerAdapter extends RecyclerView.Adapter<CorrectAnswerAdapter.MyViewHolder>{


    RecyclerView rvCourseContent;
    Context mContext;
    private List<String> answers;
   // int count=1;

    public CorrectAnswerAdapter(Context context, List<String> answers) {
        this.mContext = context;
        //    this.listener = listener;
        this.answers= answers;
    }

    @NonNull
    @Override
    public CorrectAnswerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_correct_ans, parent, false);
        return new CorrectAnswerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CorrectAnswerAdapter.MyViewHolder holder, int position) {

        int count=position+1;
        holder.tvAns.setText(count+". "+answers.get(position));


    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvAns;
        public MyViewHolder(View view) {
            super(view);
            tvAns = view.findViewById(R.id.tvAns);



        }
    }
}

