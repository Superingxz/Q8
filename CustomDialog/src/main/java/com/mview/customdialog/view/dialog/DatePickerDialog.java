package com.mview.customdialog.view.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.mview.customdialog.view.dialog.utils.CornerUtils;
import com.mview.customdialog.view.dialog.base.BaseDateDialog;


/**
 * 通用  获取日期  Dialog
 * Created by Administrator on 2015/7/19.
 */
public class DatePickerDialog extends BaseDateDialog<DatePickerDialog> {

	/** title underline */
    private View v_line_title;
    /** vertical line between btns */
    private View v_line_vertical;
    /** horizontal line above btns */
    private View v_line_horizontal;
    /** title underline color(标题下划线颜色) */
    private int titleLineColor = Color.parseColor("#61AEDC");
    /** title underline height(标题下划线高度) */
    private float titleLineHeight_DP = 1f;
    /** btn divider line color(对话框之间的分割线颜色(水平+垂直)) */
    private int dividerColor = Color.parseColor("#DCDCDC");

    public static final int STYLE_ONE = 0;
    public static final int STYLE_TWO = 1;
    private int style = STYLE_ONE;

    public DatePickerDialog(Context context) {
        super(context);

        /** default value*/
        titleTextColor = Color.parseColor("#61AEDC");
        titleTextSize_SP = 22f;

        leftBtnTextColor = Color.parseColor("#8a000000");
        rightBtnTextColor = Color.parseColor("#8a000000");
        /** default value*/
    }
    
    @Override
    public View onCreateView() {
        /** title */
        tv_title.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        ll_container.addView(tv_title);

        /** title underline */
        v_line_title = new View(context);
        ll_container.addView(v_line_title);
        
        dp_picker.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
        			LinearLayout.LayoutParams.WRAP_CONTENT));
        ll_container.addView(dp_picker);
        
        v_line_horizontal = new View(context);
        v_line_horizontal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
        ll_container.addView(v_line_horizontal);

        /** btns */
        tv_btn_left.setLayoutParams(new LinearLayout.LayoutParams(0, dp2px(45), 1));
        ll_btns.addView(tv_btn_left);

        v_line_vertical = new View(context);
        v_line_vertical.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
        ll_btns.addView(v_line_vertical);

        tv_btn_right.setLayoutParams(new LinearLayout.LayoutParams(0, dp2px(45), 1));
        ll_btns.addView(tv_btn_right);

        ll_container.addView(ll_btns);

        return ll_container;
    }

    @Override
    public void setUiBeforShow() {
        super.setUiBeforShow();

        /** title */
        if (style == STYLE_ONE) {
            tv_title.setMinHeight(dp2px(48));
            tv_title.setGravity(Gravity.CENTER_VERTICAL);
            tv_title.setPadding(dp2px(15), dp2px(5), dp2px(0), dp2px(5));
            tv_title.setVisibility(isTitleShow ? View.VISIBLE : View.GONE);
        } else if (style == STYLE_TWO) {
            tv_title.setGravity(Gravity.CENTER);
            tv_title.setPadding(dp2px(0), dp2px(15), dp2px(0), dp2px(15));
        }

        /** title underline */
        v_line_title.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp2px(titleLineHeight_DP)));
        v_line_title.setBackgroundColor(titleLineColor);
        v_line_title.setVisibility(isTitleShow ? View.VISIBLE : View.GONE);

        dp_picker.setPadding(dp2px(0), dp2px(10), dp2px(0), dp2px(10));

        /** btns */
        v_line_horizontal.setBackgroundColor(dividerColor);
        v_line_vertical.setBackgroundColor(dividerColor);

        if (btnNum == 1) {
            tv_btn_left.setVisibility(View.GONE);
            v_line_vertical.setVisibility(View.GONE);
        }

        /**set background color and corner radius */
        float radius = dp2px(cornerRadius_DP);
        ll_container.setBackgroundDrawable(CornerUtils.cornerDrawable(bgColor, radius));
        tv_btn_left.setBackgroundDrawable(CornerUtils.btnSelector(radius, bgColor, btnPressColor, 0));
        tv_btn_right.setBackgroundDrawable(CornerUtils.btnSelector(radius, bgColor, btnPressColor, 1));
    }

    // --->属性设置

    /** set style(设置style) */
    public DatePickerDialog style(int style) {
        this.style = style;
        return this;
    }

    /** set title underline color(设置标题下划线颜色) */
    public DatePickerDialog titleLineColor(int titleLineColor) {
        this.titleLineColor = titleLineColor;
        return this;
    }

    /** set title underline height(设置标题下划线高度) */
    public DatePickerDialog titleLineHeight(float titleLineHeight_DP) {
        this.titleLineHeight_DP = titleLineHeight_DP;
        return this;
    }

    /** set divider color between btns(设置btn分割线的颜色) */
    public DatePickerDialog dividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        return this;
    }
}
