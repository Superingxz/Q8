package com.xologood.q8pad.ui.scan;

import com.xologood.q8pad.bean.BarCodeLog;
import com.xologood.q8pad.bean.BarCodeLogList;
import com.xologood.q8pad.bean.BaseResponse;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by xiao on 2017/1/9 0009.
 */

public class ScanPresenter extends ScanContract.Presenter{
    @Override
    public void GetBarCodeLogList(String BarCodes, String InvId, String InvDetailId, String ProductId, String Batch, String ComKey, String ComName, String SysKey, String ReceivingWarehouseId) {
        mRxManager.add(mModel.GetBarCodeLogList(BarCodes,
                                                 InvId,
                                                 InvDetailId,
                                                 ProductId,
                                                 Batch,
                                                 ComKey,
                                                 ComName,
                                                 SysKey,
                                                 ReceivingWarehouseId)
                               .subscribe(new Action1<BaseResponse<BarCodeLogList>>() {
                                   @Override
                                   public void call(BaseResponse<BarCodeLogList> listBaseResponse) {
                                       List<BarCodeLog> data = listBaseResponse.getData().getBarCodeLogList();
                                       mView.SetBarCodeList(data);
                                   }
                               }, new Action1<Throwable>() {
                                   @Override
                                   public void call(Throwable throwable) {

                                   }
                               }));
    }
}
