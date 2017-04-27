package com.xologood.q8pad.ui.scan;

import com.xologood.mvpframework.util.helper.RxSchedulers;
import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.api.Api;
import com.xologood.q8pad.api.HostType;
import com.xologood.q8pad.bean.BarCodeLogList;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.utils.SharedPreferencesUtils;

import rx.Observable;

/**
 * Created by xiao on 2017/1/9 0009.
 */

public class ScanModel implements ScanContract.Model{
    private String recorderBase = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.RECORDERBASE);
    private String sysKeyBase = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.SYSKEYBASE);

    @Override
    public Observable<BaseResponse<BarCodeLogList>> GetBarCodeLogList(String BarCodes, String InvId, String InvDetailId, String ProductId, String Batch, String ComKey, String ComName, String SysKey, String ReceivingWarehouseId) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).GetScanBarCodeList(BarCodes,
                                                                                                         InvId,
                                                                                                         InvDetailId,
                                                                                                         ProductId,
                                                                                                         Batch,
                                                                                                         ComKey,
                                                                                                         ComName,
                                                                                                         SysKey,
                                                                                                         ReceivingWarehouseId)
                .compose(RxSchedulers.<BaseResponse<BarCodeLogList>>io_main());
    }

    @Override
    public Observable<BaseResponse<BarCodeLogList>> GetBarCodeLogListBinShi(String BarCodes, String InvId, String InvDetailId, String ProductId, String Batch, String ComKey, String ComName, String SysKey, String ReceivingWarehouseId) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).GetScanBarCodeListBinShi(BarCodes,
                InvId,
                InvDetailId,
                ProductId,
                Batch,
                ComKey,
                ComName,
                SysKey,
                ReceivingWarehouseId)
                .compose(RxSchedulers.<BaseResponse<BarCodeLogList>>io_main());
    }

    @Override
    public Observable<BaseResponse<String>> getCheckBarCode(String barcode) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).InvoicingCheckBarCode(barcode)
                .compose(RxSchedulers.<BaseResponse<String>>io_main());
    }


}
