package com.mview.customdialog.view.dialog.use;

/**
 * Created by xiao on 2016/12/25 0025.
 */


import android.content.Context;
import android.graphics.Color;

import com.mview.customdialog.view.dialog.DateAndTimePickerDialog;
import com.mview.customdialog.view.dialog.DatePickerDialog;
import com.mview.customdialog.view.dialog.NormalDialog;
import com.mview.customdialog.view.dialog.TimePickerDialog;
import com.mview.customdialog.view.dialog.listener.OnBtnSelectDateAndTimeClickL;
import com.mview.customdialog.view.dialog.listener.OnBtnSelectTimeClickL;
import com.mview.customdialog.view.dialog.listener.OnBtnSelectDateClickL;

import java.util.Calendar;


/**
 * 通用 日期 时间 选择 Dialog 工具类
 * @author Administrator
 */
public class QpadDateTimeDialogUtils {
    /**
     * 通用选择	时间	Dialog
     * @param context
     * @param time
     * @param timeInterval
     * @param onBtnClickLs
     */
    public static void showTimePickerDialog(Context context, Calendar time, String [] timeInterval,
                                            OnBtnSelectTimeClickL... onBtnClickLs) {


        final TimePickerDialog TimePicker_Dialog = new TimePickerDialog(context);
        TimePicker_Dialog.title("设置时间")
                .style(NormalDialog.STYLE_TWO)
                .setTimeAndHour(time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE))
                .setTimeInterval(timeInterval)
                .btnText("取消","确定")
                .btnTextColor(Color.parseColor("#383838"), Color.parseColor("#383838"))
                .show();
        TimePicker_Dialog.setCanceledOnTouchOutside(false);
        TimePicker_Dialog.setOnBtnClickL(onBtnClickLs);
    }


    /**
     * 通用选择	日期	Dialog
     * @param context
     * @param day
     * @param onBtnClickLs
     */
    public static void showDatePickerDialog(Context context, Calendar time,
                                            OnBtnSelectDateClickL... onBtnClickLs) {

        final DatePickerDialog DatePicker_Dialog = new DatePickerDialog(context);
        DatePicker_Dialog.title("设置日期")
                .style(NormalDialog.STYLE_TWO)
                .setYearMonthDay(time.get(Calendar.YEAR), time.get(Calendar.MONTH), time.get(Calendar.DAY_OF_MONTH))
                .btnText("取消","确定")
                .btnTextColor(Color.parseColor("#383838"), Color.parseColor("#383838"))
                .show();
        DatePicker_Dialog.setCanceledOnTouchOutside(false);
        DatePicker_Dialog.setOnBtnClickL(onBtnClickLs);
    }

    /**
     * 通用选择	日期 和 时间	Dialog
     * @param context
     * @param time
     * @param timeInterval
     * @param day
     * @param onBtnClickLs
     */
    public static void showDateAndTimePickerDialog(Context context, Calendar time, String [] timeInterval,
                                                   OnBtnSelectDateAndTimeClickL... onBtnClickLs) {

        final DateAndTimePickerDialog DatePicker_Dialog = new DateAndTimePickerDialog(context);
        DatePicker_Dialog.title("设置日期与时间")
                .style(NormalDialog.STYLE_TWO)
                .setYearMonthDay(time.get(Calendar.YEAR), time.get(Calendar.MONTH), time.get(Calendar.DAY_OF_MONTH))
                .setTimeAndHour(time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE))
                .btnText("取消","确定")
                .btnTextColor(Color.parseColor("#383838"), Color.parseColor("#383838"))
                .show();
        DatePicker_Dialog.setCanceledOnTouchOutside(false);
        DatePicker_Dialog.setOnBtnClickL(onBtnClickLs);
    }
}
