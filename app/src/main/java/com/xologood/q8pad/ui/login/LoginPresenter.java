package com.xologood.q8pad.ui.login;


import com.xologood.mvpframework.util.ToastUitl;
import com.xologood.mvpframework.util.helper.RxSubscriber;
import com.xologood.q8pad.bean.Account;
import com.xologood.q8pad.bean.BaseResponse;

/**
 * Created by Administrator on 16-12-28.
 */
public  class LoginPresenter extends LoginContract.Presenter {
    /**
     * 登陆
     * @param loginName  用户名
     * @param passWord   密码
     */
    @Override
    public void login(String loginName, String passWord) {
        mRxManager.add(mModel.Login(loginName,passWord).subscribe(new RxSubscriber<BaseResponse<Account>>(mContext) {
            @Override
            protected void _onNext(BaseResponse<Account> response) {
                mView.initData(response.getData());
            }

            @Override
            protected void _onError(String message) {
                ToastUitl.showLong(message);
            }
        }));
    }

    @Override
    public void onStart() {

    }
}
