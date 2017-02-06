package com.xologood.zxing.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.provider.Settings;

import java.util.ArrayList;

/**
 * Created by Administrator on 16-12-29.
 */

public class JudgeUtils {

    /**
     * 判断对象是否为空
     */
    public static boolean isEmpty(String str){
        if (str == null){
            return true;
        }else if ("".equals(str)){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isEmpty(Long str) {
        if (str == null){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isEmpty(Integer str) {
        if (str == null){
            return true;
        }else if ("".equals(str)){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isEqualEmpty(String str) {
        if (str == null){
            return true;
        }else if ("".equals(str)){
            return true;
        }else if("请选择".equals(str)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断对象是否为空
     * 是 返回 ""
     * 否 返回 当前值
     * @return
     */
    public static String isEmptyGetString(String s) {
        if (isEmpty(s)){
            return "";
        }else{
            return s;
        }

    }

    /**
     * 检查是否数字
     */
    public static boolean isNumber(String str) {
        return str.matches("\\d+");
    }

    /**
     * 判断   是否网络有效
     */
    public static boolean isNetAvaliable(Context mContext) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }else{
                return false;
            }
        }else{
            return false;
        }
    }


    /**
     * 判断   是否飞行模式
     */
    public static boolean isAirplaneMode(Context mContext) {
        if(mContext != null){
            int isAirplaneMode = Settings.System.getInt(mContext.getContentResolver(), Settings.System.AIRPLANE_MODE_ON,0);
            if(isAirplaneMode == 1){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * 判断   是否连着wifi
     */
    public static boolean isWifi(Context mContext) {
        if(mContext != null){
            ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * 判断   SD卡是否可用
     */
    public static boolean isSDCardAvailable() {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED)){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 判断   本地路径是否图片
     */
    public static boolean isPicure(String path) {
        if (path.endsWith(".png") || path.endsWith(".PNG") || path.endsWith(".jpg") ||
                path.endsWith(".JPG") || path.endsWith(".jpeg") || path.endsWith(".JPEG")){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断自己些的一个Service是否已经运行
     */
    public static boolean isServiceWorked(Context context, String SerbiceName) {
        ActivityManager myManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(200);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals(SerbiceName)) {
                return true;
            }
        }
        return false;
    }

}
