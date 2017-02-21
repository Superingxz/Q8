package com.xologood.q8pad.ui.outlibrary.oldoutinvoice;

import com.xologood.mvpframework.base.BaseModel;
import com.xologood.mvpframework.base.BasePresenter;
import com.xologood.mvpframework.base.BaseView;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.FirstUser;
import com.xologood.q8pad.bean.Invoice;
import com.xologood.q8pad.bean.InvoicingBean;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2017/1/8.
 */

public interface OldOutInvoiceContract {


    interface Model extends BaseModel {
        Observable<BaseResponse<List<InvoicingBean>>> GetInvoiceInvlist(String ComKey,
                                                                        String InvState,
                                                                        String CheckUserId,
                                                                        String InvType
        );

        Observable<BaseResponse<Invoice>> GetInvoicingDetail(String invId);

        Observable<BaseResponse<FirstUser>> GetFirstUserByComKey(String Comkey, String sysKeyBase);
    }

    interface View extends BaseView {
        void SetInvoiceInvlist(List<InvoicingBean> invoicingBeanList);
        void SetInvoicingDetail(Invoice invoice);
        void GetInvoiceMsg(String Msg);
        void SetFirstUserByComKey(FirstUser firstUser);
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

        public abstract void GetFirstUserByComKey( String Comkey,String sysKeyBase);
        @Override
        public void onStart() {}
    }
}
