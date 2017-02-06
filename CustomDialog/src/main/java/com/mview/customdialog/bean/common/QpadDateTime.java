package com.mview.customdialog.bean.common;

import android.annotation.SuppressLint;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 通用时间选择器
 * @author 王定波
 *
 */
public class QpadDateTime implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -859553043188302340L;
	public static final String KEY = "QpadDateTime";
	private final String PATTERN = "yyyy-MM-dd HH:mm";
	private String day = "";
	private int estimate = 0;
	private String hour = "";
	private String minute = "";
	private String month = "";
	private String year = "";

	private String format(int paramInt) {
		Object[] arrayOfObject = new Object[1];
		arrayOfObject[0] = Integer.valueOf(paramInt);
		return String.format("%02d", arrayOfObject);
	}

	private void parseDate(Calendar calendar) {
		year = format(calendar.get(Calendar.YEAR));
		month = format(1 + calendar.get(Calendar.MARCH));
		day = format(calendar.get(Calendar.DAY_OF_MONTH));
		hour = format(calendar.get(Calendar.HOUR_OF_DAY));
		minute = format(calendar.get(Calendar.MINUTE));
	}

	public void parseDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		parseDate(calendar);
	}

	// 是否当前
	public boolean isImmediately() {
		return estimate < 1;
	}

	public QpadDateTime addHour(int paramInt) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(toDate());
		localCalendar.add(Calendar.HOUR_OF_DAY, paramInt);
		parseDate(localCalendar);
		return this;
	}

	public QpadDateTime addMinute(int paramInt) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(toDate());
		localCalendar.add(Calendar.MINUTE, paramInt);
		parseDate(localCalendar);
		return this;
	}

	public QpadDateTime initNow() {
		parseDate(Calendar.getInstance());
		estimate = 0;
		return this;
	}
	
	public QpadDateTime initNowPD(){
		year = format(Calendar.getInstance().get(Calendar.YEAR));
		month = format(1 + Calendar.getInstance().get(Calendar.MARCH));
		day = format(1 + Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		hour = "09";
		minute = "00";
		estimate = 0;
		return this;
	}

	@SuppressLint("SimpleDateFormat")
	public Date toDate() {
		Date date = new Date();
		try {
			Date date2 = new SimpleDateFormat(PATTERN).parse(toString());
			return date2;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public String toString() {
		return getYear() + ":" + getMonth() + ":" + getDay() + ":" + getHour()
				+ ":" + getMinute();
	}

	public String getDateString() {
		return getYear() + "-" + getMonth() + "-" + getDay();
	}

	public String getDateTimeString() {
		return getYear() + "-" + getMonth() + "-" + getDay() + " " + getHour()
				+ ":" + getMinute()+ ":00";
	}

	public QpadDateTime setDay(String paramString) {
		day = paramString;
		return this;
	}

	public QpadDateTime setEstimate(int paramInt) {
		estimate = paramInt;
		return this;
	}

	public QpadDateTime setHour(String paramString) {
		hour = paramString;
		return this;
	}

	public QpadDateTime setHourMin(String paramString) {
		String[] hm = paramString.split(":");
		setHour(hm[0]);
		setMinute(hm[1]);
		return this;
	}

	public QpadDateTime setMinute(String paramString) {
		minute = paramString;
		return this;
	}

	public QpadDateTime setMonth(String paramString) {
		month = paramString;
		return this;
	}

	public QpadDateTime setYear(String paramString) {
		year = paramString;
		return this;
	}

	public QpadDateTime doImmediately() {
		return setEstimate(0);
	}

	public String getDay() {
		return format(Integer.parseInt(day));
	}

	public int getEstimate() {
		return estimate;
	}

	public String getHour() {
		return format(Integer.parseInt(hour));
	}

	public String getMinute() {
		return format(Integer.parseInt(minute));
	}

	public String getMonth() {
		return format(Integer.parseInt(month));
	}

	public String getYear() {
		return year;
	}
}
