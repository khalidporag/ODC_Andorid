package com.purebasicv2.app.adapter.model_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.purebasicv2.app.R;
import com.purebasicv2.app.model.OfflineVideoItems;

import java.util.ArrayList;


public class OfflineVideoAdapter extends RecyclerView.Adapter<OfflineVideoAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<OfflineVideoItems> mTopicItem;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public OfflineVideoAdapter(Context context, ArrayList<OfflineVideoItems> exampleList) {
        mContext = context;
        mTopicItem = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.adapter_offline_video_item, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        OfflineVideoItems currentItem = mTopicItem.get(position);
        holder.tvVideoTitle.setText(currentItem.getName());
        //holder.imgOfflineVideoThumb.setImageBitmap(currentItem.getBitmap());

        Glide.with(mContext).load(currentItem.getThumb()).into(holder.imgOfflineVideoThumb);
    }

    @Override
    public int getItemCount() {
        return mTopicItem.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView tvVideoTitle;
        public ImageView imgOfflineVideoThumb;
        public ExampleViewHolder(View itemView) {
            super(itemView);
            tvVideoTitle = (TextView) itemView.findViewById(R.id.tvVideoTitle);
            imgOfflineVideoThumb = (ImageView) itemView.findViewById(R.id.imgOfflineVideoThumb);
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
