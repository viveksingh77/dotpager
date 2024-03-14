package com.vivekishere.dotpaper.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vivekishere.dotpaper.Activities.WallpaperActivity;
import com.vivekishere.dotpaper.Adapters.FavAdapter;
import com.vivekishere.dotpaper.Modals.container.FavouriteDatabase;
import com.vivekishere.dotpaper.Modals.container.FavouriteModal;
import com.vivekishere.dotpaper.databinding.FragmentFavouriteBinding;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends BottomSheetDialogFragment implements FavAdapter.OnFavClickListener {
    public FavouriteFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    FragmentFavouriteBinding binding;
    BottomSheetBehavior<View> bottomSheetBehavior;
    BottomSheetDialog dialog;
    FavouriteDatabase database ;
    List<FavouriteModal> favouriteModalList = new ArrayList<>();
    FavAdapter favAdapter ;
    private boolean isScrollingUp = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavouriteBinding.inflate(inflater);
        database = FavouriteDatabase.getInstance(getContext());
        favouriteModalList.addAll(database.favouriteDao().getAll());
        favAdapter = new FavAdapter(getContext() , favouriteModalList , this);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        binding.recyclerView.setAdapter(favAdapter);
        if (database.favouriteDao().getAll().isEmpty()){
            binding.textView.setVisibility(View.VISIBLE);
        }else {
            binding.textView.setVisibility(View.INVISIBLE);
        }
//        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                // Check if scrolling up or down
//                isScrollingUp = dy < 0;
//
//            }
//        });
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomSheetBehavior.from((View) view.getParent()).setState(BottomSheetBehavior.STATE_EXPANDED);
        binding.favouriteFragment.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
    }

    @Override
    public void OnFavClick(int position) {
        Intent intent =new Intent(getContext(), WallpaperActivity.class);
        intent.putExtra("room",favouriteModalList.get(position).getFavouriteStr());
        startActivity(intent);
    }
}