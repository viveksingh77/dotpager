package com.vivekishere.dotpaper.Modals.main;

import java.util.ArrayList;

public class ParentModal {
    String title ;
    ArrayList<ImageWallpaper> ImageWallpaper;

    public ParentModal(String title, ArrayList<ImageWallpaper> imageWallpaper) {
        this.title = title;
        ImageWallpaper = imageWallpaper;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ImageWallpaper> getImageWallpaper() {
        return ImageWallpaper;
    }
}
