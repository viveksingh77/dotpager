package com.vivekishere.dotpaper.Utility;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.vivekishere.dotpaper.Modals.container.FavouriteDao;
import com.vivekishere.dotpaper.Modals.container.FavouriteDatabase;
import com.vivekishere.dotpaper.Modals.container.FavouriteModal;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WallpaperService extends Service {
    private Handler handler = new Handler();
   FavouriteDatabase database ;
    Timer timer;
    Bitmap bitmapForReal;
    private static final String TAG = "WallpaperService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        database = FavouriteDatabase.getInstance(this);
        FavouriteDao favouriteDao = database.favouriteDao();
        List<FavouriteModal> favouriteModals = favouriteDao.getAll();
        for (FavouriteModal fav:favouriteModals) {
            String ImgSource = fav.getFavouriteStr();
            helper.loadBitmapFromUrl(this, ImgSource, new helper.BitmapCallback() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap) {
                    bitmapForReal = bitmap;
                }
            });
        }

        if (timer != null) {
            timer.cancel();
        } else {
            timer = new Timer();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer.schedule(new MyTask() , 10  , 3000);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer!=null){
            timer.cancel();
        }
    }

    class  MyTask extends TimerTask{

        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (bitmapForReal!=null) {
                        helper.setAsHomeScreenWallpaper(getApplicationContext(), bitmapForReal);
                    } else {
                        Log.d(TAG, "run: "+bitmapForReal);
                    }
                }
            });
        }
    }
}
