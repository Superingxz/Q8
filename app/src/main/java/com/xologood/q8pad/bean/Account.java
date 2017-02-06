package com.xologood.q8pad.bean;

import java.util.List;

/**
 * Created by xiao on 2016/12/21 0021.
 */
public class Account {


    /**
     * userMsg : {"UserId":6390,"LoginName":"huishuxiong","Password":"e10adc3949ba59abbe56e057f20f883e","UserName":"系统管理员","HeadPhoto":"","Sex":"男","Tel":"","Addres":"","EMail":"","MsgCode":null,"CreationDate":"2016-06-22 10:08:05","UserIsUse":true,"ComKey":"1nkw0szikpqxzjqf","CType":243,"CompanyIsUse":true,"CompanyName":"灰树熊企业营销管理平台","CompanyTypeName":"广州总公司","CompanyOrderBy":0,"CreationBy":6390,"SysKey":"160622100802754nwql","SysName":"灰树熊企业营销管理平台","SystemEndDate":"2020-06-22 10:01:22","SystemUseStatus":"已收费","PCompanyName":null,"PCompanyValue":null,"RoleId":6529,"RolePid":0,"RoleName":"系统管理角色","KidCompanyIds":"0,102310,102600,102723,102723,102724,102791,102934,102953,102957,102967,102971,102980,103152,103153,103212,103212,103617,103621,103674,103677,103685,104010,104022,104420,105007,105152,105332,105504,105675,105869,106540,116627,116784,116786,116788,116802,116805,116809,116810,116811,116812,117638,117646,117690,117742,117762,117802,117818,117819,117838,117839,117892,117912,117929,117948,117978,118082,118089,118118,118141,118162,118170,118175,118199,118225","DeptName":"系统管理部","DeptIsUse":true,"ModularIds":"1,2,3","PdaVersion":null,"WebManageVersion":null,"ComLevel":1}
     * userLogin : {"IsLoginOK":true,"loginMsg":"登录成功"}
     * compList : []
     * allComList : []
     * RoleMenuRule : [{"Id":8188,"RoleId":6529,"MenuId":183,"RuleIds":"0","keyBase":null,"recorderBase":0,"versionBase":null,"projectNameBase":null,"sysKeyBase":null,"pageIndex":0,"pageSize":0},null,{"Id":13676,"RoleId":6529,"MenuId":200,"RuleIds":"0","keyBase":null,"recorderBase":0,"versionBase":null,"projectNameBase":null,"sysKeyBase":null,"pageIndex":0,"pageSize":0},{"Id":13782,"RoleId":6529,"MenuId":270,"RuleIds":"0","keyBase":null,"recorderBase":0,"versionBase":null,"projectNameBase":null,"sysKeyBase":null,"pageIndex":0,"pageSize":0}]
     */

    private UserMsg userMsg;
    private UserLogin userLogin;
    private List<?> compList;
    private List<?> allComList;
    private List<RoleMenuRule> RoleMenuRule;

    public UserMsg getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(UserMsg userMsg) {
        this.userMsg = userMsg;
    }

    public UserLogin getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

    public List<?> getCompList() {
        return compList;
    }

    public void setCompList(List<?> compList) {
        this.compList = compList;
    }

    public List<?> getAllComList() {
        return allComList;
    }

    public void setAllComList(List<?> allComList) {
        this.allComList = allComList;
    }

    public List<RoleMenuRule> getRoleMenuRule() {
        return RoleMenuRule;
    }

    public void setRoleMenuRule(List<RoleMenuRule> RoleMenuRule) {
        this.RoleMenuRule = RoleMenuRule;
    }

    public static class UserMsg {
        /**
         * UserId : 6390
         * LoginName : huishuxiong
         * Password : e10adc3949ba59abbe56e057f20f883e
         * UserName : 系统管理员
         * HeadPhoto :
         * Sex : 男
         * Tel :
         * Addres :
         * EMail :
         * MsgCode : null
         * CreationDate : 2016-06-22 10:08:05
         * UserIsUse : true
         * ComKey : 1nkw0szikpqxzjqf
         * CType : 243
         * CompanyIsUse : true
         * CompanyName : 灰树熊企业营销管理平台
         * CompanyTypeName : 广州总公司
         * CompanyOrderBy : 0
         * CreationBy : 6390
         * SysKey : 160622100802754nwql
         * SysName : 灰树熊企业营销管理平台
         * SystemEndDate : 2020-06-22 10:01:22
         * SystemUseStatus : 已收费
         * PCompanyName : null
         * PCompanyValue : null
         * RoleId : 6529
         * RolePid : 0
         * RoleName : 系统管理角色
         * KidCompanyIds : 0,102310,102600,102723,102723,102724,102791,102934,102953,102957,102967,102971,102980,103152,103153,103212,103212,103617,103621,103674,103677,103685,104010,104022,104420,105007,105152,105332,105504,105675,105869,106540,116627,116784,116786,116788,116802,116805,116809,116810,116811,116812,117638,117646,117690,117742,117762,117802,117818,117819,117838,117839,117892,117912,117929,117948,117978,118082,118089,118118,118141,118162,118170,118175,118199,118225
         * DeptName : 系统管理部
         * DeptIsUse : true
         * ModularIds : 1,2,3
         * PdaVersion : null
         * WebManageVersion : null
         * ComLevel : 1
         */

        private int UserId;
        private String LoginName;
        private String Password;
        private String UserName;
        private String HeadPhoto;
        private String Sex;
        private String Tel;
        private String Addres;
        private String EMail;
        private Object MsgCode;
        private String CreationDate;
        private boolean UserIsUse;
        private String ComKey;
        private int CType;
        private boolean CompanyIsUse;
        private String CompanyName;
        private String CompanyTypeName;
        private int CompanyOrderBy;
        private int CreationBy;
        private String SysKey;
        private String SysName;
        private String SystemEndDate;
        private String SystemUseStatus;
        private Object PCompanyName;
        private Object PCompanyValue;
        private int RoleId;
        private int RolePid;
        private String RoleName;
        private String KidCompanyIds;
        private String DeptName;
        private boolean DeptIsUse;
        private String ModularIds;
        private Object PdaVersion;
        private Object WebManageVersion;
        private int ComLevel;

        public int getUserId() {
            return UserId;
        }

        public void setUserId(int UserId) {
            this.UserId = UserId;
        }

        public String getLoginName() {
            return LoginName;
        }

        public void setLoginName(String LoginName) {
            this.LoginName = LoginName;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String Password) {
            this.Password = Password;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getHeadPhoto() {
            return HeadPhoto;
        }

        public void setHeadPhoto(String HeadPhoto) {
            this.HeadPhoto = HeadPhoto;
        }

        public String getSex() {
            return Sex;
        }

        public void setSex(String Sex) {
            this.Sex = Sex;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String Tel) {
            this.Tel = Tel;
        }

        public String getAddres() {
            return Addres;
        }

        public void setAddres(String Addres) {
            this.Addres = Addres;
        }

        public String getEMail() {
            return EMail;
        }

        public void setEMail(String EMail) {
            this.EMail = EMail;
        }

        public Object getMsgCode() {
            return MsgCode;
        }

        public void setMsgCode(Object MsgCode) {
            this.MsgCode = MsgCode;
        }

        public String getCreationDate() {
            return CreationDate;
        }

        public void setCreationDate(String CreationDate) {
            this.CreationDate = CreationDate;
        }

        public boolean isUserIsUse() {
            return UserIsUse;
        }

        public void setUserIsUse(boolean UserIsUse) {
            this.UserIsUse = UserIsUse;
        }

        public String getComKey() {
            return ComKey;
        }

        public void setComKey(String ComKey) {
            this.ComKey = ComKey;
        }

        public int getCType() {
            return CType;
        }

        public void setCType(int CType) {
            this.CType = CType;
        }

        public boolean isCompanyIsUse() {
            return CompanyIsUse;
        }

        public void setCompanyIsUse(boolean CompanyIsUse) {
            this.CompanyIsUse = CompanyIsUse;
        }

        public String getCompanyName() {
            return CompanyName;
        }

        public void setCompanyName(String CompanyName) {
            this.CompanyName = CompanyName;
        }

        public String getCompanyTypeName() {
            return CompanyTypeName;
        }

        public void setCompanyTypeName(String CompanyTypeName) {
            this.CompanyTypeName = CompanyTypeName;
        }

        public int getCompanyOrderBy() {
            return CompanyOrderBy;
        }

        public void setCompanyOrderBy(int CompanyOrderBy) {
            this.CompanyOrderBy = CompanyOrderBy;
        }

        public int getCreationBy() {
            return CreationBy;
        }

        public void setCreationBy(int CreationBy) {
            this.CreationBy = CreationBy;
        }

        public String getSysKey() {
            return SysKey;
        }

        public void setSysKey(String SysKey) {
            this.SysKey = SysKey;
        }

        public String getSysName() {
            return SysName;
        }

        public void setSysName(String SysName) {
            this.SysName = SysName;
        }

        public String getSystemEndDate() {
            return SystemEndDate;
        }

        public void setSystemEndDate(String SystemEndDate) {
            this.SystemEndDate = SystemEndDate;
        }

        public String getSystemUseStatus() {
            return SystemUseStatus;
        }

        public void setSystemUseStatus(String SystemUseStatus) {
            this.SystemUseStatus = SystemUseStatus;
        }

        public Object getPCompanyName() {
            return PCompanyName;
        }

        public void setPCompanyName(Object PCompanyName) {
            this.PCompanyName = PCompanyName;
        }

        public Object getPCompanyValue() {
            return PCompanyValue;
        }

        public void setPCompanyValue(Object PCompanyValue) {
            this.PCompanyValue = PCompanyValue;
        }

        public int getRoleId() {
            return RoleId;
        }

        public void setRoleId(int RoleId) {
            this.RoleId = RoleId;
        }

        public int getRolePid() {
            return RolePid;
        }

        public void setRolePid(int RolePid) {
            this.RolePid = RolePid;
        }

        public String getRoleName() {
            return RoleName;
        }

        public void setRoleName(String RoleName) {
            this.RoleName = RoleName;
        }

        public String getKidCompanyIds() {
            return KidCompanyIds;
        }

        public void setKidCompanyIds(String KidCompanyIds) {
            this.KidCompanyIds = KidCompanyIds;
        }

        public String getDeptName() {
            return DeptName;
        }

        public void setDeptName(String DeptName) {
            this.DeptName = DeptName;
        }

        public boolean isDeptIsUse() {
            return DeptIsUse;
        }

        public void setDeptIsUse(boolean DeptIsUse) {
            this.DeptIsUse = DeptIsUse;
        }

        public String getModularIds() {
            return ModularIds;
        }

        public void setModularIds(String ModularIds) {
            this.ModularIds = ModularIds;
        }

        public Object getPdaVersion() {
            return PdaVersion;
        }

        public void setPdaVersion(Object PdaVersion) {
            this.PdaVersion = PdaVersion;
        }

        public Object getWebManageVersion() {
            return WebManageVersion;
        }

        public void setWebManageVersion(Object WebManageVersion) {
            this.WebManageVersion = WebManageVersion;
        }

        public int getComLevel() {
            return ComLevel;
        }

        public void setComLevel(int ComLevel) {
            this.ComLevel = ComLevel;
        }
    }

    public static class UserLogin {
        /**
         * IsLoginOK : true
         * loginMsg : 登录成功
         */

        private boolean IsLoginOK;
        private String loginMsg;

        public boolean isIsLoginOK() {
            return IsLoginOK;
        }

        public void setIsLoginOK(boolean IsLoginOK) {
            this.IsLoginOK = IsLoginOK;
        }

        public String getLoginMsg() {
            return loginMsg;
        }

        public void setLoginMsg(String loginMsg) {
            this.loginMsg = loginMsg;
        }
    }

    public static class RoleMenuRule {
        /**
         * Id : 8188
         * RoleId : 6529
         * MenuId : 183
         * RuleIds : 0
         * keyBase : null
         * recorderBase : 0
         * versionBase : null
         * projectNameBase : null
         * sysKeyBase : null
         * pageIndex : 0
         * pageSize : 0
         */

        private int Id;
        private int RoleId;
        private int MenuId;
        private String RuleIds;
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

        public int getRoleId() {
            return RoleId;
        }

        public void setRoleId(int RoleId) {
            this.RoleId = RoleId;
        }

        public int getMenuId() {
            return MenuId;
        }

        public void setMenuId(int MenuId) {
            this.MenuId = MenuId;
        }

        public String getRuleIds() {
            return RuleIds;
        }

        public void setRuleIds(String RuleIds) {
            this.RuleIds = RuleIds;
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
