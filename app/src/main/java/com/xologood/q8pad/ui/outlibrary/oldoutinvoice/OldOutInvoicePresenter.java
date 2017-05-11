package com.xologood.q8pad.ui.outlibrary.oldoutinvoice;

import android.util.Log;

import com.xologood.mvpframework.util.helper.RxSchedulers;
import com.xologood.mvpframework.util.helper.RxSubscriber;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.FirstUser;
import com.xologood.q8pad.bean.Invoice;
import com.xologood.q8pad.bean.InvoicingBean;
import com.xologood.q8pad.bean.InvoicingDetail;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by Administrator on 2017/1/8.
 */

public class OldOutInvoicePresenter extends OldOutInvoiceContract.Presenter {

    @Override
    public void GetInvoiceInvlist(String ComKey, String InvState, String CheckUserId, String InvType) {
        mRxManager.add(mModel.GetInvoiceInvlist(ComKey,InvState,CheckUserId,InvType)
                .subscribe(new RxSubscriber<BaseResponse<List<InvoicingBean>>>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.startProgressDialog("正在加载...");
                    }

                    @Override
                    protected void _onNext(BaseResponse<List<InvoicingBean>> listBaseResponse) {
                        mView.SetInvoiceInvlist(listBaseResponse.getData());
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
    public void GetInvoicingDetail(final String invId) {
        mRxManager.add(mModel.GetInvoicingDetail(invId)
                .subscribe(new Action1<BaseResponse<Invoice>>() {
                    @Override
                    public void call(BaseResponse<Invoice> invoiceBaseResponse) {
                        String mInvId = invId;
                        Invoice data = invoiceBaseResponse.getData();
                        List<InvoicingDetail> invoicingDetail = data.getInvoicingDetail();
                        Log.i("superingxz", "call: 单据id:" + mInvId);
                        Log.i("superingxz", "call: 单据明细条目:" + invoicingDetail.size());
                        mView.SetInvoicingDetail(data);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
         //               mView.GetInvoiceMsg(throwable.getMessage());
                    }
                }));
    }

    @Override
    public void GetFirstUserByComKey(String Comkey,String sysKeyBase) {
        mRxManager.add(mModel.GetFirstUserByComKey(Comkey, sysKeyBase)
                               .compose(RxSchedulers.<BaseResponse<FirstUser>>io_main())
                               .subscribe(new RxSubscriber<BaseResponse<FirstUser>>(mContext,false) {
                                   @Override
                                   public void onStart() {
                                       super.onStart();
                                       mView.startProgressDialog("正在加载...");
                                   }

                                   @Override
                                   protected void _onNext(BaseResponse<FirstUser> firstUserBaseResponse) {
                                       mView.SetFirstUserByComKey(firstUserBaseResponse.getData());
                                       mView.stopProgressDialog();
                                   }

                                   @Override
                                   protected void _onError(String message) {
                                       mView.stopProgressDialog();
                                   }
                               }));
    }
}
