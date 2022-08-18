package com.purebasicv2.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.purebasicv2.app.R;
import com.purebasicv2.app.model.PaymentHistoryResponse;

import java.util.ArrayList;


    public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.ExampleViewHolder> {
        private Context mContext;
        private ArrayList<PaymentHistoryResponse.Data> paymentHistoryList;
        private PaymentHistoryAdapter.OnItemClickListener mListener;

        public interface OnItemClickListener {
            void onItemClick(PaymentHistoryResponse.Data position);
        }

        public void setOnItemClickListener(PaymentHistoryAdapter.OnItemClickListener listener) {
            mListener = listener;
        }

        public PaymentHistoryAdapter(Context context, ArrayList<PaymentHistoryResponse.Data> paymentHistoryList, PaymentHistoryAdapter.OnItemClickListener listener) {
            mContext = context;
            this.paymentHistoryList = paymentHistoryList;
            mListener=listener;
        }

        @Override
        public PaymentHistoryAdapter.ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.adapter_payment_history, parent, false);
            return new PaymentHistoryAdapter.ExampleViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ExampleViewHolder holder, int position) {
            PaymentHistoryResponse.Data paymentHistoryData = paymentHistoryList.get(position);
            holder.tvBatchName.setText(""+paymentHistoryData.getBatch().getPlan());
            holder.tvPaidAmount.setText(""+paymentHistoryData.getAmount());
            holder.tvMarchent.setText(""+paymentHistoryData.getMar());
            holder.tvSubscriptionDays.setText(""+paymentHistoryData.getBatch().getSubscriptionDays());

            if (paymentHistoryData.getIsApprove()==0){
                holder.tvPaymentStatus.setText("Pending");
                holder.tvPaymentStatus.setTextColor(mContext.getResources().getColor(R.color.colorOrangeLight));
            }else if(paymentHistoryData.getIsApprove()==1){
                holder.tvPaymentStatus.setText("Approved");
                holder.tvPaymentStatus.setTextColor(mContext.getResources().getColor(R.color.green));
            }else{
                holder.tvPaymentStatus.setText("Rejected");
                holder.tvPaymentStatus.setTextColor(mContext.getResources().getColor(R.color.red));
            }
        }

        @Override
        public int getItemCount() {
            return paymentHistoryList.size();
        }

        public class ExampleViewHolder extends RecyclerView.ViewHolder {
            public TextView tvBatchName,tvPaidAmount,tvMarchent,tvSubscriptionDays,tvPaymentStatus;
            Button btnSolveSheet;
            public LinearLayout llExam;
            public ExampleViewHolder(View itemView) {
                super(itemView);
                tvBatchName = (TextView) itemView.findViewById(R.id.tvBatchName);
                tvPaidAmount = (TextView) itemView.findViewById(R.id.tvPaidAmount);
                tvMarchent = (TextView) itemView.findViewById(R.id.tvMarchent);
                tvSubscriptionDays = (TextView) itemView.findViewById(R.id.tvSubscriptionDays);
                tvPaymentStatus = (TextView) itemView.findViewById(R.id.tvPaymentStatus);
                // llExam = (TextView) itemView.findViewById(R.id.tvModelTextTime);
//                btnSolveSheet.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (mListener != null) {
//                            int position = getAdapterPosition();
//                            if (position != RecyclerView.NO_POSITION) {
//                                mListener.onItemClick(paymentHistoryList.get(getAdapterPosition()));
//                            }
//                        }
//                    }
//                });
            }
        }

        @Override
        public int getItemViewType(int position)
        {
            return position;
        }
    }

