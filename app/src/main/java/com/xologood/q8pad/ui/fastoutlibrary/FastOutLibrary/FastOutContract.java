package com.xologood.q8pad.ui.fastoutlibrary.FastOutLibrary;

import com.xologood.mvpframework.base.BaseModel;
import com.xologood.mvpframework.base.BasePresenter;
import com.xologood.mvpframework.base.BaseView;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.Invoice;
import com.xologood.q8pad.bean.InvoicingBean;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2017/1/17.
 */

public interface FastOutContract {
    interface Model extends BaseModel {
        //快捷出库
        /**
         * 1.快捷出库列表
         * @param Syskey
         * @param Comkey
         * @return
         */
        Observable<BaseResponse<List<InvoicingBean>>> InvoicingQuickInvList(String Syskey, String Comkey);


        /**
         * 快捷出库之获取出库单据明细
         *
         * @param invId
         * @return
         */
        Observable<BaseResponse<Invoice>> GetInvoicingDetail(String invId);

    }

    interface View extends BaseView {
        void SetFastOutInvoicingBean(List<InvoicingBean> invoicingBeanList);
        void  SetInvoicingDetail(Invoice invoice);

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
        //快捷出库
        /**
         * 1.快捷出库列表
         * @param Syskey
         * @param Comkey
         * @return
         */
        public abstract void InvoicingQuickInvList(String Syskey, String Comkey);
        /**
         * 快捷出库之获取出库单据明细
         *
         * @param invId
         * @return
         */
       public abstract void GetInvoicingDetail(String invId);
        @Override
        public void onStart() {
        }
    }
}
