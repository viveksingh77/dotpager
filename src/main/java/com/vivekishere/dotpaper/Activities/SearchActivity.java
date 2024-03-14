package com.vivekishere.dotpaper.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

import com.vivekishere.dotpaper.API.RetrofitClient;
import com.vivekishere.dotpaper.Adapters.ImageAdapter;
import com.vivekishere.dotpaper.Modals.main.ImageResponse;
import com.vivekishere.dotpaper.Modals.main.ImageWallpaper;
import com.vivekishere.dotpaper.R;
import com.vivekishere.dotpaper.Utility.helper;
import com.vivekishere.dotpaper.databinding.ActivitySearchBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding binding;
    SearchView searchView;
    List<ImageWallpaper> itemList = new ArrayList<>();
    private int pageCount = 1;
    private int perPage = 60;
    private String orderTpe = "popular";
    ImageAdapter adapter;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbarS);
        setTitle("");
        setUpPagination(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_keyboard_backspace_24);
        getWindow().setStatusBarColor(Color.parseColor("#7f0012"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(SearchActivity.this, 3));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.searchS);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                title = query.toLowerCase();
                itemList.clear();
                fetchSearchPhotos(pageCount);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void setUpPagination(boolean isPaginationAllowed) {
        if (isPaginationAllowed) {
            binding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                        fetchSearchPhotos(++pageCount);
                    }
                }
            });
        }
    }

    private void fetchSearchPhotos(int pageCount) {
        RetrofitClient.getInstance(this)
                .getApi()
                .getSearchImages(title, pageCount, perPage, orderTpe)
                .enqueue(new Callback<ImageResponse>() {
                    @Override
                    public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                        List<ImageWallpaper> imageWallpapers = response.body().getPhotoList();
                        if (response.isSuccessful() && response.body() != null) {
                            itemList.addAll(imageWallpapers);
                            adapter = new ImageAdapter(SearchActivity.this, itemList);
                            binding.recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            helper.Toast(SearchActivity.this, "No Result Found");
                        }
                    }

                    @Override
                    public void onFailure(Call<ImageResponse> call, Throwable t) {
                        helper.Toast(SearchActivity.this, "Check Your Internet Connection");
                    }
                });
    } // unsplash
}