package com.xologood.q8pad.ui.invoicingdetail;

import com.xologood.mvpframework.base.BaseModel;
import com.xologood.mvpframework.base.BasePresenter;
import com.xologood.mvpframework.base.BaseView;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.Invoice;

import rx.Observable;

/**
 * Created by Administrator on 17-1-11.
 */

public interface InvoicingDetailContract {
    interface Model extends BaseModel {
        Observable<BaseResponse<Invoice>> GetInvoicingDetail(String invId);
        Observable<BaseResponse<String>> CompleteSave(String invId, String userId, String userName);
    }

    interface View extends BaseView {
        void SetInvoicingDetail(Invoice invoice);

        void CompliteSavaSuccess(String msg);

        /**
         * 开启加载进度条
         */
        public void startProgressDialog(String msg) ;
        /**
         * 停止加载进度条
         */
        public void stopProgressDialog() ;
    }

    abstract  class Presenter extends BasePresenter<Model,View> {
        public abstract void GetInvoicingDetail(String invId);
        public abstract void CompleteSave(String invId,String userId,String userName);
        @Override
        public void onStart() {}
    }
}
