package com.madpixels.tgadmintools.services;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.madpixels.apphelpers.MyLog;
import com.madpixels.tgadmintools.activity.MainActivity;
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.helper.TgUtils;
import com.madpixels.tgadmintools.utils.CommonUtils;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;

/**
 * Created by Snake on 16.11.2016.
 */

public class ServiceBackgroundStarter extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        MyLog.log("Starting ServiceBackgroundStarter");
        if (!ServiceAutoKicker.IS_RUNNING && !ServiceChatTask.IS_RUNNING) {
            TgInit();
        } else {
            MyLog.log("ServiceBackgroundStarter services already started");
            registerNextStartForBackgroundService();
            stopSelf();
        }

        return START_STICKY;
    }

    private void TgInit() {
        TgH.init(getBaseContext());
        TgH.send(new TdApi.GetAuthState(), new Client.ResultHandler() {
            @Override
            public void onResult(final TdApi.TLObject object) {
                if (!TgUtils.isAuthorized(object)) {
                    stopSelf();
                    return;
                }

                onAuthorized();

                registerNextStartForBackgroundService();
                stopSelf();
            }
        });
    }


    private void onAuthorized() {
        MainActivity.initializeLanguage(this);
        ServiceAutoKicker.registerTask(getBaseContext());
        ServiceChatTask.start(getBaseContext());
        ServiceUnbanTask.registerTask(getBaseContext());

        MyLog.log("ServiceBackgroundStarter started");
    }


    @SuppressLint("NewApi")
    public static void registerStart(Context mContext, final long registerAt){
        Intent myIntent = new Intent(mContext, ServiceBackgroundStarter.class);
        PendingIntent pi = PendingIntent.getService(mContext, 0, myIntent, 0);
        AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);


        MyLog.log("ServiceBackgroundStarter next start at: "+ CommonUtils.tsToDate(registerAt/1000));

        boolean isPowerSafeMode_API23 = false;
        if (Build.VERSION.SDK_INT < 19) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, registerAt, pi);
            return;
        }

        if (Build.VERSION.SDK_INT < 23 || isPowerSafeMode_API23) // Если киткат, или запрещена работа в лоу режиме
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, registerAt, pi);
        else // Только для апи 23+:
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, registerAt, pi);
    }


    private void registerNextStartForBackgroundService() {
        long delayMillis = 1000 * 60 * 5;// 5 min
        long timeAlarmTrigger = System.currentTimeMillis() + delayMillis;
        registerStart(getBaseContext(), timeAlarmTrigger);
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void startService(Context context) {
        context.startService(new Intent(context, ServiceBackgroundStarter.class));
    }

    public static void stop(Context c) {
        Intent intent = new Intent(c, ServiceBackgroundStarter.class);
        c.stopService(intent);

        AlarmManager alarmManager = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(c, ServiceBackgroundStarter.class);
        PendingIntent pi = PendingIntent.getService(c, 0, myIntent, 0);
        if (pi != null) {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }
}
