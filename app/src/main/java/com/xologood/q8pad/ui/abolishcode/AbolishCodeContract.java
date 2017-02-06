package com.xologood.q8pad.ui.abolishcode;

import com.xologood.mvpframework.base.BaseModel;
import com.xologood.mvpframework.base.BasePresenter;
import com.xologood.mvpframework.base.BaseView;
import com.xologood.q8pad.bean.BaseResponse;

import rx.Observable;

/**
 * Created by Administrator on 17-1-19.
 */

public interface AbolishCodeContract {
    interface Model extends BaseModel {
        Observable<BaseResponse<String>> InvoicingAbolishCode(String code);

    }

    interface View extends BaseView {
        void AbolishCodeResult(String msg);

    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void InvoicingAbolishCode(String code);

        @Override
        public void onStart() {
        }
    }
}
