package com.madpixels.apphelpers.ui;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Snake on 29.02.2016.
 */
public class ProgressDialogBuilder {


    android.app.ProgressDialog prgDlg;
    private Context ctx;
    public boolean isCancelled = false;

    public ProgressDialogBuilder(Context c) {
        prgDlg = new ProgressDialog(c);
        //prgDlg.setTitle("");
        //prgDlg.setMessage("");
        prgDlg.setIndeterminate(true);
        this.ctx = c;
    }

    public ProgressDialogBuilder setTitle(int resId) {
        return setTitle(ctx.getString(resId));
    }


    public ProgressDialogBuilder setTitle(String title) {
        prgDlg.setTitle(title);
        return this;
    }

    public ProgressDialogBuilder setMessage(int resId) {
        return setMessage(ctx.getString(resId));
    }

    public ProgressDialogBuilder setMessage(String message) {
        prgDlg.setMessage(message);
        return this;
    }

    public ProgressDialogBuilder setIndeterminate(boolean isIndeterminate) {
        prgDlg.setIndeterminate(isIndeterminate);
        return this;
    }

    public ProgressDialog show() {
        prgDlg.show();
        return prgDlg;
    }

    public ProgressDialogBuilder setNegativeButton(String text, DialogInterface.OnClickListener onClickListener) {
        setButton(DialogInterface.BUTTON_NEGATIVE, text, onClickListener);
        return this;
    }

    public ProgressDialogBuilder setNegativeButton(int textRes, DialogInterface.OnClickListener onClickListener) {
        setButton(DialogInterface.BUTTON_NEGATIVE, ctx.getString(textRes), onClickListener);
        return this;
    }

    /**
     * Set a listener to be invoked when the positive button of the dialog is pressed.
     *
     * @param whichButton Which button to set the listener on, can be one of
     *            {@link DialogInterface#BUTTON_POSITIVE},
     *            {@link DialogInterface#BUTTON_NEGATIVE}, or
     *            {@link DialogInterface#BUTTON_NEUTRAL}
     * @param text The text to display in positive button.
     */
    private void setButton(int whichButton, String text, DialogInterface.OnClickListener onClickListener) {
        prgDlg.setButton(whichButton, text, onClickListener != null ? onClickListener :
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
    }

    public ProgressDialogBuilder setPositiveButton(String text, DialogInterface.OnClickListener onClickListener) {
        setButton(DialogInterface.BUTTON_POSITIVE, text, onClickListener);
        return this;
    }

    public ProgressDialogBuilder setPositiveButton(int textRes, DialogInterface.OnClickListener onClickListener) {
        setButton(DialogInterface.BUTTON_POSITIVE, ctx.getString(textRes), onClickListener);
        return this;
    }

    public ProgressDialogBuilder setCancelable(boolean isCancellable) {
        prgDlg.setCancelable(isCancellable);
        return this;
    }

    public ProgressDialog build() {
        return prgDlg;
    }

    public ProgressDialogBuilder setMax(int max) {
        prgDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); //neew change style
        prgDlg.setMax(max);
        prgDlg.setIndeterminate(false);
        return this;
    }
}
