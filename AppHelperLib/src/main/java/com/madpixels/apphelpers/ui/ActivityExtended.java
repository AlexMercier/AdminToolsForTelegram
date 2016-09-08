package com.madpixels.apphelpers.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.madpixels.apphelpers.MyLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Snake on 18.02.2016.
 */
public class ActivityExtended extends AppCompatActivity {
    public final Context mContext = this;
    private List<Dialog> mDialogs;

    public <T> T getView(@IdRes int id) {
        return (T) findViewById(id);
    }

    public final void onUiThread(final Runnable r){
        if(!isFinishing())
            runOnUiThread(r);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            close();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        dismissAttachedDialogs();
        super.onDestroy();
    }


    public Activity getActivity(){
        return this;
    }

    public void close() {
        finish();
    }

    /**
     * When we show progress non-cancelable AlertDialog and rotate device, then this AlertDialog may call illegalstate exception, so
     * we must assign dialog to activity and on rotation dismiss it.
     */
    public void assignAlertDialog(Dialog d){
        if(mDialogs==null)
            mDialogs=new ArrayList<>();
        mDialogs.add(d);
    }

    private void dismissAttachedDialogs() {
        if(mDialogs!=null) {
            for (Dialog d : mDialogs)
                try {
                    d.dismiss();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    MyLog.log(e);
                }
        }
        mDialogs=null;
    }

}
