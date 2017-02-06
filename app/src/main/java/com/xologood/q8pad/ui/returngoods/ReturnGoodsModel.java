package com.xologood.q8pad.ui.returngoods;

import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.api.Api;
import com.xologood.q8pad.api.HostType;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.ReturnGoodsResponse;
import com.xologood.q8pad.utils.SharedPreferencesUtils;

import rx.Observable;

/**
 * Created by Administrator on 17-1-19.
 */

public class ReturnGoodsModel implements ReturnGoodsContract.Model {
    private String recorderBase = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.RECORDERBASE);
    private String sysKeyBase = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.SYSKEYBASE);

    @Override
    public Observable<BaseResponse<ReturnGoodsResponse>> ReturnGoodsDefault(String InvBy, String InvByName, String InvNumber, String InvDate, String InvReMark, String InvGet, String InvType, String CheckedParty, String InvState, String CodeType, String ReceivingComKey, String ReceivingComName, String SysKey, String ComKey, String LastUpdateDate, String LastUpdateBy, String LastUpdateByName, String CheckMemo, String BarCode) {
        return Api.getLoginInInstance(HostType.SYSTEMURL, recorderBase, sysKeyBase).ReturnGoodsDefault(InvBy,
                                                                                                            InvByName,
                                                                                                            InvNumber,
                                                                                                            InvDate,
                                                                                                            InvReMark,
                                                                                                            InvGet,
                                                                                                            InvType,
                                                                                                            CheckedParty,
                                                                                                            InvState,
                                                                                                            CodeType,
                                                                                                            ReceivingComKey,
                                                                                                            ReceivingComName,
                                                                                                            SysKey,
                                                                                                            ComKey,
                                                                                                            LastUpdateDate,
                                                                                                            LastUpdateBy,
                                                                                                            LastUpdateByName,
                                                                                                            CheckMemo,
                                                                                                            BarCode);
    }


    @Override
    public Observable<BaseResponse<ReturnGoodsResponse>> ReturnGoods(String InvBy, String InvByName, String InvNumber, String InvDate, String InvReMark, String InvGet, String InvType, String CheckedParty, String InvState, String CodeType, String ReceivingComKey, String ReceivingComName, String SysKey, String ComKey, String LastUpdateDate, String LastUpdateBy, String LastUpdateByName, String CheckMemo, String BarCode) {
        return Api.getLoginInInstance(HostType.SYSTEMURL, recorderBase, sysKeyBase).ReturnGoods(InvBy,
                                                                                                    InvByName,
                                                                                                    InvNumber,
                                                                                                    InvDate,
                                                                                                    InvReMark,
                                                                                                    InvGet,
                                                                                                    InvType,
                                                                                                    CheckedParty,
                                                                                                    InvState,
                                                                                                    CodeType,
                                                                                                    ReceivingComKey,
                                                                                                    ReceivingComName,
                                                                                                    SysKey,
                                                                                                    ComKey,
                                                                                                    LastUpdateDate,
                                                                                                    LastUpdateBy,
                                                                                                    LastUpdateByName,
                                                                                                    CheckMemo,
                                                                                                    BarCode);
    }
}
