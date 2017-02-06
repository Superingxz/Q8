package com.mview.customdialog.view.dialog.entity;

import java.io.Serializable;

public class SearchGeneraItem implements Serializable {

	private static final long serialVersionUID = -1885427302018459660L;
	
	//审批考勤   查询条件
 	private String billType;			// 单据类型
	private String startTime;			// 开始时间	2015-11-25 08:30
	private String endTime;				// 结束时间	2015-11-25 08:30
	private String theDepartment;		// 所属部门
	
	//机器查询   查询条件
 	private String atmJqbh;				// 机器编号
	private String atmNum;				// ATM号码	
	private String atmJqlb;				// 机器类别	
	private String atmSsdq;				// 所属大区
	private String atmSsqy;				// 所属区域
	private String atmSsyh;				// 所属银行
	private String atmSsfwz;			// 所属服务站
	private String atmJqgsr;			// 机器归属人
	private String atmJqzt;				// 机器状态
	
	//服务站查询   查询条件
 	private String fwzSsdq;				// 所属大区
	private String fwzSsqy;				// 所属区域	
	private String fwzName;				// 服务站名	

	//员工定位查询   查询条件
 	private String ygYhxm;				// 用户姓名
	private String ygYhzh;				// 用户账号	
	private String ygSsbm;				// 所属部门	
	private String ygCxrq;				// 查询日期	
	private String ygKssj;				// 开始时间
	private String ygJssj;				// 结束时间
	
	//登记出入库查询   查询条件
	private String qrCrkdjdh;			// 出入库登记单号
	
	//报修物料查询   查询条件
	private String qrBxwldh;			// 报修物料单号
	private String qrBxwlzt;			// 报修物料状态
	
	//发货物料查询   查询条件
	private String qrFhwldh;			// 发货物料单号
	
	//收货物料查询   查询条件
	private String qrShwldh;			// 收货物料单号
	
	
	
	public String getQrShwldh() {
		return qrShwldh;
	}
	public void setQrShwldh(String qrShwldh) {
		this.qrShwldh = qrShwldh;
	}
	public String getQrFhwldh() {
		return qrFhwldh;
	}
	public void setQrFhwldh(String qrFhwldh) {
		this.qrFhwldh = qrFhwldh;
	}
	public String getQrBxwldh() {
		return qrBxwldh;
	}
	public void setQrBxwldh(String qrBxwldh) {
		this.qrBxwldh = qrBxwldh;
	}
	public String getQrBxwlzt() {
		return qrBxwlzt;
	}
	public void setQrBxwlzt(String qrBxwlzt) {
		this.qrBxwlzt = qrBxwlzt;
	}
	public String getQrCrkdjdh() {
		return qrCrkdjdh;
	}
	public void setQrCrkdjdh(String qrCrkdjdh) {
		this.qrCrkdjdh = qrCrkdjdh;
	}
	public String getYgYhxm() {
		return ygYhxm;
	}
	public void setYgYhxm(String ygYhxm) {
		this.ygYhxm = ygYhxm;
	}
	public String getYgYhzh() {
		return ygYhzh;
	}
	public void setYgYhzh(String ygYhzh) {
		this.ygYhzh = ygYhzh;
	}
	public String getYgSsbm() {
		return ygSsbm;
	}
	public void setYgSsbm(String ygSsbm) {
		this.ygSsbm = ygSsbm;
	}
	public String getYgCxrq() {
		return ygCxrq;
	}
	public void setYgCxrq(String ygCxrq) {
		this.ygCxrq = ygCxrq;
	}
	public String getYgKssj() {
		return ygKssj;
	}
	public void setYgKssj(String ygKssj) {
		this.ygKssj = ygKssj;
	}
	public String getYgJssj() {
		return ygJssj;
	}
	public void setYgJssj(String ygJssj) {
		this.ygJssj = ygJssj;
	}
	public String getFwzSsdq() {
		return fwzSsdq;
	}
	public void setFwzSsdq(String fwzSsdq) {
		this.fwzSsdq = fwzSsdq;
	}
	public String getFwzSsqy() {
		return fwzSsqy;
	}
	public void setFwzSsqy(String fwzSsqy) {
		this.fwzSsqy = fwzSsqy;
	}
	public String getFwzName() {
		return fwzName;
	}
	public void setFwzName(String fwzName) {
		this.fwzName = fwzName;
	}
	public String getAtmJqzt() {
		return atmJqzt;
	}
	public void setAtmJqzt(String atmJqzt) {
		this.atmJqzt = atmJqzt;
	}
	public String getAtmJqbh() {
		return atmJqbh;
	}
	public void setAtmJqbh(String atmJqbh) {
		this.atmJqbh = atmJqbh;
	}
	public String getAtmNum() {
		return atmNum;
	}
	public void setAtmNum(String atmNum) {
		this.atmNum = atmNum;
	}
	public String getAtmJqlb() {
		return atmJqlb;
	}
	public void setAtmJqlb(String atmJqlb) {
		this.atmJqlb = atmJqlb;
	}
	public String getAtmSsdq() {
		return atmSsdq;
	}
	public void setAtmSsdq(String atmSsdq) {
		this.atmSsdq = atmSsdq;
	}
	public String getAtmSsqy() {
		return atmSsqy;
	}
	public void setAtmSsqy(String atmSsqy) {
		this.atmSsqy = atmSsqy;
	}
	public String getAtmSsyh() {
		return atmSsyh;
	}
	public void setAtmSsyh(String atmSsyh) {
		this.atmSsyh = atmSsyh;
	}
	public String getAtmSsfwz() {
		return atmSsfwz;
	}
	public void setAtmSsfwz(String atmSsfwz) {
		this.atmSsfwz = atmSsfwz;
	}
	public String getAtmJqgsr() {
		return atmJqgsr;
	}
	public void setAtmJqgsr(String atmJqgsr) {
		this.atmJqgsr = atmJqgsr;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getTheDepartment() {
		return theDepartment;
	}
	public void setTheDepartment(String theDepartment) {
		this.theDepartment = theDepartment;
	}
	
	
	
}
