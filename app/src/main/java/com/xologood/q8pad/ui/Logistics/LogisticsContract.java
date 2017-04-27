package com.xologood.q8pad.ui.Logistics;


import com.xologood.mvpframework.base.BaseModel;
import com.xologood.mvpframework.base.BasePresenter;
import com.xologood.mvpframework.base.BaseView;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.ReportInfo;
import com.xologood.q8pad.bean.ReportInv;

import rx.Observable;

/**
 * Created by Administrator on 16-12-28.
 */
public interface LogisticsContract {
    interface Model extends BaseModel{
        Observable<BaseResponse<ReportInfo>> getProductDetailByBarcode(String barCode);
        Observable<BaseResponse<ReportInv>> invByBarCodeList(String sBarCode);
    }

    interface View extends BaseView {
        void setProductDetailByBarcode(ReportInfo reportInfo);
        void setInvByBarCodeList(ReportInv reportInv);

        /**
         * 开启加载进度条
         */
        public void startProgressDialog(String msg) ;
        /**
         * 停止加载进度条
         */
        public void stopProgressDialog() ;
    }

    abstract  class Presenter extends BasePresenter<Model,View>{
        public abstract void getProductDetailByBarcode(String barCode);
        public abstract void getInvByBarCodeList(String sBarCode);

        @Override
        public void onStart() {}
    }
}
