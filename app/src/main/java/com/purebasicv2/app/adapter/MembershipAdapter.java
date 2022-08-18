package com.purebasicv2.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.purebasicv2.app.R;
import com.purebasicv2.app.model.MembershipItem;

import java.util.ArrayList;


public class MembershipAdapter extends RecyclerView.Adapter<MembershipAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<MembershipItem> mTopicItem;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MembershipAdapter(Context context, ArrayList<MembershipItem> exampleList) {
        mContext = context;
        mTopicItem = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.adapter_membership_item, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        MembershipItem currentItem = mTopicItem.get(position);
        holder.tvMName.setText(currentItem.getName());
        holder.tvMAmount.setText(currentItem.getAmount()+" BDT");
        holder.tvMType.setText("Type: "+currentItem.getType());
        holder.tvMGraduate.setText("Graduate: "+currentItem.getGraduation());
        holder.tvMDuration.setText("Duration: "+currentItem.getDuration());
    }

    @Override
    public int getItemCount() {
        return mTopicItem.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMName,tvMAmount,tvMType,tvMGraduate,tvMDuration;
        public Button btnGetStart;
        public ExampleViewHolder(View itemView) {
            super(itemView);
            tvMName = (TextView) itemView.findViewById(R.id.tvMName);
            tvMAmount = (TextView) itemView.findViewById(R.id.tvMAmount);
            tvMType = (TextView) itemView.findViewById(R.id.tvMType);
            tvMGraduate = (TextView) itemView.findViewById(R.id.tvMGraduate);
            tvMDuration = (TextView) itemView.findViewById(R.id.tvMDuration);
            btnGetStart = (Button) itemView.findViewById(R.id.btnGetStart);
            btnGetStart.setOnClickListener(new View.OnClickListener() {
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
}
