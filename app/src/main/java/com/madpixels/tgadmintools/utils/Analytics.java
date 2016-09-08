package com.madpixels.tgadmintools.utils;


import com.madpixels.tgadmintools.App;
import com.yandex.metrica.YandexMetrica;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Snake on 04.10.2015.
 */
public class Analytics {

    public static void setup(App app) {
        YandexMetrica.activate(app, "41a540f1-c48f-4aa8-8659-cf4d244f131f");
        YandexMetrica.setCollectInstalledApps(false);
        YandexMetrica.setReportCrashesEnabled(false);
        YandexMetrica.enableActivityAutoTracking(app);
    }

    public static void sendReport(final String group, final String action, final String value) {
        Map<String, Object> eventAttributes = new HashMap<>();
        eventAttributes.put(action, value);
        YandexMetrica.reportEvent(group, eventAttributes);
    }

    public static void sendReportJson(final String event, final JSONObject data) {
        YandexMetrica.reportEvent(event, data.toString());
    }

    public static void sendError(String msg, Throwable e) {
       // if(Utils.isConnected(App.getContext()))
        //    YandexMetrica.reportError(msg, e);
    }
}
