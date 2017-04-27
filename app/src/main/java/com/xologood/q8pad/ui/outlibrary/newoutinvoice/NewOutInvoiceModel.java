package com.xologood.q8pad.ui.outlibrary.newoutinvoice;

import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.api.Api;
import com.xologood.q8pad.api.HostType;
import com.xologood.q8pad.bean.BarCodeLogList;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.Company;
import com.xologood.q8pad.bean.InvoicingDetailVo;
import com.xologood.q8pad.bean.InvoicingBean;
import com.xologood.q8pad.bean.Product;
import com.xologood.q8pad.bean.ProportionConversion;
import com.xologood.q8pad.bean.StandardUnit;
import com.xologood.q8pad.bean.Warehouse;
import com.xologood.q8pad.utils.SharedPreferencesUtils;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by Administrator on 17-1-6.
 */

public class NewOutInvoiceModel implements NewOutInvoiceContract.Model{
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

    @Override
    public Observable<BaseResponse<ProportionConversion>> GetProportionConversio(String id, String Bunit, String count) {
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

    @Override
    public Observable<BaseResponse<List<Product>>> GetProductList(String SysKey, String IsUse) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).GetProductList(SysKey, IsUse);
    }

    @Override
    public Observable<BaseResponse<List<StandardUnit>>> GetStandardUnitByProductId(String ProductId, String SysKey) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).GetStandardUnitByProductId(ProductId, SysKey);
    }

    @Override
    public Observable<BaseResponse<List<Company>>> GetAllCompList(String ComKey, String CType) {
        return Api.getLoginInInstance(HostType.USERURL,recorderBase,sysKeyBase).GetAllCompList(ComKey, CType);
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
     * 出库上传
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
    public Observable<BaseResponse<BarCodeLogList>> GetScanBarCodeList(String BarCodes,
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
                                                                       String sysKeyBase) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).GetScanBarCodeList(BarCodes,
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
                                                                                                        sysKeyBase);
    }

/**
 * 出库上传-BinShi
 *
 * */
    @Override
    public Observable<BaseResponse<BarCodeLogList>> GetScanBarCodeListBinShi(String BarCodes, String InvId, String InvNumber, String InvType, String InvGet, String InvReMark, String InvBy, String InvByName, String CodeType, String InvState, String InvDate, String LastUpdateBy, String LastUpdateByName, String LastUpdateDate, String ComKey, String ComName, String SysKey, String CheckMemo, String ReceivingComKey, String ReceivingComName, String ReceivingWarehouseId, String ReceivingWarehouseName, String CheckedParty, String sysKeyBase) {
        return Api.getLoginInInstance(HostType.SYSTEMURL,recorderBase,sysKeyBase).GetScanBarCodeListBinShi(BarCodes,
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
                sysKeyBase);
    }
}
