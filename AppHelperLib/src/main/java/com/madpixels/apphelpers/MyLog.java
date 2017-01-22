package com.madpixels.apphelpers;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by Snake on 23.04.2015.
 */
public class MyLog {
    static public boolean IS_ENABLED = false;
    static public boolean WRITE_TO_FILE = false;
    public static String TAG = "null";
    private static File logWriteFile = null;
    private static AcraListener acraListener = null;

    public static void init(String logTag) {
        TAG = logTag;
    }

    public static void log(final String msg) {
        if (IS_ENABLED && msg != null && !msg.isEmpty()) {
            Log.d(TAG, msg);
            if (WRITE_TO_FILE) {
                writeToFile(msg);
            }
        }
    }

    static void writeToFile(final String msg) {
        if (logWriteFile == null)
            logWriteFile = new File(Environment.getExternalStorageDirectory(), TAG + ".log");

        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(logWriteFile, true));
            buf.append("[" + Utils.TimestampToDateFormat(System.currentTimeMillis() / 1000, "d MMMM, yyyy, HH:mm:ss") + "] " + msg);
            buf.newLine();
            buf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void log(final int msg) {
        if (IS_ENABLED)
            log("int value: " + msg);
    }

    public static void log(final Throwable e) {
        if (IS_ENABLED && e != null && e.getMessage() != null) {
            log(e.getMessage());
            e.printStackTrace();
            if(acraListener!=null)
                acraListener.onError(e);
        }
    }

    public static void setAcraListener(final AcraListener acraListener) {
        MyLog.acraListener = acraListener;
    }


    public static abstract class AcraListener {
        public abstract void onError(Throwable s);
    }
}
