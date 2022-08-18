package com.purebasicv2.app.adapter.model_test;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.purebasicv2.app.R;
import com.purebasicv2.app.model.ModelTestQuestionItems;

import java.util.ArrayList;
import java.util.List;

public class ModelTestQuestionAdapter extends RecyclerView.Adapter<ModelTestQuestionAdapter.ItemViewHolder> {

    private final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private final List<ModelTestQuestionItems> itemList;
    private static OnItemClickListenerMulti mListenerMulti;

    public interface OnItemClickListenerMulti {
        void onItemClickMultiOption(int questionId, List<Integer> options);
    }

    public static void setOnItemClickListenerMulti(OnItemClickListenerMulti listener) {
        mListenerMulti = listener;
    }

    private static OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int optionId, int questionId);
    }

    public static void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ModelTestQuestionAdapter(List<ModelTestQuestionItems> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_live_exam_parent_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int i) {
        final ModelTestQuestionItems item = itemList.get(i);
        holder.tvLEWName.setText((i + 1)+". "+item.getQuestionTitle());
        holder.btnCheckSubmit.setVisibility(View.VISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.rvLEQuestionOptions.getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setInitialPrefetchItemCount(item.getOptions().size());
        holder.rvLEQuestionOptions.setLayoutManager(layoutManager);
        holder.rvLEQuestionOptions.setRecycledViewPool(viewPool);
        holder.overlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        switch (item.getIs_multi()){
            case 0:
                final ArrayList<RadioButton> radioButton = new ArrayList<>();
                holder.tvLEWType.setText("SBA");
                ModelTestQuestionOptionsAdapter subItemAdapter = new ModelTestQuestionOptionsAdapter(item.getOptions(), i, item.getId(),radioButton);
                holder.rvLEQuestionOptions.setAdapter(subItemAdapter);
                holder.btnCheckSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListenerMulti != null) {
                            if (i != RecyclerView.NO_POSITION) {
                                if (radioButton.size() != 0){
                                    mListener.onItemClick(radioButton.get(0).getId(),item.getId());
                                    holder.btnCheckSubmit.setVisibility(View.INVISIBLE);
                                    holder.overlay.setVisibility(View.VISIBLE);
                                    holder.itemView.setAlpha(0.4f);
                                } else {
                                    Toast.makeText(holder.itemView.getContext(), "No SBA Selected", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
                break;
            case 1:
                final List<Integer> optionsId = new ArrayList<>();
                holder.lnrLEWParent.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.whiteLight));
                holder.tvLEWType.setText("MCQ");
                ModelTestQuestionOptionsMultiAdapter modelTestQuestionOptionsMultiAdapter = new ModelTestQuestionOptionsMultiAdapter(item.getOptions(), optionsId);
                holder.rvLEQuestionOptions.setAdapter(modelTestQuestionOptionsMultiAdapter);
                holder.btnCheckSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListenerMulti != null) {
                            if (i != RecyclerView.NO_POSITION) {
                                mListenerMulti.onItemClickMultiOption(item.getId(), optionsId);
                                holder.btnCheckSubmit.setVisibility(View.INVISIBLE);
                                holder.overlay.setVisibility(View.VISIBLE);
                                holder.itemView.setAlpha(0.4f);
                            }
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvLEWName;
        private final TextView tvLEWType;
        private final RecyclerView rvLEQuestionOptions;
        private final View overlay;
        private final LinearLayout lnrLEWParent;
        private final ImageView btnCheckSubmit;

        ItemViewHolder(View itemView) {
            super(itemView);
            lnrLEWParent = itemView.findViewById(R.id.lnrLEWParent);
            tvLEWName = itemView.findViewById(R.id.tvLEWName);
            tvLEWType = itemView.findViewById(R.id.tvLEWType);
            rvLEQuestionOptions = itemView.findViewById(R.id.rvLEQuestionOptions);
            btnCheckSubmit = itemView.findViewById(R.id.btnCheckSubmit);
            overlay = itemView.findViewById(R.id.overlay);
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }
}