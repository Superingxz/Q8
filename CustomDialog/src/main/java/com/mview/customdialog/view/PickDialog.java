package com.mview.customdialog.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.mview.customdialog.R;
import com.mview.customdialog.view.DatePickView.PickerDateListener;
import com.mview.customdialog.view.DateTimePickView.PickerDateTimeListener;

import java.util.Date;

public class PickDialog extends Dialog {
	public static final int NO_DATE = -1;
	private Context mContext;
	private View mView;
	private String dataTypes;

	public PickDialog(Context context, PickerDateListener pickerDateListener,
			String titlestr) {
		super(context, R.style.PickDialog);
		mContext = context;
		initDateView(pickerDateListener, titlestr);
		setHeight();
		setCancelable(false);
	}

	public PickDialog(Context context, PickerDateListener pickerDateListener,
			String titlestr, int paramInt) {
		super(context, R.style.PickDialog);
		mContext = context;
		initDateView(pickerDateListener, titlestr, paramInt);
		setHeight();
		setCancelable(false);
	}

	public PickDialog(Context context,
			PickerDateTimeListener pickerDateListener, String titlestr) {
		super(context, R.style.PickDialog);
		mContext = context;
		initDateView(pickerDateListener, titlestr);
		setHeight();
		setCancelable(false);
	}
	
	public PickDialog(Context context,
			PickerDateTimeListener pickerDateListener, String titlestr,String dataTypes){
		super(context, R.style.PickDialog);
		mContext = context;
		this.dataTypes = dataTypes;
		initDateView(pickerDateListener, titlestr,dataTypes);
		setHeight();
		setCancelable(false);
	}

	public PickDialog(Context context,
					  TimePickView.PickerTimeListener paramPickerTimeListener, String titlestr) {
		super(context, R.style.PickDialog);
		mContext = context;
		initTimeView(paramPickerTimeListener, titlestr);
		setHeight();
		setCancelable(false);
	}

	private void initDateView(PickerDateListener pickerDateListener,
			String titlestr) {
		mView = new DatePickView(mContext, titlestr);
		((DatePickView) mView).setDatePickerListener(pickerDateListener, this);
		setContentView(mView);
	}

	private void initDateView(PickerDateListener pickerDateListener,
			String titlestr, int paramInt) {
		mView = new DatePickView(mContext, titlestr, paramInt);
		((DatePickView) mView).setDatePickerListener(pickerDateListener, this);
		setContentView(mView);
	}

	private void initDateView(PickerDateTimeListener pickerDateListener,
			String titlestr) {
		mView = new DateTimePickView(mContext, titlestr);
		((DateTimePickView) mView).setDatePickerListener(pickerDateListener,
				this);
		setContentView(mView);
	}
	
	private void initDateView(PickerDateTimeListener pickerDateListener,
			String titlestr,String dataTypes) {
		mView = new DateTimePickView(mContext, titlestr,dataTypes);
		((DateTimePickView) mView).setDatePickerListener(pickerDateListener,
				this);
		setContentView(mView);
	}

	private void initTimeView(TimePickView.PickerTimeListener pickerTimeListener,
			String titlestr) {
		mView = new TimePickView(mContext, titlestr);
		((TimePickView) mView).setTimePickerListener(pickerTimeListener, this);
		setContentView(mView);
	}

	private void setHeight() {
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.height = (int) (0.5D * display.getHeight());
		getWindow().setAttributes(params);
	}

	public void setOverflowAndStar(Date data1, Date data2) {
		((PickSetDate) mView).setOverflow(data1);
		((PickSetDate) mView).setDate(data2);
	}
}
