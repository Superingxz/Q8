package com.xologood.q8pad.ui.fastoutlibrary.newfastoutinvoice;

import com.xologood.mvpframework.util.helper.RxSchedulers;
import com.xologood.mvpframework.util.helper.RxSubscriber;
import com.xologood.q8pad.bean.BarCodeLogList;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.Company;
import com.xologood.q8pad.bean.InvoicingBean;
import com.xologood.q8pad.bean.Product;
import com.xologood.q8pad.bean.ProductBatch;
import com.xologood.q8pad.bean.Warehouse;

import java.util.List;
import java.util.Map;

import rx.functions.Action1;

/**
 * Created by Administrator on 17-1-16.
 */

public class  NewFastOutInvoicePresenter extends NewFastOutInvoiceContract.Presenter{

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

                    }

                    @Override
                    protected void _onError(String message) {

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
                }));
    }

    @Override
    public void GetProductList(String SysKey, String IsUse) {
        mRxManager.add(mModel.GetProductList(SysKey,IsUse)
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
                }));
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
                        mView.stopProgressDialog();
                    }
                })
        );

    }


    /**
     * 快捷出库-上传条码
     * @param BarCodes          全部条码 逗号分隔
     * @param InvNumber         单号
     * @param InvType           类型 写死1
     * @param InvGet            获取类型 写”在线PDA”
     * @param InvReMark         备注 写”暂无”
     * @param InvBy             单据创建人id
     * @param InvByName         单据创建人名称
     * @param CodeType          条形码进出写死true
     * @param InvState          单据状态 写死”2”
     * @param InvDate           创建时间
     * @param LastUpdaeBy       更新人id
     * @param LastUpdateByName  更新人名字
     * @param LastUpdateDate    最后更新时间
     * @param ComKey             机构唯一标识
     * @param ComName           机构名称
     * @param SysKey            仓库id
     * @param CheckMemo         仓库名称
     * @param ReceivingComKey   机构key
     * @param ReceivingComName  系统key
     * @param ReceivingWarehouseId  仓库id
     * @param ReceivingWarehouseName    仓库名称
     * @param CheckedParty          签收管理  写死true
     * @param sysKeyBase            系统key
     * @param ProductId             产品id
     * @param Batch                  批次id
     * @return
     */
    @Override
    public void NewQuickScanBarCode(String BarCodes, String InvNumber, String InvType, String InvGet, String InvReMark, String InvBy, String InvByName, String CodeType, String InvState, String InvDate, String LastUpdaeBy, String LastUpdateByName, String LastUpdateDate, String ComKey, String ComName, String SysKey, String CheckMemo, String ReceivingComKey, String ReceivingComName, String ReceivingWarehouseId, String ReceivingWarehouseName, String CheckedParty, String sysKeyBase, String ProductId, String Batch) {
        mRxManager.add(mModel.NewQuickScanBarCode(BarCodes,
                                                 InvNumber,
                                                 InvType,
                                                 InvGet,
                                                 InvReMark,
                                                 InvBy,
                                                 InvByName,
                                                 CodeType,
                                                 InvState,
                                                 InvDate,
                                                 LastUpdaeBy,
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
                                                 sysKeyBase,
                                                 ProductId,
                                                 Batch)
                             .compose(RxSchedulers.<BaseResponse<BarCodeLogList>>io_main())
                             .subscribe(new RxSubscriber<BaseResponse<BarCodeLogList>>(mContext,false) {
                                 @Override
                                 public void onStart() {
                                     super.onStart();
                                     mView.startProgressDialog("正在上传...");
                                 }

                                 @Override
                                 protected void _onNext(BaseResponse<BarCodeLogList> barCodeLogListBaseResponse) {
                                     mView.SetBarCodeList(barCodeLogListBaseResponse.getData().getBarCodeLogList());
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
    public void insertInv(Map<String, String> options) {
        mRxManager.add(mModel.insertInv(options)
                .compose(RxSchedulers.<BaseResponse<InvoicingBean>>io_main())
                .subscribe(new Action1<BaseResponse<InvoicingBean>>() {
                    @Override
                    public void call(BaseResponse<InvoicingBean> invoicingBeanBaseResponse) {
                        mView.insertInv(invoicingBeanBaseResponse.getData());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {


                    }
                }));
    }

    @Override
    public void insertInv(String SysKey, String InvNumber) {
        mRxManager.add(mModel.insertInv(SysKey,InvNumber)
                .compose(RxSchedulers.<BaseResponse<InvoicingBean>>io_main())
                .subscribe(new Action1<BaseResponse<InvoicingBean>>() {
                    @Override
                    public void call(BaseResponse<InvoicingBean> invoicingBeanBaseResponse) {
                        mView.SetInvid(invoicingBeanBaseResponse.getData().getInvId());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                }));
    }
}
