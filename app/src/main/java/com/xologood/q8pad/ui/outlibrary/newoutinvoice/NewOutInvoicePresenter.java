package com.xologood.q8pad.ui.outlibrary.newoutinvoice;

import android.util.Log;

import com.xologood.mvpframework.util.helper.RxSchedulers;
import com.xologood.mvpframework.util.helper.RxSubscriber;
import com.xologood.q8pad.bean.BarCodeLogList;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.Company;
import com.xologood.q8pad.bean.InvoicingDetailVo;
import com.xologood.q8pad.bean.InvoicingBean;
import com.xologood.q8pad.bean.Product;
import com.xologood.q8pad.bean.ProportionConversion;
import com.xologood.q8pad.bean.StandardUnit;
import com.xologood.q8pad.bean.Warehouse;

import java.util.List;
import java.util.Map;

import rx.functions.Action1;

/**
 * Created by Administrator on 17-1-6.
 */

public class NewOutInvoicePresenter extends NewOutInvoiceContract.Presenter {

    private static final String TAG = "Superingxz";

    /**
     * 保存入库主表
     * @param options
     */
    @Override
    public void insertInv(Map<String, String> options) {
        mRxManager.add(mModel.insertInv(options)
                .compose(RxSchedulers.<BaseResponse<InvoicingBean>>io_main())
                .subscribe(new RxSubscriber<BaseResponse<InvoicingBean>>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.startProgressDialog("正在新增订单...");
                    }

                    @Override
                    protected void _onNext(BaseResponse<InvoicingBean> response) {
                        mView.insertInv(response.getData());
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
    public void GetProportionConversion(String id, String Bunit, String count) {
        mRxManager.add(mModel.GetProportionConversio(id,Bunit,count)
                .compose(RxSchedulers.<BaseResponse<ProportionConversion>>io_main())
                .subscribe(new Action1<BaseResponse<ProportionConversion>>() {
                    @Override
                    public void call(BaseResponse<ProportionConversion> proportionConversion) {

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                })
        );
    }

    @Override
    public void GetProportionConversionString(String id, String Bunit, String count) {
        mRxManager.add(mModel.GetProportionConversionString(id,Bunit,count)
                .compose(RxSchedulers.<BaseResponse<String>>io_main())
                .subscribe(new Action1<BaseResponse<String>>() {
                    @Override
                    public void call(BaseResponse<String> stringBaseResponse) {
                        Log.i(TAG, "call: GetProportionConversionString");
     //                   mView.SetProportionConversion(stringBaseResponse.getData());
                        Log.i(TAG, "call: "+stringBaseResponse.getData());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                }));
    }

    @Override
    public void GetWareHouseList(String ComKey, String IsUse) {
        mRxManager.add(mModel.GetWareHouseList(ComKey,IsUse)
                  .compose(RxSchedulers.<BaseResponse<List<Warehouse>>>io_main())
                  .subscribe(new RxSubscriber<BaseResponse<List<Warehouse>>>(mContext,false) {
                      @Override
                      public void onStart() {
                          super.onStart();
                          mView.startProgressDialog("正在加载...");
                      }

                      @Override
                      protected void _onNext(BaseResponse<List<Warehouse>> listBaseResponse) {
                          mView.SetWareHouseList(listBaseResponse.getData());
                          mView.stopProgressDialog();
                      }

                      @Override
                      protected void _onError(String message) {
                          mView.stopProgressDialog();
                      }
                  }));
    }

    @Override
    public void GetProductList(String SysKey, String IsUse) {
        mRxManager.add(mModel.GetProductList(SysKey,IsUse)
                        .compose(RxSchedulers.<BaseResponse<List<Product>>>io_main())
                        .subscribe(new Action1<BaseResponse<List<Product>>>() {
                            @Override
                            public void call(BaseResponse<List<Product>> listBaseResponse) {
                             //   mView.SetProductList(listBaseResponse.getData());
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                            }
                        }));
    }

    @Override
    public void GetStandardUnitByProductId(String ProductId, String SysKey) {
        mRxManager.add(mModel.GetStandardUnitByProductId(ProductId, SysKey)
                  .compose(RxSchedulers.<BaseResponse<List<StandardUnit>>>io_main())
                  .subscribe(new Action1<BaseResponse<List<StandardUnit>>>() {
                      @Override
                      public void call(BaseResponse<List<StandardUnit>> listBaseResponse) {
                     //   mView.SetStandardUnitByProductId(listBaseResponse.getData());
                      }
                  }, new Action1<Throwable>() {
                      @Override
                      public void call(Throwable throwable) {

                      }
                  }));
    }

    @Override
    public void GetAllCompList(final String ComKey, String CType) {
        mRxManager.add(mModel.GetAllCompList(ComKey, CType)
                             .compose(RxSchedulers.<BaseResponse<List<Company>>>io_main())
                             .subscribe(new RxSubscriber<BaseResponse<List<Company>>>(mContext,false) {
                                 @Override
                                 public void onStart() {
                                     super.onStart();
                                     mView.startProgressDialog("正在加载...");
                                 }

                                 @Override
                                 protected void _onNext(BaseResponse<List<Company>> listBaseResponse) {
                                     String mConKey = ComKey;
                                     List<Company> data = listBaseResponse.getData();
                                     mView.SetAllCompList(data);
                                     mView.stopProgressDialog();
                                 }

                                 @Override
                                 protected void _onError(String message) {
                                     mView.stopProgressDialog();
                                 }
                             }));
    }
    /**
     * 验证入库明细
     * @param Id            明细id  写死0
     * @param InvId         单号id
     * @param ProductId     产品id
     * @param Batch         批次id
     * @param ActualQty     实际数量
     * @param ExpectedQty   预计数量
     * @param ComKey        机构唯一标识
     * @param SysKey        系统唯一标识
     * @return
     */
    @Override
    public void GetInvoiceDetail(String Id,
                                 String InvId,
                                 String ProductId,
                                 String Batch,
                                 String ActualQty,
                                 String ExpectedQty,
                                 String ComKey,
                                 String SysKey) {
        mRxManager.add(mModel.GetInvoiceDetail(Id,InvId,ProductId,Batch,ActualQty,ExpectedQty,ComKey,SysKey)
                .compose(RxSchedulers.<BaseResponse<InvoicingDetailVo>>io_main())
                .subscribe(new Action1<BaseResponse<InvoicingDetailVo>>() {
                    @Override
                    public void call(BaseResponse<InvoicingDetailVo> invoiceDetailBaseResponse) {
                        int id = invoiceDetailBaseResponse.getData().getId();
      //                  mView.GetInvoiceDetailSuccess(id);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        // mView.GetInvoiceDetailSuccess(throwable.getCause().getMessage());
                    }
                })
        );
    }

    @Override
    public void InsertInvoiceDetail(String Id,
                                    String InvId,
                                    String ProductId,
                                    String Batch,
                                    String ActualQty,
                                    String ExpectedQty,
                                    String ComKey,
                                    String SysKey) {
        mRxManager.add(mModel.InsertInvoiceDetail(Id,
                InvId,
                ProductId,
                Batch,
                ActualQty,
                ExpectedQty,
                ComKey,
                SysKey)
                .compose(RxSchedulers.<BaseResponse<InvoicingDetailVo>>io_main())
                .subscribe(new Action1<BaseResponse<InvoicingDetailVo>>() {
                    @Override
                    public void call(BaseResponse<InvoicingDetailVo> invoiceingDetailVoBaseResponse) {
            //            mView.InsertInvoiceDetailSuccess(invoiceingDetailVoBaseResponse.getData().getId());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                })
        );
    }

    @Override
    public void UpdateInvoiceDetail(String Id,
                                    String InvId,
                                    String ProductId,
                                    String Batch,
                                    String ActualQty,
                                    String ExpectedQty,
                                    String ComKey,
                                    String SysKey
    ) {
        mRxManager.add(mModel.UpdateInvoiceDetail(Id,
                InvId,
                ProductId,
                Batch,
                ActualQty,
                ExpectedQty,
                ComKey,
                SysKey)
                .compose(RxSchedulers.<BaseResponse<InvoicingDetailVo>>io_main())
                .subscribe(new Action1<BaseResponse<InvoicingDetailVo>>() {
                    @Override
                    public void call(BaseResponse<InvoicingDetailVo> invoiceingDetailVoBaseResponse) {
           //             mView.UpdateInvoiceDetailSuccess(invoiceingDetailVoBaseResponse.getData().getId());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                })
        );
    }

    /**
     *出库上传
     * @param BarCodes                  全部条码 逗号分隔
     * @param InvId                     单号id
     * @param InvNumber                 单号
     * @param InvType                   类型 写死2
     * @param InvGet                    获取类型 写”在线PDA”
     * @param InvReMark                 备注 写”暂无”
     * @param InvBy                     单据创建人id
     * @param InvByName                 单据创建人名称
     * @param CodeType                  条形码进出 写死false
     * @param InvState                  单据状态 写死”2”
     * @param InvDate                   创建时间
     * @param LastUpdateBy              更新人id
     * @param LastUpdateByName          更新人名字
     * @param LastUpdateDate            最后更新时间
     * @param ComKey                    机构唯一标识
     * @param ComName                   机构名称
     * @param SysKey                    系统唯一标识
     * @param CheckMemo                 审核备注 写”暂无”
     * @param ReceivingComKey           机构key
     * @param ReceivingComName          机构名称
     * @param ReceivingWarehouseId      仓库id
     * @param ReceivingWarehouseName    仓库名称
     * @param CheckedParty              签收管理  写死true
     * @param sysKeyBase                系统key
     * @return
     */
    @Override
    public void GetScanBarCodeList(String BarCodes, String InvId, String InvNumber, String InvType, String InvGet, String InvReMark, String InvBy, String InvByName, String CodeType, String InvState, String InvDate, String LastUpdateBy, String LastUpdateByName, String LastUpdateDate, String ComKey, String ComName, String SysKey, String CheckMemo, String ReceivingComKey, String ReceivingComName, String ReceivingWarehouseId, String ReceivingWarehouseName, String CheckedParty, String sysKeyBase) {
        mRxManager.add(mModel.GetScanBarCodeList(BarCodes,
                                                    InvId,
                                                    InvNumber,
                                                    InvType,
                                                    InvGet,
                                                    InvReMark,
                                                    InvBy,
                                                    InvByName,
                                                    CodeType,
                                                    InvState,
                                                    InvDate,
                                                    LastUpdateBy,
                                                    LastUpdateByName,
                                                    LastUpdateDate,
                                                    ComKey,
                                                    ComName,
                                                    SysKey,
                                                    CheckMemo,
                                                    ReceivingComKey,
                                                    ReceivingComName,
                                                    ReceivingWarehouseId,
                                                    ReceivingWarehouseName,
                                                    CheckedParty,
                                                    sysKeyBase)
                        .compose(RxSchedulers.<BaseResponse<BarCodeLogList>>io_main())
                        .subscribe(new RxSubscriber<BaseResponse<BarCodeLogList>>(mContext,false) {
                            @Override
                            public void onStart() {
                                super.onStart();
                                mView.startProgressDialog("正在上传...");
                            }

                            @Override
                            protected void _onNext(BaseResponse<BarCodeLogList> barCodeLogListBaseResponse) {
                                mView.setScanBarCodeList(barCodeLogListBaseResponse.getData().getBarCodeLogList());
                                mView.stopProgressDialog();
                            }

                            @Override
                            protected void _onError(String message) {
                                mView.stopProgressDialog();
                            }
                        }));

    }

    //宾氏-出库上传
    @Override
    public void GetScanBarCodeListBinShi(String BarCodes, String InvId, String InvNumber, String InvType, String InvGet, String InvReMark, String InvBy, String InvByName, String CodeType, String InvState, String InvDate, String LastUpdateBy, String LastUpdateByName, String LastUpdateDate, String ComKey, String ComName, String SysKey, String CheckMemo, String ReceivingComKey, String ReceivingComName, String ReceivingWarehouseId, String ReceivingWarehouseName, String CheckedParty, String sysKeyBase) {
        mRxManager.add(mModel.GetScanBarCodeListBinShi(BarCodes,
                InvId,
                InvNumber,
                InvType,
                InvGet,
                InvReMark,
                InvBy,
                InvByName,
                CodeType,
                InvState,
                InvDate,
                LastUpdateBy,
                LastUpdateByName,
                LastUpdateDate,
                ComKey,
                ComName,
                SysKey,
                CheckMemo,
                ReceivingComKey,
                ReceivingComName,
                ReceivingWarehouseId,
                ReceivingWarehouseName,
                CheckedParty,
                sysKeyBase)
                .compose(RxSchedulers.<BaseResponse<BarCodeLogList>>io_main())
                .subscribe(new RxSubscriber<BaseResponse<BarCodeLogList>>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.startProgressDialog("正在上传...");
                    }

                    @Override
                    protected void _onNext(BaseResponse<BarCodeLogList> barCodeLogListBaseResponse) {
                        mView.setScanBarCodeList(barCodeLogListBaseResponse.getData().getBarCodeLogList());
                        mView.stopProgressDialog();
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.stopProgressDialog();
                    }
                }));
    }


    @Override
    public void onStart() {

    }
}
