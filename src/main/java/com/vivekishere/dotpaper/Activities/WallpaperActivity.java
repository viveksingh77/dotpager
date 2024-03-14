package com.vivekishere.dotpaper.Activities;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import com.bumptech.glide.Glide;
import com.vivekishere.dotpaper.Fragment.ApplyFragment;
import com.vivekishere.dotpaper.Modals.container.FavouriteDatabase;
import com.vivekishere.dotpaper.Modals.container.FavouriteModal;
import com.vivekishere.dotpaper.R;
import com.vivekishere.dotpaper.Utility.Constants;
import com.vivekishere.dotpaper.Utility.helper;
import com.vivekishere.dotpaper.databinding.ActivityWallpaperBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class WallpaperActivity extends AppCompatActivity {
    ActivityWallpaperBinding binding;
    String url;
    private  boolean download_st = true;
    private  boolean like_st = true;
    FavouriteDatabase database;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWallpaperBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //for room
        database = FavouriteDatabase.getInstance(this);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
       url= getIntent().getStringExtra(Constants.CTOWALLPAPER);
  //     url2 = getIntent().getStringExtra("wallpaper");
       helper.ImageLoaderP(this,url,binding.wallpaper , binding.progressBar);
  //    helper.ImageLoaderP(this,url2,binding.wallpaper , binding.progressBar);

        initListener();
    }

    private void initListener() {
        binding.download.setOnClickListener(view -> {
            if (download_st){
                    binding.download.playAnimation();
                binding.download.addAnimatorListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animator) {
                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animator) {
                        downloadImage();
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animator) {

                    }
                });
                download_st=false;
                binding.download.setEnabled(false);
            }
        });
        binding.like.setOnClickListener(view -> {
            if (like_st){
                binding.like.setMinAndMaxProgress(0.0f , 1.0f);
                binding.like.playAnimation();
                if (database.favouriteDao().isPresentInDatabase(url)){
                  helper.Toast(this , "Already in Favourites");
                } else
                {  database.favouriteDao().insertInFavList(new FavouriteModal(url));}
                like_st=false;
                binding.like.setEnabled(false);
                }

        });
        binding.share.setOnClickListener(view -> {
            BitmapDrawable bitmapDrawable =(BitmapDrawable) binding.wallpaper.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            shareImage(bitmap);
        });
        binding.apply.setOnClickListener(view -> {
            ApplyFragment applyFragment = new ApplyFragment();
            Bundle bundle = new Bundle();
            Bitmap bitmap = ((BitmapDrawable)binding.wallpaper.getDrawable()).getBitmap();
           bundle.putParcelable("bitmapKey" , bitmap);
            applyFragment.setArguments(bundle);
            applyFragment.show(getSupportFragmentManager() , null);
        });
        binding.onback.setOnClickListener(view -> {
            finish();
        });
    }
    private void downloadImage(){
        Bitmap bitmap = ((BitmapDrawable)binding.wallpaper.getDrawable()).getBitmap();
        FileOutputStream fileOutputStream=null;
        File sdCard = Environment.getExternalStorageDirectory();
        File Directory = new File(sdCard.getAbsolutePath()+"/Download");
        Directory.mkdir();
        String filename = String.format("%d.jpg",System.currentTimeMillis());
        File outPutfile = new File(Directory , filename);
        try {
            fileOutputStream = new FileOutputStream(outPutfile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(outPutfile));
            sendBroadcast(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void shareImage(Bitmap bitmap) {
        Uri uri = getImageToShare(bitmap);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM ,uri);
        intent.putExtra(Intent.EXTRA_TEXT , "Check Out this Image "+url);
        intent.setType("image/*");
        startActivity(Intent.createChooser(intent , "Share Via"));
    }
    private Uri getImageToShare(Bitmap bitmap)  {
        File folder = new File(getCacheDir() , "images");
        Uri uri = null;
        try {
            folder.mkdirs();
            File file = new File(folder , "image.jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG , 100 , fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            uri = FileProvider.getUriForFile(this , "com.vivekishere.dotpaper",file);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return uri;
    }

}