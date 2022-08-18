package com.purebasicv2.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.purebasicv2.app.R;
import com.purebasicv2.app.model.CategoryLectureItems;
import java.util.ArrayList;


public class CategoryLectureAdapter extends RecyclerView.Adapter<CategoryLectureAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<CategoryLectureItems> mTopicItem;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public CategoryLectureAdapter(Context context, ArrayList<CategoryLectureItems> exampleList) {
        mContext = context;
        mTopicItem = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.adapter_category_lecture_item, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        CategoryLectureItems currentItem = mTopicItem.get(position);
        holder.tvCategoryLectureTitle.setText(currentItem.getName());
        if (currentItem.getType().equals("premium")){
            holder.lytLecturePremium.setVisibility(View.VISIBLE);
        }
        holder.tvCategoryLectureDate.setText(currentItem.getDate());
    }

    @Override
    public int getItemCount() {
        return mTopicItem.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCategoryLectureTitle,tvCategoryLectureDate;
        public LinearLayout lytLecturePremium;
        public ExampleViewHolder(View itemView) {
            super(itemView);
            tvCategoryLectureTitle = (TextView) itemView.findViewById(R.id.tvCategoryLectureTitle);
            tvCategoryLectureDate = (TextView) itemView.findViewById(R.id.tvCategoryLectureDate);
            lytLecturePremium = (LinearLayout) itemView.findViewById(R.id.lytLecturePremium);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
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
