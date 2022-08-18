package com.purebasicv2.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.purebasicv2.app.R;
import com.purebasicv2.app.model.ModelTestItems;

import java.util.ArrayList;


public class ModelTestAdapter extends RecyclerView.Adapter<ModelTestAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<ModelTestItems> mTopicItem;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ModelTestAdapter(Context context, ArrayList<ModelTestItems> exampleList) {
        mContext = context;
        mTopicItem = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.adapter_model_test_item, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        ModelTestItems currentItem = mTopicItem.get(position);
        holder.tvModelTextName.setText(currentItem.getName());
        if (currentItem.getExam_time() != 404){
            holder.tvModelTextTime.setText(String.format("%d minute", currentItem.getExam_time()));
        } else {
            holder.tvModelTextTime.setText("");
        }

    }

    @Override
    public int getItemCount() {
        return mTopicItem.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView tvModelTextName,tvModelTextTime;
        public ExampleViewHolder(View itemView) {
            super(itemView);
            tvModelTextName = (TextView) itemView.findViewById(R.id.tvModelTextName);
            tvModelTextTime = (TextView) itemView.findViewById(R.id.tvModelTextTime);
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
