package com.madpixels.apphelpers;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Snake on 18.11.2015.
 */
public class PlayStore {
    public static void openStore(Context c) {
        String id = c.getPackageName();

        Uri uri = Uri.parse("market://details?id=" + id);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            c.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            c.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + id)));
        }

    }

    public static void openOtherApps(Context c, String devLink) {
        Uri uri = Uri.parse("https://play.google.com/store/apps/developer?id="+devLink);
        Utils.openUrl(uri.toString(), c);
    }
}
