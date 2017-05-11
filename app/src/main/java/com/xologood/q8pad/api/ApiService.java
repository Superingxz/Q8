package com.xologood.q8pad.api;


import com.xologood.q8pad.bean.Account;
import com.xologood.q8pad.bean.BarCodeLogList;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.Company;
import com.xologood.q8pad.bean.FirstUser;
import com.xologood.q8pad.bean.Invoice;
import com.xologood.q8pad.bean.InvoicingDetailVo;
import com.xologood.q8pad.bean.InvoicingBean;
import com.xologood.q8pad.bean.Product;
import com.xologood.q8pad.bean.ProductBatch;
import com.xologood.q8pad.bean.ProportionConversion;
import com.xologood.q8pad.bean.ReportInfo;
import com.xologood.q8pad.bean.ReportInv;
import com.xologood.q8pad.bean.ReturnGoodsResponse;
import com.xologood.q8pad.bean.StandardUnit;
import com.xologood.q8pad.bean.SupplierBean;
import com.xologood.q8pad.bean.Version;
import com.xologood.q8pad.bean.Warehouse;
import com.xologood.q8pad.bean.bean;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * des:ApiService
 * Created by xsf
 * on 2016.06.15:47
 */
public interface ApiService {

    /**
     * 登录
     *
     * @param loginName 用户名
     * @param passWord  密码
     * @return
     */
    @GET(ApiConstants.ACCOUNT_PADLOGINON)
    Observable<BaseResponse<Account>> login(@Query("LoginName") String loginName, @Query("PassWord") String passWord);


    /**
     * 测试用
     *
     * @return
     */
    @GET(ApiConstants.BEAN)
    Observable<bean> getBean();

    /**
     * 保存入库/出库主表
     *
     * @param options
     * @return
     */
    @GET(ApiConstants.INVOICING_INSERTINV)
    Observable<BaseResponse<InvoicingBean>> insertInv(@QueryMap Map<String, String> options);

    /**
     * 保存入库/出库主表(查询单据的接口)
     * @return
     */
    @GET(ApiConstants.INVOICING_INVOICING)
    Observable<BaseResponse<InvoicingBean>> Invoicing(@Query("SysKey") String SysKey,
                                                      @Query("InvNumber") String InvNumber);

    //宾氏-入库保存
    @GET(ApiConstants.INVOICING_INSERTINVSUPPLIER)
    Observable<BaseResponse<InvoicingBean>> insertInvSupplier(@QueryMap Map<String, String> options);

    /**
     * 获取单位比例
     * @param id        产品id
     * @param Bunit     单位id
     * @param count     预计数量
     * @return
     */
    @GET(ApiConstants.PROPORTION_GET_PROPORTIONCONVERSION)
    Observable<BaseResponse<ProportionConversion>> GetProportionConversion(@Query("id") String id,
                                                                           @Query("Bunit") String Bunit,
                                                                           @Query("count") String count
    );

    /**
     * 获取单位比例
     * @param id        产品id
     * @param Bunit     单位id
     * @param count     预计数量
     * @return
     */
    @GET(ApiConstants.PROPORTION_GET_PROPORTIONCONVERSION)
    Observable<BaseResponse<String>> GetProportionConversionString(@Query("id") String id,
                                                                   @Query("Bunit") String Bunit,
                                                                   @Query("count") String count
    );

    /**
     * 获取仓库列表
     *
     * @param ComKey 系统key
     * @param IsUse  是否启用
     * @return
     */
    @GET(ApiConstants.WAREHOUSE_GETLIST)
    Observable<BaseResponse<List<Warehouse>>> GetWareHouseList(@Query("ComKey") String ComKey, @Query("IsUse") String IsUse);

    /**
     * 获取供应商列表
     * @return
     */
    @GET(ApiConstants.INVOICING_GETSUPPLIERLIST)
    Observable<BaseResponse<List<SupplierBean>>> GetSupplierBeanList(@QueryMap Map<String, String> options);

    /**
     * 获取产品列表
     *
     * @param SysKey 系统key
     * @param IsUse  是否启用
     * @return
     */
    @GET(ApiConstants.PRODUCT_GET_PRODUCT_LIST)
    Observable<BaseResponse<List<Product>>> GetProductList(@Query("SysKey") String SysKey, @Query("IsUse") String IsUse);

    /**
     * 获取产品批次
     * @param ProductId  产品id
     * @return
     */
    @GET(ApiConstants.PRODUCT_GET_PRODUCT_BATCH_BY_PRODUCTID)
    Observable<BaseResponse<List<ProductBatch>>> GetProductBatchByProductId(@Query("Id") String ProductId);

    /**
     * 根据产品id获取单位
     * @param ProductId  产品Id
     * @param SysKey    系统唯一标识
     * @return
     */
    @GET(ApiConstants.PRODUCT_GET_STANDARDUNIT_BY_PRODUCTID)
    Observable<BaseResponse<List<StandardUnit>>> GetStandardUnitByProductId(@Query("Id") String ProductId,
                                                                            @Query("SysKey") String SysKey);
    /**
     * 添加批次
     * @param BatchNO     批次名
     * @param ProductId   产品id
     * @param SysKey      系统唯一标识
     * @param ProductDate 当前时间
     * @param CreationBy  创建人
     * @return
     */
   @GET(ApiConstants.PRODUCT_INSERT_PRODUCT_BATCH)
    Observable<BaseResponse<String>> InsertProductBatch(@Query("BatchNO") String BatchNO,
                                                        @Query("Product") String ProductId,
                                                        @Query("SysKey") String SysKey,
                                                        @Query("ProductDate") String ProductDate,
                                                        @Query("CreationBy") String CreationBy);


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
    @GET(ApiConstants.INVOICING_GET_INVOICINGDETAIL)
    Observable<BaseResponse<InvoicingDetailVo>> GetInvoiceDetail(@Query("Id") String Id,
                                                                 @Query("InvId") String InvId,
                                                                 @Query("ProductId") String ProductId,
                                                                 @Query("Batch") String Batch,
                                                                 @Query("ActualQty") String ActualQty,
                                                                 @Query("ExpectedQty") String ExpectedQty,
                                                                 @Query("ComKey") String ComKey,
                                                                 @Query("SysKey") String SysKey
    );

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
    @GET(ApiConstants.INVOICING_INSERT_DETAIL)
    Observable<BaseResponse<InvoicingDetailVo>> InsertInvoiceDetail(@Query("Id") String Id,
                                                                    @Query("InvId") String InvId,
                                                                    @Query("ProductId") String ProductId,
                                                                    @Query("Batch") String Batch,
                                                                    @Query("ActualQty") String ActualQty,
                                                                    @Query("ExpectedQty") String ExpectedQty,
                                                                    @Query("ComKey") String ComKey,
                                                                    @Query("SysKey") String SysKey);
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
    @GET(ApiConstants.INVOICING_UPDATE_INVOICINGDETAIL)
    Observable<BaseResponse<InvoicingDetailVo>> UpdateInvoiceDetail(@Query("Id") String Id,
                                                                    @Query("InvId") String InvId,
                                                                    @Query("ProductId") String ProductId,
                                                                    @Query("Batch") String Batch,
                                                                    @Query("ActualQty") String ActualQty,
                                                                    @Query("ExpectedQty") String ExpectedQty,
                                                                    @Query("ComKey") String ComKey,
                                                                    @Query("SysKey") String SysKey
    );


    /**
     * 获取机构列表
     * @param ComKey    机构唯一标识
     * @param CType     类型 写死”2”
     * @return
     */
    @GET(ApiConstants.COMPANY_GET_ALLCOPLIST)
    Observable<BaseResponse<List<Company>>> GetAllCompList(@Query("ComKey") String ComKey, @Query("CType") String CType);


    /**
     * 获取单据列表
     * @param ComKey
     * @param InvState
     * @param CheckUserId
     * @param InvType
     * @return
     */
    @GET(ApiConstants.INVOICING_INVLIST)
    Observable<BaseResponse<List<InvoicingBean>>> GetInvoiceInvlist(@Query("ComKey") String ComKey,
                                                                    @Query("InvState") String InvState,
                                                                    @Query("CheckUserId") String CheckUserId,
                                                                    @Query("InvType") String InvType
    );

    /**
     * 获取单据信息
     * @param invId
     * @return
     */
    @GET(ApiConstants.INVOICING_INVMSG)
    Observable<BaseResponse<Invoice>> GetInvoice(@Query("invId") String invId);

    /**
     * 获取单据明细
     * @param invId
     * @return
     */
    @GET(ApiConstants.INVOICING_INVDETAIL)
    Observable<BaseResponse<Invoice>> GetInvoicingDetail(@Query("invId") String invId);

    /**
     * 确认提交
     * @param invId     入库单号id
     * @param userId    用户id
     * @param userName  用户名
     * @return
     */
    @GET(ApiConstants.INVOICINT_COMPLETESAVE)
    Observable<BaseResponse<String>> CompleteSave(@Query("invId") String invId,
                                                  @Query("userId") String userId,
                                                  @Query("userName") String userName);

    /**
     * 入库上传
     */
    @POST(ApiConstants.NEWSCANBARCODE_NEWORDINARY_IN_SCANBARCODE)
    Observable<BaseResponse<BarCodeLogList>> GetScanBarCodeList(@Query("BarCodes") String BarCodes,
                                                                @Query("InvId") String InvId,
                                                                @Query("InvDetailId") String InvDetailId,
                                                                @Query("ProductId") String ProductId,
                                                                @Query("Batch") String Batch,
                                                                @Query("ComKey") String ComKey,
                                                                @Query("ComName") String ComName,
                                                                @Query("SysKey") String SysKey,
                                                                @Query("ReceivingWarehouseId") String ReceivingWarehouseId);

    /**
     * 宾氏—入库上传
     */
    @POST(ApiConstants.NEWSCANBARCODE_NEW_BIN_SHI_SCANBARCODE)
    Observable<BaseResponse<BarCodeLogList>> GetScanBarCodeListBinShi(@Query("BarCodes") String BarCodes,
                                                                      @Query("InvId") String InvId,
                                                                      @Query("InvDetailId") String InvDetailId,
                                                                      @Query("ProductId") String ProductId,
                                                                      @Query("Batch") String Batch,
                                                                      @Query("ComKey") String ComKey,
                                                                      @Query("ComName") String ComName,
                                                                      @Query("SysKey") String SysKey,
                                                                      @Query("ReceivingWarehouseId") String ReceivingWarehouseId);

    /**
     * 出库上传
     * @return
     */
   @POST(ApiConstants.NEWSCANBARCODE_NEWORDINARY_OUT_SCANBARCODE)
    Observable<BaseResponse<BarCodeLogList>> GetScanBarCodeList(@Query("BarCodes") String BarCodes,
                                                                @Query("InvId") String InvId,
                                                                @Query("InvNumber") String InvNumber,
                                                                @Query("InvType") String InvType,
                                                                @Query("InvGet") String InvGet,
                                                                @Query("InvReMark") String InvReMark,
                                                                @Query("InvBy") String InvBy,
                                                                @Query("InvByName") String InvByName,
                                                                @Query("CodeType") String CodeType,
                                                                @Query("InvState") String InvState,
                                                                @Query("InvDate") String InvDate,
                                                                @Query("LastUpdateBy") String LastUpdateBy,
                                                                @Query("LastUpdateByName") String LastUpdateByName,
                                                                @Query("LastUpdateDate") String LastUpdateDate,
                                                                @Query("ComKey") String ComKey,
                                                                @Query("ComName") String ComName,
                                                                @Query("SysKey") String SysKey,
                                                                @Query("CheckMemo") String CheckMemo,
                                                                @Query("ReceivingComKey") String ReceivingComKey,
                                                                @Query("ReceivingComName") String ReceivingComName,
                                                                @Query("ReceivingWarehouseId") String ReceivingWarehouseId,
                                                                @Query("ReceivingWarehouseName") String ReceivingWarehouseName,
                                                                @Query("CheckedParty") String CheckedParty,
                                                                @Query("sysKeyBase") String sysKeyBase
   );

    /**
     *宾氏—出库上传
     */
    @POST(ApiConstants.NEWSCANBARCODE_NEW_BIN_SHI_OUT_SCANBARCODE)
    Observable<BaseResponse<BarCodeLogList>> GetScanBarCodeListBinShi(@Query("BarCodes") String BarCodes,
                                                                      @Query("InvId") String InvId,
                                                                      @Query("InvNumber") String InvNumber,
                                                                      @Query("InvType") String InvType,
                                                                      @Query("InvGet") String InvGet,
                                                                      @Query("InvReMark") String InvReMark,
                                                                      @Query("InvBy") String InvBy,
                                                                      @Query("InvByName") String InvByName,
                                                                      @Query("CodeType") String CodeType,
                                                                      @Query("InvState") String InvState,
                                                                      @Query("InvDate") String InvDate,
                                                                      @Query("LastUpdateBy") String LastUpdateBy,
                                                                      @Query("LastUpdateByName") String LastUpdateByName,
                                                                      @Query("LastUpdateDate") String LastUpdateDate,
                                                                      @Query("ComKey") String ComKey,
                                                                      @Query("ComName") String ComName,
                                                                      @Query("SysKey") String SysKey,
                                                                      @Query("CheckMemo") String CheckMemo,
                                                                      @Query("ReceivingComKey") String ReceivingComKey,
                                                                      @Query("ReceivingComName") String ReceivingComName,
                                                                      @Query("ReceivingWarehouseId") String ReceivingWarehouseId,
                                                                      @Query("ReceivingWarehouseName") String ReceivingWarehouseName,
                                                                      @Query("CheckedParty") String CheckedParty,
                                                                      @Query("sysKeyBase") String sysKeyBase
    );

    /**
     * 替换
     * @param code
     * @return
     */
    @GET(ApiConstants.INVOICING_REPLACECODE)
    Observable<BaseResponse<String>> InvoicingReplaceCode(@Query("code") String code, @Query("CreationBy") String CreationBy);

    //快捷出库
    /**
     * 1.快捷出库列表
     * @param Syskey
     * @param Comkey
     * @return
     */
    @GET(ApiConstants.INVOICING_QUICKINVLIST)
    Observable<BaseResponse<List<InvoicingBean>>> InvoicingQuickInvList(@Query("Syskey") String Syskey,
                                                                        @Query("Comkey") String Comkey
    );

    /**
     * 获取用户
     * @param Comkey
     * @return
     */
    @GET(ApiConstants.USER_GET_FIRSTUSER_BY_COMKE)
   Observable<BaseResponse<FirstUser>> GetFirstUserByComKey(@Query("Comkey") String Comkey,
                                                            @Query("sysKeyBase") String sysKeyBase);

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
    @GET(ApiConstants.NEWSCANBARCODE_NEWQUICKSCANBARCODE)
    Observable<BaseResponse<BarCodeLogList>> NewQuickScanBarCode(@Query("BarCodes") String BarCodes,
                                                                 @Query("InvNumber") String InvNumber,
                                                                 @Query("InvType") String InvType,
                                                                 @Query("InvGet") String InvGet,
                                                                 @Query("InvReMark") String InvReMark,
                                                                 @Query("InvBy") String InvBy,
                                                                 @Query("InvByName") String InvByName,
                                                                 @Query("CodeType") String CodeType,
                                                                 @Query("InvState") String InvState,
                                                                 @Query("InvDate") String InvDate,
                                                                 @Query("LastUpdateBy") String LastUpdaeBy,
                                                                 @Query("LastUpdateByName") String LastUpdateByName,
                                                                 @Query("LastUpdateDate") String LastUpdateDate,
                                                                 @Query("ComKey") String ComKey,
                                                                 @Query("ComName") String ComName,
                                                                 @Query("SysKey") String SysKey,
                                                                 @Query("CheckMemo") String CheckMemo,
                                                                 @Query("ReceivingComKey") String ReceivingComKey,
                                                                 @Query("ReceivingComName") String ReceivingComName,
                                                                 @Query("ReceivingWarehouseId") String ReceivingWarehouseId,
                                                                 @Query("ReceivingWarehouseName") String ReceivingWarehouseName,
                                                                 @Query("CheckedParty") String CheckedParty,
                                                                 @Query("sysKeyBase") String sysKeyBase,
                                                                 @Query("ProductId") String ProductId,
                                                                 @Query("Batch") String Batch
    );


    /**
     * 快捷出库之获取出库单据明细
     *
     * @param
     * @return
     */
   /* @GET(ApiConstants.FAST_OUT_INVOICING_INVDETAIL)
    Observable<BaseResponse<Invoice>> GetInvoicingDetail(@Query("invId") String invId,@Query("sysKeyBase") String sysKeyBase);*/

    /**
     * 退货默认
     *
     * @return
     */
    @GET(ApiConstants.RETURNGOODS_DEFAULT)
    Observable<BaseResponse<ReturnGoodsResponse>> ReturnGoodsDefault(@Query("InvBy") String InvBy,
                                                                     @Query("InvByName") String InvByName,
                                                                     @Query("InvNumber") String InvNumber,
                                                                     @Query("InvDate") String InvDate,
                                                                     @Query("InvReMark") String InvReMark,
                                                                     @Query("InvGet") String InvGet,
                                                                     @Query("InvType") String InvType,
                                                                     @Query("CheckedParty") String CheckedParty,
                                                                     @Query("InvState") String InvState,
                                                                     @Query("CodeType") String CodeType,
                                                                     @Query("ReceivingComKey") String ReceivingComKey,
                                                                     @Query("ReceivingComName") String ReceivingComName,
                                                                     @Query("SysKey") String SysKey,
                                                                     @Query("ComKey") String ComKey,
                                                                     @Query("LastUpdateDate") String LastUpdateDate,
                                                                     @Query("LastUpdateBy") String LastUpdateBy,
                                                                     @Query("LastUpdateByName") String LastUpdateByName,
                                                                     @Query("CheckMemo") String CheckMemo,
                                                                     @Query("BarCode") String BarCode);

    /**
     * 退货
     * @return
     */
    @GET(ApiConstants.RETURNGOODS)
    Observable<BaseResponse<ReturnGoodsResponse>> ReturnGoods(@Query("InvBy") String InvBy,
                                                              @Query("InvByName") String InvByName,
                                                              @Query("InvNumber") String InvNumber,
                                                              @Query("InvDate") String InvDate,
                                                              @Query("InvReMark") String InvReMark,
                                                              @Query("InvGet") String InvGet,
                                                              @Query("InvType") String InvType,
                                                              @Query("CheckedParty") String CheckedParty,
                                                              @Query("InvState") String InvState,
                                                              @Query("CodeType") String CodeType,
                                                              @Query("ReceivingComKey") String ReceivingComKey,
                                                              @Query("ReceivingComName") String ReceivingComName,
                                                              @Query("SysKey") String SysKey,
                                                              @Query("ComKey") String ComKey,
                                                              @Query("LastUpdateDate") String LastUpdateDate,
                                                              @Query("LastUpdateBy") String LastUpdateBy,
                                                              @Query("LastUpdateByName") String LastUpdateByName,
                                                              @Query("CheckMemo") String CheckMemo,
                                                              @Query("BarCode") String BarCode
    );

    /**
     * 条码作废
     * @param code
     * @return
     */
    @GET(ApiConstants.INVOICING_ABOLISHCODE)
    Observable<BaseResponse<String>> InvoicingAbolishCode(@Query("code") String code);


    /**
     * 扫描条码数量
     * @param barcode
     * @return
     */
    @GET(ApiConstants.INVOICING_CHECKBARCODE)
    Observable<BaseResponse<String>> InvoicingCheckBarCode(@Query("barcode") String barcode);


    /**
     * 检查版本更新
     * @return
     */
    @GET(ApiConstants.CHECKVERSION)
    Observable<BaseResponse<Version>> CheckVersion();

    /**
     * 产品物流
     */
    @GET(ApiConstants.REPORT_GETPRODUCTDETAILBYBARCODE)
    Observable<BaseResponse<ReportInfo>> getProductDetailByBarcode(@Query("barCode") String barCode);

    /**
     * 产品物流列表
     */
    @GET(ApiConstants.REPORT_INVBYBARCODELIST)
    Observable<BaseResponse<ReportInv>> invByBarCodeList(@Query("SBarCode") String sBarCode);

    /**
     * 下载文件
     * @param fileUrl
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);



}

