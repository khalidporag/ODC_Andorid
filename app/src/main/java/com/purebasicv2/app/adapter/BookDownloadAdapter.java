package com.purebasicv2.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.purebasicv2.app.R;
import com.purebasicv2.app.model.BookDownloadResponse;

import java.util.ArrayList;

public class BookDownloadAdapter extends RecyclerView.Adapter<BookDownloadAdapter.MyViewHolder>{

    private Context mContext;
    private ArrayList<BookDownloadResponse.Data> bookLists;
    private BookDownloadAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(BookDownloadResponse.Data position);
    }


    public BookDownloadAdapter(Context context, ArrayList<BookDownloadResponse.Data> exampleList, BookDownloadAdapter.OnItemClickListener listener) {
        mContext = context;
        bookLists = exampleList;
        mListener=listener;
    }
    @NonNull
    @Override
    public BookDownloadAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_book_list, parent, false);
        return new BookDownloadAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookDownloadAdapter.MyViewHolder holder, int position) {
        BookDownloadResponse.Data booklist = bookLists.get(position);
        holder.tvTitle.setText(booklist.getName());
        Glide.with(mContext).load(booklist.getImage()).into(holder.ivCover);

    }

    @Override
    public int getItemCount() {
        return bookLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        ImageView ivCover;
        Button btnDownload;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            ivCover = itemView.findViewById(R.id.ivCover);
            btnDownload = itemView.findViewById(R.id.btnDownload);
            // llExam = (TextView) itemView.findViewById(R.id.tvModelTextTime);
            btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(bookLists.get(getAdapterPosition()));
                        }
                    }
                }
            });
        }
    }
}
