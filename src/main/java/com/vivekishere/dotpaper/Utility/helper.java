package com.vivekishere.dotpaper.Utility;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.vivekishere.dotpaper.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class helper {
    public static  void ImageLoader(Context context, String imageUrl , ImageView imageView){
        try {
//            Picasso.get().load(imageUrl).into(imageView);
            Glide.with(context).load(imageUrl).into(imageView);

        } catch (Exception e){
            e.printStackTrace();
        }

    }
public static void Toast(Context context , String string){
    Toast.makeText(context,string , Toast.LENGTH_LONG).show();
}
    public static ArrayList<Integer> getColors() {
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(R.color.white); //white
        colors.add(R.color.turquoise); //turquoise
        colors.add(R.color.yellow); //yellow
        colors.add(R.color.orange); //orange
        colors.add(R.color.blue); //blue
        colors.add(R.color.pink); //pink
        colors.add(R.color.brown); // brown
        colors.add(R.color.Red); //red
        colors.add(R.color.grayscale); // grayscale
        colors.add(R.color.black); //black
        return colors;
    }

    public static int getRandomColor(){
        ArrayList<Integer> color_code = new ArrayList<>();
        color_code.add(R.color.color1);
        color_code.add(R.color.turquoise);
        color_code.add(R.color.color3);
        color_code.add(R.color.orange);
        color_code.add(R.color.blue);
        color_code.add(R.color.brown);
        color_code.add(R.color.pink);
        Random random = new Random();
        return color_code.get(random.nextInt(color_code.size()));
    }
    public static  void ImageLoaderP(Context context, String imageUrl , ImageView imageView , LottieAnimationView progressBar){
        progressBar.setVisibility(View.VISIBLE);
        try {
            Glide.with(context).load(imageUrl)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(@NonNull Drawable resource, @NonNull Object model, Target<Drawable> target, @NonNull DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(imageView);

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void setAsHomeScreenWallpaper(Context context , Bitmap bitmap){
        setWallpaper(context , bitmap , WallpaperManager.FLAG_SYSTEM);
    }
    public static void setAsLockScreenWallpaper(Context context , Bitmap bitmap){
        setWallpaper(context , bitmap , WallpaperManager.FLAG_LOCK);
    }

    private static void setWallpaper(Context context, Bitmap bitmap, int flag) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        try {
            wallpaperManager.setBitmap(bitmap,null,true,flag);
        } catch (IOException e) {
            e.printStackTrace();
            helper.Toast(context, "Failed to set Wallpaper");
        }
    }


    public static void loadBitmapFromUrl(Context context, String imageUrl, final BitmapCallback callback) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.override(Target.SIZE_ORIGINAL); // Set the desired size
        Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .apply(requestOptions)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        // Pass the loaded Bitmap to the callback
                        if (callback != null) {
                            callback.onBitmapLoaded(bitmap);
                        }
                    }
                });
    }

    public interface BitmapCallback {
        void onBitmapLoaded(Bitmap bitmap);
    }
}
