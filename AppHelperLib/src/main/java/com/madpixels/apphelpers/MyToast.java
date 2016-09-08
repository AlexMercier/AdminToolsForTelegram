package com.madpixels.apphelpers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

public class MyToast {

	private Context mContext;
	private Toast toast;
	private int mColor = Color.WHITE;

	public MyToast(Context mContext) {
		this.mContext = mContext;
	}

	public static void toast(final Context c, final Object msg){
        toast(c, msg, false);
	}

    public static void toastL(final Context c, final Object msg){
        toast(c, msg, true);
    }

	public static void toast(final Context c, final Object msg, boolean isLong){
		if(Looper.myLooper() == Looper.getMainLooper())
			new MyToast(c).show(msg, isLong);
		else
			new MyToast(c).showOnUiThread(msg, isLong);
	}
	
	public void fast(final Object msg){
		show(msg, false);
	}
	public void longToast(final Object msg){
		show(msg, true);
	}
	
	/** @param msg переменная типа String или int - для ресурса
	 * 	@param isLong true долгий показ, false - быстрый показ.
	 */
	public void show(final Object msg, final  boolean isLong){
		String text = "";
		if(msg instanceof Integer){
			text = mContext.getResources().getString((Integer)msg);
		}else{
			text = msg.toString();
		}
		if(toast==null){
			int len = isLong?Toast.LENGTH_LONG:Toast.LENGTH_SHORT; 
			toast = Toast.makeText(mContext,text, len);

			TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
			if(mColor!=Color.WHITE)
				v.setTextColor(mColor);
			resetColor();

			toast.show();
			
			toast.show();
			new Thread(dismissToast).start();
		}
		else{ 
			if(toast!=null)
				toast.setText(text);
		}
	}
	
	private void setColor(int pColor){
		if(pColor!=0)
			mColor = pColor;
	}
	private void resetColor() {
		mColor = Color.WHITE;		
	}

	private Runnable dismissToast = new Runnable() {		
		@Override
		public void run() {
			try {
				Thread.sleep(2000);
				toast = null;		
			} catch (InterruptedException e) {}			
		}
	};
	
	/** @param msg переменная типа String или int - для ресурса
	 * 	@param isLong true долгий показ, false - быстрый показ.
	 * 	@param pColor - Цвет текста, 0 - по умолчанию
	 */
	public void show(final Object msg,final  boolean isLong, final int pColor){
		setColor(pColor);
		show(msg, isLong);
	}
	
	public void showOnUiThread(final Object msg,final boolean isLong) {
		Activity a = UIUtils.scanForActivity(mContext);
		a.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				show(msg, isLong);				
			}
		});		
	}
	
	public void showOnUiThread(final Object msg ,final boolean isLong, final int pColor){
		setColor(pColor);
		showOnUiThread(msg, isLong);
	}

	public void fastOnUi(final Object msg) {
		showOnUiThread(msg, false);
	}

	public void longOnUi(final Object msg) {
		showOnUiThread(msg, true);
	}
	
}
