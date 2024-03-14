package com.vivekishere.dotpaper.Modals.container;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "FavouriteList")
public class FavouriteModal {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "favImageUrl")
    public String favouriteStr;

    public String getFavouriteStr() {
        return favouriteStr;
    }

    public FavouriteModal(String favouriteStr) {
        this.favouriteStr = favouriteStr;
    }

    public void setFavouriteStr(String favouriteStr) {
        this.favouriteStr = favouriteStr;
    }

}
