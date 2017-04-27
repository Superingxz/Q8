package com.xologood.q8pad.ui.outlibrary.newoutinvoice;

import com.xologood.mvpframework.base.BaseModel;
import com.xologood.mvpframework.base.BasePresenter;
import com.xologood.mvpframework.base.BaseView;
import com.xologood.q8pad.api.ApiConstants;
import com.xologood.q8pad.bean.BarCodeLog;
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

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Administrator on 17-1-6.
 */

public interface NewOutInvoiceContract {
    public interface Model extends BaseModel {

        /**
         * 保存入库/出库主表
         *
         * @param options
         * @return
         */
        Observable<BaseResponse<InvoicingBean>> insertInv(Map<String, String> options);

        /**
         * 获取单位比例
         * @param id        产品id
         * @param Bunit     单位id
         * @param count     预计数量
         * @return
         */
        Observable<BaseResponse<ProportionConversion>> GetProportionConversio(String id, String Bunit, String count);
        Observable<BaseResponse<String>> GetProportionConversionString(String id, String Bunit, String count);

        /**
         * 获取仓库列表
         *
         * @param ComKey 系统key
         * @param IsUse  是否启用
         * @return
         */
        Observable<BaseResponse<List<Warehouse>>> GetWareHouseList(String ComKey, String IsUse);
        /**
         * 获取产品列表
         *
         * @param SysKey 系统key
         * @param IsUse  是否启用
         * @return
         */
        Observable<BaseResponse<List<Product>>> GetProductList(String SysKey, String IsUse);
        /**
         * 根据产品id获取单位
         * @param ProductId  产品Id
         * @param SysKey    系统唯一标识
         * @return
         */
        Observable<BaseResponse<List<StandardUnit>>> GetStandardUnitByProductId(String ProductId, String SysKey);


        /**
         * 获取机构列表
         * @param ComKey    机构唯一标识
         * @param CType     类型 写死”2”
         * @return
         */
        @GET(ApiConstants.COMPANY_GET_ALLCOPLIST)
        Observable<BaseResponse<List<Company>>> GetAllCompList(String ComKey, String CType);


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
        Observable<BaseResponse<InvoicingDetailVo>> UpdateInvoiceDetail(String Id,
                                                                        String InvId,
                                                                        String ProductId,
                                                                        String Batch,
                                                                        String ActualQty,
                                                                        String ExpectedQty,
                                                                        String ComKey,
                                                                        String SysKey);

        //出库上传
        Observable<BaseResponse<BarCodeLogList>> GetScanBarCodeList(String BarCodes,
                                                                    String InvId,
                                                                    String InvNumber,
                                                                    String InvType,
                                                                    String InvGet,
                                                                    String InvReMark,
                                                                    String InvBy,
                                                                    String InvByName,
                                                                    String CodeType,
                                                                    String InvState,
                                                                    String InvDate,
                                                                    String LastUpdateBy,
                                                                    String LastUpdateByName,
                                                                    String LastUpdateDate,
                                                                    String ComKey,
                                                                    String ComName,
                                                                    String SysKey,
                                                                    String CheckMemo,
                                                                    String ReceivingComKey,
                                                                    String ReceivingComName,
                                                                    String ReceivingWarehouseId,
                                                                    String ReceivingWarehouseName,
                                                                    String CheckedParty,
                                                                    String sysKeyBase);

        //宾氏-出库上传
        Observable<BaseResponse<BarCodeLogList>> GetScanBarCodeListBinShi(String BarCodes,
                                                                          String InvId,
                                                                          String InvNumber,
                                                                          String InvType,
                                                                          String InvGet,
                                                                          String InvReMark,
                                                                          String InvBy,
                                                                          String InvByName,
                                                                          String CodeType,
                                                                          String InvState,
                                                                          String InvDate,
                                                                          String LastUpdateBy,
                                                                          String LastUpdateByName,
                                                                          String LastUpdateDate,
                                                                          String ComKey,
                                                                          String ComName,
                                                                          String SysKey,
                                                                          String CheckMemo,
                                                                          String ReceivingComKey,
                                                                          String ReceivingComName,
                                                                          String ReceivingWarehouseId,
                                                                          String ReceivingWarehouseName,
                                                                          String CheckedParty,
                                                                          String sysKeyBase);
    }


    interface View extends BaseView {

        /**
         * 保存入库主表回调
         * @param invoicingBean
         */
        void insertInv(InvoicingBean invoicingBean);
        /**
         * 获取到单位比例
       //  * @param proportionConversion
         */
    //    void SetProportionConversion(ProportionConversion proportionConversion);
     //   void SetProportionConversion(String proportionConversion);

        void SetWareHouseList(List<Warehouse> warehouseList);
       /* void SetProductList(List<Product> productList);
        void SetStandardUnitByProductId(List<StandardUnit> standardUnitList);*/
        void SetAllCompList(List<Company> companyList);

        //入库明细方法

        /**
         * 验证入库明细成功回调
         */
     //   void GetInvoiceDetailSuccess(int Id);

        /**
         * 增加入库明细成功回调
         *
         * @param
         */
     /*   void InsertInvoiceDetailSuccess(int Id);

        void UpdateInvoiceDetailSuccess(int Id);*/

        void setScanBarCodeList(List<BarCodeLog> barCodeLogList);

        /**
         * 开启加载进度条
         */
        public void startProgressDialog(String msg) ;
        /**
         * 停止加载进度条
         */
        public void stopProgressDialog() ;
    }


    abstract class Presenter extends BasePresenter<Model,View> {

        /**
         * 保存入库/出库主表
         */
        public abstract void insertInv(Map<String, String> options);

        /**
         * 获取单位比例
         */
        public abstract void GetProportionConversion(String id, String Bunit, String count);

        public abstract void GetProportionConversionString(String id, String Bunit, String count);

        public abstract void GetWareHouseList(String ComKey, String IsUse);

        public abstract void GetProductList(String SysKey, String IsUse);

        public abstract void GetStandardUnitByProductId(String ProductId, String SysKey);

        public abstract void GetAllCompList(String ComKey, String CType);

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
                                              String SysKey
        );

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
                                                 String SysKey
        );

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

        //出库上传
        public abstract void GetScanBarCodeList(String BarCodes,
                                                String InvId,
                                                String InvNumber,
                                                String InvType,
                                                String InvGet,
                                                String InvReMark,
                                                String InvBy,
                                                String InvByName,
                                                String CodeType,
                                                String InvState,
                                                String InvDate,
                                                String LastUpdateBy,
                                                String LastUpdateByName,
                                                String LastUpdateDate,
                                                String ComKey,
                                                String ComName,
                                                String SysKey,
                                                String CheckMemo,
                                                String ReceivingComKey,
                                                String ReceivingComName,
                                                String ReceivingWarehouseId,
                                                String ReceivingWarehouseName,
                                                String CheckedParty,
                                                String sysKeyBase);

        public abstract void GetScanBarCodeListBinShi(String BarCodes,
                                                String InvId,
                                                String InvNumber,
                                                String InvType,
                                                String InvGet,
                                                String InvReMark,
                                                String InvBy,
                                                String InvByName,
                                                String CodeType,
                                                String InvState,
                                                String InvDate,
                                                String LastUpdateBy,
                                                String LastUpdateByName,
                                                String LastUpdateDate,
                                                String ComKey,
                                                String ComName,
                                                String SysKey,
                                                String CheckMemo,
                                                String ReceivingComKey,
                                                String ReceivingComName,
                                                String ReceivingWarehouseId,
                                                String ReceivingWarehouseName,
                                                String CheckedParty,
                                                String sysKeyBase);


    }

}
