package com.xologood.q8pad.ui.Logistics;

import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.api.Api;
import com.xologood.q8pad.api.HostType;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.ReportInfo;
import com.xologood.q8pad.bean.ReportInv;
import com.xologood.q8pad.utils.SharedPreferencesUtils;

import rx.Observable;

/**
 * Created by wei on 2017/3/13.
 */

public class LogisticsModel implements LogisticsContract.Model {
    private String recorderBase = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.RECORDERBASE);
    private String sysKeyBase = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.SYSKEYBASE);

    @Override
    public Observable<BaseResponse<ReportInfo>> getProductDetailByBarcode(String barCode) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).getProductDetailByBarcode(barCode);
    }

    @Override
    public Observable<BaseResponse<ReportInv>> invByBarCodeList(String sBarCode) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).invByBarCodeList(sBarCode);
    }


}
