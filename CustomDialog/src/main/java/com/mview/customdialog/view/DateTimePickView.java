package com.mview.customdialog.view;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mview.customdialog.R;

import java.util.Calendar;
import java.util.Date;



public class DateTimePickView extends RelativeLayout implements
		OnClickListener, PickSetDate {
	private Button btn_date_add;
	private Button btn_date_subtract;
	private Button btn_month_add;
	private Button btn_month_subtract;
	private Button btn_year_add;
	private Button btn_year_subtract;
	private Calendar c = Calendar.getInstance();
	private Button cancel;
	private Dialog mDialog;
	private PickerDateTimeListener mListener;
	private Button ok;
	private Date overflowDate = new Date();
	private TextView title;
	private TextView txt_date;
	private TextView txt_month;
	private TextView txt_year;
	private TextView txt_hour;
	private TextView txt_min;
	private Button btn_hour_add;
	private Button btn_hour_subtract;
	private Button btn_min_add;
	private Button btn_min_subtract;

	public DateTimePickView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		init(context, "提示");
	}

	public DateTimePickView(Context context, String title) {
		super(context);
		init(context, title);
	}
	
	public DateTimePickView(Context context, String title,String dataTypes) {
		super(context);
		init(context, title,dataTypes);
	}

	private void addDay(int paramInt) {

		c.add(Calendar.DAY_OF_MONTH, paramInt);
		show();
	}

	private void addMonth(int paramInt) {
		c.add(Calendar.MONTH, paramInt);
		show();
	}

	private void addYear(int paramInt) {
		c.add(Calendar.YEAR, paramInt);
		show();
	}

	private void addMin(int paramInt) {
		c.add(Calendar.MINUTE, paramInt);
		show();
	}

	private void addHour(int paramInt) {
		c.add(Calendar.HOUR_OF_DAY, paramInt);
		show();
	}

	private void closeDialog() {
		if (mDialog != null && mDialog.isShowing())
			mDialog.dismiss();
	}

	public void init(Context context, String title) {
		inflate(context, R.layout.datetime_picker_layout, this);
		initView(title);
		show();
		/*if(title.equals("新建工单")){
			show1();
		}else{	
			show();
		}*/
	}
	
	public void init(Context context, String title,String dataTypes){
		inflate(context, R.layout.datetime_picker_layout, this);
		initView(title);
		show1();
	}

	private void initView(String paramString) {
		title = (TextView) findViewById(R.id.title);
		txt_year = (TextView) findViewById(R.id.txt_year);
		txt_month = (TextView) findViewById(R.id.txt_month);
		txt_date = (TextView) findViewById(R.id.txt_date);
		ok = (Button) findViewById(R.id.ok);
		cancel = (Button) findViewById(R.id.cancel);
		btn_year_add = (Button) findViewById(R.id.btn_year_add);
		btn_year_subtract = (Button) findViewById(R.id.btn_year_subtract);
		btn_month_add = (Button) findViewById(R.id.btn_month_add);
		btn_month_subtract = (Button) findViewById(R.id.btn_month_subtract);
		btn_date_add = (Button) findViewById(R.id.btn_date_add);
		btn_date_subtract = (Button) findViewById(R.id.btn_date_subtract);

		txt_hour = (TextView) findViewById(R.id.txt_hour);
		txt_min = (TextView) findViewById(R.id.txt_min);
		btn_hour_add = (Button) findViewById(R.id.btn_hour_add);
		btn_hour_subtract = (Button) findViewById(R.id.btn_hour_subtract);
		btn_min_add = (Button) findViewById(R.id.btn_min_add);
		btn_min_subtract = (Button) findViewById(R.id.btn_min_subtract);

		title.setText(paramString);

		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);
		btn_year_add.setOnClickListener(this);
		btn_year_subtract.setOnClickListener(this);
		btn_month_add.setOnClickListener(this);
		btn_month_subtract.setOnClickListener(this);
		btn_date_add.setOnClickListener(this);
		btn_date_subtract.setOnClickListener(this);
		btn_hour_add.setOnClickListener(this);
		btn_hour_subtract.setOnClickListener(this);
		btn_min_add.setOnClickListener(this);
		btn_min_subtract.setOnClickListener(this);
	}

	private void show() {
		txt_year.setText(String.format("%02d", c.get(Calendar.YEAR)));
		txt_month.setText(String.format("%02d", c.get(Calendar.MONTH) + 1));
		txt_date.setText(String.format("%02d", c.get(Calendar.DAY_OF_MONTH)));
		txt_min.setText(String.format("%02d", c.get(Calendar.MINUTE)));
		txt_hour.setText(String.format("%02d", c.get(Calendar.HOUR_OF_DAY)));
	}
	
	private void show1(){
		txt_year.setText(String.format("%02d", c.get(Calendar.YEAR)));
		txt_month.setText(String.format("%02d", c.get(Calendar.MONTH) + 1));
		txt_date.setText(String.format("%02d", c.get(Calendar.DAY_OF_MONTH) + 1));
		txt_min.setText("00");
		txt_hour.setText("09");
	}

	@Override
	public void onClick(View paramView) {
		int i = paramView.getId();
		if (i == R.id.btn_year_add) {
			addYear(1);

		} else if (i == R.id.btn_year_subtract) {
			addYear(-1);

		} else if (i == R.id.btn_month_add) {
			addMonth(1);

		} else if (i == R.id.btn_month_subtract) {
			addMonth(-1);

		} else if (i == R.id.btn_date_add) {
			addDay(1);

		} else if (i == R.id.btn_date_subtract) {
			addDay(-1);

		} else if (i == R.id.ok) {
			if (mListener != null)
				mListener.onDateChanged(c.getTime());
			closeDialog();
			return;
		} else if (i == R.id.cancel) {
			closeDialog();

		} else if (i == R.id.btn_hour_add) {
			addHour(1);


		} else if (i == R.id.btn_hour_subtract) {
			addHour(-1);

		} else if (i == R.id.btn_min_add) {
			addMin(1);

		} else if (i == R.id.btn_min_subtract) {
			addMin(-1);

		}

	}

	public void setDate(Date paramDate) {
		c.setTime(paramDate);
		show();
	}

	public void setDatePickerListener(
			PickerDateTimeListener paramPickerDateListener, Dialog paramDialog) {
		mListener = paramPickerDateListener;
		mDialog = paramDialog;
	}

	public void setOverflow(Date paramDate) {
		overflowDate = paramDate;
	}

	public static abstract interface PickerDateTimeListener {
		public abstract void onDateChanged(Date date);
	}
}