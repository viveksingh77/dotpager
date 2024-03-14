package com.vivekishere.dotpaper.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vivekishere.dotpaper.API.RetrofitClient;
import com.vivekishere.dotpaper.Adapters.CategoryAdapter;
import com.vivekishere.dotpaper.Adapters.ParentRvAdapter;
import com.vivekishere.dotpaper.Adapters.ImageAdapter;
import com.vivekishere.dotpaper.Adapters.themeAdapter;
import com.vivekishere.dotpaper.Fragment.FavouriteFragment;
import com.vivekishere.dotpaper.Modals.main.ParentModal;
import com.vivekishere.dotpaper.Modals.main.categoryModal;
import com.vivekishere.dotpaper.Modals.main.ImageResponse;
import com.vivekishere.dotpaper.Modals.main.ImageWallpaper;
import com.vivekishere.dotpaper.R;
import com.vivekishere.dotpaper.Utility.WallpaperService;
import com.vivekishere.dotpaper.Utility.helper;
import com.vivekishere.dotpaper.databinding.ActivityMainBinding;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ParentRvAdapter.OnCLickListener , CategoryAdapter.OnChoseCategoryListener{
    ActivityMainBinding binding;
    ArrayList<categoryModal> categoryModal = new ArrayList<>();
    List<ImageWallpaper> trendModalList = new ArrayList<>();
    ArrayList<ParentModal> parentModalArrayList = new ArrayList<>();
    ArrayList<ImageWallpaper> CarList= new ArrayList<>();
    ArrayList<ImageWallpaper> MarvelList= new ArrayList<>();
    ArrayList<ImageWallpaper> ThreeDList= new ArrayList<>();
    ArrayList<ImageWallpaper> EditorList = new ArrayList<>();
    ArrayList<ImageWallpaper> CartoonList = new ArrayList<>();
    ArrayList<ImageWallpaper> InsectList = new ArrayList<>();
    ArrayList<ImageWallpaper> OLdList = new ArrayList<>();
    ArrayList<ImageWallpaper> PlantList = new ArrayList<>();
    ArrayList<ImageWallpaper> SportsList = new ArrayList<>();
    ArrayList<ImageWallpaper> ChemistryList = new ArrayList<>();
    ParentRvAdapter parentRvAdapter ;
    ImageAdapter trendAdapter;
    CategoryAdapter categoryAdapter;
    private static  int pageCount =1;
    private static int perPage=30; // pageSize
    private static String order="popular";
    private static String SearchOrder="latest"; // relevent


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        fetchSearchPhotos(pageCount , "Editor's choice" , EditorList);
        setContentView(binding.getRoot());
        getCategories();
        getWindow().setStatusBarColor(Color.parseColor("#7f0012"));
        setSupportActionBar(binding.toolbar);
        initListener();
        fetchTrendPhotos(pageCount);
        setUpPagination(true);
        binding.trendRecyclerView.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false));
        fetchSearchPhotos(pageCount , "Marvel" , MarvelList);
        fetchSearchPhotos(pageCount , "3D" , ThreeDList);
        fetchSearchPhotos(pageCount , "Cars" , CarList);
        fetchSearchPhotos(pageCount , "Cartoons" , CartoonList);
        fetchSearchPhotos(pageCount , "Chemistry" , ChemistryList);
        fetchSearchPhotos(pageCount , "Sports" , SportsList);
        fetchSearchPhotos(pageCount , "Old" , OLdList);
        fetchSearchPhotos(pageCount , "Plants" , PlantList);
        fetchSearchPhotos(pageCount , "Insects" , InsectList);
    }
    private void setUpPagination(boolean isPaginationAllowed) {
        if (isPaginationAllowed){
            binding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY==v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight()){
                        fetchTrendPhotos(++pageCount);
                    }
                }
            });
        } else {

        }
    }

    private void fetchSearchPhotos(int pageCount , String query , ArrayList<ImageWallpaper> list){
        RetrofitClient.getInstance(this)
                .getApi()
                .getSearchImages(query, pageCount , perPage , SearchOrder)
                .enqueue(new Callback<ImageResponse>() {
                    @Override
                    public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                        ArrayList<ImageWallpaper> imageWallpapers = response.body().getPhotoList();
                        if (response.isSuccessful() && response.body()!=null){
                           list.addAll(imageWallpapers);
                           Collections.shuffle(list);
                            parentModalArrayList.add(new ParentModal(query , list));
                            parentRvAdapter= new ParentRvAdapter(MainActivity.this , parentModalArrayList , MainActivity.this);
                            binding.rvParent.setAdapter(parentRvAdapter);
                            binding.rvParent.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            parentRvAdapter.notifyDataSetChanged();
                        } else {
                            helper.Toast(MainActivity.this , response.code()+"");
                        }
                    }

                    @Override
                    public void onFailure(Call<ImageResponse> call, Throwable t) {
                        helper.Toast(MainActivity.this , t.getMessage());
                    }});} // unsplash
    private void fetchTrendPhotos(int pageCount) {
        RetrofitClient.getInstance(this)
                .getApi()
                .getImages(pageCount, perPage , order)
                .enqueue(new Callback<List<ImageWallpaper>>() {
                    @Override
                    public void onResponse(Call<List<ImageWallpaper>> call, Response<List<ImageWallpaper>> response) {
                        List<ImageWallpaper> trendWallpaper = response.body();
                        if (response.isSuccessful() && trendWallpaper!=null){
                            trendModalList.addAll(trendWallpaper);
                            trendAdapter  = new ImageAdapter(MainActivity.this , trendModalList);
                            binding.trendRecyclerView.setAdapter(trendAdapter);
                            trendAdapter.notifyDataSetChanged();
                        } else {
                            helper.Toast(MainActivity.this , response.code()+"");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ImageWallpaper>> call, Throwable t) {
                        helper.Toast(MainActivity.this , ""+t.getMessage());
                    }
                });

    }

    private void getCategories() {
        // on below lines we are adding data to our category array list.
        categoryModal.add(new categoryModal("Technology", "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTJ8fHRlY2hub2xvZ3l8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryModal.add(new categoryModal("Programming", "https://images.unsplash.com/photo-1542831371-29b0f74f9713?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8cHJvZ3JhbW1pbmd8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryModal.add(new categoryModal("Nature", "https://images.pexels.com/photos/2387873/pexels-photo-2387873.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryModal.add(new categoryModal("Travel", "https://images.pexels.com/photos/672358/pexels-photo-672358.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryModal.add(new categoryModal("Architecture", "https://images.pexels.com/photos/256150/pexels-photo-256150.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryModal.add(new categoryModal("Food","https://tse1.mm.bing.net/th?id=OIP.rvGD1uhnaVbGY07Jo88uMgHaEo&pid=Api&P=0&h=180"));
        categoryModal.add(new categoryModal("Arts", "https://images.pexels.com/photos/1194420/pexels-photo-1194420.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryModal.add(new categoryModal("Music", "https://images.pexels.com/photos/4348093/pexels-photo-4348093.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryModal.add(new categoryModal("Abstract", "https://images.pexels.com/photos/2110951/pexels-photo-2110951.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryModal.add(new categoryModal("Cars", "https://images.pexels.com/photos/3802510/pexels-photo-3802510.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryModal.add(new categoryModal("Flowers", "https://images.pexels.com/photos/1086178/pexels-photo-1086178.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryModal.add(new categoryModal("Music Vibes","https://tse4.mm.bing.net/th?id=OIP.87L28KIiWxs8xvkxnlCTqgHaD4&pid=Api&P=0&h=180"));
        categoryModal.add(new categoryModal("Fashion","https://2.bp.blogspot.com/-3h8UoIehJCU/V9lj24R90DI/AAAAAAAD8ZI/hupq-ViiV6UR2z7I8Lb56xirOlKH4W5kQCLcB/s1600/street-style-nyfw-spring-2017_cool-chic-style-fashion-IMG_6413.jpg"));
        categoryModal.add(new categoryModal("Space","https://tse1.mm.bing.net/th?id=OIP.GtPBwDAqHOW-QWVnPaoT1QHaEo&pid=Api&P=0&h=180"));
        categoryModal.add(new categoryModal("Animals","https://tse1.mm.bing.net/th?id=OIP.0hUULtBUdiGfqTTw25-KaQHaE8&pid=Api&P=0&h=180"));
        categoryModal.add(new categoryModal("Futuristic","https://tse2.mm.bing.net/th?id=OIP.QyxNZZpPeoACbVKDnnxBHAHaEK&pid=Api&P=0&h=180"));
        categoryModal.add(new categoryModal("Ocean","https://tse4.mm.bing.net/th?id=OIP.oxZRj0yjRXO6AjR-XUkyfgHaE8&pid=Api&P=0&h=180"));
        categoryModal.add(new categoryModal("Insects","https://tse1.mm.bing.net/th?id=OIP.ZJzPtEdLSg6pQ5lYfOJggAHaE6&pid=Api&P=0&h=180"));
        Collections.shuffle(categoryModal);
        categoryAdapter =new CategoryAdapter(this, categoryModal);
        binding.categoryRV.setAdapter(categoryAdapter);
        categoryAdapter.setOnChoseCategoryListener(this);
        binding.categoryRV.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false));
    }

    private void performSearch(String query) {

    }

    private void initListener() {
        themeAdapter themeAdapter=new themeAdapter(this,helper.getColors());
        themeAdapter.setOnThemeClickListener((position, itemColor) -> {
            String colorname = getResources().getResourceName(itemColor);
            String colorName = colorname.substring(colorname.lastIndexOf('/')+1);
            Intent intent = new Intent(this , ContainerActivity.class);
            intent.putExtra("title" , colorName);
            startActivity(intent);

        });
        binding.themeGridView.setAdapter(themeAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.top_items , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.like){
            openFavouriteFragment();
        } else if (item.getItemId()==R.id.search) {
            Intent intent = new Intent(this , SearchActivity.class);
            startActivity(intent);
        } else if (item.getItemId()==R.id.queue) {
            //   startService(new Intent(this , WallpaperService.class));
            helper.Toast(this , "no working on it yet");
        }
        return super.onOptionsItemSelected(item);
    }
    private void openFavouriteFragment() {
        FavouriteFragment favouriteFragment = new FavouriteFragment();
        favouriteFragment.show(getSupportFragmentManager() , null);
    }

    @Override
    public void OnClick(int position) {
        String CategoryPassed = parentModalArrayList.get(position).getTitle();
        openContainerActivity(CategoryPassed , "title");
    }

    private void openContainerActivity(String category , String passKey) {
        Intent intent =new Intent(MainActivity.this , ContainerActivity.class);
        intent.putExtra(passKey, category);
        startActivity(intent);
    }

    @Override
    public void OnChoose(int position) {
        String CategoryPass= categoryModal.get(position).getCategory();
        openContainerActivity(CategoryPass , "title");
    }

}