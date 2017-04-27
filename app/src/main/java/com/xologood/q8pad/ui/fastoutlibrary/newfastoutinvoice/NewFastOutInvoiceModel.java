package com.xologood.q8pad.ui.fastoutlibrary.newfastoutinvoice;

import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.api.Api;
import com.xologood.q8pad.api.HostType;
import com.xologood.q8pad.bean.BarCodeLogList;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.Company;
import com.xologood.q8pad.bean.InvoicingBean;
import com.xologood.q8pad.bean.Product;
import com.xologood.q8pad.bean.ProductBatch;
import com.xologood.q8pad.bean.Warehouse;
import com.xologood.q8pad.utils.SharedPreferencesUtils;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Administrator on 17-1-16.
 */

public class NewFastOutInvoiceModel implements NewFastOutInvoiceContract.Model{
    private String recorderBase = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.RECORDERBASE);
    private String sysKeyBase = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.SYSKEYBASE);

    @Override
    public Observable<BaseResponse<List<Company>>> GetAllCompList(String ComKey, String CType) {
        return Api.getLoginInInstance(HostType.USERURL,recorderBase,sysKeyBase).GetAllCompList(ComKey, CType);
    }

    @Override
    public Observable<BaseResponse<List<Warehouse>>> GetWareHouseList(String ComKey, String IsUse) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).GetWareHouseList(ComKey, IsUse);
    }

    @Override
    public Observable<BaseResponse<List<Product>>> GetProductList(String SysKey, String IsUse) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).GetProductList(SysKey, IsUse);
    }

    @Override
    public Observable<BaseResponse<String>> InsertProductBatch(String BatchNO, String ProductId, String SysKey, String ProductDate, String CreationBy) {
        return Api.getDefault(HostType.SYSTEMURL).InsertProductBatch(BatchNO, ProductId, SysKey, ProductDate, CreationBy);
    }

    @Override
    public Observable<BaseResponse<List<ProductBatch>>> GetProductBatchByProductId(String ProductId) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).GetProductBatchByProductId(ProductId);
    }

    //快捷出库-上传条码
    @Override
    public Observable<BaseResponse<BarCodeLogList>> NewQuickScanBarCode(String BarCodes,
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
                                                                        String Batch) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).NewQuickScanBarCode(BarCodes,
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
                                                                                                       Batch
        );
    }

    /**
     * 保存入库主表
     * @param options
     * @return
     */
    @Override
    public Observable<BaseResponse<InvoicingBean>> insertInv(Map<String, String> options) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).insertInv( options);
    }

    /**
     * 保存入库主表2-查询
     * @param SysKey
     * @param InvNumber
     * @return
     */
    @Override
    public Observable<BaseResponse<InvoicingBean>> insertInv(String SysKey, String InvNumber) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).Invoicing(SysKey,InvNumber);
    }
}
