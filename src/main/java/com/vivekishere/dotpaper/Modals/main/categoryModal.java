package com.vivekishere.dotpaper.Modals.main;

public class categoryModal {
    private String category;
    private String ImgUrl;

    public categoryModal(String category, String imgUrl) {
        this.category = category;
        ImgUrl = imgUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public categoryModal() {
    }
}
