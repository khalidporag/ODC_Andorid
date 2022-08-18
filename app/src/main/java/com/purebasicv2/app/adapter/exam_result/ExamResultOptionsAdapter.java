package com.purebasicv2.app.adapter.exam_result;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.purebasicv2.app.R;
import com.purebasicv2.app.model.ModelTestResultOptionItems;
import java.util.List;

public class ExamResultOptionsAdapter extends RecyclerView.Adapter<ExamResultOptionsAdapter.SubItemViewHolder> {

    private List<ModelTestResultOptionItems> subItemList;

    ExamResultOptionsAdapter(List<ModelTestResultOptionItems> subItemList) {
        this.subItemList = subItemList;
    }

    @NonNull
    @Override
    public SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_live_exam_option_item, viewGroup, false);
        return new SubItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SubItemViewHolder subItemViewHolder, final int position) {
        final ModelTestResultOptionItems options = subItemList.get(position);
        subItemViewHolder.tvLEOptionName.setText(options.getOptionName());

        subItemViewHolder.rbLEOption.setEnabled(false);
        if (options.isAnswered()){
            subItemViewHolder.rbLEOption.setChecked(true);
            if (options.getCorrect() == 0){
                subItemViewHolder.tvLEOptionName.setPadding(5,0,5,0);
                subItemViewHolder.tvLEOptionName.setBackgroundColor(Color.RED);
                subItemViewHolder.tvLEOptionName.setTextColor(Color.WHITE);
            }
        }

        if (options.getCorrect() == 1){
            subItemViewHolder.tvLEOptionName.setPadding(5,0,5,0);
            subItemViewHolder.tvLEOptionName.setBackgroundColor(ContextCompat.getColor(subItemViewHolder.itemView.getContext(), R.color.greenDeep));
            subItemViewHolder.tvLEOptionName.setTextColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return subItemList.size();
    }

    static class SubItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvLEOptionName;
        RadioButton rbLEOption;

        SubItemViewHolder(View itemView) {
            super(itemView);
            tvLEOptionName = itemView.findViewById(R.id.tvLEOptionName);
            rbLEOption = itemView.findViewById(R.id.rbLEOption);
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }
}