package com.xologood.q8pad.download;

import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.api.Api;
import com.xologood.q8pad.api.HostType;
import com.xologood.q8pad.utils.SharedPreferencesUtils;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by Administrator on 17-2-9.
 */

public class DownloadModel {
    private String recorderBase = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.RECORDERBASE);
    private String sysKeyBase = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.SYSKEYBASE);

    public Observable<ResponseBody> downloadFileWithDynamicUrlSync(String fileUrl) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).downloadFileWithDynamicUrlSync(fileUrl);
    }
}
