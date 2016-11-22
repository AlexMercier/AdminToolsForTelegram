package com.madpixels.tgadmintools.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;

import com.madpixels.apphelpers.Sets;
import com.madpixels.tgadmintools.Const;
import com.madpixels.tgadmintools.R;

/**
 * Created by Snake on 18.01.2016.
 */
public class MyNotification {
    //NotificationManagerCompat mNotificationManager;
    public NotificationCompat.Builder mNotificationBuilder;
    private int ID;
    private PendingIntent pendingIntentDismiss;
    Context mContext;

    NotificationCompat.Style bigTextStyle;
    // public static boolean notification_type_changed = true;
    // private static int id_next_generator=Const.NEWS_NOTIFICATION_ID+1;

    public MyNotification(int id, Context c) {
        this.mContext = c;
        ID = id;
        Drawable largeIcon = mContext.getResources().getDrawable(R.mipmap.ic_launcher);

        mNotificationBuilder = new NotificationCompat.Builder(mContext)
                .setContentTitle(mContext.getString(R.string.app_name))
                .setContentInfo(mContext.getString(R.string.app_name))
                .setContentText("")
                .setLargeIcon(((BitmapDrawable) largeIcon).getBitmap())
                .setSmallIcon(R.drawable.ic_notification)
                .setAutoCancel(true)
                .setOnlyAlertOnce(false);
        if (Build.VERSION.SDK_INT >= 21) {
            if (!Sets.getBoolean(Const.NOTIFICATION_HIDE_CONTENT_API21, false))
                mNotificationBuilder.setVisibility(Notification.VISIBILITY_PUBLIC);
        }
    }

    public void setContentIntent(PendingIntent pIntent) {
        mNotificationBuilder.setContentIntent(pIntent);
    }

    public void setTexts(String title, String msg) {
        mNotificationBuilder.setContentTitle(title);
        mNotificationBuilder.setContentText(msg);
    }

    public void setTexts(String title, String msg, String bigText) {
        mNotificationBuilder.setContentTitle(title);
        mNotificationBuilder.setContentText(msg);

        if (Build.VERSION.SDK_INT >= 16) {
            NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
            style.setBigContentTitle(title);
            style.bigText(bigText);
            style.setSummaryText(mContext.getString(R.string.app_name));
        }
    }

    public void setStyle(NotificationCompat.Style bigText) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.bigTextStyle = bigText;
            mNotificationBuilder.setStyle(this.bigTextStyle);
        }
    }

    private static String bigText="";
    public void appendBigText(String text){
        bigText = text+"\n"+bigText;
        NotificationCompat.BigTextStyle bs = (NotificationCompat.BigTextStyle) bigTextStyle;
        bs.bigText(bigText);
    }

    public void alert() {
        NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(mContext);
        mNotificationBuilder.setWhen(System.currentTimeMillis());
        Notification n = mNotificationBuilder.getNotification();
        mNotificationManager.notify(ID, n);
    }

    public void setAllowDismiss() {
        boolean fixed = false; //!Sets.getBoolean(Const.SET_NOTIFICATION_CAN_SWIPE, false);
        mNotificationBuilder.setOngoing(fixed);
        if (fixed) {// Если нельзя смахнуть то добавим кнопку ЗАКРЫТЬ
            mNotificationBuilder.addAction(0, "Скрыть", pendingIntentDismiss);
        }
    }

    public void setSound() {

        if (Sets.getBoolean(Const.SET_NOTIFICATION_SOUND_ENABLE, true)) {
            int type = 0;//Sets.getInteger(Const.SOUND_TYPE, 0);
            Uri u;
            u = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            try {
                mNotificationBuilder.setSound(u);
            } catch (Exception e) {// При ошибке установим стандартный
                mNotificationBuilder.setDefaults(Notification.DEFAULT_SOUND);
            }
        } else {
            mNotificationBuilder.setSound(null);
        }
    }

    private final static StyleSpan mBoldSpan = new StyleSpan(Typeface.BOLD);

    public static SpannableString makeNotificationLine(String title, String text) {
        final SpannableString spannableString;
        if (title != null && title.length() > 0) {
            spannableString = new SpannableString(String.format("%s  %s", title, text));
            spannableString.setSpan(mBoldSpan, 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            spannableString = new SpannableString(text);
        }
        return spannableString;
    }

    public void setlargeImage(Drawable d) {
        mNotificationBuilder.setLargeIcon(((BitmapDrawable) d).getBitmap());
    }

    public void setTitile(String title) {
        mNotificationBuilder.setContentTitle(title);
    }

    public void startForeground(Service s) {
        s.startForeground(300, mNotificationBuilder.build());
    }
}
