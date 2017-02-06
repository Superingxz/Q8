package com.xologood.q8pad.bean;

/**
 * Created by Administrator on 2017/1/3.
 */

public class StandardUnit {


    /**
     * Id : 40
     * StandardUnitName : 包
     * Type : 2
     * SysKey : 160622100802754nwql
     * IsUse : true
     * keyBase : null
     * recorderBase : 0
     * versionBase : null
     * projectNameBase : null
     * sysKeyBase : null
     * pageIndex : 0
     * pageSize : 0
     */

    private int Id;
    private String StandardUnitName;
    private int Type;
    private String SysKey;
    private boolean IsUse;
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

    public String getStandardUnitName() {
        return StandardUnitName;
    }

    public void setStandardUnitName(String StandardUnitName) {
        this.StandardUnitName = StandardUnitName;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public String getSysKey() {
        return SysKey;
    }

    public void setSysKey(String SysKey) {
        this.SysKey = SysKey;
    }

    public boolean isIsUse() {
        return IsUse;
    }

    public void setIsUse(boolean IsUse) {
        this.IsUse = IsUse;
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
