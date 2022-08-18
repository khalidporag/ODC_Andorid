package com.purebasicv2.app.ongoingbatch.batchcontent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.purebasicv2.app.R;
import com.purebasicv2.app.model.CourseContentResponse;

import java.util.List;

public class AdapterCourseContentDetails2 extends RecyclerView.Adapter<AdapterCourseContentDetails2.MyViewHolder>{

    RecyclerView rvCourseContent;
    Context mContext;
    private List<CourseContentResponse.Lecture> lectures;


    public AdapterCourseContentDetails2(List<CourseContentResponse.Lecture> lectureDetailList) {
    //    this.mContext = context;
        //    this.listener = listener;
        this.lectures = lectureDetailList;
    }
    @NonNull
    @Override
    public AdapterCourseContentDetails2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_course_content_details2, parent, false);
        return new AdapterCourseContentDetails2.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCourseContentDetails2.MyViewHolder holder, int position) {

        holder.tvLecTitle.setText("Lecture "+(position+1)+": "+lectures.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return lectures.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvLecTitle;
        public MyViewHolder(View view) {
            super(view);
            tvLecTitle = view.findViewById(R.id.tvLecTitle);



        }
    }
}