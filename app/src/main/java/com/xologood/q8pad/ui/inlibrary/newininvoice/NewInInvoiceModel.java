package com.xologood.q8pad.ui.inlibrary.newininvoice;

import com.xologood.mvpframework.util.helper.RxSchedulers;
import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.api.Api;
import com.xologood.q8pad.api.HostType;
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
import com.xologood.q8pad.utils.SharedPreferencesUtils;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Administrator on 17-1-3.
 */

public class NewInInvoiceModel implements NewInInvoiceContract.Model {
    private String recorderBase = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.RECORDERBASE);
    private String sysKeyBase = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.SYSKEYBASE);

    /**
     * 保存入库主表
     * @param options
     * @return
     */
    @Override
    public Observable<BaseResponse<InvoicingBean>> insertInv(Map<String, String> options) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).insertInv(options);
    }

    /**
     * 保存入库主表2
     * @param SysKey
     * @param InvNumber
     * @return
     */
    @Override
    public Observable<BaseResponse<InvoicingBean>> Invoicing(String SysKey, String InvNumber) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).Invoicing(SysKey,InvNumber);
    }

    /**
     * 宾氏-保存入库主表
     * @param options
     * @return
     */
    @Override
    public Observable<BaseResponse<InvoicingBean>> insertInvSupplier(Map<String, String> options) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).insertInvSupplier(options);
    }

    @Override
    public Observable<BaseResponse<ProportionConversion>> GetProportionConversion(String id, String Bunit, String count) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).GetProportionConversion(id, Bunit, count);
    }

    @Override
    public Observable<BaseResponse<String>> GetProportionConversionString(String id, String Bunit, String count) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).GetProportionConversionString(id,Bunit,count);
    }

    @Override
    public Observable<BaseResponse<List<Warehouse>>> GetWareHouseList(String ComKey, String IsUse) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).GetWareHouseList(ComKey, IsUse);
    }

    //获取供应商
    @Override
    public Observable<BaseResponse<List<SupplierBean>>> GetSupplierList(Map<String, String> options) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).GetSupplierBeanList(options);
    }

    @Override
    public Observable<BaseResponse<List<Product>>> GetProductList(String SysKey, String IsUse) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).GetProductList(SysKey, IsUse);
    }

    @Override
    public Observable<BaseResponse<List<ProductBatch>>> GetProductBatchByProductId(String ProductId) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).GetProductBatchByProductId(ProductId);
    }

    @Override
    public Observable<BaseResponse<List<StandardUnit>>> GetStandardUnitByProductId(String ProductId, String SysKey) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).GetStandardUnitByProductId(ProductId, SysKey);
    }


    @Override
    public Observable<BaseResponse<String>> InsertProductBatch(String BatchNO, String ProductId, String SysKey, String ProductDate, String CreationBy) {
        return Api.getDefault(HostType.SYSTEMURL).InsertProductBatch(BatchNO, ProductId, SysKey, ProductDate, CreationBy);
    }




    @Override
    public Observable<BaseResponse<InvoicingDetailVo>> GetInvoiceDetail(String Id,
                                                                        String InvId,
                                                                        String ProductId,
                                                                        String Batch,
                                                                        String ActualQty,
                                                                        String ExpectedQty,
                                                                        String ComKey,
                                                                        String SysKey) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).GetInvoiceDetail(Id, InvId, ProductId, Batch, ActualQty, ExpectedQty, ComKey, SysKey);
    }

    @Override
    public Observable<BaseResponse<InvoicingDetailVo>> InsertInvoiceDetail(String Id,
                                                                           String InvId,
                                                                           String ProductId,
                                                                           String Batch,
                                                                           String ActualQty,
                                                                           String ExpectedQty,
                                                                           String ComKey,
                                                                           String SysKey) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).InsertInvoiceDetail( Id,
                                                                                                        InvId,
                                                                                                        ProductId,
                                                                                                        Batch,
                                                                                                        ActualQty,
                                                                                                        ExpectedQty,
                                                                                                        ComKey,
                                                                                                        SysKey);

    }

    @Override
    public Observable<BaseResponse<InvoicingDetailVo>> UpdateInvoiceDetail(String Id,
                                                                           String InvId,
                                                                           String ProductId,
                                                                           String Batch,
                                                                           String ActualQty,
                                                                           String ExpectedQty,
                                                                           String ComKey,
                                                                           String SysKey) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).UpdateInvoiceDetail(Id,
                                                                                                       InvId,
                                                                                                       ProductId,
                                                                                                       Batch,
                                                                                                       ActualQty,
                                                                                                       ExpectedQty,
                                                                                                       ComKey,
                                                                                                       SysKey);
    }

    /**
     * 获取单据明细
     * @param invId
     * @return
     */
    @Override
    public Observable<BaseResponse<Invoice>> GetInvoicingDetail(String invId) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).GetInvoicingDetail(invId)
                .compose(RxSchedulers.<BaseResponse<Invoice>>io_main());
    }

    @Override
    public Observable<BaseResponse<String>> CompleteSave(String invId, String userId, String userName) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).CompleteSave(invId,userId,userName);
    }


}
