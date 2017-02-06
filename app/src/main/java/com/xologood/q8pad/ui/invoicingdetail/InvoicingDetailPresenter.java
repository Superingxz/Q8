package com.xologood.q8pad.ui.invoicingdetail;

import com.xologood.mvpframework.util.helper.RxSchedulers;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.Invoice;

import rx.functions.Action1;

/**
 * Created by Administrator on 17-1-11.
 */

public class InvoicingDetailPresenter extends InvoicingDetailContract.Presenter{

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
                                }));
    }

    @Override
    public void CompleteSave(String invId, String userId, String userName) {
        mRxManager.add(mModel.CompleteSave(invId,userId,userName)
                                .compose(RxSchedulers.<BaseResponse<String>>io_main())
                                .subscribe(new Action1<BaseResponse<String>>() {
                                    @Override
                                    public void call(BaseResponse<String> stringBaseResponse) {
                                        mView.CompliteSavaSuccess(stringBaseResponse.getData());
                                    }
                                }, new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {

                                    }
                                }));
    }
}
