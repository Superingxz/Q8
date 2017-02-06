package com.xologood.q8pad.ui.abolishcode;

import com.xologood.mvpframework.util.helper.RxSchedulers;
import com.xologood.q8pad.bean.BaseResponse;

import rx.functions.Action1;

/**
 * Created by Administrator on 17-1-19.
 */

public class AbolishPresenter extends AbolishCodeContract.Presenter {
    @Override
    public void InvoicingAbolishCode(String code) {
        mRxManager.add(mModel.InvoicingAbolishCode(code)
                               .compose(RxSchedulers.<BaseResponse<String>>io_main())
                               .subscribe(new Action1<BaseResponse<String>>() {
                                   @Override
                                   public void call(BaseResponse<String> stringBaseResponse) {
                                        mView.AbolishCodeResult(stringBaseResponse.getData());
                                   }
                               }, new Action1<Throwable>() {
                                   @Override
                                   public void call(Throwable throwable) {

                                   }
                               })
        );
    }
}
