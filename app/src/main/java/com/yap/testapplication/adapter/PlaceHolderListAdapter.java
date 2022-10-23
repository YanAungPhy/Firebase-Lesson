package com.yap.testapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yap.testapplication.R;
import com.yap.testapplication.modal.PlaceHolderListModel;

import java.util.List;

public class PlaceHolderListAdapter extends RecyclerView.Adapter<PlaceHolderListAdapter.MyViewHolder> {

    Context context;
    List<PlaceHolderListModel> placeHolderListModels;
    private final OnItemClickListener itemClickListener;

    public PlaceHolderListAdapter(Context context, List<PlaceHolderListModel> placeHolderListModels, OnItemClickListener itemClickListener) {
        this.context = context;
        this.placeHolderListModels = placeHolderListModels;
        this.itemClickListener = itemClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPlaceHolderListModels(List<PlaceHolderListModel> placeHolderListModels) {
        this.placeHolderListModels = placeHolderListModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.listId.setText(this.placeHolderListModels.get(position).getId());
        holder.listTitle.setText(this.placeHolderListModels.get(position).getTitle());
        Glide.with(context)
//                .load(this.placeHolderListModels.get(position).getUrl())
                .load(R.drawable.profile)
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imageUrl);

        Glide.with(context)
                .load(this.placeHolderListModels.get(position).getThumbnailUrl())
                //.load(R.drawable.close)
                .into(holder.thumbnailUrl);

        Log.i("IMAGEIMAGE", this.placeHolderListModels.get(position).getThumbnailUrl());
    }

    @Override
    public int getItemCount() {
        return placeHolderListModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageUrl;
        ImageView thumbnailUrl;
        TextView listId;
        TextView listTitle;
        //OnItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener itemClickListener) {
            super(itemView);

            imageUrl = itemView.findViewById(R.id.img_url);
            thumbnailUrl = itemView.findViewById(R.id.thumbnail_Url);
            listId = itemView.findViewById(R.id.list_id);
            listTitle = itemView.findViewById(R.id.list_title);

            itemView.setOnClickListener(v -> {
                if (itemClickListener != null) {    
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        itemClickListener.onItemClick(pos);
                    }
                }
            });
        }


    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
