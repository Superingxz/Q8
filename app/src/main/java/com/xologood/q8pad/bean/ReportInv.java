package com.xologood.q8pad.bean;

import java.util.List;

/**
 * Created by wei on 2017/3/13.
 */

public class ReportInv {

    /**
     * Total : 2
     * Records : [{"ProductId":52,"Batch":145,"ActualQty":1,"ExpectedQty":20,"CurrentQty":0,"TypeName":"产品入库","StateName":"已扫描","CompanyNo":"18916316813856","Province":null,"City":null,"Area":null,"InvId":2986,"Pid":0,"InvBy":2,"InvByName":"系统管理员","InvNumber":"A00000217022211062416848","InvDate":"2017-02-22 11:06:36","InvReMark":"暂无","InvGet":"在线web","InvType":1,"CheckedParty":false,"InvState":4,"InvIsStop":false,"CodeType":true,"StartScanDate":"2017-02-22 11:06:53","EndScanDate":"2017-02-22 11:07:09","CheckUserId":2,"CheckUserName":"系统管理员","CheckDate":"2017-02-22 11:06:42","ReceivingComKey":"vijxvry9vjthfa7a","ReceivingComName":"开发测试机构","ReceivingWarehouseId":"45","ReceivingWarehouseName":"测试_总仓库","CompleteDate":"2017-02-22 11:07:09","CompleteBy":2,"CompleteByName":"系统管理员","CompleteMeno":"单据已完成，无问题数据！","SysKey":"150623155902966stlt","ComKey":"vijxvry9vjthfa7a","ComName":"开发测试机构","LastUpdateDate":"2017-02-22 11:06:42","LastUpdateBy":2,"LastUpdateByName":"系统管理员","CheckMemo":null,"IsSign":null,"SignByName":null,"SignDate":null,"keyBase":null,"recorderBase":0,"versionBase":null,"projectNameBase":null,"sysKeyBase":null,"pageIndex":0,"pageSize":0},{"ProductId":52,"Batch":145,"ActualQty":1,"ExpectedQty":1,"CurrentQty":0,"TypeName":"产品出库","StateName":"待扫描","CompanyNo":"12119672","Province":null,"City":null,"Area":null,"InvId":2985,"Pid":0,"InvBy":2,"InvByName":"系统管理员","InvNumber":"L00000217022210552189116","InvDate":"2017-02-22 10:56:40","InvReMark":"暂无","InvGet":"在线web","InvType":2,"CheckedParty":true,"InvState":1,"InvIsStop":null,"CodeType":false,"StartScanDate":null,"EndScanDate":null,"CheckUserId":2,"CheckUserName":"系统管理员","CheckDate":"2017-02-22 10:57:30","ReceivingComKey":"eebgibgwze0qmrkw","ReceivingComName":"测试采集器app经销商","ReceivingWarehouseId":"45","ReceivingWarehouseName":"测试_总仓库","CompleteDate":"2017-02-22 10:57:30","CompleteBy":null,"CompleteByName":null,"CompleteMeno":null,"SysKey":"150623155902966stlt","ComKey":"vijxvry9vjthfa7a","ComName":"开发测试机构","LastUpdateDate":"2017-02-22 10:57:30","LastUpdateBy":2,"LastUpdateByName":"系统管理员","CheckMemo":null,"IsSign":null,"SignByName":null,"SignDate":null,"keyBase":null,"recorderBase":0,"versionBase":null,"projectNameBase":null,"sysKeyBase":null,"pageIndex":0,"pageSize":0}]
     */

    private int Total;
    private List<RecordsBean> Records;

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

    public List<RecordsBean> getRecords() {
        return Records;
    }

    public void setRecords(List<RecordsBean> Records) {
        this.Records = Records;
    }

    public static class RecordsBean {
        /**
         * ProductId : 52
         * Batch : 145
         * ActualQty : 1
         * ExpectedQty : 20
         * CurrentQty : 0
         * TypeName : 产品入库
         * StateName : 已扫描
         * CompanyNo : 18916316813856
         * Province : null
         * City : null
         * Area : null
         * InvId : 2986
         * Pid : 0
         * InvBy : 2
         * InvByName : 系统管理员
         * InvNumber : A00000217022211062416848
         * InvDate : 2017-02-22 11:06:36
         * InvReMark : 暂无
         * InvGet : 在线web
         * InvType : 1
         * CheckedParty : false
         * InvState : 4
         * InvIsStop : false
         * CodeType : true
         * StartScanDate : 2017-02-22 11:06:53
         * EndScanDate : 2017-02-22 11:07:09
         * CheckUserId : 2
         * CheckUserName : 系统管理员
         * CheckDate : 2017-02-22 11:06:42
         * ReceivingComKey : vijxvry9vjthfa7a
         * ReceivingComName : 开发测试机构
         * ReceivingWarehouseId : 45
         * ReceivingWarehouseName : 测试_总仓库
         * CompleteDate : 2017-02-22 11:07:09
         * CompleteBy : 2
         * CompleteByName : 系统管理员
         * CompleteMeno : 单据已完成，无问题数据！
         * SysKey : 150623155902966stlt
         * ComKey : vijxvry9vjthfa7a
         * ComName : 开发测试机构
         * LastUpdateDate : 2017-02-22 11:06:42
         * LastUpdateBy : 2
         * LastUpdateByName : 系统管理员
         * CheckMemo : null
         * IsSign : null
         * SignByName : null
         * SignDate : null
         * keyBase : null
         * recorderBase : 0
         * versionBase : null
         * projectNameBase : null
         * sysKeyBase : null
         * pageIndex : 0
         * pageSize : 0
         */

        private int ProductId;
        private int Batch;
        private int ActualQty;
        private int ExpectedQty;
        private int CurrentQty;
        private String TypeName;
        private String StateName;
        private String CompanyNo;
        private String Province;
        private String City;
        private String Area;
        private int InvId;
        private int Pid;
        private int InvBy;
        private String InvByName;
        private String InvNumber;
        private String InvDate;
        private String InvReMark;
        private String InvGet;
        private int InvType;
        private boolean CheckedParty;
        private int InvState;
        private boolean InvIsStop;
        private boolean CodeType;
        private String StartScanDate;
        private String EndScanDate;
        private int CheckUserId;
        private String CheckUserName;
        private String CheckDate;
        private String ReceivingComKey;
        private String ReceivingComName;
        private String ReceivingWarehouseId;
        private String ReceivingWarehouseName;
        private String CompleteDate;
        private int CompleteBy;
        private String CompleteByName;
        private String CompleteMeno;
        private String SysKey;
        private String ComKey;
        private String ComName;
        private String LastUpdateDate;
        private int LastUpdateBy;
        private String LastUpdateByName;
        private Object CheckMemo;
        private Object IsSign;
        private Object SignByName;
        private Object SignDate;
        private Object keyBase;
        private int recorderBase;
        private Object versionBase;
        private Object projectNameBase;
        private Object sysKeyBase;
        private int pageIndex;
        private int pageSize;

        public int getProductId() {
            return ProductId;
        }

        public void setProductId(int ProductId) {
            this.ProductId = ProductId;
        }

        public int getBatch() {
            return Batch;
        }

        public void setBatch(int Batch) {
            this.Batch = Batch;
        }

        public int getActualQty() {
            return ActualQty;
        }

        public void setActualQty(int ActualQty) {
            this.ActualQty = ActualQty;
        }

        public int getExpectedQty() {
            return ExpectedQty;
        }

        public void setExpectedQty(int ExpectedQty) {
            this.ExpectedQty = ExpectedQty;
        }

        public int getCurrentQty() {
            return CurrentQty;
        }

        public void setCurrentQty(int CurrentQty) {
            this.CurrentQty = CurrentQty;
        }

        public String getTypeName() {
            return TypeName;
        }

        public void setTypeName(String TypeName) {
            this.TypeName = TypeName;
        }

        public String getStateName() {
            return StateName;
        }

        public void setStateName(String StateName) {
            this.StateName = StateName;
        }

        public String getCompanyNo() {
            return CompanyNo;
        }

        public void setCompanyNo(String CompanyNo) {
            this.CompanyNo = CompanyNo;
        }

        public int getInvId() {
            return InvId;
        }

        public void setInvId(int InvId) {
            this.InvId = InvId;
        }

        public String getProvince() {
            return Province;
        }

        public void setProvince(String province) {
            Province = province;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String city) {
            City = city;
        }

        public String getArea() {
            return Area;
        }

        public void setArea(String area) {
            Area = area;
        }

        public int getPid() {
            return Pid;
        }

        public void setPid(int Pid) {
            this.Pid = Pid;
        }

        public int getInvBy() {
            return InvBy;
        }

        public void setInvBy(int InvBy) {
            this.InvBy = InvBy;
        }

        public String getInvByName() {
            return InvByName;
        }

        public void setInvByName(String InvByName) {
            this.InvByName = InvByName;
        }

        public String getInvNumber() {
            return InvNumber;
        }

        public void setInvNumber(String InvNumber) {
            this.InvNumber = InvNumber;
        }

        public String getInvDate() {
            return InvDate;
        }

        public void setInvDate(String InvDate) {
            this.InvDate = InvDate;
        }

        public String getInvReMark() {
            return InvReMark;
        }

        public void setInvReMark(String InvReMark) {
            this.InvReMark = InvReMark;
        }

        public String getInvGet() {
            return InvGet;
        }

        public void setInvGet(String InvGet) {
            this.InvGet = InvGet;
        }

        public int getInvType() {
            return InvType;
        }

        public void setInvType(int InvType) {
            this.InvType = InvType;
        }

        public boolean isCheckedParty() {
            return CheckedParty;
        }

        public void setCheckedParty(boolean CheckedParty) {
            this.CheckedParty = CheckedParty;
        }

        public int getInvState() {
            return InvState;
        }

        public void setInvState(int InvState) {
            this.InvState = InvState;
        }

        public boolean isInvIsStop() {
            return InvIsStop;
        }

        public void setInvIsStop(boolean InvIsStop) {
            this.InvIsStop = InvIsStop;
        }

        public boolean isCodeType() {
            return CodeType;
        }

        public void setCodeType(boolean CodeType) {
            this.CodeType = CodeType;
        }

        public String getStartScanDate() {
            return StartScanDate;
        }

        public void setStartScanDate(String StartScanDate) {
            this.StartScanDate = StartScanDate;
        }

        public String getEndScanDate() {
            return EndScanDate;
        }

        public void setEndScanDate(String EndScanDate) {
            this.EndScanDate = EndScanDate;
        }

        public int getCheckUserId() {
            return CheckUserId;
        }

        public void setCheckUserId(int CheckUserId) {
            this.CheckUserId = CheckUserId;
        }

        public String getCheckUserName() {
            return CheckUserName;
        }

        public void setCheckUserName(String CheckUserName) {
            this.CheckUserName = CheckUserName;
        }

        public String getCheckDate() {
            return CheckDate;
        }

        public void setCheckDate(String CheckDate) {
            this.CheckDate = CheckDate;
        }

        public String getReceivingComKey() {
            return ReceivingComKey;
        }

        public void setReceivingComKey(String ReceivingComKey) {
            this.ReceivingComKey = ReceivingComKey;
        }

        public String getReceivingComName() {
            return ReceivingComName;
        }

        public void setReceivingComName(String ReceivingComName) {
            this.ReceivingComName = ReceivingComName;
        }

        public String getReceivingWarehouseId() {
            return ReceivingWarehouseId;
        }

        public void setReceivingWarehouseId(String ReceivingWarehouseId) {
            this.ReceivingWarehouseId = ReceivingWarehouseId;
        }

        public String getReceivingWarehouseName() {
            return ReceivingWarehouseName;
        }

        public void setReceivingWarehouseName(String ReceivingWarehouseName) {
            this.ReceivingWarehouseName = ReceivingWarehouseName;
        }

        public String getCompleteDate() {
            return CompleteDate;
        }

        public void setCompleteDate(String CompleteDate) {
            this.CompleteDate = CompleteDate;
        }

        public int getCompleteBy() {
            return CompleteBy;
        }

        public void setCompleteBy(int CompleteBy) {
            this.CompleteBy = CompleteBy;
        }

        public String getCompleteByName() {
            return CompleteByName;
        }

        public void setCompleteByName(String CompleteByName) {
            this.CompleteByName = CompleteByName;
        }

        public String getCompleteMeno() {
            return CompleteMeno;
        }

        public void setCompleteMeno(String CompleteMeno) {
            this.CompleteMeno = CompleteMeno;
        }

        public String getSysKey() {
            return SysKey;
        }

        public void setSysKey(String SysKey) {
            this.SysKey = SysKey;
        }

        public String getComKey() {
            return ComKey;
        }

        public void setComKey(String ComKey) {
            this.ComKey = ComKey;
        }

        public String getComName() {
            return ComName;
        }

        public void setComName(String ComName) {
            this.ComName = ComName;
        }

        public String getLastUpdateDate() {
            return LastUpdateDate;
        }

        public void setLastUpdateDate(String LastUpdateDate) {
            this.LastUpdateDate = LastUpdateDate;
        }

        public int getLastUpdateBy() {
            return LastUpdateBy;
        }

        public void setLastUpdateBy(int LastUpdateBy) {
            this.LastUpdateBy = LastUpdateBy;
        }

        public String getLastUpdateByName() {
            return LastUpdateByName;
        }

        public void setLastUpdateByName(String LastUpdateByName) {
            this.LastUpdateByName = LastUpdateByName;
        }

        public Object getCheckMemo() {
            return CheckMemo;
        }

        public void setCheckMemo(Object CheckMemo) {
            this.CheckMemo = CheckMemo;
        }

        public Object getIsSign() {
            return IsSign;
        }

        public void setIsSign(Object IsSign) {
            this.IsSign = IsSign;
        }

        public Object getSignByName() {
            return SignByName;
        }

        public void setSignByName(Object SignByName) {
            this.SignByName = SignByName;
        }

        public Object getSignDate() {
            return SignDate;
        }

        public void setSignDate(Object SignDate) {
            this.SignDate = SignDate;
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
}
