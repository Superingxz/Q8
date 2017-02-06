package com.xologood.mvpframework.baseapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import java.util.Stack;

/**
 * APPLICATION
 */
public class BaseApplication extends Application {

    private static BaseApplication baseApplication;
    public static Stack<Activity> activityLists = new Stack<>();
    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
    }

    public static Context getAppContext() {
        return baseApplication;
    }
    public static Resources getAppResources() {
        return baseApplication.getResources();
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 分包
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }

    /**
     * Activity关闭时，删除Activity列表中的Activity对象
     */
    public static void removeActivity(Activity a){
        if(a != null){
            activityLists.remove(a);
        }
    }

    /**
     * 向Activity列表中添加Activity对象
     */
    public static void addActivity(Activity a){
        if(activityLists == null){
            activityLists = new Stack<>();
        }
        activityLists.add(a);
    }

    /**
     * 获得当前栈顶Activity
     */
    public static Activity currentActivity(){
        if(activityLists.size() > 0){
            return activityLists.lastElement();
        }
        return null;
    }

    /**
     * 关闭Activity列表中的所有Activity
     */
    public static void finishActivity(){
        for (Activity activity : activityLists) {
            if (null != activity) {
                activity.finish();
            }
        }
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
