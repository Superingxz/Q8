package com.xologood.q8pad.ui.abolishcode;

import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.api.Api;
import com.xologood.q8pad.api.HostType;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.utils.SharedPreferencesUtils;

import rx.Observable;

/**
 * Created by Administrator on 17-1-19.
 */

public class AbolishModel implements AbolishCodeContract.Model {
    private String recorderBase = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.RECORDERBASE);
    private String sysKeyBase = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.SYSKEYBASE);
    @Override
    public Observable<BaseResponse<String>> InvoicingAbolishCode(String code) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).InvoicingAbolishCode(code);
    }
}
