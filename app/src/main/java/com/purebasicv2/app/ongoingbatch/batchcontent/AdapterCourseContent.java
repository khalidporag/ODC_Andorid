package com.purebasicv2.app.ongoingbatch.batchcontent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.purebasicv2.app.R;
import com.purebasicv2.app.model.CourseContentResponse;
import com.purebasicv2.app.model.OngoingBatch;

import java.util.ArrayList;
import java.util.List;

public class AdapterCourseContent extends RecyclerView.Adapter<AdapterCourseContent.MyViewHolder> {
    private Context context;
    private List<CourseContentResponse.LectureDetail> lectureDetailList;
    private AdapterCourseContent.CourseContentAdapterListener listener;
    int count=1;

    interface CourseContentAdapterListener {
        void onSubjectClick(OngoingBatch batch);

       // void onCourseContentClick(OngoingBatch ongoingBatch);
    }

    public AdapterCourseContent(Context context, List<CourseContentResponse.LectureDetail> lectureDetailList) {
        this.context = context;
    //    this.listener = listener;
        this.lectureDetailList = lectureDetailList;
    }
    @NonNull
    @Override
    public AdapterCourseContent.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_course_content, parent, false);
        return new AdapterCourseContent.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCourseContent.MyViewHolder holder, int position) {
         CourseContentResponse.LectureDetail lectureDetail = lectureDetailList.get(position);
         holder.tvSubTitle.setText("Subject "+(position+1)+": "+lectureDetail.getSubjectName());
        if (lectureDetail.isExpanded()) {
            holder.rvCourseContentDetail1.setVisibility(View.VISIBLE);
            holder.rvCourseContentDetail1.setLayoutManager(new LinearLayoutManager(holder.rvCourseContentDetail1.getContext()));
            AdapterCourseContentDetails1 adapterCourseContentDetails1 = new AdapterCourseContentDetails1(context, lectureDetail.getChapterList());
            holder.rvCourseContentDetail1.setAdapter(adapterCourseContentDetails1);
            holder.rvCourseContentDetail1.getAdapter().notifyDataSetChanged();

            holder.rlMain.setBackgroundColor(context.getResources().getColor(R.color.black));
            holder.tvSubTitle.setTextColor(context.getResources().getColor(R.color.white));
            holder.rlExpand.setBackgroundColor(context.getResources().getColor(R.color.red));
            holder.ivExpand.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_minus_24));


        } else {
            holder.rvCourseContentDetail1.setVisibility(View.GONE);

            holder.rlMain.setBackgroundColor(context.getResources().getColor(R.color.light_gray));
            holder.tvSubTitle.setTextColor(context.getResources().getColor(R.color.black));
            holder.rlExpand.setBackgroundColor(context.getResources().getColor(R.color.light_gray));
            holder.ivExpand.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_add_32));
        }

    }

    @Override
    public int getItemCount() {
        return lectureDetailList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSubTitle;
        RelativeLayout rlMain,rlExpand;
        RecyclerView rvCourseContentDetail1;
        ImageView ivExpand;

        public List<CourseContentResponse.Chapter> chapters=new ArrayList<>();
        public MyViewHolder(View view) {
            super(view);
            tvSubTitle = view.findViewById(R.id.tvSubTitle);
            rlMain = view.findViewById(R.id.rlMain);
            rlExpand = view.findViewById(R.id.rlExpand);
            rvCourseContentDetail1 = view.findViewById(R.id.rvCourseContentDetail1);
            ivExpand = view.findViewById(R.id.ivExpand);

//            CourseContentResponse.LectureDetail lectureDetail = lectureDetailList.get(getAdapterPosition());
//            rvCourseContentDetail1.setLayoutManager(new LinearLayoutManager(context));
//            AdapterCourseContentDetails1 adapterCourseContentDetails1 = new AdapterCourseContentDetails1(context, lectureDetail.getChapterList());
//            rvCourseContentDetail1.setAdapter(adapterCourseContentDetails1);

            rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        CourseContentResponse.LectureDetail lectureDetail = lectureDetailList.get(getAdapterPosition());
                        if (lectureDetail.isExpanded()) {
                            lectureDetail.setExpanded(false);
                        } else {
                            lectureDetail.setExpanded(true);
                        }
                        lectureDetailList.set(getAdapterPosition(), lectureDetail);
                        notifyItemChanged(getAdapterPosition());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


        }
    }





}
