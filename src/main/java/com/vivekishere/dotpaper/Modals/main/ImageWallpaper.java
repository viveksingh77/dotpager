package com.vivekishere.dotpaper.Modals.main;

import com.google.gson.annotations.SerializedName;

public class ImageWallpaper {
    @SerializedName("urls")
    private ImageModal getImageList;

    public ImageWallpaper(ImageModal getImageList) {
        this.getImageList = getImageList;
    }

    public ImageModal getGetImageList() {
        return getImageList;
    }
}
