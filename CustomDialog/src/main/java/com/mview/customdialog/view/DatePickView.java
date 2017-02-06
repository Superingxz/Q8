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

public class DatePickView extends RelativeLayout implements OnClickListener,
		PickSetDate {
	private Button btn_date_add;
	private Button btn_date_subtract;
	private Button btn_month_add;
	private Button btn_month_subtract;
	private Button btn_year_add;
	private Button btn_year_subtract;
	private Calendar c = Calendar.getInstance();
	private Button cancel;
	private RelativeLayout date_layout;
	private int flag;
	private Dialog mDialog;
	private PickerDateListener mListener;
	private Button ok;
	private Date overflowDate = new Date();
	private TextView title;
	private TextView txt_date;
	private TextView txt_month;
	private TextView txt_year;

	public DatePickView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		init(context, "提示");
	}

	public DatePickView(Context context, String title) {
		super(context);
		init(context, title);
	}

	public DatePickView(Context context, String title, int pflag) {
		super(context);
		flag = pflag;
		init(context, title);
	}

	private void addDay(int paramInt) {
//		Date date = new Date(c.getTime().getTime());
		c.add(Calendar.DAY_OF_MONTH, paramInt);
//		if (overflowDate.after(c.getTime())) {
//			setDate(date);
//			return;
//		}
		show();
	}

	private void addMonth(int paramInt) {
//		Date date = new Date(c.getTime().getTime());
		c.add(Calendar.MONTH, paramInt);
//		if (overflowDate.after(c.getTime())) {
//			setDate(date);
//			return;
//		}
		show();
	}

	private void addYear(int paramInt) {
//		Date date = new Date(c.getTime().getTime());
		c.add(Calendar.YEAR, paramInt);
//		if (overflowDate.after(c.getTime())) {
//			setDate(date);
//			return;
//		}
		show();
	}

	private void closeDialog() {
		if (mDialog != null && mDialog.isShowing())
			mDialog.dismiss();
	}

	private void init(Context context, String title) {
		inflate(context, R.layout.date_picker_layout, this);
		initView(title);
		show();
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
		title.setText(paramString);
		if (flag < 0) {
			date_layout = (RelativeLayout) findViewById(R.id.date_layout);
			date_layout.setVisibility(View.GONE);
		}
		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);
		btn_year_add.setOnClickListener(this);
		btn_year_subtract.setOnClickListener(this);
		btn_month_add.setOnClickListener(this);
		btn_month_subtract.setOnClickListener(this);
		btn_date_add.setOnClickListener(this);
		btn_date_subtract.setOnClickListener(this);
	}

	private void show() {
		txt_year.setText(String.format("%02d", c.get(Calendar.YEAR)));
		txt_month.setText(String.format("%02d", c.get(Calendar.MONTH)+1));
		txt_date.setText(String.format("%02d", c.get(Calendar.DAY_OF_MONTH)));
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

		}

	}

	public void setDate(Date paramDate) {
		c.setTime(paramDate);
		show();
	}

	public void setDatePickerListener(
			PickerDateListener paramPickerDateListener, Dialog paramDialog) {
		mListener = paramPickerDateListener;
		mDialog = paramDialog;
	}

	public void setOverflow(Date paramDate) {
		overflowDate = paramDate;
	}

	public static abstract interface PickerDateListener {
		public abstract void onDateChanged(Date paramDate);
	}
}