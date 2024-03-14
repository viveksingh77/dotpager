package com.vivekishere.dotpaper.Modals.container;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavouriteDao {
    @Query("SELECT * FROM FavouriteList ORDER BY id DESC ")
    List<FavouriteModal> getAll();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertInFavList(FavouriteModal favourite);

    @Query("SELECT COUNT(*) FROM favouritelist WHERE favImageUrl = :favourite")
    boolean  isPresentInDatabase(String favourite);
    @Delete
    void DeleteFromFavList(FavouriteModal favourite);
}
