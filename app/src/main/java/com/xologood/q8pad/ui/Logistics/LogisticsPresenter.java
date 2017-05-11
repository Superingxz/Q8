package com.xologood.q8pad.ui.Logistics;

import com.xologood.mvpframework.util.helper.RxSchedulers;
import com.xologood.mvpframework.util.helper.RxSubscriber;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.ReportInfo;
import com.xologood.q8pad.bean.ReportInv;

/**
 * Created by wei on 2017/3/13.
 */

public class LogisticsPresenter extends LogisticsContract.Presenter {

    @Override
    public void getProductDetailByBarcode(String barCode) {
        mRxManager.add(mModel.getProductDetailByBarcode(barCode)
                .compose(RxSchedulers.<BaseResponse<ReportInfo>>io_main())
                .subscribe(new RxSubscriber<BaseResponse<ReportInfo>>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.startProgressDialog("正在加载中...");
                    }

                    @Override
                    protected void _onNext(BaseResponse<ReportInfo> reportInfoBaseResponse) {
                        mView.setProductDetailByBarcode(reportInfoBaseResponse.getData());

                        mView.stopProgressDialog();
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopProgressDialog();
                    }
                })
        );
    }


    @Override
    public void getInvByBarCodeList(String sBarCode) {
        mRxManager.add(mModel.invByBarCodeList(sBarCode)
                    .compose(RxSchedulers.<BaseResponse<ReportInv>>io_main())
                    .subscribe(new RxSubscriber<BaseResponse<ReportInv>>(mContext,false) {
                        @Override
                        protected void _onNext(BaseResponse<ReportInv> reportInvBaseResponse) {
                            mView.setInvByBarCodeList(reportInvBaseResponse.getData());

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
