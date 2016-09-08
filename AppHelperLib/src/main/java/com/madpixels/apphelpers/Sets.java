package com.madpixels.apphelpers;
/**
 * Settings module by SnakeD3
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Sets {
	private static final Sets instance = new Sets();
	Context mContext;
	
	public static Sets getInstance() {
		return instance;
	}
	
	// При старте новой активити или в onResume обязательно переинициализировать так как статичный инстанс может устареть (выгрузиться) при сворачивании
	public void init(Context mContext){
		this.mContext = mContext;
	}
	
	private Context getContext(){
		return mContext;
	}
	
	private static SharedPreferences pref (){	
		return PreferenceManager.getDefaultSharedPreferences(getInstance().getContext());
	}
	
	public static void clearSettings(){
		//Log.d(GameConst.TAG,"settings reset");		
		Editor shpfed = pref().edit();
		shpfed.clear();
		shpfed.commit();		
	}
	
	public static void removeSetting(String param){		
		Editor shpfed = pref().edit();
		shpfed.remove(param);
		shpfed.commit();
	}

	public static Object getSetting(String param, Object defVal, Context context){
		
		//SharedPreferences shpf = pref(context);
				
		Object returnValue = null;
		try{		
		if (defVal instanceof String)
			returnValue = pref().getString(param,(String) defVal);
		else
		if (defVal instanceof Integer)
			returnValue = pref().getInt(param,(Integer) defVal);
		else
		if (defVal instanceof Boolean)
			returnValue = pref().getBoolean(param,(Boolean) defVal);
		else
			if (defVal instanceof Long)
				returnValue = pref().getLong(param,(Long) defVal);
		else
			if (defVal instanceof Float)
				returnValue = pref().getFloat(param,(Float) defVal);
		else
			returnValue = defVal;
		}catch(Exception e){
			returnValue = defVal;
			}			
		
		return returnValue;
	}
	
public static String getString(String param, String defVal){
		String returnValue = defVal;
		try{
			returnValue = pref().getString(param, defVal);
		}catch(Exception e){
			returnValue = defVal;
		}
		
		return returnValue;
	}
	
	
public static Boolean getBoolean(String param, Boolean defval){ 
		
		//SharedPreferences shpf = pref(getInstance().mContext);
				
		Boolean returnValue = defval;
		try{
			returnValue = pref().getBoolean(param, defval);
		
		}catch(Exception e){
			returnValue = defval;
			}			
		
		return returnValue;
	}
	
	public static void set(String param, Object value){
		Editor shpfed = pref().edit();
		
		if (value instanceof String)
			shpfed.putString(param, (String) value);
		else
		if (value instanceof Boolean)
				shpfed.putBoolean(param, (Boolean) value);
		else
		if (value instanceof Integer)
				shpfed.putInt(param, (Integer) value);
		else
		if (value instanceof Long)
			shpfed.putLong(param, (Long) value);
		else 
		if (value instanceof Float)
			shpfed.putFloat(param, (Float) value);	
			
		shpfed.apply();	
	}
		
	public static int getInteger(String param, int defval) {				
		int returnValue = defval;
		try{
			//SharedPreferences shpf = pref(getInstance().mContext);
			returnValue = pref().getInt(param, defval);
		}catch(Exception e){
			returnValue = defval;
		}			
		
		return returnValue;
	}
	
	public static long getLong(String param, long defval) {
		//SharedPreferences shpf = pref(getInstance().mContext);
		
		long returnValue = defval;
		try{
			returnValue = pref().getLong(param, defval);
		
		}catch(Exception e){
			returnValue = defval;
			}			
		
		return returnValue;
	}

	public static float getFloat(String param, float defVal, Context context) {
		//SharedPreferences shpf = pref(context);
		
		float returnValue = defVal;
		try{
			returnValue = pref().getFloat(param, defVal);
		
		}catch(Exception e){
			returnValue = defVal;
			}			
		
		return returnValue;
	}

	public static boolean has(final String param){
		final boolean result = pref().contains(param);
		return result;
	}


    public boolean isNull() {
        return mContext==null;
    }

}
