package com.madpixels.apphelpers;

/**
 * Created by Snake on 26.05.2015.
 */
public abstract class Callback {
    public int intParam;
    public abstract void onCallback(Object o, int code);
}
