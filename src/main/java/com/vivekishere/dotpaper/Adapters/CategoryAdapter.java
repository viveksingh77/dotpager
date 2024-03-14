package com.vivekishere.dotpaper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vivekishere.dotpaper.Modals.main.categoryModal;
import com.vivekishere.dotpaper.R;
import com.vivekishere.dotpaper.Utility.helper;
import com.vivekishere.dotpaper.databinding.CategoryRvItemBinding;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

        private Context context;
    private ArrayList<categoryModal> CategoryList ;
    private OnChoseCategoryListener onChoseCategoryListener;
    public interface OnChoseCategoryListener{
        void OnChoose(int position);
    }
    public void setOnChoseCategoryListener(OnChoseCategoryListener onChoseCategoryListener){
        this.onChoseCategoryListener = onChoseCategoryListener;
    }

    public CategoryAdapter(Context context, ArrayList<categoryModal> categoryList) {
        this.context = context;
        CategoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.category_rv_item , parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        categoryModal category = CategoryList.get(position);
        helper.ImageLoader(context , category.getImgUrl() , holder.binding.idIVCategory);
        holder.binding.idTVCategory.setText(category.getCategory());
        holder.itemView.setOnClickListener(view -> {
            onChoseCategoryListener.OnChoose(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return CategoryList.size();
    }


    public void updateData(ArrayList<categoryModal> newLata) {
        CategoryList.clear();
        CategoryList.addAll(newLata);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CategoryRvItemBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CategoryRvItemBinding.bind(itemView);
        }
    }

}
