package com.xologood.q8pad.ui.returngoods;

import com.xologood.mvpframework.util.helper.RxSchedulers;
import com.xologood.mvpframework.util.helper.RxSubscriber;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.ReturnGoodsResponse;

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
                            .subscribe(new RxSubscriber<BaseResponse<ReturnGoodsResponse>>(mContext,false) {
                                @Override
                                public void onStart() {
                                    super.onStart();
                                    mView.startProgressDialog("正在处理退货...");
                                }

                                @Override
                                protected void _onNext(BaseResponse<ReturnGoodsResponse> returnGoodsResponseBaseResponse) {
                                    mView.SetReturnGoodsResponse(returnGoodsResponseBaseResponse.getData());
                                    mView.stopProgressDialog();
                                }

                                @Override
                                protected void _onError(String message) {
                                    mView.stopProgressDialog();
                                }
                            }));
    }

    @Override
    public void ReturnGoods(String InvBy, String InvByName, String InvNumber, String InvDate, String InvReMark, String InvGet, String InvType, String CheckedParty, String InvState, String CodeType, String ReceivingComKey, String ReceivingComName, String SysKey, String ComKey, String LastUpdateDate, String LastUpdateBy, String LastUpdateByName, String CheckMemo, String BarCode) {
        mRxManager.add(mModel.ReturnGoods(        InvBy,
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
                            .subscribe(new RxSubscriber<BaseResponse<ReturnGoodsResponse>>(mContext,false) {
                                @Override
                                public void onStart() {
                                    super.onStart();
                                    mView.startProgressDialog("正在处理退货...");
                                }

                                @Override
                                protected void _onNext(BaseResponse<ReturnGoodsResponse> returnGoodsResponseBaseResponse) {
                                    mView.SetReturnGoodsResponse(returnGoodsResponseBaseResponse.getData());
                                    mView.stopProgressDialog();
                                }

                                @Override
                                protected void _onError(String message) {
                                    mView.stopProgressDialog();
                                }
                            })
        );
    }
}
