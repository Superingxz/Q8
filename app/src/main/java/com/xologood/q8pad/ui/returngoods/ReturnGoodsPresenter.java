package com.xologood.q8pad.ui.returngoods;

import com.xologood.mvpframework.util.helper.RxSchedulers;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.ReturnGoodsResponse;

import rx.functions.Action1;

/**
 * Created by Administrator on 17-1-19.
 */

public class ReturnGoodsPresenter extends ReturnGoodsContract.Presenter {
    @Override
    public void ReturnGoodsDefault(String InvBy, String InvByName, String InvNumber, String InvDate, String InvReMark, String InvGet, String InvType, String CheckedParty, String InvState, String CodeType, String ReceivingComKey, String ReceivingComName, String SysKey, String ComKey, String LastUpdateDate, String LastUpdateBy, String LastUpdateByName, String CheckMemo, String BarCode) {
        mRxManager.add(mModel.ReturnGoodsDefault(InvBy,
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
                                                    BarCode)
                            .compose(RxSchedulers.<BaseResponse<ReturnGoodsResponse>>io_main())
                            .subscribe(new Action1<BaseResponse<ReturnGoodsResponse>>() {
                                @Override
                                public void call(BaseResponse<ReturnGoodsResponse> returnGoodsResponseBaseResponse) {
                                    mView.SetReturnGoodsResponse(returnGoodsResponseBaseResponse.getData());
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {

                                }
                            }));
    }

    @Override
    public void ReturnGoods(String InvBy, String InvByName, String InvNumber, String InvDate, String InvReMark, String InvGet, String InvType, String CheckedParty, String InvState, String CodeType, String ReceivingComKey, String ReceivingComName, String SysKey, String ComKey, String LastUpdateDate, String LastUpdateBy, String LastUpdateByName, String CheckMemo, String BarCode) {
        mRxManager.add(mModel.ReturnGoodsDefault(InvBy,
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
                                                    BarCode)
                            .compose(RxSchedulers.<BaseResponse<ReturnGoodsResponse>>io_main())
                            .subscribe(new Action1<BaseResponse<ReturnGoodsResponse>>() {
                                @Override
                                public void call(BaseResponse<ReturnGoodsResponse> returnGoodsResponseBaseResponse) {
                                    mView.SetReturnGoodsResponse(returnGoodsResponseBaseResponse.getData());
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {

                                }
                            })
        );
    }
}
