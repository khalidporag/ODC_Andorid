package com.purebasicv2.app.adapter.model_test;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.purebasicv2.app.R;
import com.purebasicv2.app.model.ModelTestOptionItems;
import java.util.List;

public class ModelTestQuestionOptionsAdapter extends RecyclerView.Adapter<ModelTestQuestionOptionsAdapter.SubItemViewHolder> {

    private List<ModelTestOptionItems> subItemList;
    private final List<RadioButton> radioButtons;

    ModelTestQuestionOptionsAdapter(List<ModelTestOptionItems> subItemList, int questionPosition, int qId, List<RadioButton> options) {
        this.subItemList = subItemList;
        this.radioButtons = options;
    }

    @NonNull
    @Override
    public SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_live_exam_option_item, viewGroup, false);
        return new SubItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SubItemViewHolder subItemViewHolder, final int position) {
        final ModelTestOptionItems options = subItemList.get(position);
        subItemViewHolder.tvLEOptionName.setText(options.getOptionName());
        subItemViewHolder.rbLEOption.setId(options.getOptionId());
        subItemViewHolder.rbLEOption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //buttonView.setEnabled(false);
                if (isChecked){
                    Log.d("7070", "onClick-111: "+options.getOptionId());
                    for (int i=0;i<radioButtons.size();i++){
                        radioButtons.get(i).setChecked(false);
                    }
                    radioButtons.clear();
                    radioButtons.add(subItemViewHolder.rbLEOption);
                }
            }
        });
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