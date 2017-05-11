package com.xologood.q8pad.ui.fastoutlibrary.newfastoutinvoice;

import com.xologood.mvpframework.base.BaseModel;
import com.xologood.mvpframework.base.BasePresenter;
import com.xologood.mvpframework.base.BaseView;
import com.xologood.q8pad.api.ApiConstants;
import com.xologood.q8pad.bean.BarCodeLog;
import com.xologood.q8pad.bean.BarCodeLogList;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.Company;
import com.xologood.q8pad.bean.InvoicingBean;
import com.xologood.q8pad.bean.Product;
import com.xologood.q8pad.bean.ProductBatch;
import com.xologood.q8pad.bean.Warehouse;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Administrator on 17-1-16.
 */

public interface NewFastOutInvoiceContract {
    interface Model extends BaseModel {

        /**
         * 获取机构列表
         * @param ComKey    机构唯一标识
         * @param CType     类型 写死”2”
         * @return
         */
        @GET(ApiConstants.COMPANY_GET_ALLCOPLIST)
        Observable<BaseResponse<List<Company>>> GetAllCompList(String ComKey, String CType);

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
         * 插入批次
         * @param BatchNO
         * @param ProductId
         * @param SysKey
         * @param ProductDate
         * @param CreationBy
         * @return
         */
        Observable<BaseResponse<String>> InsertProductBatch(String BatchNO,
                                                            String ProductId,
                                                            String SysKey,
                                                            String ProductDate,
                                                            String CreationBy
        );

        /**
         * 获取批次列表
         * @param ProductId
         * @return
         */
        Observable<BaseResponse<List<ProductBatch>>> GetProductBatchByProductId(String ProductId);


        /**
         * 快捷出库-上传条码
         * @return
         */
        @GET(ApiConstants.NEWSCANBARCODE_NEWQUICKSCANBARCODE)
        Observable<BaseResponse<BarCodeLogList>> NewQuickScanBarCode(String BarCodes,
                                                                     String InvNumber,
                                                                     String InvType,
                                                                     String InvGet,
                                                                     String InvReMark,
                                                                     String InvBy,
                                                                     String InvByName,
                                                                     String CodeType,
                                                                     String InvState,
                                                                     String InvDate,
                                                                     String LastUpdaeBy,
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
                                                                     String sysKeyBase,
                                                                     String ProductId,
                                                                     String Batch
        );


        /**
         * 保存入库/出库主表
         *
         * @param options
         * @return
         */
        Observable<BaseResponse<InvoicingBean>> insertInv(Map<String, String> options);

        /**
         * 保存入库/出库主表2
         *
         * @param SysKey
         * @param InvNumber
         * @return
         */
        Observable<BaseResponse<InvoicingBean>> insertInv(String SysKey, String InvNumber);
    }

    interface View extends BaseView {
        void SetAllCompList(List<Company> companyList);

        void SetWareHouseList(List<Warehouse> warehouseList);

        void SetProductList(List<Product> productList);

        /**
         * 添加批次回调
         *
         * @param msg
         */
        void InsertProductBatch(String msg);

        void SetProductBatch(List<ProductBatch> productBatchList);

        void InsertProductBatchFailed(String msg);

        void SetBarCodeList(List<BarCodeLog> barCodeLogList);

        void SetInvid(int invId);


        /**
         * 保存入库主表回调
         *
         * @param invoicingBean
         */
        void insertInv(InvoicingBean invoicingBean);

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

         public abstract void GetAllCompList(String ComKey, String CType);

         public abstract void GetWareHouseList(String ComKey, String IsUse);

         public abstract void GetProductList(String SysKey, String IsUse);

         public abstract void InsertProductBatch(String BatchNO,
                                                 String ProductId,
                                                 String SysKey,
                                                 String ProductDate,
                                                 String CreationBy);

         public abstract void GetProductBatchByProductId(String ProductId);


         /**
          * 新建快捷出库
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
        public abstract void  NewQuickScanBarCode(String BarCodes,
                                                                     String InvNumber,
                                                                     String InvType,
                                                                     String InvGet,
                                                                     String InvReMark,
                                                                     String InvBy,
                                                                     String InvByName,
                                                                     String CodeType,
                                                                     String InvState,
                                                                     String InvDate,
                                                                     String LastUpdaeBy,
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
                                                                     String sysKeyBase,
                                                                     String ProductId,
                                                                     String Batch
        );

         /**
          * 保存入库主表回调
          *
          * @param options
          */
         public abstract void insertInv(Map<String, String> options);
         public abstract void insertInv(String SysKey,String InvNumber);

        @Override
        public void onStart() {
        }
    }
}
