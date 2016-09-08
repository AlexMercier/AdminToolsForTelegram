package com.madpixels.apphelpers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Snake on 18.11.2015.
 */
public class Amazon {
    public static void openStore(Context c){
        try {
            String id =  "amzn://apps/android?p=" + c.getPackageName();
            c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(id))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } catch (Exception e) {
            String id =  "http://www.amazon.com/gp/mas/dl/android?p="+c.getPackageName();
            c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(id))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    public static void openOtherApps(Context c){
        String url="amzn://apps/android?p="+c.getPackageName()+"&showAll=1";
        if(!Utils.openUrl(url, c))
            Utils.openUrl("http://www.amazon.com/gp/mas/dl/android?p="+c.getPackageName()+"&showAll=1", c);
    }
}
