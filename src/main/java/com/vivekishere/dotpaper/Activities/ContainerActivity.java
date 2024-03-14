package com.vivekishere.dotpaper.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import android.graphics.Color;
import android.os.Bundle;
import android.window.OnBackInvokedDispatcher;

import com.vivekishere.dotpaper.API.RetrofitClient;
import com.vivekishere.dotpaper.Adapters.ImageAdapter;
import com.vivekishere.dotpaper.Modals.main.ImageResponse;
import com.vivekishere.dotpaper.Modals.main.ImageWallpaper;
import com.vivekishere.dotpaper.R;
import com.vivekishere.dotpaper.Utility.helper;
import com.vivekishere.dotpaper.databinding.ActivityContainerBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContainerActivity extends AppCompatActivity  {
    ActivityContainerBinding binding;
    private  int pageCount = 1;
    private int perPage =60;
    private String orderTpe ="popular";
    ImageAdapter adapter ;
    String title; //String category , themeColor; if error occurs then proble m is here
    List<ImageWallpaper> itemList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContainerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbarC);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_keyboard_backspace_24);
        getWindow().setStatusBarColor(Color.parseColor("#7f0012"));
       // category =getIntent().getStringExtra("title")+"";
        title = getIntent().getStringExtra("title")+"";
        setTitle(title);


        fetchSearchPhotos(pageCount);
        fetchThemePhotos(pageCount);
        setUpPagination(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(ContainerActivity.this , 3));
    }
    private void setUpPagination(boolean isPaginationAllowed) {
        if (isPaginationAllowed){
            binding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY==v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight()){
                        fetchSearchPhotos(++pageCount);
                        fetchThemePhotos(++pageCount);
                    }
                }
            });
        } else {

        }
    }
    private void fetchThemePhotos(int pageCount){
        RetrofitClient.getInstance(this)
                .getApi()
                .getSearchImages(title, pageCount , perPage , orderTpe)
                .enqueue(new Callback<ImageResponse>() {
                    @Override
                    public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                        List<ImageWallpaper> imageWallpapers = response.body().getPhotoList();
                        if (response.isSuccessful() && response.body()!=null){
                            itemList.addAll(imageWallpapers);
                            adapter = new ImageAdapter(ContainerActivity.this , itemList);
//                           adapter.setOnClickItemListener(ContainerActivity.this);
                            binding.recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            helper.Toast(ContainerActivity.this , response.code()+"");
                        }
                    }

                    @Override
                    public void onFailure(Call<ImageResponse> call, Throwable t) {
                        helper.Toast(ContainerActivity.this , t.getMessage());
                    }});
    }

    private void fetchSearchPhotos(int pageCount){
        RetrofitClient.getInstance(this)
                .getApi()
                .getSearchImages(title, pageCount , perPage , orderTpe)
                .enqueue(new Callback<ImageResponse>() {
                    @Override
                    public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                       List<ImageWallpaper> imageWallpapers = response.body().getPhotoList();
                        if (response.isSuccessful() && response.body()!=null){
                            itemList.addAll(imageWallpapers);
                           adapter = new ImageAdapter(ContainerActivity.this , itemList);
//                           adapter.setOnClickItemListener(ContainerActivity.this);
                           binding.recyclerView.setAdapter(adapter);
                           adapter.notifyDataSetChanged();
                        } else {
                            helper.Toast(ContainerActivity.this , response.code()+"");
                        }
                    }

                    @Override
                    public void onFailure(Call<ImageResponse> call, Throwable t) {
                        helper.Toast(ContainerActivity.this , t.getMessage());
                    }});} // unsplash

//    @Override
//    public void OnImage(int position) {
//        Intent intent = new Intent(ContainerActivity.this , WallpaperActivity.class);
//        intent.putExtra(Constants.CTOWALLPAPER , itemList.get(position).getGetImageList().getFullImage());
//        startActivity(intent);
//    }

}