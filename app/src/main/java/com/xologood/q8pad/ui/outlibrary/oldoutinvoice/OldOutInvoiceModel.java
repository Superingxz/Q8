package com.xologood.q8pad.ui.outlibrary.oldoutinvoice;

import com.xologood.mvpframework.util.helper.RxSchedulers;
import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.api.Api;
import com.xologood.q8pad.api.HostType;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.FirstUser;
import com.xologood.q8pad.bean.Invoice;
import com.xologood.q8pad.bean.InvoicingBean;
import com.xologood.q8pad.utils.SharedPreferencesUtils;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2017/1/8.
 */

public class OldOutInvoiceModel implements OldOutInvoiceContract.Model{

    private String recorderBase = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.RECORDERBASE);
    private String sysKeyBase = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.SYSKEYBASE);

    @Override
    public Observable<BaseResponse<List<InvoicingBean>>> GetInvoiceInvlist(String ComKey,
                                                                           String InvState,
                                                                           String CheckUserId,
                                                                           String InvType) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).GetInvoiceInvlist(ComKey,
                InvState,
                CheckUserId,
                InvType
        ).compose(RxSchedulers.<BaseResponse<List<InvoicingBean>>>io_main());
    }

    @Override
    public Observable<BaseResponse<Invoice>> GetInvoicingDetail(String invId) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).GetInvoicingDetail(invId)
                .compose(RxSchedulers.<BaseResponse<Invoice>>io_main());
    }

    @Override
    public Observable<BaseResponse<FirstUser>> GetFirstUserByComKey(String Comkey,String sysKeyBase) {
        return Api.getDefault(HostType.USERURL).GetFirstUserByComKey(Comkey, sysKeyBase);
    }
}
