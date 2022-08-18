package com.purebasicv2.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.purebasicv2.app.R;
import com.purebasicv2.app.model.CategoryItems;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private List<CategoryItems> categoryItems;
    private List<CategoryItems> categoryListFiltered;
    private CategoryAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCategoryName;
        public ImageView imgCategoryIcon;

        public MyViewHolder(View view) {
            super(view);
            tvCategoryName = view.findViewById(R.id.tvCategoryName);
//            imgCategoryIcon = view.findViewById(R.id.imgCategoryIcon);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onCategorySelected(categoryListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    public CategoryAdapter(Context context, List<CategoryItems> categoryItems, CategoryAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.categoryItems = categoryItems;
        this.categoryListFiltered = categoryItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_category_row_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final CategoryItems contact = categoryListFiltered.get(position);
        holder.tvCategoryName.setText(contact.getName());
//        try {
//            holder.imgCategoryIcon.setImageResource(getResId(contact.getName().toLowerCase(), R.drawable.class));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public int getItemCount() {
        return categoryListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    categoryListFiltered = categoryItems;
                } else {
                    List<CategoryItems> filteredList = new ArrayList<>();
                    for (CategoryItems row : categoryItems) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getName().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }
                    categoryListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = categoryListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                categoryListFiltered = (ArrayList<CategoryItems>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface CategoryAdapterListener {
        void onCategorySelected(CategoryItems contact);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}