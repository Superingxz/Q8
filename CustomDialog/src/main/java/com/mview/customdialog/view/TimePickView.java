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



public class TimePickView extends RelativeLayout implements OnClickListener {
	private Button btn_hour_add;
	private Button btn_hour_subtract;
	private Button btn_min_add;
	private Button btn_min_subtract;
	private Button cancel;
	private int currHour;
	private int currMin;
	private Calendar mCalendar = Calendar.getInstance();
	private Dialog mDialog;
	private PickerTimeListener mListener;
	private Button ok;
	private TextView title;
	private TextView txt_hour;
	private TextView txt_min;

	public TimePickView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		init(context, "提示");
	}

	public TimePickView(Context context, String str) {
		super(context);
		init(context, str);
	}

	private void closeDialog() {
		if (mDialog != null && mDialog.isShowing())
			mDialog.dismiss();
	}

	private int getCurrentMinute() {
		int i = mCalendar.get(Calendar.MINUTE);
		return i;
	}

	private void init(Context context, String str) {
		inflate(context, R.layout.time_picker_layout, this);
		initView(str);
		reset();
	}

	private void initView(String str) {
		title = (TextView) findViewById(R.id.title);
		txt_hour = (TextView) findViewById(R.id.txt_hour);
		txt_min = (TextView) findViewById(R.id.txt_min);
		title.setText(str);
		ok = (Button) findViewById(R.id.ok);
		cancel = (Button) findViewById(R.id.cancel);
		btn_hour_add = (Button) findViewById(R.id.btn_hour_add);
		btn_hour_subtract = (Button) findViewById(R.id.btn_hour_subtract);
		btn_min_add = (Button) findViewById(R.id.btn_min_add);
		btn_min_subtract = (Button) findViewById(R.id.btn_min_subtract);
		cancel.setOnClickListener(this);
		ok.setOnClickListener(this);
		btn_hour_add.setOnClickListener(this);
		btn_hour_subtract.setOnClickListener(this);
		btn_min_add.setOnClickListener(this);
		btn_min_subtract.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		int i = view.getId();
		if (i == R.id.txt_min) {
			refresh();

		} else if (i == R.id.btn_hour_add) {
			if (currHour == 23) {
				currHour = 0;
			} else {
				currHour++;
			}
			refresh();

		} else if (i == R.id.btn_hour_subtract) {
			if (currHour == 0) {
				currHour = 23;
			} else
				currHour--;
			refresh();

		} else if (i == R.id.btn_min_add) {
			if (currMin == 59) {
				currMin = 0;
			} else
				currMin++;
			refresh();

		} else if (i == R.id.btn_min_subtract) {
			if (currMin == 0) {
				currMin = 59;
			} else
				currMin--;

			refresh();

		} else if (i == R.id.ok) {
			if (mListener != null)
				mListener.onTimeChanged(currHour, currMin);
			closeDialog();

		} else if (i == R.id.cancel) {
			closeDialog();

		}

	}

	public void refresh() {
		mCalendar.setTimeInMillis(System.currentTimeMillis());
		if (currMin == 0)
			txt_min.setText("00");
		else
			txt_min.setText(currMin + "");

		txt_hour.setText(currHour + "");
	}

	public void reset() {
		if (1 + mCalendar.get(Calendar.HOUR_OF_DAY) == 24)
			currHour = 0;
		else
			currHour = 1 + mCalendar.get(Calendar.HOUR_OF_DAY);
		currMin = getCurrentMinute();
		refresh();

	}

	public void setTimePickerListener(PickerTimeListener pickerTimeListener,
			Dialog dialog) {
		mListener = pickerTimeListener;
		mDialog = dialog;
	}

	public static abstract interface PickerTimeListener {
		public abstract void onTimeChanged(int hour, int min);
	}
}