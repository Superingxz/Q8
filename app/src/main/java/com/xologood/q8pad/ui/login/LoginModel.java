package com.xologood.q8pad.ui.login;


import com.xologood.mvpframework.util.helper.RxSchedulers;
import com.xologood.q8pad.api.Api;
import com.xologood.q8pad.api.HostType;
import com.xologood.q8pad.bean.Account;
import com.xologood.q8pad.bean.BaseResponse;

import rx.Observable;

/**
 * Created by Administrator on 16-12-28.
 */
public class LoginModel implements LoginContract.Model{
    @Override
    public Observable<BaseResponse<Account>> Login(String loginName, String passWord) {
        return Api.getDefault(HostType.USERURL).login(loginName,passWord).compose(RxSchedulers.<BaseResponse<Account>>io_main());
    }




}
