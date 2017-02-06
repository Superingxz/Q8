package com.xologood.q8pad.ui.invoicingdetail;

import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.api.Api;
import com.xologood.q8pad.api.HostType;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.Invoice;
import com.xologood.q8pad.utils.SharedPreferencesUtils;

import rx.Observable;

/**
 * Created by Administrator on 17-1-11.
 */

public class InvoicingDetailModel implements InvoicingDetailContract.Model {
    private String recorderBase = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.RECORDERBASE);
    private String sysKeyBase = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.SYSKEYBASE);
    @Override
    public Observable<BaseResponse<Invoice>> GetInvoicingDetail(String invId) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).GetInvoicingDetail(invId);
    }

    @Override
    public Observable<BaseResponse<String>> CompleteSave(String invId, String userId, String userName) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).CompleteSave(invId,userId,userName);
    }
}
