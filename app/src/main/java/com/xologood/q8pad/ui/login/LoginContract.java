package com.xologood.q8pad.ui.login;


import com.xologood.mvpframework.base.BaseModel;
import com.xologood.mvpframework.base.BasePresenter;
import com.xologood.mvpframework.base.BaseView;
import com.xologood.q8pad.bean.Account;
import com.xologood.q8pad.bean.BaseResponse;

import rx.Observable;

/**
 * Created by Administrator on 16-12-28.
 */
public interface LoginContract {
    interface Model extends BaseModel{
        Observable<BaseResponse<Account>> Login(String loginName, String passWord);
    }

    interface View extends BaseView {
        void initData(Account account);
    }

    abstract  class Presenter extends BasePresenter<Model,View>{
        public abstract void login(String loginName, String passWord);
        @Override
        public void onStart() {}
    }
}
