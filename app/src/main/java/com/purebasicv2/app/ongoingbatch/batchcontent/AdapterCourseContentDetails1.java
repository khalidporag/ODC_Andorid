package com.purebasicv2.app.ongoingbatch.batchcontent;

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

import java.util.List;

public class AdapterCourseContentDetails1 extends RecyclerView.Adapter<AdapterCourseContentDetails1.MyViewHolder>{


    RecyclerView rvCourseContent;
    Context mContext;
    private List<CourseContentResponse.Chapter> chapters;


    public AdapterCourseContentDetails1(Context context, List<CourseContentResponse.Chapter> lectureDetailList) {
        this.mContext = context;
        //    this.listener = listener;
        this.chapters = lectureDetailList;
    }

    @NonNull
    @Override
    public AdapterCourseContentDetails1.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_course_content_details, parent, false);
        return new AdapterCourseContentDetails1.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCourseContentDetails1.MyViewHolder holder, int position) {
        CourseContentResponse.Chapter chapter = chapters.get(position);
        holder.tvChapTitle.setText("Chapter "+(position+1)+": "+chapter.getChapterName()+" (Total Lecures: "+chapter.getLectureList().size()+" )");

        holder.rvCourseContentDetai2.setVisibility(View.VISIBLE);
        holder.rvCourseContentDetai2.setLayoutManager(new LinearLayoutManager(holder.rvCourseContentDetai2.getContext()));
        AdapterCourseContentDetails2 adapterCourseContentDetails2 = new AdapterCourseContentDetails2(chapters.get(position).getLectureList());
        holder.rvCourseContentDetai2.setAdapter(adapterCourseContentDetails2);
        holder.rvCourseContentDetai2.getAdapter().notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvChapTitle;
        RelativeLayout rlMain,rlExpand;
        RecyclerView rvCourseContentDetai2;
        public MyViewHolder(View view) {
            super(view);
            tvChapTitle = view.findViewById(R.id.tvChapTitle);
            rvCourseContentDetai2 = view.findViewById(R.id.rvCourseContentDetai2);



        }
    }
}
