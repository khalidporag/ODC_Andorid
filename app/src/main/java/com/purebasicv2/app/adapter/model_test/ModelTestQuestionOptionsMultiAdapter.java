package com.purebasicv2.app.adapter.model_test;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.purebasicv2.app.R;
import com.purebasicv2.app.model.ModelTestOptionItems;
import java.util.List;

public class ModelTestQuestionOptionsMultiAdapter extends RecyclerView.Adapter<ModelTestQuestionOptionsMultiAdapter.SubItemViewHolderMulti> {

    private final List<ModelTestOptionItems> subItemListMulti;
    private int indexMulti = -1;
    private final List<Integer> optionsIds;

    ModelTestQuestionOptionsMultiAdapter(List<ModelTestOptionItems> subItemList, List<Integer> options) {
        this.subItemListMulti = subItemList;
        this.optionsIds = options;
    }

    @NonNull
    @Override
    public SubItemViewHolderMulti onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_live_exam_option_multi, viewGroup, false);
        return new SubItemViewHolderMulti(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SubItemViewHolderMulti subItemViewHolderMulti, final int position) {
        final ModelTestOptionItems optionsMulti = subItemListMulti.get(position);
        subItemViewHolderMulti.tvLEOptionName.setText(optionsMulti.getOptionName());

        subItemViewHolderMulti.cbLEOption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                //buttonView.setEnabled(false);
                if (isChecked){
                    optionsIds.add(optionsMulti.getOptionId());
                    indexMulti = position;
                } else {
                    optionsIds.remove((Integer) optionsMulti.getOptionId());
                }
            }
        });

        subItemViewHolderMulti.cbLEOption.setChecked(indexMulti == position);

    }

    @Override
    public int getItemCount() {
        return subItemListMulti.size();
    }

    static class SubItemViewHolderMulti extends RecyclerView.ViewHolder {
        TextView tvLEOptionName;
        CheckBox cbLEOption;

        SubItemViewHolderMulti(View itemView) {
            super(itemView);
            tvLEOptionName = itemView.findViewById(R.id.tvLEOptionName);
            cbLEOption = itemView.findViewById(R.id.cbLEOption);
        }
    }
    @Override
    public int getItemViewType(int position)
    {
        return position;
    }
}