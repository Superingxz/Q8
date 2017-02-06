package com.xologood.zxing.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast统一管理类
 */
public class T {
	
	private static Toast toast = null;
	private static Activity activity = null;
	
	/**
	 * 短时间显示Toast
	 */
	public static void showShort(Context context, CharSequence message){
		if(null == toast || activity !=  (Activity) context){
			activity = (Activity) context;
			toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
			// toast.setGravity(Gravity.CENTER, 0, 0);
		}else{
			toast.setText(message);
		}
		toast.show();
	}

	/**
	 * 短时间显示Toast
	 */
	public static void showShort(Context context, int message){
		if(null == toast || activity !=  (Activity) context){
			activity = (Activity) context;
			toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
			// toast.setGravity(Gravity.CENTER, 0, 0);
		}else{
			toast.setText(message);
		}
		toast.show();
	}

	/**
	 * 长时间显示Toast
	 */
	public static void showLong(Context context, CharSequence message){
		if (null == toast || activity !=  (Activity) context){
			activity = (Activity) context;
			toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
			// toast.setGravity(Gravity.CENTER, 0, 0);
		}else{
			toast.setText(message);
		}
		toast.show();
	}

	/**
	 * 长时间显示Toast
	 */
	public static void showLong(Context context, int message){
		if (null == toast || activity !=  (Activity) context){
			activity = (Activity) context;
			toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
			// toast.setGravity(Gravity.CENTER, 0, 0);
		}else{
			toast.setText(message);
		}
		toast.show();
	}
	
	/**
	 * 长时间显示Toast
	 */
	public static void showLongSetG(Context context, CharSequence message){
		if (context != null && (null == toast || activity !=  (Activity) context)){
			activity = (Activity) context;
			toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.BOTTOM, 0, 10);
		}else{
			toast.setText(message);
		}
		toast.show();
	}

	/**
	 * 自定义显示Toast时间
	 */
	public static void show(Context context, CharSequence message, int duration){
		if (null == toast || activity !=  (Activity) context){
			activity = (Activity) context;
			toast = Toast.makeText(context, message, duration);
			// toast.setGravity(Gravity.CENTER, 0, 0);
		}else{
			toast.setText(message);
		}
		toast.show();
	}

	/**
	 * 自定义显示Toast时间
	 */
	public static void show(Context context, int message, int duration){
		if (null == toast || activity !=  (Activity) context){
			activity = (Activity) context;
			toast = Toast.makeText(context, message, duration);
			// toast.setGravity(Gravity.CENTER, 0, 0);
		}else{
			toast.setText(message);
		}
		toast.show();
	}

	/** Hide the toast, if any. */
	public static void hideToast(){
		if (null != toast){
			toast.cancel();
		}
	}
}
