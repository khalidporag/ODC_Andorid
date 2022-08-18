package com.purebasicv2.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.purebasicv2.app.R;
import com.purebasicv2.app.model.ModelTestResultMerit;
import com.purebasicv2.app.model.NoticeResponse;

import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ExampleViewHolder> {
    private Context mContext;
    private List<NoticeResponse.Datum> notices;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public NoticeAdapter(Context context, List<NoticeResponse.Datum> exampleList, OnItemClickListener listener) {
        mContext = context;
        notices = exampleList;
        this.mListener=listener;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_notice, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        NoticeResponse.Datum currentItem = notices.get(position);
        holder.tvTitle.setText(currentItem.getNotice());
        holder.tvDate.setText(currentItem.getCreatedAt());

    }

    @Override
    public int getItemCount() {
        return notices.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle,tvDate;
        public ExampleViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);

        }
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }
}
