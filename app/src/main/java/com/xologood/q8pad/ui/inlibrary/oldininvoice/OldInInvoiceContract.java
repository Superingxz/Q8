package com.xologood.q8pad.ui.inlibrary.oldininvoice;

import com.xologood.mvpframework.base.BaseModel;
import com.xologood.mvpframework.base.BasePresenter;
import com.xologood.mvpframework.base.BaseView;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.Invoice;
import com.xologood.q8pad.bean.InvoicingBean;

import java.util.List;

import rx.Observable;

/**
 * Created by xiaomo on 2017/1/8.
 */

public interface OldInInvoiceContract {
    interface Model extends BaseModel {
        Observable<BaseResponse<List<InvoicingBean>>> GetInvoiceInvlist(String ComKey,
                                                                        String InvState,
                                                                        String CheckUserId,
                                                                        String InvType
        );

        Observable<BaseResponse<Invoice>> GetInvoicingDetail(String invId);

    }

    interface View extends BaseView {

        /**
         * 获取单据列表成功
         * @param invoicingBeanList
         */
        void SetInvoiceInvlist(List<InvoicingBean> invoicingBeanList);

        /**
         * 获取单据信息成功
         * @param invoice
         */
        void SetInvoicingDetail(Invoice invoice);

        /**
         * 获取单据明细信息成功
         * @param Msg
         */
        void GetInvoiceMsg(String Msg);



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
        public abstract void GetInvoiceInvlist(String ComKey,
                                               String InvState,
                                               String CheckUserId,
                                               String InvType
        );

        public abstract void GetInvoicingDetail(String invId);


        @Override
        public void onStart() {}
    }
}
