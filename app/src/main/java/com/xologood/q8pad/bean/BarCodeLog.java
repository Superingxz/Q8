package com.xologood.q8pad.bean;

/**
 * Created by xiao on 2017/1/9 0009.
 */

public class BarCodeLog {

    /**
     * Id : 0
     * BarCode : 27618582669
     * IsOk : false
     * Remark : 失败，此条码不存在
     * CreationDate : 0001-01-01 08:00:00
     * CreationBy : 0
     * SysKey : null
     * keyBase : null
     * recorderBase : 0
     * versionBase : null
     * projectNameBase : null
     * sysKeyBase : null
     * pageIndex : 0
     * pageSize : 0
     */

    private int Id;
    private String BarCode;
    private boolean IsOk;
    private String Remark;
    private String CreationDate;
    private int CreationBy;
    private Object SysKey;
    private Object keyBase;
    private int recorderBase;
    private Object versionBase;
    private Object projectNameBase;
    private Object sysKeyBase;
    private int pageIndex;
    private int pageSize;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String BarCode) {
        this.BarCode = BarCode;
    }

    public boolean isIsOk() {
        return IsOk;
    }

    public void setIsOk(boolean IsOk) {
        this.IsOk = IsOk;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String CreationDate) {
        this.CreationDate = CreationDate;
    }

    public int getCreationBy() {
        return CreationBy;
    }

    public void setCreationBy(int CreationBy) {
        this.CreationBy = CreationBy;
    }

    public Object getSysKey() {
        return SysKey;
    }

    public void setSysKey(Object SysKey) {
        this.SysKey = SysKey;
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
