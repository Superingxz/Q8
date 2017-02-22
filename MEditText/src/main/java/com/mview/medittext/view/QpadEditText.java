package com.mview.medittext.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mview.customdialog.bean.common.QpadDateTime;
import com.mview.customdialog.view.DateTimePickView;
import com.mview.customdialog.view.PickDialog;
import com.mview.customdialog.view.calendar.CalendarDay;
import com.mview.customdialog.view.datatype.DataTypes;
import com.mview.customdialog.view.dialog.KTAlertDialog;
import com.mview.customdialog.view.dialog.NormalListDialog;
import com.mview.customdialog.view.dialog.listener.OnBtnSelectDateAndTimeClickL;
import com.mview.customdialog.view.dialog.listener.OnBtnSelectDateClickL;
import com.mview.customdialog.view.dialog.listener.OnBtnSelectTimeClickL;
import com.mview.customdialog.view.dialog.listener.OnOperItemClickL;
import com.mview.customdialog.view.dialog.use.QpadDateTimeDialogUtils;
import com.mview.medittext.R;
import com.mview.medittext.bean.common.CommonSelectData;
import com.mview.medittext.utils.QpadJudgeUtils;
import com.mview.medittext.utils.QpadTimeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



/**
 * Created by xiao on 2016/12/25 0025.
 */
public class QpadEditText extends LinearLayout  implements View.OnClickListener{
    private EditText edittext;
    private Context mContext;

    private TextView title;
    private TextView textview;
    private String fieldName;
    private String fieldValue;
    private String fieldTitle;
    private boolean fieldRequested;
    private int fieldType;
    private String fieldHint;
    private float fieldTextSize;
    private String fieldText;
    private int fieldBindData;
    private boolean fieldBordingBackground;
    private KTAlertDialog dialog;
    private List<CommonSelectData> lists = new ArrayList<CommonSelectData>();
    private boolean fieldEnabled;
    private int fieldLines;
    private OnChangeListener onChangeListener;
    private OnDialogClickLister onDialogClickLister;

    private Calendar calendar = null;
    private boolean mIsAddBorderLine = false;

    private EditText border_edittext;

    public QpadEditText(Context context) {
        super(context);
        mContext = context;
        initView(context);
    }

    public QpadEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.kingteller_edittext);
        setFieldName(typedArray.getString(R.styleable.kingteller_edittext_fieldName));

        fieldValue = typedArray.getString(R.styleable.kingteller_edittext_fieldValue);
        fieldTitle = typedArray.getString(R.styleable.kingteller_edittext_fieldTitle);
        fieldRequested = typedArray.getBoolean(R.styleable.kingteller_edittext_fieldRequested, false);
        fieldType = typedArray.getInt(R.styleable.kingteller_edittext_fieldType, DataTypes.String);
        fieldHint = typedArray.getString(R.styleable.kingteller_edittext_fieldHint);
        fieldText = typedArray.getString(R.styleable.kingteller_edittext_fieldText);
        fieldBindData = typedArray.getResourceId(R.styleable.kingteller_edittext_fieldBindData, 0);
        fieldEnabled = typedArray.getBoolean(R.styleable.kingteller_edittext_fieldEnabled, true);
        fieldLines = typedArray.getInt(R.styleable.kingteller_edittext_fieldLines, 1);
        fieldTextSize = typedArray.getInt(R.styleable.kingteller_edittext_fieldTextSize, 16);
        fieldBordingBackground = typedArray.getBoolean(R.styleable.kingteller_edittext_fieldBordingBackground, false);
        if (!QpadJudgeUtils.isEmpty(fieldText) && QpadJudgeUtils.isEmpty(fieldValue)){
            fieldValue = fieldText;
        }else if (!QpadJudgeUtils.isEmpty(fieldValue) && QpadJudgeUtils.isEmpty(fieldText)){
            fieldText = fieldValue;
        }


        if (QpadJudgeUtils.isEmpty(fieldHint)) {
            fieldHint = (fieldType == DataTypes.Select || fieldType == DataTypes.Dialog
                    || fieldType == DataTypes.Date || fieldType == DataTypes.Time
                    || fieldType == DataTypes.DateTime) ? "请选择" : "请输入";

            if (!fieldEnabled){
                fieldHint = "";
            }
        }

        if (fieldBindData != 0) {
            setLists(getResources().getStringArray(fieldBindData));
        }

        typedArray.recycle();
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(mContext).inflate(R.layout.qpad_edittext, this);
        if (!fieldBordingBackground) {
            edittext = (EditText) findViewById(R.id.edittext);
        } else {
            edittext = (EditText) findViewById(R.id.border_edittext);
            edittext.setVisibility(View.VISIBLE);
        }
        title = (TextView) findViewById(R.id.title);
        textview = (TextView) findViewById(R.id.textview);

        edittext.setText(fieldValue);
        setFieldEnabled(fieldEnabled);
        setFieldLines(fieldLines);
        setFieldTextSize(fieldTextSize);

        if (fieldType > DataTypes.Number) {
            edittext.setVisibility(View.GONE);
            textview.setVisibility(View.VISIBLE);
        } else {
            edittext.setVisibility(View.VISIBLE);
            textview.setVisibility(View.GONE);
        }

        if (!QpadJudgeUtils.isEmpty(fieldHint)) {
            edittext.setHint(fieldHint);
            textview.setHint(fieldHint);
        }

        textview.setOnClickListener(this);

        title.setText(fieldTitle);

        if (fieldType == DataTypes.Number) {
            edittext.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }

        if (fieldRequested){
            title.setTextColor(getResources().getColor(R.color.orange));
        }
    }



    /**
     * 获取标题
     * @return
     */
    public String getFieldTitle() {
        return title.getText().toString();
    }

    public void setTextSize(float size){
        edittext.setTextSize(size);
    }

    public void setFouces(boolean bool){
        edittext.setFocusable(bool);
        if (!QpadJudgeUtils.isEmpty(fieldHint)) {
            edittext.setHint("");
            textview.setHint("");
        }
    }

    /**
     * 设置输入的类型
     * @param type
     */
    public void setInputType(int type) {
        edittext.setInputType(type);
    }

    public void setMaxLength(int paramInt) {
        EditText localEditText = edittext;
        InputFilter[] arrayOfInputFilter = new InputFilter[1];
        arrayOfInputFilter[0] = new InputFilter.LengthFilter(paramInt);
        localEditText.setFilters(arrayOfInputFilter);
    }

    public void setFieldTitle(String str) {
        title.setText(str);
    }

//=====================================设置 文本值  并返回 事件  start
    /**
     * 设置 String 值 并返回事件
     * @param text
     */
    public void setFieldTextAndValue(String text) {
        setFieldTextAndValue(text, text);
    }

    /**
     * 设置 CommonSelectData 值 并返回事件
     * @param data
     */
    public void setFieldTextAndValue(CommonSelectData data) {
        setFieldTextAndValue(data.getText().trim(), data.getValue().trim(),data.getObj());
    }




    /**
     * 根据value 设置value和text的值
     * @param value
     */
    public void setFieldTextAndValueFromValue(String value) {
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i).getValue().equals(value)) {
                setFieldTextAndValue(lists.get(i).getText(), lists.get(i).getValue(),lists.get(i).getObj());
                break;
            }
        }
    }


    /**
     * 设置text和value的值 并返回事件
     * @param text
     * @param value
     */
    public void setFieldTextAndValue(String text, String value) {
        if(fieldType > DataTypes.Number){
            textview.setText(text);
        }else{
            edittext.setText(text);
        }

        String oldvalue = getFieldValue();
        setFieldValue(value);
        setFieldText(text);

        if ((oldvalue != null && value != null && !oldvalue.equals(value))
                || (oldvalue == null && value != null) || (oldvalue != null && value == null)) {
            CommonSelectData data = new CommonSelectData();
            data.setText(text);
            data.setValue(value);
            if (onChangeListener != null){
                onChangeListener.onChanged(data);
            }
        }
    }

    /**
     * 设置text和value的值 并返回事件
     * @param text
     * @param value
     */
    public void setFieldTextAndValue(String text, String value,Object obj) {
        if(fieldType > DataTypes.Number){
            textview.setText(text);
        }else{
            edittext.setText(text);
        }

        String oldvalue = getFieldValue();
        setFieldValue(value);
        setFieldText(text);

        if ((oldvalue != null && value != null && !oldvalue.equals(value))
                || (oldvalue == null && value != null) || (oldvalue != null && value == null)
                ) {
            CommonSelectData data = new CommonSelectData();
            data.setText(text);
            data.setValue(value);
            if (obj != null) {
                data.setObj(obj);
            }
            if (onChangeListener != null) {
                onChangeListener.onChanged(data);
            }
        }
    }

//=====================================设置 文本值  并返回 事件   end


    public void setTextAndValue(CommonSelectData data){
        setTextAndValue(data.getText(),data.getValue(),data.getObj());
    }

    public void setTextAndValue(String text, String value){
        textview.setText(value);
        edittext.setText(text);
        String oldvalue = getFieldValue();
        setFieldValue(value);
        setFieldText(text);

        if ((oldvalue != null && value != null && !oldvalue.equals(value))
                || (oldvalue == null && value != null)|| (oldvalue != null && value == null)) {
            CommonSelectData data = new CommonSelectData();
            data.setText(text);
            data.setValue(value);
            if (onChangeListener != null){
                onChangeListener.onChanged(data);
            }
        }

    }

    public void setTextAndValue(String text, String value,Object obj){
        textview.setText(value);
        edittext.setText(text);
        String oldvalue = getFieldValue();
        setFieldValue(value);
        setFieldText(text);

        if ((oldvalue != null && value != null && !oldvalue.equals(value))
                || (oldvalue == null && value != null)|| (oldvalue != null && value == null)) {
            CommonSelectData data = new CommonSelectData();
            data.setText(text);
            data.setValue(value);
            if (obj != null) {
                data.setObj(obj);
            }
            if (onChangeListener != null){
                onChangeListener.onChanged(data);
            }
        }

    }

    @Override
    public void onClick(View view) {
        int i1 = view.getId();
        if (i1 == R.id.textview) {
            if (!isFieldEnabled()) {
                return;
            }

            switch (fieldType) {
                case DataTypes.DateTime://选择日期与时间

                    if (QpadJudgeUtils.isEmpty(getFieldStringValue())) {
                        calendar = Calendar.getInstance();
                    } else {
                        calendar = Calendar.getInstance();
                        calendar.setTime(QpadTimeUtils.getConversionFormatDateByString(getFieldStringValue(), 1));
                    }

                    QpadDateTimeDialogUtils.showDateAndTimePickerDialog(mContext, calendar, null,
                            new OnBtnSelectDateAndTimeClickL() {
                                @Override
                                public void OnBtnSelectDateAndTimeClick(int year, int month, int day, int hour, int minute) {
                                    //取消
                                }
                            }, new OnBtnSelectDateAndTimeClickL() {
                                @Override
                                public void OnBtnSelectDateAndTimeClick(int year, int month, int day, int hour, int minute) {
                                    //确定
                                    String datetime = QpadTimeUtils.getConversionFormatStringByDate(CalendarDay.from(year, month, day).getDate(), 2)
                                            + " " + QpadTimeUtils.getConversionFormatTime(hour + ":" + minute, 1);
                                    setFieldTextAndValue(datetime, datetime);
                                }
                            });

                    break;
                case DataTypes.DateTimePD:
                    PickDialog datetimepddialog = new PickDialog(getContext(),
                            new DateTimePickView.PickerDateTimeListener() {
                                public void onDateChanged(Date date) {
                                    QpadDateTime kdate = new QpadDateTime()
                                            .initNowPD();
                                    kdate.parseDate(date);

                                    setFieldTextAndValue(kdate.getDateTimeString());

                                }
                            }, "选择日期", "DataTypes.DateTimePD");
                    datetimepddialog.show();
                    break;
                case DataTypes.Date:

                    if (QpadJudgeUtils.isEmpty(getFieldStringValue())) {
                        calendar = Calendar.getInstance();
                    } else {
                        calendar = Calendar.getInstance();
                        calendar.setTime(QpadTimeUtils.getConversionFormatDateByString(getFieldStringValue(), 2));
                    }

                    QpadDateTimeDialogUtils.showDatePickerDialog(mContext, calendar,
                            new OnBtnSelectDateClickL() {
                                @Override
                                public void OnBtnSelectDateClick(DatePicker view, int year, int month, int day) {
                                    //取消
                                }
                            }, new OnBtnSelectDateClickL() {
                                @Override
                                public void OnBtnSelectDateClick(DatePicker view, int year, int month, int day) {
                                    //确定
                                    String date = QpadTimeUtils.getConversionFormatStringByDate(
                                            CalendarDay.from(year, month, day).getDate(), 2);
                                    setFieldTextAndValue(date);
                                }
                            });

                    break;
                case DataTypes.Time:

                    if (QpadJudgeUtils.isEmpty(getFieldStringValue())) {
                        calendar = Calendar.getInstance();
                    } else {
                        calendar = Calendar.getInstance();
                        calendar.setTime(QpadTimeUtils.getConversionFormatDateByString(getFieldStringValue(), 3));
                    }

                    QpadDateTimeDialogUtils.showTimePickerDialog(mContext, calendar, null,
                            new OnBtnSelectTimeClickL() {
                                @Override
                                public void OnBtnSelectTimeClick(TimePicker view, int hour, int minute) {
                                    //取消
                                }
                            }, new OnBtnSelectTimeClickL() {
                                @Override
                                public void OnBtnSelectTimeClick(TimePicker view, int hour, int minute) {
                                    //确定
                                    String time = QpadTimeUtils.getConversionFormatTime(hour + ":" + minute, 1);
                                    setFieldTextAndValue(time);
                                }
                            });

                    break;
                case DataTypes.Select:
                    if (lists != null && lists.size() > 0) {
                        String[] mbdlx_StringItem = new String[lists.size()];
                        for (int i = 0; i < lists.size(); i++) {
                            mbdlx_StringItem[i] = lists.get(i).getText();
                        }
                        final NormalListDialog dialog_bdlx = new NormalListDialog(mContext, mbdlx_StringItem);
                        dialog_bdlx.title(getFieldTitle())//
                                .layoutAnimation(null)
                                .titleBgColor(Color.parseColor("#409ED7"))//
                                .itemPressColor(Color.parseColor("#85D3EF"))//
                                .itemTextColor(Color.parseColor("#303030"))//
                                .show();
                        dialog_bdlx.setOnOperItemClickL(new OnOperItemClickL() {
                            @Override
                            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                                dialog_bdlx.dismiss();

                                CommonSelectData data = lists.get(position);
                                Object obj =  data.getObj();
                                if (obj == null) {
                                    setFieldTextAndValue(data.getText(), data.getValue());
                                } else {
                                    setFieldTextAndValue(data.getText(),data.getValue(),obj);
                                }
//				            	if (onChangeListener != null){
//				            		onChangeListener.onChanged(data);
//				            	}
                            }
                        });
                    }

                    break;
                case DataTypes.Dialog:
                    if (onDialogClickLister != null)
                        onDialogClickLister.OnDialogClick();
                    break;
                default:
                    break;
            }

        } else {
        }
    }

    /**
     * 获取控件的值
     * @return
     */
    public String getFieldValue() {
        if((fieldType == DataTypes.String || fieldType == DataTypes.Number) && fieldEnabled){
            return edittext.getText().toString().trim();
        }else{
            return this.fieldValue;
        }
    }

    public String getFieldStringValue(){
        return this.fieldValue;
    }

    private void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    /**
     * 获取控件显示的值
     * @return
     */
    public String getFieldText() {
        if (fieldType == DataTypes.String){
            return edittext.getText().toString().trim();
        }else{
            return fieldText;
        }
    }

    private void setFieldText(String fieldText) {
        this.fieldText = fieldText;
    }

    /**
     * 当类型为Select的时候设置的值
     * @param lists
     */
    public void setLists(List<CommonSelectData> lists) {
        this.lists = lists;
    }

    /**
     * 当类型为Select的时候设置的值
     * @param liststr
     */
    public void setLists(String[] liststr) {
        lists = new ArrayList<CommonSelectData>();
        for (int i = 0; i < liststr.length; i++) {
            CommonSelectData data = new CommonSelectData();
            data.setText(liststr[i]);
            data.setValue(liststr[i]);
            lists.add(data);
        }
    }

    public boolean isFieldEnabled() {
        return fieldEnabled;
    }

    /**
     * 设置空间是否有效
     * @param fieldEnabled
     */
    public void setFieldEnabled(boolean fieldEnabled) {
        this.fieldEnabled = fieldEnabled;
        edittext.setEnabled(fieldEnabled);
        if (!fieldEnabled) {
            edittext.setTextColor(getResources().getColor(R.color.gray));
            title.setTextColor(getResources().getColor(R.color.gray));
            textview.setTextColor(getResources().getColor(R.color.gray));
            edittext.setHint("");
            textview.setHint("");
        } else {
            edittext.setTextColor(getResources().getColor(R.color.black));
            title.setTextColor(getResources().getColor(R.color.black));
            textview.setTextColor(getResources().getColor(R.color.black));
        }
    }

    public void setFieldMachineEnabled(boolean fieldEnabled){
        this.fieldEnabled = fieldEnabled;
        edittext.setEnabled(fieldEnabled);
        if (!fieldEnabled) {
            edittext.setTextColor(getResources().getColor(R.color.gray));
            title.setTextColor(getResources().getColor(R.color.gray));
            textview.setTextColor(getResources().getColor(R.color.gray));
            edittext.setHint("");
            textview.setHint("");
        } else {
            edittext.setTextColor(getResources().getColor(R.color.orange));
            title.setTextColor(getResources().getColor(R.color.orange));
            textview.setTextColor(getResources().getColor(R.color.black));
        }
    }

    public void setFieldCheckoutEnabled(boolean fieldEnabled){
        this.fieldEnabled = fieldEnabled;
        edittext.setEnabled(fieldEnabled);
        if (!fieldEnabled) {
            edittext.setTextColor(getResources().getColor(R.color.black));
            title.setTextColor(getResources().getColor(R.color.black));
            textview.setTextColor(getResources().getColor(R.color.black));
            edittext.setHint("");
            textview.setHint("");
        }
    }

    public int getFieldLines() {
        return fieldLines;
    }

    /**
     * 设置控件的行数
     * @param fieldLines
     */
    public void setFieldLines(int fieldLines) {
        this.fieldLines = fieldLines;
        if (fieldLines > 1) {
            edittext.setSingleLine(false);
            edittext.setLines(fieldLines);
            edittext.setGravity(Gravity.TOP);
            title.setGravity(Gravity.TOP);

            textview.setSingleLine(false);
            textview.setLines(fieldLines);
            textview.setGravity(Gravity.TOP);

            LayoutParams lp = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    getResources().getDimensionPixelSize(R.dimen.common_line_height) * fieldLines);
            edittext.setLayoutParams(lp);
            textview.setLayoutParams(lp);

        } else {
            edittext.setSingleLine(true);
            edittext.setGravity(Gravity.CENTER_VERTICAL);
            textview.setGravity(Gravity.CENTER_VERTICAL);
            textview.setSingleLine(true);
            title.setGravity(Gravity.CENTER_VERTICAL);
            LayoutParams lp = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            edittext.setLayoutParams(lp);
            textview.setLayoutParams(lp);
        }
    }

    public void setFieldTextSize(float fieldTextSize){
        edittext.setTextSize(fieldTextSize);
        textview.setTextSize(fieldTextSize);
    }

    public boolean isFieldRequested() {
        return fieldRequested;
    }

    public void setFieldRequested(boolean fieldRequested) {
        this.fieldRequested = fieldRequested;
        if (fieldRequested){
            title.setTextColor(getResources().getColor(R.color.orange));
        }else{
            title.setTextColor(getResources().getColor(R.color.black));
        }
    }



    public boolean checkDataType() {
        switch (fieldType) {
            case DataTypes.Number:
                if (QpadJudgeUtils.isNumber(getFieldValue())){
                    return true;
                }else{
                    return false;
                }
            default:
                return true;

        }
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    public void setOnDialogClickLister(OnDialogClickLister onDialogClickLister) {
        this.onDialogClickLister = onDialogClickLister;
    }



    /**
     * 设置数值改变的回调接口
     * @author 王定波
     *
     */
    public interface OnChangeListener {
        public abstract void onChanged(CommonSelectData data);
    }

    /**
     * 设置类型为Dialog时的点击的接口
     * @author 王定波
     *
     */
    public interface OnDialogClickLister {
        public abstract void OnDialogClick();
    }
    public interface onDialogClickListener{
        public abstract void OnDialogClick(CommonSelectData data);
    }

}
