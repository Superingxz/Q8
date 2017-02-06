package com.xologood.q8pad.ui.fastoutlibrary.FastOutLibrary;

import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.api.Api;
import com.xologood.q8pad.api.HostType;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.Invoice;
import com.xologood.q8pad.bean.InvoicingBean;
import com.xologood.q8pad.utils.SharedPreferencesUtils;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2017/1/17.
 */

public class FastOutModel implements FastOutContract.Model {
    private String recorderBase = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.RECORDERBASE);
    private String sysKeyBase = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.SYSKEYBASE);

    @Override
    public Observable<BaseResponse<List<InvoicingBean>>> InvoicingQuickInvList(String Syskey, String Comkey) {
        return Api.getLoginInInstance(HostType.SYSTEMURL, recorderBase, sysKeyBase).InvoicingQuickInvList(Syskey, Comkey);
    }

    @Override
    public Observable<BaseResponse<Invoice>> GetInvoicingDetail(String invId) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).GetInvoicingDetail(invId);
    }
}
