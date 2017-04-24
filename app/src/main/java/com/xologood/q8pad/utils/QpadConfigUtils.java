package com.xologood.q8pad.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.view.WindowManager;

import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;


/**
 * 配置工具类
 * Created by Administrator on 16-1-22.
 */

public class QpadConfigUtils {

    private final static String IPDOMAIN = "ipdomain";
    private final static String PORT = "port";

    public final static String KEY_PUSH_SY = "push_sy";
    public final static String KEY_PUSH_ZD = "push_zd";
    public final static String KEY_PUSH_LOCK = "push_lock";
    public final static String KEY_APP_NEW = "app_new";

    /**
     * 设置config的值
     */
    public static void setConfigMeta(String key, boolean value) {
        Editor sharedata = Qpadapplication.getAppContext().getSharedPreferences(Config.SHARED_PREFERENCES.CONFIG,
                Context.MODE_APPEND).edit();
        sharedata.putBoolean(key, value);
        sharedata.commit();
    }

    /**
     * 得到config的值
     */
    public static boolean getConfigMeta(String key, boolean defaultdata) {
        return Qpadapplication.getAppContext().getSharedPreferences(Config.SHARED_PREFERENCES.CONFIG,
                Context.MODE_APPEND).getBoolean(key, defaultdata);
    }

    /**
     * 设置域名
     */
    public static void setIpDomain(Context context, String IpDomain) {
        Editor sharedata = context.getSharedPreferences(Config.SHARED_PREFERENCES.CONFIG,
                Context.MODE_APPEND).edit();
        sharedata.putString(IPDOMAIN, IpDomain);
        sharedata.commit();
    }




    /**
     * 获取JsessionID
     */
    public static String getAuthCookie(String cookieValue) {
        String sessionId = "";
        if (cookieValue != null) {
            sessionId = cookieValue.substring(0, cookieValue.indexOf(";"));
            sessionId = sessionId.substring(sessionId.indexOf("=") + 1, sessionId.length());
        }
        return sessionId;
    }

    /**
     * 拨打电话
     */
    public static void dial(Context context,String telephone){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telephone));
        context.startActivity(intent);
    }


    
	/**
	 * 隐藏软键盘
	 */
	public static void hideInputMethod(Context context) {
		((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}
	
	/**
	 * 打开浏览器
	 */
	public static void openBrowser(Context context, String url) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.setData(Uri.parse(url));
		context.startActivity(intent);
	}
	
	/**
	 * 将字符转为2进制数据
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			// if (n<b.length-1) hs=hs+":";
		}
		return hs.toUpperCase();
	}

    /** 获取屏幕尺寸 */
    public final static class SCREEN {
        public static int Width = 0;
        public static int Height = 0;
    }
}
