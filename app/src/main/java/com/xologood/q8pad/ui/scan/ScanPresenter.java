package com.xologood.q8pad.ui.scan;

import com.xologood.mvpframework.util.helper.RxSubscriber;
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
                               .subscribe(new RxSubscriber<BaseResponse<BarCodeLogList>>(mContext,false) {
                                   @Override
                                   public void onStart() {
                                       super.onStart();
                                       mView.startProgressDialog("正在上传...");
                                   }

                                   @Override
                                   protected void _onNext(BaseResponse<BarCodeLogList> barCodeLogListBaseResponse) {
                                       List<BarCodeLog> data = barCodeLogListBaseResponse.getData().getBarCodeLogList();
                                       mView.SetBarCodeList(data);
                                       mView.stopProgressDialog();
                                   }

                                   @Override
                                   protected void _onError(String message) {
                                       mView.stopProgressDialog();
                                   }
                               }));
    }

    @Override
    public void GetBarCodeLogListBinShi(String BarCodes, String InvId, String InvDetailId, String ProductId, String Batch, String ComKey, String ComName, String SysKey, String ReceivingWarehouseId) {
        mRxManager.add(mModel.GetBarCodeLogListBinShi(BarCodes,
                InvId,
                InvDetailId,
                ProductId,
                Batch,
                ComKey,
                ComName,
                SysKey,
                ReceivingWarehouseId)
                .subscribe(new RxSubscriber<BaseResponse<BarCodeLogList>>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.startProgressDialog("正在上传...");
                    }

                    @Override
                    protected void _onNext(BaseResponse<BarCodeLogList> barCodeLogListBaseResponse) {
                        List<BarCodeLog> data = barCodeLogListBaseResponse.getData().getBarCodeLogList();
                        mView.SetBarCodeList(data);
                        mView.stopProgressDialog();
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopProgressDialog();
                    }
                }));
    }

    @Override
    public void getCheckBarCode(String barcode) {
        mRxManager.add(mModel.getCheckBarCode(barcode)
                .subscribe(new Action1<BaseResponse<String>>() {
                    @Override
                    public void call(BaseResponse<String> stringBaseResponse) {
                        String data = stringBaseResponse.getData();
                        mView.SetCheckBarCode(data);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                })
        );
    }
}
