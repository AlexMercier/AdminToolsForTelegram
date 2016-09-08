package com.madpixels.tgadmintools.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.madpixels.apphelpers.Sets;
import com.madpixels.tgadmintools.Const;


public class ReceiverStartupSystem extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean enabled = Sets.getBoolean(Const.AUTORUN, true);
        if(!enabled)
            return;
        context.startService(new Intent(context, ServiceOnStartCheckAuth.class));
    }
}
