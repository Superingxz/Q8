package com.xologood.q8pad.ui.replace;

import com.xologood.mvpframework.util.helper.RxSchedulers;
import com.xologood.q8pad.bean.BaseResponse;

import rx.functions.Action1;

/**
 * Created by Administrator on 17-1-12.
 */

public class ReplacePresenter extends ReplaceContract.Presenter {
    @Override
    public void InvoicingReplaceCode(String code) {
        mRxManager.add(mModel.InvoicingReplaceCode(code)
                               .compose(RxSchedulers.<BaseResponse<String>>io_main())
                               .subscribe(new Action1<BaseResponse<String>>() {
                                   @Override
                                   public void call(BaseResponse<String> stringBaseResponse) {
                                        mView.InvoicingReplaceCode(stringBaseResponse.getData());
                                   }
                               }, new Action1<Throwable>() {
                                   @Override
                                   public void call(Throwable throwable) {

                                   }
                               }));
    }
}
