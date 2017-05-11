package com.xologood.q8pad;

import android.content.Context;
import android.content.res.Resources;

import com.mview.medittext.utils.QpadJudgeUtils;
import com.tencent.bugly.Bugly;
import com.xologood.mvpframework.baseapp.BaseApplication;
import com.xologood.q8pad.utils.SharedPreferencesUtils;


/**
 * Created by xiao on 2016/12/20 0020.
 */
public class Qpadapplication extends BaseApplication {
    private static Qpadapplication baseApplication;
    private String UserName;
    private String UserId;
    private static String LoginName;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        UserName = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.USERNAME);
        UserId = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.USERID);
        LoginName = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.LOGINNAME);

        Bugly.init(getApplicationContext(), "0e6f777e96", false);
    }

    public static final Qpadapplication getAppContext() {
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
      //  MultiDex.install(this);
    }

    /** 判断用户是否登陆 */
    public static boolean IsLogin() {
        if (QpadJudgeUtils.isEmpty(LoginName)) {
            return false;
        } else{
            return true;
        }
    }

}
