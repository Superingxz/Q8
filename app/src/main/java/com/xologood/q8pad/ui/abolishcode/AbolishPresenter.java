package com.xologood.q8pad.ui.abolishcode;

import com.xologood.mvpframework.util.helper.RxSchedulers;
import com.xologood.mvpframework.util.helper.RxSubscriber;
import com.xologood.q8pad.bean.BaseResponse;

/**
 * Created by Administrator on 17-1-19.
 */

public class AbolishPresenter extends AbolishCodeContract.Presenter {
    @Override
    public void InvoicingAbolishCode(String code) {
        mRxManager.add(mModel.InvoicingAbolishCode(code)
                               .compose(RxSchedulers.<BaseResponse<String>>io_main())
                               .subscribe(new RxSubscriber<BaseResponse<String>>(mContext,false) {
                                   @Override
                                   public void onStart() {
                                       super.onStart();
                                       mView.startProgressDialog("正在处理...");
                                   }

                                   @Override
                                   protected void _onNext(BaseResponse<String> stringBaseResponse) {
                                       mView.AbolishCodeResult(stringBaseResponse.getData());
                                       mView.stopProgressDialog();
                                   }

                                   @Override
                                   protected void _onError(String message) {
                                       mView.stopProgressDialog();
                                   }
                               })
        );
    }
}
