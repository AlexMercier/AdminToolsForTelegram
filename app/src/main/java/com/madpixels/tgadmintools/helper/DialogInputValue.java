package com.madpixels.tgadmintools.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.madpixels.apphelpers.MyToast;
import com.madpixels.apphelpers.UIUtils;
import com.madpixels.tgadmintools.R;

/**
 * Created by Snake on 28.05.2016.
 */
public class DialogInputValue extends AlertDialog{

    private  int value = 0;
    private EditText editValue;
    private SetValueCallback onSetValueCallback;

    public DialogInputValue(final Context context) {
        super(context);
        setTitle(R.string.title_change_value);

        LinearLayout l = new LinearLayout(context);
        l.setOrientation(LinearLayout.VERTICAL);
        l.setPadding(16, 8, 16, 8);

        TextView text = new TextView(context);
        text.setText(R.string.caption_enter_value);
        l.addView(text);

        editValue = new EditText(context);
        editValue.setInputType(InputType.TYPE_CLASS_NUMBER);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                editValue.selectAll();
                editValue.requestFocus();
                UIUtils.showSoftKeyboard(editValue);
            }
        }, 300);

        l.addView(editValue);

        setView(l);
        setButton(BUTTON_POSITIVE, context.getString(R.string.btnSave), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    int val = Integer.valueOf(editValue.getText().toString());
                    setValue(val);
                }catch (Exception e){
                    MyToast.toast(context, "Number format exception");
                    return;
                }
                if(onSetValueCallback!=null)
                    onSetValueCallback.onSetValue(value);
            }
        });
        setButton(BUTTON_NEGATIVE, context.getString(R.string.cancel), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    public DialogInputValue setValue(int value) {
        this.value = value;
        editValue.setText(this.value+"");
        return this;
    }

    public DialogInputValue onApply(SetValueCallback callback){
        onSetValueCallback = callback;
        return this;
    }

    public DialogInputValue showDialog(){
        super.show();
        return this;
    }

    public interface SetValueCallback {
        void onSetValue(int value);
    }


}
