package com.xologood.q8pad.ui.replace;

import com.xologood.mvpframework.util.helper.RxSchedulers;
import com.xologood.mvpframework.util.helper.RxSubscriber;
import com.xologood.q8pad.bean.BaseResponse;

/**
 * Created by Administrator on 17-1-12.
 */

public class ReplacePresenter extends ReplaceContract.Presenter {
    @Override
    public void InvoicingReplaceCode(String code,String CreationBy) {
        mRxManager.add(mModel.InvoicingReplaceCode(code,CreationBy)
                               .compose(RxSchedulers.<BaseResponse<String>>io_main())
                               .subscribe(new RxSubscriber<BaseResponse<String>>(mContext,false) {
                                   @Override
                                   public void onStart() {
                                       super.onStart();
                                       mView.startProgressDialog("正在处理...");
                                   }

                                   @Override
                                   protected void _onNext(BaseResponse<String> stringBaseResponse) {
                                       mView.InvoicingReplaceCode(stringBaseResponse.getData());
                                       mView.stopProgressDialog();
                                   }

                                   @Override
                                   protected void _onError(String message) {
                                  //     ToastUitl.showLong(message);
                                       mView.stopProgressDialog();
                                   }
                               }));
    }

//    new Action1<BaseResponse<String>>() {
//        @Override
//        public void call(BaseResponse<String> stringBaseResponse) {
//            mView.InvoicingReplaceCode(stringBaseResponse.getData());
//        }
//    }, new Action1<Throwable>() {
//        @Override
//        public void call(Throwable throwable) {
//
//        }
//    }
}
