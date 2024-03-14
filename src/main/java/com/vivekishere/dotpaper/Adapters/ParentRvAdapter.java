package com.vivekishere.dotpaper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vivekishere.dotpaper.Modals.main.ParentModal;
import com.vivekishere.dotpaper.R;
import com.vivekishere.dotpaper.databinding.ParentRvLayoutBinding;

import java.util.List;

public class ParentRvAdapter extends RecyclerView.Adapter<ParentRvAdapter.ViewHolder> {
    Context context;
    List<ParentModal> parentModalList;
   private OnCLickListener onCLickListener;
    public interface OnCLickListener{
        void OnClick(int position);
    }

    public ParentRvAdapter(Context context, List<ParentModal> parentModalList, OnCLickListener onCLickListener) {
        this.context = context;
        this.parentModalList = parentModalList;
        this.onCLickListener = onCLickListener;
    }

    @NonNull
    @Override
    public ParentRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.parent_rv_layout , parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ParentRvAdapter.ViewHolder holder, int position) {
        holder.binding.parentTitle.setText(parentModalList.get(position).getTitle());
        ImageAdapter imageAdapter;
        imageAdapter = new ImageAdapter(context , parentModalList.get(position).getImageWallpaper());
        holder.binding.rvChild.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL , false));
       holder.binding.rvChild.setAdapter(imageAdapter);
       holder.binding.parentTitle.setOnClickListener(view -> {
           onCLickListener.OnClick(holder.getAdapterPosition());
       });
       imageAdapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return parentModalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ParentRvLayoutBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ParentRvLayoutBinding.bind(itemView);
        }
    }
}
