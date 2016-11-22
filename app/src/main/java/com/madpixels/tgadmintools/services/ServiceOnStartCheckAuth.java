package com.madpixels.tgadmintools.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.madpixels.tgadmintools.activity.MainActivity;
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.helper.TgUtils;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;

/**
 * Created by Snake on 22.05.2016.
 */
public class ServiceOnStartCheckAuth extends Service{
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Проверяем авторизацию и запускаем всякие сервисы
        TgH.init(getBaseContext());
        TgH.send(new TdApi.GetAuthState(), new Client.ResultHandler() {
            @Override
            public void onResult(final TdApi.TLObject object) {
                if(!TgUtils.isAuthorized(object)) {
                    stopSelf();
                    return;
                }

                onAuthorized();
            }
        });

        return START_NOT_STICKY;
    }

    void onAuthorized(){
        MainActivity.initializeLanguage(this);
        ServiceUnbanTask.registerTask(getBaseContext());
        ServiceAutoKicker.registerTask(getBaseContext());
        ServiceChatTask.start(getBaseContext());
        ServiceGarbageCollector.start(getBaseContext());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
