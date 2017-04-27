package com.xologood.q8pad.ui.scan;

import com.xologood.mvpframework.base.BaseModel;
import com.xologood.mvpframework.base.BasePresenter;
import com.xologood.mvpframework.base.BaseView;
import com.xologood.q8pad.bean.BarCodeLog;
import com.xologood.q8pad.bean.BarCodeLogList;
import com.xologood.q8pad.bean.BaseResponse;

import java.util.List;

import rx.Observable;

/**
 * Created by xiao on 2017/1/9 0009.
 */

public interface ScanContract {
    interface Model extends BaseModel {
        Observable<BaseResponse<BarCodeLogList>> GetBarCodeLogList(String BarCodes,
                                                                   String InvId,
                                                                   String InvDetailId,
                                                                   String ProductId,
                                                                   String Batch,
                                                                   String ComKey,
                                                                   String ComName,
                                                                   String SysKey,
                                                                   String ReceivingWarehouseId);


        Observable<BaseResponse<BarCodeLogList>> GetBarCodeLogListBinShi(String BarCodes,
                                                                         String InvId,
                                                                         String InvDetailId,
                                                                         String ProductId,
                                                                         String Batch,
                                                                         String ComKey,
                                                                         String ComName,
                                                                         String SysKey,
                                                                         String ReceivingWarehouseId);


        Observable<BaseResponse<String>> getCheckBarCode(String barcode);


    }

    interface View extends BaseView {
        void SetBarCodeList(List<BarCodeLog> barCodeLogList);
        void UploadBarCodeError(String msg);

        void SetCheckBarCode(String needToScan);

        /**
         * 开启加载进度条
         */
        public void startProgressDialog(String msg) ;
        /**
         * 停止加载进度条
         */
        public void stopProgressDialog() ;
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void GetBarCodeLogList(String BarCodes,
                                                 String InvId,
                                                 String InvDetailId,
                                                 String ProductId,
                                                 String Batch,
                                                 String ComKey,
                                                 String ComName,
                                                 String SysKey,
                                                 String ReceivingWarehouseId);

        public abstract void GetBarCodeLogListBinShi(String BarCodes,
                                               String InvId,
                                               String InvDetailId,
                                               String ProductId,
                                               String Batch,
                                               String ComKey,
                                               String ComName,
                                               String SysKey,
                                               String ReceivingWarehouseId);

        public abstract void getCheckBarCode(String barcode);

        @Override
        public void onStart() {
        }
    }
}
