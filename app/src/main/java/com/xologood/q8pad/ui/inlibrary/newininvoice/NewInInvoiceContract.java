package com.xologood.q8pad.ui.inlibrary.newininvoice;

import com.xologood.mvpframework.base.BaseModel;
import com.xologood.mvpframework.base.BasePresenter;
import com.xologood.mvpframework.base.BaseView;
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

import rx.Observable;

/**
 * Created by Administrator on 17-1-3.
 */

public interface NewInInvoiceContract {
    public interface Model extends BaseModel {

        /**
         * 保存入库/出库主表
         *
         * @param options
         * @return
         */
        Observable<BaseResponse<InvoicingBean>> insertInv(Map<String, String> options);

        /**
         * 查询入库/出库主表2
         *
         * @param SysKey
         * @param InvNumber
         * @return
         */
        Observable<BaseResponse<InvoicingBean>> Invoicing(String SysKey, String InvNumber);

        /**
         * 宾氏-保存入库主表
         CompleteBy 创建人
         CompleteDate 创建时间
         IsUse 是否使用
         SysKey 系统key
         InvId 创建完主单后返回的ID
         SupplierId 供应商ID
         SupplierName 供应商名称
         * @param options
         * @return
         */
        Observable<BaseResponse<InvoicingBean>> insertInvSupplier(Map<String, String> options);

        /**
         * 获取单位比例
         *
         * @param id    产品id
         * @param Bunit 单位id
         * @param count 预计数量
         * @return
         */
        Observable<BaseResponse<ProportionConversion>> GetProportionConversion(String id, String Bunit, String count);

        Observable<BaseResponse<String>> GetProportionConversionString(String id, String Bunit, String count);


        Observable<BaseResponse<List<Warehouse>>> GetWareHouseList(String ComKey, String IsUse);

        Observable<BaseResponse<List<SupplierBean>>> GetSupplierList(Map<String, String> options);

        Observable<BaseResponse<List<Product>>> GetProductList(String SysKey, String IsUse);

        Observable<BaseResponse<List<ProductBatch>>> GetProductBatchByProductId(String ProductId);

        Observable<BaseResponse<List<StandardUnit>>> GetStandardUnitByProductId(String ProductId, String SysKey);

        Observable<BaseResponse<String>> InsertProductBatch(String BatchNO,
                                                            String ProductId,
                                                            String SysKey,
                                                            String ProductDate,
                                                            String CreationBy);

        /**
         * 验证入库明细
         *
         * @param Id          明细id  写死0
         * @param InvId       单号id
         * @param ProductId   产品id
         * @param Batch       批次id
         * @param ActualQty   实际数量
         * @param ExpectedQty 预计数量
         * @param ComKey      机构唯一标识
         * @param SysKey      系统唯一标识
         * @return
         */
        Observable<BaseResponse<InvoicingDetailVo>> GetInvoiceDetail(String Id,
                                                                     String InvId,
                                                                     String ProductId,
                                                                     String Batch,
                                                                     String ActualQty,
                                                                     String ExpectedQty,
                                                                     String ComKey,
                                                                     String SysKey);

        /**
         * 增加入库明细
         *
         * @param Id          明细id  写死0
         * @param InvId       单号id
         * @param ProductId   产品id
         * @param Batch       批次id
         * @param ActualQty   实际数量
         * @param ExpectedQty 预计数量
         * @param ComKey      机构唯一标识
         * @param SysKey      系统唯一标识
         * @return
         */
        Observable<BaseResponse<InvoicingDetailVo>> InsertInvoiceDetail(String Id,
                                                                        String InvId,
                                                                        String ProductId,
                                                                        String Batch,
                                                                        String ActualQty,
                                                                        String ExpectedQty,
                                                                        String ComKey,
                                                                        String SysKey);

        /**
         * 更新入库明细
         *
         * @param Id          明细id  写死0
         * @param InvId       单号id
         * @param ProductId   产品id
         * @param Batch       批次id
         * @param ActualQty   实际数量
         * @param ExpectedQty 预计数量
         * @param ComKey      机构唯一标识
         * @param SysKey      系统唯一标识
         * @return
         */
        Observable<BaseResponse<InvoicingDetailVo>> UpdateInvoiceDetail(String Id,
                                                                        String InvId,
                                                                        String ProductId,
                                                                        String Batch,
                                                                        String ActualQty,
                                                                        String ExpectedQty,
                                                                        String ComKey,
                                                                        String SysKey
        );

        Observable<BaseResponse<Invoice>> GetInvoicingDetail(String invId);

        /**
         * 确认提交
         *
         * @param invId    入库单号id
         * @param userId   用户id
         * @param userName 用户名
         * @return
         */
        Observable<BaseResponse<String>> CompleteSave(String invId,
                                                      String userId,
                                                      String userName
        );

    }


    interface View extends BaseView {
        /**
         * 保存入库主表回调
         *
         * @param invoicingBean
         */
        void insertInv(InvoicingBean invoicingBean);

        /**
         * 查询入库主表回调
         *
         * @param invoicingBean
         */
        void Invoicing(InvoicingBean invoicingBean);

        /**
         * 宾氏-保存入库主表回调
         */
        void insertInvSupplier(InvoicingBean invoicingBean);

        /**
         * 获取到单位比例
         *
         * @param proportionConversion
         */
        void SetProportionConversion(ProportionConversion proportionConversion);

        void SetProportionConversion(String proportionConversion);

        /**
         * 初始化列表
         */
        void SetWareHouseList(List<Warehouse> warehouseList);

        void SetSupplierList(List<SupplierBean> supplierList);

        void SetProductList(List<Product> productList);

        void SetProductBatch(List<ProductBatch> productBatchList);

        void SetStandardUnitByProductId(List<StandardUnit> standardUnitList);

        /**
         * 添加批次回调
         *
         * @param msg
         */
        void InsertProductBatch(String msg);

        void InsertProductBatchFailed(String msg);


        //入库明细方法

        /**
         * 验证入库明细成功回调
         */
        void GetInvoiceDetailSuccess(int id, String InvId, String ExpectedQty);

        /**
         * 增加入库明细成功回调
         *
         * @param Id
         */
        void InsertInvoiceDetailSuccess(int Id);

        void UpdateInvoiceDetailSuccess(int Id);

        /**
         * 获取单据信息成功
         * @param invoice
         */
        void SetInvoicingDetail(Invoice invoice);

        /**
         * 确定提交成功
         */
        void CompleteSaveSuccess(String msg);


        /**
         * 开启加载进度条
         */
        public void startProgressDialog(String msg) ;
        /**
         * 停止加载进度条
         */
        public void stopProgressDialog() ;

    }

    abstract class Presenter extends BasePresenter<Model, View> {

        /**
         * 保存入库/出库主表
         */
        public abstract void insertInv(Map<String, String> options);

        public abstract void Invoicing(String SysKey,String InvNumber);

        /**
         * 宾氏额外调用-保存入库/出库主表
         */
        public abstract void insertInvSupplier(Map<String, String> options);

        /**
         * 获取单位比例
         *
         * @param id    产品id
         * @param Bunit 单位id
         * @param count 预计数量
         * @return
         */
        public abstract void GetProportionConversion(String id, String Bunit, String count);

        public abstract void GetProportionConversionString(String id, String Bunit, String count);

        public abstract void GetWareHouseList(String ComKey, String IsUse);

        public abstract void GetSupplierList(Map<String, String> options);

        public abstract void GetProductList(String SysKey, String IsUse);

        public abstract void GetProductBatchByProductId(String ProductId);

        public abstract void GetStandardUnitByProductId(String ProductId, String SysKey);

        public abstract void InsertProductBatch(String BatchNO,
                                                String ProductId,
                                                String SysKey,
                                                String ProductDate,
                                                String CreationBy);

        /**
         * 验证入库明细
         *
         * @param Id          明细id  写死0
         * @param InvId       单号id
         * @param ProductId   产品id
         * @param Batch       批次id
         * @param ActualQty   实际数量
         * @param ExpectedQty 预计数量
         * @param ComKey      机构唯一标识
         * @param SysKey      系统唯一标识
         * @return
         */
        public abstract void GetInvoiceDetail(String Id,
                                              String InvId,
                                              String ProductId,
                                              String Batch,
                                              String ActualQty,
                                              String ExpectedQty,
                                              String ComKey,
                                              String SysKey);

        /**
         * 增加入库明细
         *
         * @param Id          明细id  写死0
         * @param InvId       单号id
         * @param ProductId   产品id
         * @param Batch       批次id
         * @param ActualQty   实际数量
         * @param ExpectedQty 预计数量
         * @param ComKey      机构唯一标识
         * @param SysKey      系统唯一标识
         * @return
         */
        public abstract void InsertInvoiceDetail(String Id,
                                                 String InvId,
                                                 String ProductId,
                                                 String Batch,
                                                 String ActualQty,
                                                 String ExpectedQty,
                                                 String ComKey,
                                                 String SysKey);

        /**
         * 更新入库明细
         *
         * @param Id          明细id  写死0
         * @param InvId       单号id
         * @param ProductId   产品id
         * @param Batch       批次id
         * @param ActualQty   实际数量
         * @param ExpectedQty 预计数量
         * @param ComKey      机构唯一标识
         * @param SysKey      系统唯一标识
         * @return
         */
        public abstract void UpdateInvoiceDetail(String Id,
                                                 String InvId,
                                                 String ProductId,
                                                 String Batch,
                                                 String ActualQty,
                                                 String ExpectedQty,
                                                 String ComKey,
                                                 String SysKey
        );

        public abstract void GetInvoicingDetail(String invId);

        /**
         * 确认提交
         *
         * @param invId    入库单号id
         * @param userId   用户id
         * @param userName 用户名
         * @return
         */
        public abstract void CompleteSave(String invId,
                                          String userId,
                                          String userName
        );


    }
}
