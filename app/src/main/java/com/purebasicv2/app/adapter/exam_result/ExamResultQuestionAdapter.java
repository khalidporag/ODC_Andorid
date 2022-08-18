package com.purebasicv2.app.adapter.exam_result;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.purebasicv2.app.R;
import com.purebasicv2.app.model.ModelTestResultQuestionItems;
import java.util.List;

public class ExamResultQuestionAdapter extends RecyclerView.Adapter<ExamResultQuestionAdapter.ItemViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<ModelTestResultQuestionItems> itemList;

    public ExamResultQuestionAdapter(List<ModelTestResultQuestionItems> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_live_exam_parent_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        ModelTestResultQuestionItems item = itemList.get(i);
        itemViewHolder.tvLEWName.setText(item.getQuestionTitle());
        itemViewHolder.tvLEWDetails.setText(item.getDetails());
        itemViewHolder.tvLEWDetails.setVisibility(View.VISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(itemViewHolder.rvLEQuestionOptions.getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setInitialPrefetchItemCount(item.getOptions().size());
        itemViewHolder.rvLEQuestionOptions.setLayoutManager(layoutManager);
        itemViewHolder.rvLEQuestionOptions.setRecycledViewPool(viewPool);

        switch (item.getIs_multi()){
            case 0:
                itemViewHolder.tvLEWType.setText("SBA");
                ExamResultOptionsAdapter subItemAdapter = new ExamResultOptionsAdapter(item.getOptions());
                itemViewHolder.rvLEQuestionOptions.setAdapter(subItemAdapter);
                break;
            case 1:
                itemViewHolder.lnrLEWParent.setBackgroundColor(itemViewHolder.itemView.getContext().getResources().getColor(R.color.whiteLight));
                itemViewHolder.tvLEWType.setText("MCQ");
                ExamResultOptionsMultiAdapter modelTestQuestionOptionsMultiAdapter = new ExamResultOptionsMultiAdapter(item.getOptions());
                itemViewHolder.rvLEQuestionOptions.setAdapter(modelTestQuestionOptionsMultiAdapter);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvLEWName,tvLEWType,tvLEWDetails;
        private RecyclerView rvLEQuestionOptions;
        private LinearLayout lnrLEWParent;

        ItemViewHolder(View itemView) {
            super(itemView);
            lnrLEWParent = itemView.findViewById(R.id.lnrLEWParent);
            tvLEWName = itemView.findViewById(R.id.tvLEWName);
            tvLEWDetails = itemView.findViewById(R.id.tvLEWDetails);
            tvLEWType = itemView.findViewById(R.id.tvLEWType);
            rvLEQuestionOptions = itemView.findViewById(R.id.rvLEQuestionOptions);
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }
}