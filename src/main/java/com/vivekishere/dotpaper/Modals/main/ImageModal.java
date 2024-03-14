package com.vivekishere.dotpaper.Modals.main;

import com.google.gson.annotations.SerializedName;

public class ImageModal {
    @SerializedName("small")
    private String Image ;
    @SerializedName("full")
    private String FullImage;
    @SerializedName("regular")
    private String RegularImage;

    public ImageModal(String image, String fullImage, String regularImage) {
        Image = image;
        FullImage = fullImage;
        RegularImage = regularImage;
    }

    public String getImage() {
        return Image;
    }

    public String getFullImage() {
        return FullImage;
    }

    public String getRegularImage() {
        return RegularImage;
    }
}
