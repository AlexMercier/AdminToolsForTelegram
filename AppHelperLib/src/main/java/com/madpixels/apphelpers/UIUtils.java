package com.madpixels.apphelpers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * ActivityUtils Helper <br>
 * Created by Snake on 25.02.2015.
 */
@SuppressWarnings("deprecation")
public class UIUtils {
    /**
     * Включить меню в виде трех точек вверху экшн-бара
     */
    public static void turnOnThreeDotMenuHack(Activity activity) {
        try {
            ViewConfiguration config = ViewConfiguration.get(activity);
            java.lang.reflect.Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");

            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        }
        catch (Exception e) {
            // presumably, not relevant
        }
    }

    /**
     * getString with default value for API<12
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static String getStringFromBundle(Bundle bundle, String key, String defaultValue){
        if (Build.VERSION.SDK_INT < 12){
            String returns = bundle.getString(key);
            if(returns==null)
                returns = defaultValue;
            return returns;
        } else
            return bundle.getString(key, defaultValue);
    }

    /** Показать экшн бар */
    public static void restoreActionBar(ActionBarActivity activity) {
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public static void setFullScreen(ActionBarActivity activity){
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static Activity scanForActivity(final Context mCtx) {
        if (mCtx == null)
            return null;
        else if (mCtx instanceof Activity)
            return (Activity)mCtx;
        else if (mCtx instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper)mCtx).getBaseContext());

        return null;
    }

    public static <T> T getViewT(final View parent, final int res_id) {
        View view = parent.findViewById(res_id);
        return (T) view;
    }


    public static <T extends View> T getView(final View parent, final int res_id) {
        View view = parent.findViewById(res_id);
        return (T) view;
    }

    public static <T extends View> T getView(final Dialog dialog, final int res_id){
        return getView(dialog.getWindow(), res_id);
    }

    public static <T extends View> T getView(final Window parent, final int res_id) {
        return getView(parent.getDecorView(), res_id);
    }

    public static <T extends View> T getView(final Activity parent, final int res_id) {
        return getView(parent.getWindow().getDecorView(), res_id);
    }

    static public  void showSoftKeyboard(EditText et){
        if(et == null) return;
        InputMethodManager imm = (InputMethodManager) scanForActivity(et.getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideKeyboard(Activity a) {
        InputMethodManager im = (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im.isAcceptingText())//Если клава показана
            im.hideSoftInputFromWindow(a.getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static <T extends View> T getHolderView(View parent, int res_id) {
        SparseArray localSparseArray = (SparseArray) parent.getTag();
        if (localSparseArray == null) {
            localSparseArray = new SparseArray();
            parent.setTag(localSparseArray);
        }
        View view = (View) localSparseArray.get(res_id);
        if (view == null) {
            view = parent.findViewById(res_id);

            localSparseArray.put(res_id, view);
        }
        return (T) view;
    }

    public static void setBatchClickListener(View.OnClickListener onClick, View...views) {
        for(View v:views)
            v.setOnClickListener(onClick);
    }

    public static Toolbar setToolbar(AppCompatActivity a, int toolbarRes) {
        Toolbar tb = getView(a, toolbarRes);
        a.setSupportActionBar(tb);
        if(Build.VERSION.SDK_INT>=21)
            tb.setTransitionName("actionBar");
        return tb;
    }

    public static Toolbar setToolbarWithBackArrow(AppCompatActivity a, int toolbarRes){
        Toolbar tb = setToolbar(a, toolbarRes);
        a.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return tb;
    }

    public static void setTextColotRes(final TextView textView, final int colorRes) {
        final int c = textView.getResources().getColor(colorRes);
        textView.setTextColor(c);
    }

    /**
     * Set the enabled state of this view.
     *
     * @param visibility One of {@link View#VISIBLE}, {@link View#INVISIBLE}, or {@link View#GONE}.
     * @attr ref android.R.styleable#View_visibility
     */
    public static void setBatchVisibility(final int visibility, View...views) {
        for(View v:views)
            v.setVisibility(visibility);
    }

    public static <T> T getLayoutParams(ViewGroup view) {
        return (T) view.getLayoutParams();
    }


    public static View inflate(final Context mContext, final int layoutResId) {
       return  ((LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE )).inflate(layoutResId, null);
    }

    public static void include(Activity activity, int parent_id, int included_child) {
        ViewGroup layer_parent = getView(activity, parent_id);
        layer_parent.addView(UIUtils.inflate(activity, included_child));
    }

    public static Drawable getTintDrawable(Context c, @DrawableRes int drawableRes, @ColorRes int tintColorRes) {
        @ColorInt int color = c.getResources().getColor(tintColorRes);
        Drawable drawable = c.getResources().getDrawable(drawableRes);
        Drawable wrapped = DrawableCompat.wrap(drawable.mutate());
        DrawableCompat.setTint(wrapped, color);
        return wrapped;
    }


    public static void removeGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if(Build.VERSION.SDK_INT>=16)
            v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        else
            v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
    }

    public static int getPixelsFromDp(int dpValue) {
        return (int) (dpValue * Resources.getSystem().getDisplayMetrics().density);
    }

    public static void setActionBarWithBackArrow(AppCompatActivity activity) {
        android.support.v7.app.ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
