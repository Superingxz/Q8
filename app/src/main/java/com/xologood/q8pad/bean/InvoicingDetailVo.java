package com.xologood.q8pad.bean;

/**
 * Created by Administrator on 2017/1/7.
 */

public class InvoicingDetailVo {

    /**
     * Id : 976
     * InvId : 1094
     * ProductId : 1
     * Batch : 37
     * ActualQty : 0
     * ExpectedQty : 1
     * ComKey : vijxvry9vjthfa7a
     * SysKey : 150623155902966stlt
     * keyBase : D29606034B9F9ADC6AC59B1A3665210E
     * recorderBase : 2
     * versionBase : 4.0.3.7
     * projectNameBase : 千里码Q8云战略合作平台-PDA
     * sysKeyBase : 150623155902966stlt
     * pageIndex : 0
     * pageSize : 0
     */

    private int Id;
    private String InvId;
    private int ProductId;
    private String Batch;
    private String ActualQty;
    private String ExpectedQty;
    private String ComKey;
    private String SysKey;
    private String keyBase;
    private int recorderBase;
    private String versionBase;
    private String projectNameBase;
    private String sysKeyBase;
    private int pageIndex;
    private int pageSize;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getInvId() {
        return InvId;
    }

    public void setInvId(String invId) {
        InvId = invId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getBatch() {
        return Batch;
    }

    public void setBatch(String batch) {
        Batch = batch;
    }

    public String getActualQty() {
        return ActualQty;
    }

    public void setActualQty(String actualQty) {
        ActualQty = actualQty;
    }

    public String getExpectedQty() {
        return ExpectedQty;
    }

    public void setExpectedQty(String expectedQty) {
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

    public String getKeyBase() {
        return keyBase;
    }

    public void setKeyBase(String keyBase) {
        this.keyBase = keyBase;
    }

    public int getRecorderBase() {
        return recorderBase;
    }

    public void setRecorderBase(int recorderBase) {
        this.recorderBase = recorderBase;
    }

    public String getVersionBase() {
        return versionBase;
    }

    public void setVersionBase(String versionBase) {
        this.versionBase = versionBase;
    }

    public String getProjectNameBase() {
        return projectNameBase;
    }

    public void setProjectNameBase(String projectNameBase) {
        this.projectNameBase = projectNameBase;
    }

    public String getSysKeyBase() {
        return sysKeyBase;
    }

    public void setSysKeyBase(String sysKeyBase) {
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
