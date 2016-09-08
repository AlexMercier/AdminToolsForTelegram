package com.madpixels.tgadmintools;

import android.app.Application;
import android.content.Context;

import com.madpixels.apphelpers.MyLog;
import com.madpixels.apphelpers.Sets;
import com.madpixels.tgadmintools.utils.Analytics;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

/**
 * Created by Snake on 16.01.2016.
 */
@ReportsCrashes(
        formUri = "http://crashrep.esy.es/log/crash_report.php",
        mode = ReportingInteractionMode.DIALOG,
        resDialogText = R.string.crash_dialog_text,
        resDialogTitle = R.string.crash_dialog_title,
        //resDialogCommentPrompt = R.string.crash_dialog_comment, // optional. when defined, adds a user text field input with this text resource as a label
        resDialogOkToast = R.string.crash_dialog_sendok, // optional. displays a Toast message when the user accepts to send a report.
        resDialogTheme = R.style.AppTheme
)
public class App extends Application{

    private static Context mContext;

    @Override
    public void onCreate() {
        mContext = this;
        MyLog.TAG = "tgadmin";
        MyLog.WRITE_TO_FILE = BuildConfig.DEBUG && false;
        MyLog.IS_ENABLED = BuildConfig.DEBUG && true;
        setupAcra();

        Sets.getInstance().init(this);
        Analytics.setup(this);
        super.onCreate();
    }

    private void setupAcra(){
        ACRA.init(this);
        ACRA.getErrorReporter().checkReportsOnApplicationStart();
        MyLog.setAcraListener(new MyLog.AcraListener() {
            @Override
            public void onError(Exception e) {
                ACRA.getErrorReporter().handleSilentException(e);
            }
        });
    }

    public static Context getContext(){
        return mContext;
    }
}
