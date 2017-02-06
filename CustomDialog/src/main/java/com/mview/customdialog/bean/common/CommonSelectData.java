package com.mview.customdialog.bean.common;

import java.io.Serializable;

/**
 * 通用的Select选择数据
 * @author 王定波
 *
 */
public class CommonSelectData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5183852249343815258L;
	private String value;
	private String text;
	private String textcolor;
	private int image;
	private Object obj;
	
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public String getTextcolor() {
		return textcolor;
	}
	public void setTextcolor(String textcolor) {
		this.textcolor = textcolor;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if(obj==null)
		return getText();
		else return obj.toString();
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	public CommonSelectData() {
	}
	
	public CommonSelectData(String text, String value) {
		super();
		this.text = text;
		this.value = value;
	}
	
	
}
