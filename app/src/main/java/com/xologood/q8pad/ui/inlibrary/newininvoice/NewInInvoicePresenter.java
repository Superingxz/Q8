package com.xologood.q8pad.ui.inlibrary.newininvoice;

import android.util.Log;

import com.xologood.mvpframework.util.helper.RxSchedulers;
import com.xologood.mvpframework.util.helper.RxSubscriber;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.Invoice;
import com.xologood.q8pad.bean.InvoicingBean;
import com.xologood.q8pad.bean.InvoicingDetailVo;
import com.xologood.q8pad.bean.Product;
import com.xologood.q8pad.bean.ProductBatch;
import com.xologood.q8pad.bean.ProportionConversion;
import com.xologood.q8pad.bean.StandardUnit;
import com.xologood.q8pad.bean.SupplierBean;
import com.xologood.q8pad.bean.Warehouse;

import java.util.List;
import java.util.Map;

import rx.functions.Action1;

/**
 * Created by Administrator on 17-1-3.
 */

public class NewInInvoicePresenter extends NewInInvoiceContract.Presenter {

    private static final String TAG = "Superingxz";


    @Override
    public void onStart() {

    }

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
                        mView.startProgressDialog("正在保存...");
                    }

                    @Override
                    protected void _onNext(BaseResponse<InvoicingBean> invoicingBeanBaseResponse) {
                        mView.insertInv(invoicingBeanBaseResponse.getData());
                    }

                    @Override
                    protected void _onError(String message) {

                    }
                })
        );
    }

    //查询入库主表
    @Override
    public void Invoicing(String SysKey, String InvNumber) {
        mRxManager.add(mModel.Invoicing(SysKey,InvNumber)
                               .compose(RxSchedulers.<BaseResponse<InvoicingBean>>io_main())
                               .subscribe(new Action1<BaseResponse<InvoicingBean>>() {
                                   @Override
                                   public void call(BaseResponse<InvoicingBean> invoicingBeanBaseResponse) {
                                       mView.Invoicing(invoicingBeanBaseResponse.getData());
                                   }
                               }, new Action1<Throwable>() {
                                   @Override
                                   public void call(Throwable throwable) {


                                   }
                               }));
    }


    /**
     * 宾氏额外调用-保存入库主表
     * @param options
     */
    @Override
    public void insertInvSupplier(Map<String, String> options) {
        mRxManager.add(mModel.insertInvSupplier(options)
                .compose(RxSchedulers.<BaseResponse<InvoicingBean>>io_main())
                .subscribe(new RxSubscriber<BaseResponse<InvoicingBean>>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.startProgressDialog("正在保存...");
                    }

                    @Override
                    protected void _onNext(BaseResponse<InvoicingBean> invoicingBeanBaseResponse) {
                        mView.insertInvSupplier(invoicingBeanBaseResponse.getData());
                    }

                    @Override
                    protected void _onError(String message) {

                    }
                })
        );
    }

    /**
     * 获取单位比例
     */
    @Override
    public void GetProportionConversion(String id, String Bunit, String count) {
        mRxManager.add(mModel.GetProportionConversion(id,Bunit,count)
                             .compose(RxSchedulers.<BaseResponse<ProportionConversion>>io_main())
                             .subscribe(new Action1<BaseResponse<ProportionConversion>>() {
                                 @Override
                                 public void call(BaseResponse<ProportionConversion> proportionConversion) {
                                        mView.SetProportionConversion(proportionConversion.getData());
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
                        mView.SetProportionConversion(stringBaseResponse.getData());
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

                    }
                })
        );
    }

    @Override
    public void GetSupplierList(Map<String, String> options) {
        mRxManager.add(mModel.GetSupplierList(options)
                .compose(RxSchedulers.<BaseResponse<List<SupplierBean>>>io_main())
                .subscribe(new RxSubscriber<BaseResponse<List<SupplierBean>>>(mContext,false) {
                    public void onStart() {
                        super.onStart();
                        mView.startProgressDialog("正在加载...");
                    }

                    @Override
                    protected void _onNext(BaseResponse<List<SupplierBean>> listBaseResponse) {
                        mView.SetSupplierList(listBaseResponse.getData());
                        mView.stopProgressDialog();
                    }

                    @Override
                    protected void _onError(String message) {

                    }
                })
        );
    }

    @Override
    public void GetProductList(String SysKey, String IsUse) {
        mRxManager.add(mModel.GetProductList(SysKey, IsUse)
                .compose(RxSchedulers.<BaseResponse<List<Product>>>io_main())
                .subscribe(new RxSubscriber<BaseResponse<List<Product>>>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.startProgressDialog("正在加载...");
                    }

                    @Override
                    protected void _onNext(BaseResponse<List<Product>> listBaseResponse) {
                        mView.SetProductList(listBaseResponse.getData());
                        mView.stopProgressDialog();
                    }

                    @Override
                    protected void _onError(String message) {

                    }
                })
        );

    }

    @Override
    public void GetProductBatchByProductId(String ProductId) {
        mRxManager.add(mModel.GetProductBatchByProductId(ProductId)
                .compose(RxSchedulers.<BaseResponse<List<ProductBatch>>>io_main())
                .subscribe(new RxSubscriber<BaseResponse<List<ProductBatch>>>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.startProgressDialog("正在加载...");
                    }

                    @Override
                    protected void _onNext(BaseResponse<List<ProductBatch>> listBaseResponse) {
                        mView.SetProductBatch(listBaseResponse.getData());
                        mView.stopProgressDialog();
                    }

                    @Override
                    protected void _onError(String message) {

                    }
                })
        );

    }

    @Override
    public void GetStandardUnitByProductId(String ProductId, String SysKey) {
        mRxManager.add( mModel.GetStandardUnitByProductId(ProductId,SysKey)
                .compose(RxSchedulers.<BaseResponse<List<StandardUnit>>>io_main())
                .subscribe(new RxSubscriber<BaseResponse<List<StandardUnit>>>(mContext, false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.startProgressDialog("正在加载...");
                    }

                    @Override
                    protected void _onNext(BaseResponse<List<StandardUnit>> listBaseResponse) {
                        mView.SetStandardUnitByProductId(listBaseResponse.getData());
                        mView.stopProgressDialog();
                    }

                    @Override
                    protected void _onError(String message) {

                    }
                })
        );
    }


    @Override
    public void InsertProductBatch(String BatchNO, String ProductId, String SysKey, String ProductDate, String CreationBy) {
        mRxManager.add(mModel.InsertProductBatch(BatchNO,ProductId,SysKey,ProductDate,CreationBy)
                  .compose(RxSchedulers.<BaseResponse<String>>io_main())
                  .subscribe(new Action1<BaseResponse<String>>() {
                      @Override
                      public void call(BaseResponse<String> stringBaseResponse) {
                            mView.InsertProductBatch(stringBaseResponse.getData());
                      }
                  }, new Action1<Throwable>() {
                      @Override
                      public void call(Throwable throwable) {
                          mView.InsertProductBatchFailed("添加异常失败！");
                      }
                  })
        );
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
                                InvoicingDetailVo data = invoiceDetailBaseResponse.getData();
                                mView.GetInvoiceDetailSuccess(data.getId(),data.getInvId(),data.getExpectedQty());

                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                               // mView.GetInvoiceDetailSuccess(throwable.getCause().getMessage());
                            }
                        })
        );
    }

    //新增入库明细数据
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
                             .subscribe(new RxSubscriber<BaseResponse<InvoicingDetailVo>>(mContext,false) {
                                 @Override
                                 protected void _onNext(BaseResponse<InvoicingDetailVo> invoiceingDetailVoBaseResponse) {
                                     mView.InsertInvoiceDetailSuccess(invoiceingDetailVoBaseResponse.getData().getId());
                                     mView.stopProgressDialog();
                                 }

                                 @Override
                                 protected void _onError(String message) {

                                 }
                             })
        );
    }

    //更新修改入库明细数据
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
                              .subscribe(new RxSubscriber<BaseResponse<InvoicingDetailVo>>(mContext,false) {
                                  @Override
                                  protected void _onNext(BaseResponse<InvoicingDetailVo> invoiceingDetailVoBaseResponse) {
                                      mView.UpdateInvoiceDetailSuccess(invoiceingDetailVoBaseResponse.getData().getId());
                                      mView.stopProgressDialog();
                                  }

                                  @Override
                                  protected void _onError(String message) {

                                  }
                              })
        );
    }


    /**
     * 获取单据明细
     */
    @Override
    public void GetInvoicingDetail(final String invId) {
        mRxManager.add(mModel.GetInvoicingDetail(invId)
                .subscribe(new RxSubscriber<BaseResponse<Invoice>>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.startProgressDialog("正在加载...");
                    }

                    @Override
                    protected void _onNext(BaseResponse<Invoice> invoiceBaseResponse) {
                        Invoice data = invoiceBaseResponse.getData();
                        mView.SetInvoicingDetail(data);
                        mView.stopProgressDialog();
                    }

                    @Override
                    protected void _onError(String message) {

                    }
                }));
    }

    @Override
    public void CompleteSave(String invId, String userId, String userName) {
        mRxManager.add(mModel.CompleteSave(invId,userId,userName)
                .compose(RxSchedulers.<BaseResponse<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse<String>>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.startProgressDialog("正在确认...");
                    }

                    @Override
                    protected void _onNext(BaseResponse<String> stringBaseResponse) {
                        mView.CompleteSaveSuccess(stringBaseResponse.getData());
                        mView.stopProgressDialog();
                    }

                    @Override
                    protected void _onError(String message) {

                    }
                })
        );
    }
}
