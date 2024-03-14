package com.vivekishere.dotpaper.Modals.container;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavouriteModal.class} , version = 1)
public abstract class FavouriteDatabase extends RoomDatabase {
    private static FavouriteDatabase database;
    private static String DATABASE_NAME = "FavouriteList_DataBase";
    public synchronized static FavouriteDatabase getInstance(Context context){
        if (database== null){
            database = Room.databaseBuilder(context , FavouriteDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
    public abstract FavouriteDao favouriteDao();
}
