package com.vivekishere.dotpaper.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vivekishere.dotpaper.Utility.helper;
import com.vivekishere.dotpaper.databinding.FragmentApplyBinding;

public class ApplyFragment extends BottomSheetDialogFragment {

    FragmentApplyBinding binding;
    Bitmap bitmap;
    public ApplyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentApplyBinding.inflate(inflater);
         bitmap =getArguments().getParcelable("bitmapKey");

        innitListener();
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void innitListener() {
        binding.tvhome.setOnClickListener(view -> {
           helper.setAsHomeScreenWallpaper(getContext() , bitmap);
           dismiss();
        });
        binding.tvlock.setOnClickListener(view -> {
           helper.setAsLockScreenWallpaper(getContext() , bitmap);
           dismiss();
        });
        binding.tvqueue.setOnClickListener(view -> {
            helper.Toast(getContext() , "currently is unavailable");
           dismiss();
        });
    }
}