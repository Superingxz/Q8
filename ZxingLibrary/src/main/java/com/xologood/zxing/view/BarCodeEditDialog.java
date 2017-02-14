package com.xologood.zxing.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mview.customdialog.view.dialog.utils.CornerUtils;
import com.xologood.zxing.R;
import com.xologood.zxing.utils.JudgeUtils;
import com.xologood.zxing.utils.T;


/**
 * 输入二维码	Dialog
 * Created by Administrator on 2015/7/19.
 */
public class BarCodeEditDialog extends Dialog implements OnClickListener {
	    private Context mContext;
	    private TextView title;
	    private EditText barcode_num;
	    private Button confim, cancel;
	    
	    private int bgColor = Color.parseColor("#ffffff");
	    private int btnPressColor = Color.parseColor("#E3E3E3");
	    
	    private EditOnClickL EditOnClickL;
	    
		public void setEditOnClickL(EditOnClickL EditOnClickL) {
			this.EditOnClickL = EditOnClickL;
		}
		
		public interface EditOnClickL{
			public void EditOnClick(String str);
		}
	    
	    public BarCodeEditDialog(Context context, int theme) {
	        super(context, theme);
	        this.mContext = context;
	    }

	    public BarCodeEditDialog(Context context) {
	        super(context);
	        this.mContext = context;
	    }

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	        WindowManager.LayoutParams.FLAG_FULLSCREEN);//让window进行全屏显示
	        setContentView(R.layout.layout_dialog_barcodeedit);
	        setCanceledOnTouchOutside(false);// 设置点击屏幕 Dialog不消失
	        setCancelable(false);// 设置点击返回键 Dialog不消失
	        initUI();
	    }

	    private void initUI(){
	        /**将对话框的大小按屏幕大小的百分比设置**/
	        WindowManager.LayoutParams lp = getWindow().getAttributes();
	        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
	        lp.width = (int) (d.widthPixels * 0.85); // 宽度设置为屏幕的0.85
	        getWindow().setAttributes(lp);

	        title = (TextView) this.findViewById(R.id.edit_barcode_title);
	        title.setText("请输入二维码");
	        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f);
	        
	        confim = (Button) this.findViewById(R.id.btn_barcode_num_confirm);
	        cancel = (Button) this.findViewById(R.id.btn_barcode_num_cancel);
	        
	        float radius = dp2px(5);
	        cancel.setBackgroundDrawable(CornerUtils.btnSelector(radius, bgColor, btnPressColor, 0));
	        confim.setBackgroundDrawable(CornerUtils.btnSelector(radius, bgColor, btnPressColor, 1));
	 
	        barcode_num = (EditText) this.findViewById(R.id.edit_barcode_num);
   			//设置只能输入数字
			barcode_num.setInputType(InputType.TYPE_CLASS_NUMBER);
	        confim.setOnClickListener(this);
	        cancel.setOnClickListener(this);

	    }

	    @Override
	    public void onClick(View v) {
			int i = v.getId();
			if (i == R.id.btn_barcode_num_confirm) {
				String barcode = barcode_num.getText().toString().trim();

				if (JudgeUtils.isEmpty(barcode)) {
					T.showShort(mContext, "二维码不能为空!");
					return;
				}
				if (EditOnClickL != null) {
					EditOnClickL.EditOnClick(barcode);
				}

			} else if (i == R.id.btn_barcode_num_cancel) {
				if (EditOnClickL != null) {
					EditOnClickL.EditOnClick("");
				}

			} else {
			}
	    }
	    
	    /** dp to px */
	    private int dp2px(float dp) {
	        final float scale = mContext.getResources().getDisplayMetrics().density;
	        return (int) (dp * scale + 0.5f);
	    }
	}