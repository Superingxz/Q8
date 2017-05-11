package com.xologood.q8pad.ui.fastoutlibrary.FastOutLibrary;

import com.xologood.mvpframework.util.helper.RxSchedulers;
import com.xologood.mvpframework.util.helper.RxSubscriber;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.Invoice;
import com.xologood.q8pad.bean.InvoicingBean;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by Administrator on 2017/1/17.
 */

public class FastOutPresenter extends FastOutContract.Presenter {

    @Override
    public void InvoicingQuickInvList(String Syskey, String Comkey) {
        mRxManager.add(mModel.InvoicingQuickInvList(Syskey,Comkey)
                             .compose(RxSchedulers.<BaseResponse<List<InvoicingBean>>>io_main())
                             .subscribe(new RxSubscriber<BaseResponse<List<InvoicingBean>>>(mContext,false) {
                                 @Override
                                 public void onStart() {
                                     super.onStart();
                                     mView.startProgressDialog("正在加载...");
                                 }

                                 @Override
                                 protected void _onNext(BaseResponse<List<InvoicingBean>> listBaseResponse) {
                                     mView.SetFastOutInvoicingBean(listBaseResponse.getData());
                                     mView.stopProgressDialog();
                                 }

                                 @Override
                                 protected void _onError(String message) {
                                     mView.stopProgressDialog();
                                 }
                             })
        );
    }

    @Override
    public void GetInvoicingDetail(String invId) {
        mRxManager.add(mModel.GetInvoicingDetail(invId)
                             .compose(RxSchedulers.<BaseResponse<Invoice>>io_main())
                             .subscribe(new Action1<BaseResponse<Invoice>>() {
                                 @Override
                                 public void call(BaseResponse<Invoice> invoiceBaseResponse) {
                                    mView.SetInvoicingDetail(invoiceBaseResponse.getData());
                                 }
                             }, new Action1<Throwable>() {
                                 @Override
                                 public void call(Throwable throwable) {

                                 }
                             })
        );
    }
}
