package com.mview.customdialog.view;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mview.customdialog.R;

/**
 * 下载通用 进度dialog
 * @author Administrator
 */
public class DownloadAPPDialog extends Dialog {
	
	 private Context mContext;
	 private TextView title, count;
	 private ProgressBar pro;
	 
    public DownloadAPPDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    public DownloadAPPDialog(Context context) {
        super(context);
        this.mContext = context;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);//让window进行全屏显示
        setContentView(R.layout.layout_dialog_downloadapp);
        
        setCanceledOnTouchOutside(false);// 设置点击屏幕 Dialog不消失
        setCancelable(false);// 设置点击返回键 Dialog不消失
        
        initUI();
    }
    
    private void initUI() {
		/**将对话框的大小按屏幕大小的百分比设置**/
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.85); // 宽度设置为屏幕的0.85
        getWindow().setAttributes(lp);
        
        title = (TextView) this.findViewById(R.id.down_app_title);
        title.setText("下载应用");
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f);
        
        count = (TextView) this.findViewById(R.id.down_app_text);
        count.setText("正在下载...");
        count.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);

        pro = (ProgressBar) findViewById(R.id.down_app_progressBar);
	}
    
	public void setProgress(int progress) {
		pro.setProgress(progress);
		count.setText("正在下载..." + progress + "%");
	}
    
}
