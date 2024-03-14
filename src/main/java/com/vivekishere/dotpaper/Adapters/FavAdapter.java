package com.vivekishere.dotpaper.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.vivekishere.dotpaper.Modals.container.FavouriteDatabase;
import com.vivekishere.dotpaper.Modals.container.FavouriteModal;
import com.vivekishere.dotpaper.R;
import com.vivekishere.dotpaper.Utility.helper;
import com.vivekishere.dotpaper.databinding.CardItemsBinding;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    Context context;
    List<FavouriteModal> favouriteList;
    OnFavClickListener onFavClickListener;
    public interface OnFavClickListener{
        void OnFavClick(int position);
    }

    public FavAdapter(Context context, List<FavouriteModal> favouriteList, OnFavClickListener onFavClickListener) {
        this.context = context;
        this.favouriteList = favouriteList;
        this.onFavClickListener = onFavClickListener;
    }

    @NonNull
    @Override
    public FavAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_items , parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapter.ViewHolder holder, int position) {
        FavouriteModal favourite = favouriteList.get(position);
        helper.ImageLoader(context , favourite.getFavouriteStr(), holder.binding.images);
        holder.itemView.setOnClickListener(view -> {
            onFavClickListener.OnFavClick(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardItemsBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CardItemsBinding.bind(itemView);
        }
    }
}
