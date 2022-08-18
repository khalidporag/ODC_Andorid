package com.purebasicv2.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.purebasicv2.app.R;
import com.purebasicv2.app.model.ModelTestResultMerit;
import java.util.List;


public class ModelTestMeritAdapter extends RecyclerView.Adapter<ModelTestMeritAdapter.ExampleViewHolder> {
    private Context mContext;
    private List<ModelTestResultMerit> mTopicItem;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ModelTestMeritAdapter(Context context, List<ModelTestResultMerit> exampleList) {
        mContext = context;
        mTopicItem = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.adapter_model_merit_item, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        ModelTestResultMerit currentItem = mTopicItem.get(position);
        holder.tvModelMeritPosition.setText(String.valueOf(currentItem.getSerialNumber()));
        holder.tvModelMeritName.setText(currentItem.getName());
        holder.tvModelMeritPoint.setText(currentItem.getPoint());
    }

    @Override
    public int getItemCount() {
        return mTopicItem.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView tvModelMeritPosition,tvModelMeritName,tvModelMeritPoint;
        public ExampleViewHolder(View itemView) {
            super(itemView);
            tvModelMeritPosition = (TextView) itemView.findViewById(R.id.tvModelMeritPosition);
            tvModelMeritName = (TextView) itemView.findViewById(R.id.tvModelMeritName);
            tvModelMeritPoint = (TextView) itemView.findViewById(R.id.tvModelMeritPoint);
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
