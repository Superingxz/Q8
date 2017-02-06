package com.xologood.q8pad.ui.inlibrary.oldininvoice;

import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.Invoice;
import com.xologood.q8pad.bean.InvoicingBean;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by xiaomo on 2017/1/8.
 */

public class OldInInvoicePresenter extends OldInInvoiceContract.Presenter {
    @Override
    public void GetInvoiceInvlist(String ComKey, String InvState, String CheckUserId, String InvType) {
        mRxManager.add(mModel.GetInvoiceInvlist(ComKey,InvState,CheckUserId,InvType)
                             .subscribe(new Action1<BaseResponse<List<InvoicingBean>>>() {
                                 @Override
                                 public void call(BaseResponse<List<InvoicingBean>> listBaseResponse) {
                                     List<InvoicingBean> data = listBaseResponse.getData();
                                     mView.SetInvoiceInvlist(data);
                                 }
                             }, new Action1<Throwable>() {
                                 @Override
                                 public void call(Throwable throwable) {

                                 }
                             })
        );
    }

    @Override
    public void GetInvoicingDetail(final String invId) {
        mRxManager.add(mModel.GetInvoicingDetail(invId)
                             .subscribe(new Action1<BaseResponse<Invoice>>() {
                                 @Override
                                 public void call(BaseResponse<Invoice> invoiceBaseResponse) {
                                     Invoice data = invoiceBaseResponse.getData();
                                     mView.SetInvoicingDetail(data);
                                 }
                             }, new Action1<Throwable>() {
                                 @Override
                                 public void call(Throwable throwable) {
                                 }
                             }));
    }
}
