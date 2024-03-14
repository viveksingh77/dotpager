package com.vivekishere.dotpaper.Modals.main;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ImageResponse {
    @SerializedName("results")
    private ArrayList<ImageWallpaper> photoList;

    public ImageResponse(ArrayList<ImageWallpaper> photoList) {
        this.photoList = photoList;
    }

    public ArrayList<ImageWallpaper> getPhotoList() {
        return photoList;
    }
}
