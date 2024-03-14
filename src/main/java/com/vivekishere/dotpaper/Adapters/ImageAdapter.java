package com.vivekishere.dotpaper.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vivekishere.dotpaper.Activities.WallpaperActivity;
import com.vivekishere.dotpaper.Modals.main.ImageModal;
import com.vivekishere.dotpaper.Modals.main.ImageWallpaper;
import com.vivekishere.dotpaper.R;
import com.vivekishere.dotpaper.Utility.Constants;
import com.vivekishere.dotpaper.Utility.helper;
import com.vivekishere.dotpaper.databinding.CardItemsBinding;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    Context context;
    List<ImageWallpaper> list;

//    private OnClickImageListener onClickItemListener;
//    public interface OnClickImageListener {
//        void OnImage(int position);
//    }

    public ImageAdapter(Context context, List<ImageWallpaper> list) {
        this.context = context;
        this.list = list;
    }
//    public void setOnClickItemListener(OnClickImageListener onClickItemListener){
//        this.onClickItemListener = onClickItemListener;
//    }

    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_items, parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ViewHolder holder, int position) {
        ImageModal trendItem = list.get(position).getGetImageList();
        helper.ImageLoader(context ,trendItem.getImage(), holder.binding.images);
        holder.binding.imgContainer.setCardBackgroundColor(context.getResources().getColor(helper.getRandomColor() , null));
        holder.itemView.setOnClickListener(view -> {
//            onClickItemListener.OnImage(holder.getAdapterPosition());
            Intent intent = new Intent(context , WallpaperActivity.class);
            intent.putExtra(Constants.CTOWALLPAPER, trendItem.getFullImage());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
      CardItemsBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CardItemsBinding.bind(itemView);
        }
    }
}
