package com.xologood.q8pad.ui.returngoods;

import com.xologood.mvpframework.base.BaseModel;
import com.xologood.mvpframework.base.BasePresenter;
import com.xologood.mvpframework.base.BaseView;
import com.xologood.q8pad.api.ApiConstants;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.ReturnGoodsResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Administrator on 17-1-19.
 */

public interface ReturnGoodsContract {
    interface Model extends BaseModel {

        /**
         * 退货默认
         *
         * @return
         */
        @GET(ApiConstants.RETURNGOODS_DEFAULT)
        Observable<BaseResponse<ReturnGoodsResponse>> ReturnGoodsDefault(String InvBy,
                                                                         String InvByName,
                                                                         String InvNumber,
                                                                         String InvDate,
                                                                         String InvReMark,
                                                                         String InvGet,
                                                                         String InvType,
                                                                         String CheckedParty,
                                                                         String InvState,
                                                                         String CodeType,
                                                                         String ReceivingComKey,
                                                                         String ReceivingComName,
                                                                         String SysKey,
                                                                         String ComKey,
                                                                         String LastUpdateDate,
                                                                         String LastUpdateBy,
                                                                         String LastUpdateByName,
                                                                         String CheckMemo,
                                                                         String BarCode);

        /**
         * 退货
         * @return
         */
        @GET(ApiConstants.RETURNGOODS)
        Observable<BaseResponse<ReturnGoodsResponse>> ReturnGoods(String InvBy,
                                                                  String InvByName,
                                                                  String InvNumber,
                                                                  String InvDate,
                                                                  String InvReMark,
                                                                  String InvGet,
                                                                  String InvType,
                                                                  String CheckedParty,
                                                                  String InvState,
                                                                  String CodeType,
                                                                  String ReceivingComKey,
                                                                  String ReceivingComName,
                                                                  String SysKey,
                                                                  String ComKey,
                                                                  String LastUpdateDate,
                                                                  String LastUpdateBy,
                                                                  String LastUpdateByName,
                                                                  String CheckMemo,
                                                                  String BarCode);

    }

    interface View extends BaseView {
        void SetReturnGoodsResponse(ReturnGoodsResponse returnGoodsResponse);
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
        /**
         * 退货默认
         *
         * @return
         */
        public abstract void ReturnGoodsDefault(String InvBy,
                                                  String InvByName,
                                                  String InvNumber,
                                                  String InvDate,
                                                  String InvReMark,
                                                  String InvGet,
                                                  String InvType,
                                                  String CheckedParty,
                                                  String InvState,
                                                  String CodeType,
                                                  String ReceivingComKey,
                                                  String ReceivingComName,
                                                  String SysKey,
                                                  String ComKey,
                                                  String LastUpdateDate,
                                                  String LastUpdateBy,
                                                  String LastUpdateByName,
                                                  String CheckMemo,
                                                  String BarCode
                                                                         );

        /**
         * 退货
         * @return
         */
        public abstract void ReturnGoods(String InvBy,
                                           String InvByName,
                                           String InvNumber,
                                           String InvDate,
                                           String InvReMark,
                                           String InvGet,
                                           String InvType,
                                           String CheckedParty,
                                           String InvState,
                                           String CodeType,
                                           String ReceivingComKey,
                                           String ReceivingComName,
                                           String SysKey,
                                           String ComKey,
                                           String LastUpdateDate,
                                           String LastUpdateBy,
                                           String LastUpdateByName,
                                           String CheckMemo,
                                           String BarCode);

        @Override
        public void onStart() {}
    }
}
