package com.vivekishere.dotpaper.API;

import com.vivekishere.dotpaper.Modals.main.ImageResponse;
import com.vivekishere.dotpaper.Modals.main.ImageWallpaper;
import com.vivekishere.dotpaper.Utility.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api {
    @Headers("Authorization: Client-ID "+ Constants.API_KEY)
    @GET("photos")
    Call<List<ImageWallpaper>> getImages(
            @Query("page") int page,
            @Query("per_page") int perPage,
            @Query("order_by") String orderType
    );
    @Headers("Authorization: Client-ID "+ Constants.API_KEY)
    @GET("search/photos")
    Call<ImageResponse> getSearchImages(
            @Query("query") String query,
            @Query("page") int page,
            @Query("per_page") int perPage,
            @Query("order_by") String orderType
    );

//    @GET("curated")
//    Call<ImageResponse> getWallpaperP(
//            @Header("Authorization") String credentials, // for api key
//            @Query("page") int pageCount,
//            @Query("per_page") int perPage
//    );
//
//    @GET("search")
//    Call<ImageResponse> getSearchedPicP (
//            @Header("Authorization") String yourKey,
//            @Query("query") String Category,
//            @Query("page") int pageCount,
//            @Query("per_page") int perPage
//    );

}
