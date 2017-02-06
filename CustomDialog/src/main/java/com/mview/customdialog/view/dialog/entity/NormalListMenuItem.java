package com.mview.customdialog.view.dialog.entity;

import java.io.Serializable;

public class NormalListMenuItem implements Serializable{
	
	private static final long serialVersionUID = 8921160025073727323L;
	
	public String resName;
	public String resId;
	
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	
	
}
