package com.xologood.q8pad.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 17-1-3.
 */

public class Invoice implements Serializable{

    /**
     * Invoicing : {"InvId":1101,"Pid":0,"InvBy":2,"InvByName":"系统管理员","InvNumber":"A00000216120811065200039","InvDate":"2016-12-08 11:07:10","InvReMark":"暂无","InvGet":"在线PDA","InvType":1,"CheckedParty":false,"InvState":1,"InvIsStop":null,"CodeType":true,"StartScanDate":null,"EndScanDate":null,"CheckUserId":2,"CheckUserName":"系统管理员","CheckDate":"2016-12-08 11:07:29","ReceivingComKey":"vijxvry9vjthfa7a","ReceivingComName":"开发测试机构","ReceivingWarehouseId":"45","ReceivingWarehouseName":"测试_总仓库","CompleteDate":null,"CompleteBy":null,"CompleteByName":null,"CompleteMeno":null,"SysKey":"150623155902966stlt","ComKey":"vijxvry9vjthfa7a","ComName":"开发测试机构","LastUpdateDate":"2016-12-08 11:07:10","LastUpdateBy":2,"LastUpdateByName":"系统管理员","CheckMemo":"暂无","IsSign":null,"SignByName":null,"SignDate":null,"keyBase":null,"recorderBase":0,"versionBase":null,"projectNameBase":null,"sysKeyBase":"150623155902966stlt","pageIndex":0,"pageSize":0}
     * Recomname : null
     * Warahousename : null
     * stateName : 待扫描
     * EmpName : null
     * InvoicingDetail : [{"ProductName":"测试数量C","ProductCode":"005","BatchNO":"11111","Warehousename":null,"CreationDate":"2016-11-16 00:00:00","StandardUnitName":"罐","Price":0,"GiftSum":0,"Id":986,"InvId":1101,"ProductId":5,"Batch":5,"ActualQty":0,"ExpectedQty":220,"ComKey":"vijxvry9vjthfa7a","SysKey":"150623155902966stlt","keyBase":null,"recorderBase":0,"versionBase":null,"projectNameBase":null,"sysKeyBase":null,"pageIndex":0,"pageSize":0}]
     * keyBase : null
     * recorderBase : 0
     * versionBase : null
     * projectNameBase : null
     * sysKeyBase : null
     * pageIndex : 0
     * pageSize : 0
     */

    private InvoicingBean Invoicing;
    private Object Recomname;
    private Object Warahousename;
    private String stateName;
    private Object EmpName;
    private Object keyBase;
    private int recorderBase;
    private Object versionBase;
    private Object projectNameBase;
    private Object sysKeyBase;
    private int pageIndex;
    private int pageSize;
    @SerializedName("InvoicingDetail")
    private List<InvoicingDetail> invoicingDetailList;

    public InvoicingBean getInvoicing() {
        return Invoicing;
    }

    public void setInvoicing(InvoicingBean Invoicing) {
        this.Invoicing = Invoicing;
    }

    public Object getRecomname() {
        return Recomname;
    }

    public void setRecomname(Object Recomname) {
        this.Recomname = Recomname;
    }

    public Object getWarahousename() {
        return Warahousename;
    }

    public void setWarahousename(Object Warahousename) {
        this.Warahousename = Warahousename;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Object getEmpName() {
        return EmpName;
    }

    public void setEmpName(Object EmpName) {
        this.EmpName = EmpName;
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

    public List<InvoicingDetail> getInvoicingDetail() {
        return invoicingDetailList;
    }

    public void setInvoicingDetail(List<InvoicingDetail> invoicingDetailList) {
        this.invoicingDetailList = invoicingDetailList;
    }



}
