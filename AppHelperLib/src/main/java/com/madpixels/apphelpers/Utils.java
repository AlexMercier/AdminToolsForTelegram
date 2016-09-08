package com.madpixels.apphelpers;


import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Snake on 23.04.2015.
 */
public class Utils {


    public static void sleep(final float millis){
        sleep( (int)millis );
    }
    public static void sleep(final int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            MyLog.log("Sleep was breaked by interrupt");
            //Thread.currentThread().interrupt();
        }
    }

    /**@return Возвращает timestamp даты указанной в смещении daysCount от текущей
     *
     */
    public static long getMillisForNextDay(int daysCount){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.DAY_OF_YEAR, daysCount);
        long ts = cal.getTimeInMillis();
        return ts;
    }

    public static boolean openUrl(String url, Context mCtx){
        try{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(url));
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(browserIntent);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean isMobileConnection(Context context) { //Проверка типа соединения, return TRUE если мобильный интернет.
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    public static boolean isNetworkExists(Context context) {
        try{
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            }
            return false;
        }catch(Exception ex){return false;}
    }

    public static final String[] pluralFriends = new String[] {"друг","друга","друзей"};
    public static final String[] pluralFollowers = new String[] {"подписчик","подписчика","подписчиков"};
    public static final String[] pluralPhotos = new String[] {"изображение","изображения","изображений"};
    //public static final String[] pluralNews = new String[] {"Новая запись","новости","новостей"};
    public static final String[] pluralUpdateNews = new String[] {"новая запись", "новых записи","новых записей"};
    public static final String[] pluralsPoll = new String[] {"голос", "голоса","голосов"};
    //public static final String[] pluralsMoreNews = new String[] {"новость", "новости","новостей"};

    // Default android plural method
    public static String pluralQuantity(Context c, int pluralRes, int value){
        return c.getResources().getQuantityString(pluralRes, value, value);
    }

    /**
     * старндартный метод андроид
     */
    public static String pluralTextQuantity(Context c, int pluralRes, int value){
        return c.getResources().getQuantityString(pluralRes, value);
    }

    /** Массив /один, два, много/
     */
    public static String pluralValue(Context c, int pluralArrayRes, int value){
        String[] s = c.getResources().getStringArray(pluralArrayRes);
        return pluralValue(value, s);
    }

    public static String pluralValue(final int value, final String[] plurals)
    {
        final int val = Math.abs(value);
        int[] numeric = new int[] {2, 0, 1, 1, 1, 2};
        int word = (val%100>4 && val%100<20)? 2 : numeric[(val%10<5)?val%10:5];
        return plurals[word];
    }

    /**
     * vk time to date
     */
    public static String TimestampToDate(String ts){
      return TimestampToDate(Long.valueOf(ts));
    }

    /**
     * @param ts В секундах, не милисекунды(!)
     */
    public static String TimestampToDate(long ts){
        //Calendar c = Calendar.getInstance();
        //c.setTimeInMillis(ts*1000);
        //SimpleDateFormat format = new SimpleDateFormat("d MMMM, yyyy, HH:mm:ss");
        return TimestampToDateFormat(ts, "d MMMM, yyyy, HH:mm:ss");
    }

    public static boolean isDateToday(long seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(seconds * 1000);

        Date getDate = calendar.getTime();

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date startDate = calendar.getTime();

        return getDate.compareTo(startDate) > 0;

    }

    public static String TimestampToDateFormat(long ts, String sFormat){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(ts*1000);
        SimpleDateFormat format = new SimpleDateFormat(sFormat);
        return format.format(c.getTime());
    }

    public static int newWidthByHeight(int oldW, int oldH, int newH){
        int newW = oldW * newH / oldH;
        return newW;
    }

    public static int newHeightByWidth(int oldW, int oldH, int newW){
        int newH = oldH * newW / oldW;
        return newH;
    }

    /**
     *
     * @param totalSecs
     * @return
     */
    public static String convertSecToReadableDate(long totalSecs){
        if(totalSecs==0) return "0";
        long hours = totalSecs / 3600;
        long  minutes = (totalSecs % 3600) / 60;
        long seconds = totalSecs % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }


    public static String convertBytesToReadableSize(long bytes){
        String returnValue = "";

        if (bytes < 1024)
            returnValue=bytes+" Byte";
        else
        if(bytes < 1048576)
            returnValue=(bytes/1024)+" Kb";
        else
        if(bytes < 1073741824) /* 1 Gb */
        {
            returnValue=String.format("%8.1f Mb", bytes/(float)1048576);
        }
        else
        {
            Long gb = bytes/1073741824;//гигабайт. целое от деления.
            Long mod = bytes % 1073741824; //остаток от деления
            Long mb = mod/1048576; //мегов
            Long bt = mod % 1048576;//байт
            Long kb = bt/1024; //килобайты
            returnValue=String.format("%d Gb, %d Mb, %d Kb", gb, mb, kb);//(value/1073741824)+" Gb";
        }

        return ""+returnValue;
    }

    public static String splitThousands(final int value){
        return splitThousands(String.valueOf(value));
    }

    public static String splitThousands(final String value){
        if (value.length()<4) return value;
        String newValue = "";
        int n = 0;
        for(int i=value.length()-1;i>=0;i--){
            Character c = value.charAt(i);
            if(n % 3 ==0 )
                newValue=" "+newValue;
            newValue= c+newValue;
            n++;
        }
        return newValue;
    }

    public static boolean isNumeric(String str)
    {
        if(str.isEmpty()) return false;
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    public static String getStringMD5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (Exception e) {
            //Log.d("","md5 error"+e.getMessage());
        }
        return "";
    }

    public static int getDaysFromTimestamp(long ts_msec) {
        return (int) (ts_msec/1000 / 24 / 60 / 60);
    }

    public static int getStringLinesCount(final String s){
        return s.split("[\n\r]").length;
    }

    public static String getLinesFromString(final String body, final int limit) {
        String strings[] = body.split("[\n\r]");
        if(strings.length<limit)
            return body;
        String returnVal="";
        for(int i=0;i<limit;i++)
            returnVal +=strings[i]+"\n";
        return returnVal.trim();
    }

    @SuppressWarnings("deprecation")
    public static void copyToClipboard(String text, Context c) {
        if(Build.VERSION.SDK_INT>10) {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager)c.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("text", text);
            clipboard.setPrimaryClip(clip);
        }else {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) c.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        }
    }

    public static boolean stringIsNumeric(String owner_id) {
        try{
            Integer.valueOf(owner_id);
            return true;
        }catch (NumberFormatException e){
        }
        return false;
    }

    public static String getTextFromAssets(Context mCtx, String name) {
        try {
            InputStreamReader reader = new InputStreamReader(mCtx.getAssets().open(name), "UTF-8");

            //InputStream ins = mCtx.getAssets().open(name);
            int ch;
            StringBuilder sb = new StringBuilder();
            while((ch = reader.read())!= -1)
                sb.append((char)ch);
            reader.close();
            return sb.toString();

        } catch (IOException e) {
            return null;
        }
    }

    public static int getPercentFrom(int value, int max){
        //float v = value;
        //float m = max;
        float r = value/(max/100f);
        return (int) r;
    }

    public static void openStore(Context c) {
        String appPackageName = c.getPackageName();
        try {
            c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } catch (android.content.ActivityNotFoundException anfe) {
            c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }



    public static void shareText(String text, Context c){
        String shareBody = text;
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share App");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        c.startActivity(Intent.createChooser(sharingIntent, "Share App"));
    }

    /**
     * сообщаем галерее что добавлено новое фото
     */
    public static void invalidateGallery(Context c, File f) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        c.sendBroadcast(mediaScanIntent);    }

    public static String millisToText(long millis) {
        if(millis<1000){
            return millis+" мсек.";
        }
        int year=0, month=0,days=0, hours=0, min=0;
        min = (int) (millis / 60000);


        if(min>59){
            hours = min / 60;
            min = min%60;
        }
        if(hours>23){
            days=hours/24;
            hours = hours%24;
        }
        if(days>29){
            month = days / 30;
            days = days%30;
        }
        if(month>11){
            year = month/12;
            month = month%12;
        }

        String text = "";
        if(year>0)
            text+=year+" г. ";
        if(month>0)
            text+=month+" м. ";
        if(days>0){
            text+=days+" дн. ";
        }
        if(hours>0){
            text+=hours+" ч.";
        }
        if(min>0){
            text+=min+" мин.";
        }
        return text;
    }

    public static int minNoZero(int val1, int val2) {
        int min =  Math.min(val1, val2);
        return min!=0?min :
                val1!=0?val1:val2;
    }


    /**
     * Parse int ranges to Integer List
     * @param textRanges range or single valies, example "1,2,3-5,100-500,777,888"
     */
    public static List<Integer> parseIntRanges(String textRanges) {
        Pattern re_next_val = Pattern.compile(
                "# extract next integers/integer range value.    \n" +
                        "([0-9]+)      # $1: 1st integer (Base).         \n" +
                        "(?:           # Range for value (optional).     \n" +
                        "  -           # Dash separates range integer.   \n" +
                        "  ([0-9]+)    # $2: 2nd integer (Range)         \n" +
                        ")?            # Range for value (optional). \n" +
                        "(?:,|$)       # End on comma or string end.",
                Pattern.COMMENTS);
        Matcher m = re_next_val.matcher(textRanges);
        List<Integer> values = new ArrayList<>();
        while (m.find()) {
            String val1 = m.group(1);
            if (m.group(2) != null) {
                String val2 = m.group(2);
                int range1 = Integer.valueOf(val1);
                int range2 = Integer.valueOf(val2);
                for (int i = range1; i <= range2; i++)
                    values.add(i);
            } else {
                values.add(Integer.valueOf(val1));
            }
        }
        return values;
    }

    public static int getMinutesFromTimestamp(long timestamp) {
        return (int) (timestamp / 1000 / 60);
    }
}
