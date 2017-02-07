package com.mview.customdialog.view.dialog.use;

import android.content.Context;

import com.mview.customdialog.view.dialog.NormalProgressDialog;
import com.mview.customdialog.R;

public class QpadProgressUtils {
	
	private static NormalProgressDialog NormalProgressDialog;
	
	/**
	 * 关闭
	 */
	public static void closeProgress() {
			if (NormalProgressDialog != null && NormalProgressDialog.isShowing()){
				NormalProgressDialog.dismiss();
				NormalProgressDialog = null;
			}
	}

	/**
	 * 显示
	 * @param context
	 * @param msg
	 */
	public static void showProgress(Context context, String msg) {
			if (context != null) {
				if (NormalProgressDialog != null && NormalProgressDialog.isShowing()) {
					NormalProgressDialog.setMsgText(msg);
					return;
				} else {
					NormalProgressDialog = new NormalProgressDialog(context, R.style.Login_dialog);
					NormalProgressDialog.setMsgText(msg);
					NormalProgressDialog.show();
				}
			}
		}

	
	
}
