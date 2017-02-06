package com.xologood.q8pad.bean;

/**
 * Created by Administrator on 17-1-6.
 */


/**
 * 入库单据明细返回对象
 */
public class InvoicingDetail {


    /**
     * ProductName : 测试数量C
     * ProductCode : 005
     * BatchNO : 11111
     * Warehousename : null
     * CreationDate : 2016-11-16 00:00:00
     * StandardUnitName : 罐
     * Price : 0
     * GiftSum : 0
     * Id : 986
     * InvId : 1101
     * ProductId : 5
     * Batch : 5
     * ActualQty : 0
     * ExpectedQty : 220
     * ComKey : vijxvry9vjthfa7a
     * SysKey : 150623155902966stlt
     * keyBase : null
     * recorderBase : 0
     * versionBase : null
     * projectNameBase : null
     * sysKeyBase : null
     * pageIndex : 0
     * pageSize : 0
     */

    private String ProductName;
    private String ProductCode;
    private String BatchNO;
    private Object Warehousename;
    private String CreationDate;
    private String StandardUnitName;
    private int Price;
    private int GiftSum;
    private int Id;
    private int InvId;
    private String ProductId;
    private int Batch;
    private int ActualQty;
    private int ExpectedQty;
    private String ComKey;
    private String SysKey;
    private Object keyBase;
    private int recorderBase;
    private Object versionBase;
    private Object projectNameBase;
    private Object sysKeyBase;
    private int pageIndex;
    private int pageSize;

    public InvoicingDetail() {
    }

    public InvoicingDetail(int Id,
                           int invId,
                           String productId,
                           String productName,
                           int Batch,
                           String produceBatch,
                           int expectedQty,
                           int actualQty,
                           String creationDate,
                           String standardUnit) {
        this.Id = Id;
        this.InvId = invId;
        this.ProductId = productId;
        this.ProductName = productName;
        this.BatchNO = produceBatch;
        this.Batch = Batch;
        this.ExpectedQty = expectedQty;
        this.ActualQty = actualQty;
        this.CreationDate = creationDate;
        this.StandardUnitName = standardUnit;

    }
    public InvoicingDetail(String productId,
                           String productName,
                           int expectedQty,
                           int actualQty,
                           String creationDate,
                           String standardUnit) {
        this.ProductId = productId;
        this.ProductName = productName;
        this.ExpectedQty = expectedQty;
        this.ActualQty = actualQty;
        this.CreationDate = creationDate;
        this.StandardUnitName = standardUnit;

    }



    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getBatchNO() {
        return BatchNO;
    }

    public void setBatchNO(String batchNO) {
        BatchNO = batchNO;
    }

    public Object getWarehousename() {
        return Warehousename;
    }

    public void setWarehousename(Object warehousename) {
        Warehousename = warehousename;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
    }

    public String getStandardUnitName() {
        return StandardUnitName;
    }

    public void setStandardUnitName(String standardUnitName) {
        StandardUnitName = standardUnitName;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getGiftSum() {
        return GiftSum;
    }

    public void setGiftSum(int giftSum) {
        GiftSum = giftSum;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getInvId() {
        return InvId;
    }

    public void setInvId(int invId) {
        InvId = invId;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public int getBatch() {
        return Batch;
    }

    public void setBatch(int batch) {
        Batch = batch;
    }

    public int getActualQty() {
        return ActualQty;
    }

    public void setActualQty(int actualQty) {
        ActualQty = actualQty;
    }

    public int getExpectedQty() {
        return ExpectedQty;
    }

    public void setExpectedQty(int expectedQty) {
        ExpectedQty = expectedQty;
    }

    public String getComKey() {
        return ComKey;
    }

    public void setComKey(String comKey) {
        ComKey = comKey;
    }

    public String getSysKey() {
        return SysKey;
    }

    public void setSysKey(String sysKey) {
        SysKey = sysKey;
    }

    public Object getKeyBase() {
        return keyBase;
    }

    public void setKeyBase(Object keyBase) {
        this.keyBase = keyBase;
    }

    public int getRecorderBase() {
        return recorderBase;
    }

    public void setRecorderBase(int recorderBase) {
        this.recorderBase = recorderBase;
    }

    public Object getVersionBase() {
        return versionBase;
    }

    public void setVersionBase(Object versionBase) {
        this.versionBase = versionBase;
    }

    public Object getProjectNameBase() {
        return projectNameBase;
    }

    public void setProjectNameBase(Object projectNameBase) {
        this.projectNameBase = projectNameBase;
    }

    public Object getSysKeyBase() {
        return sysKeyBase;
    }

    public void setSysKeyBase(Object sysKeyBase) {
        this.sysKeyBase = sysKeyBase;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
