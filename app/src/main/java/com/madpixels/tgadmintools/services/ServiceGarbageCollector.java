package com.madpixels.tgadmintools.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.madpixels.apphelpers.FileUtils2;
import com.madpixels.apphelpers.MyLog;
import com.madpixels.tgadmintools.BuildConfig;
import com.madpixels.tgadmintools.db.DBHelper;
import com.madpixels.tgadmintools.helper.TgH;

/**
 * Created by Snake on 31.03.2016.
 */
public class ServiceGarbageCollector extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void start(Context context){
        Intent myIntent = new Intent(context, ServiceGarbageCollector.class);
        PendingIntent pi = PendingIntent.getService(context, 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pi);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + AlarmManager.INTERVAL_HOUR,
                AlarmManager.INTERVAL_HALF_DAY,  pi); // каждые два часа
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        DBHelper.getInstance().clearOldLog();


        TgH.clearAppCache(getBaseContext(), new FileUtils2.ClearCallback() {
            @Override
            public void onCallback() {
                MyLog.log("removedCount: "+removedFilesCount);
                if(BuildConfig.DEBUG) {
                    MyNotification m = new MyNotification(123, getBaseContext());
                    m.mNotificationBuilder.setContentText("remove files: " + removedFilesCount);
                    m.alert();
                }
                stopSelf();
            }
        });

        return START_STICKY;
    }
}
