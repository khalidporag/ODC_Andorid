package com.purebasicv2.app.adapter.exam_result;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.purebasicv2.app.R;
import com.purebasicv2.app.model.ModelTestResultOptionItems;
import java.util.List;

public class ExamResultOptionsMultiAdapter extends RecyclerView.Adapter<ExamResultOptionsMultiAdapter.SubItemViewHolderMulti> {

    private List<ModelTestResultOptionItems> subItemListMulti;

    ExamResultOptionsMultiAdapter(List<ModelTestResultOptionItems> subItemList) {
        this.subItemListMulti = subItemList;
    }

    @NonNull
    @Override
    public SubItemViewHolderMulti onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_live_exam_option_multi, viewGroup, false);
        return new SubItemViewHolderMulti(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SubItemViewHolderMulti subItemViewHolderMulti, final int position) {
        final ModelTestResultOptionItems optionsMulti = subItemListMulti.get(position);
        subItemViewHolderMulti.tvLEOptionName.setText(optionsMulti.getOptionName());

        subItemViewHolderMulti.cbLEOption.setEnabled(false);
        if (optionsMulti.isAnswered()){
            subItemViewHolderMulti.cbLEOption.setChecked(true);
            if (optionsMulti.getCorrect() == 0){
                subItemViewHolderMulti.tvLEOptionName.setPadding(5,0,5,0);
                subItemViewHolderMulti.tvLEOptionName.setBackgroundColor(Color.RED);
                subItemViewHolderMulti.tvLEOptionName.setTextColor(Color.WHITE);
            }
        }

        if (optionsMulti.getCorrect() == 1){
            subItemViewHolderMulti.tvLEOptionName.setPadding(5,0,5,0);
            subItemViewHolderMulti.tvLEOptionName.setBackgroundColor(ContextCompat.getColor(subItemViewHolderMulti.itemView.getContext(), R.color.greenDeep));
            subItemViewHolderMulti.tvLEOptionName.setTextColor(Color.WHITE);
        }
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