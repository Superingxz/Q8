package com.xologood.q8pad.ui.replace;

import com.xologood.mvpframework.base.BaseModel;
import com.xologood.mvpframework.base.BasePresenter;
import com.xologood.mvpframework.base.BaseView;
import com.xologood.q8pad.bean.BaseResponse;

import rx.Observable;

/**
 * Created by Administrator on 17-1-12.
 */

public interface ReplaceContract {
    interface Model extends BaseModel {
        Observable<BaseResponse<String>> InvoicingReplaceCode(String code, String CreationBy);

    }

    interface View extends BaseView {
       void InvoicingReplaceCode(String msg);

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
        public abstract void InvoicingReplaceCode(String code,String CreationBy);

        @Override
        public void onStart() {}
    }
}
