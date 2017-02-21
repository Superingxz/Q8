package com.xologood.q8pad.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.xologood.q8pad.Qpadapplication;

public class SharedPreferencesUtils {

	private static SharedPreferences sharedata  = Qpadapplication.getAppContext().getSharedPreferences("submitTime", 0);
	private static Editor editor = sharedata.edit();

	public static void saveStringData(Context context, String key, String value) {
		//sharedata = context.getSharedPreferences("submitTime", 0).edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static String getStringData(Context context,String key){
	//	SharedPreferences sharedata = context.getSharedPreferences("submitTime", 0);
		String data = sharedata.getString(key, null);
		return data;
	}

	public static void saveIntData(Context context,String key,int value) {
	//	Editor sharedata = context.getSharedPreferences("submitTime", 0).edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static int getIntData(Context context,String key){
	//	SharedPreferences sharedata = context.getSharedPreferences("submitTime", 0);
		int value = sharedata.getInt(key, 0);
		return value;
	}

	public static void saveBooleanData(Context context,String key,boolean value) {
		//	Editor sharedata = context.getSharedPreferences("submitTime", 0).edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static boolean getBooleanData(Context context,String key){
		//	SharedPreferences sharedata = context.getSerences("submitTime", 0);
		boolean value = sharedata.getBoolean(key, false);
		return value;
	}

	public static void clearData(Context context) {
		editor.clear().commit();
	}
}
