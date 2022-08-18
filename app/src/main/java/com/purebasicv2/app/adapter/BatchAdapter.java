package com.purebasicv2.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.purebasicv2.app.R;
import com.purebasicv2.app.model.BatchRoot;
import com.purebasicv2.app.utils.AppConstants;


import java.util.List;

public class BatchAdapter extends RecyclerView.Adapter<BatchAdapter.MyViewHolder> {
    private Context context;
    private List<BatchRoot.Batch> BatchItems;
    private List<BatchRoot.Batch> BatchListFiltered;
    private BatchAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvBatchName;
        public TextView tvBatchType;
        public TextView tvBatchGraduation;
        public TextView tvSubscription;
        public TextView tvCourseFee;
        public TextView tvDueAmount;
        public TextView tvStatus;
        public LinearLayoutCompat layoutDueAmount;
        public Button btnGotoLecture;
        public Button btnExam,btnPayNow;

        public MyViewHolder(View view) {
            super(view);
            tvBatchName = view.findViewById(R.id.tvBatchPlan);
            tvBatchType = view.findViewById(R.id.tvBatchType);
            tvBatchGraduation = view.findViewById(R.id.tvBatchGraduation);
            tvSubscription = view.findViewById(R.id.tvSubscription);
            tvCourseFee = view.findViewById(R.id.tvCourseFee);
            tvDueAmount = view.findViewById(R.id.tvDueAmount);
            tvStatus = view.findViewById(R.id.tvStatus);
            layoutDueAmount = view.findViewById(R.id.layoutDueAmount);
            btnPayNow = view.findViewById(R.id.btnPayNow);
            btnGotoLecture = view.findViewById(R.id.btnLecture);
            btnExam = view.findViewById(R.id.btnExam);
            btnGotoLecture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                        listener.onBatchSelected(BatchListFiltered.get(getAdapterPosition()), AppConstants.GO_TO_ALL_SUBJECT);
                }
            });

        btnExam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                        listener.onBatchSelected(BatchListFiltered.get(getAdapterPosition()), AppConstants.GO_TO_EXAM_BY_BATCH);
                }
            });

            btnPayNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onPayNowButtonClicked(BatchListFiltered.get(getAdapterPosition()), AppConstants.GO_TO_EXAM_BY_BATCH);
                }
            });
        }
    }

    public BatchAdapter(Context context, List<BatchRoot.Batch> BatchItems, BatchAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.BatchItems = BatchItems;
        this.BatchListFiltered = BatchItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_batch_row_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final BatchRoot.Batch batch = BatchListFiltered.get(position);
        try {
            if (batch.getCourse() != null) {
                holder.tvBatchName.setText(batch.getCourse().getPlan());
                holder.tvBatchGraduation.setText("course for " + batch.getCourse().getGraduation());
                holder.tvSubscription.setText(batch.getCourse().getSubscription_days() + " Days");
                holder.tvCourseFee.setText(batch.getCourse().getAmmount() + " BDT");
                Boolean isDue = ((batch.getFees() - batch.getPaid()) > 0);
                if (isDue) {
                    holder.layoutDueAmount.setVisibility(View.VISIBLE);
                    holder.tvDueAmount.setText((batch.getFees() - batch.getPaid()) + " BDT");
                } else {
                    holder.layoutDueAmount.setVisibility(View.GONE);
                }
                if (batch.getEnroll_status() == 1) {
                    holder.tvStatus.setText("Approved");
                } else {
                    holder.tvStatus.setText("Pending");
                }
                String upperString = batch.getCourse().getType().substring(0, 1).toUpperCase() + batch.getCourse().getType().substring(1).toLowerCase();
                holder.tvBatchType.setText(upperString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return BatchListFiltered.size();
    }

    public interface BatchAdapterListener {
        void onBatchSelected(BatchRoot.Batch contact, int whatToDo);
        void onPayNowButtonClicked(BatchRoot.Batch contact, int whatToDo);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void addAll(List<BatchRoot.Batch> batchList) {
//        BatchListFiltered = new ArrayList<>();
        BatchListFiltered = (batchList);
        notifyDataSetChanged();
    }
}