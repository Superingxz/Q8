package com.xologood.q8pad.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

import com.xologood.mvpframework.util.helper.RxSchedulers;
import com.xologood.mvpframework.util.helper.RxSubscriber;
import com.xologood.q8pad.download.DownloadModel;

import java.io.File;

import okhttp3.ResponseBody;

/**
 * 应用升级处理 工具类
 * Created by Administrator on 16-1-6.
 */
public class QpadUpdateUtils {
	
	 /**
	  *  获取版本号
	  */
	public static String getCurrentVersionName(Context context) {
		String versionName = "";
		try {
			versionName = context.getPackageManager().getPackageInfo(
					context.getPackageName(), PackageManager.GET_PERMISSIONS).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}
	
    /**
     *  获取内部识别号
     */
    private static int getVersionCode(Context context) {  
        try {  
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);  
            return pi.versionCode;  
        } catch (NameNotFoundException e) {  
            return 0;  
        }  
    }  
    
	/**
	 * 比较版本号
	 */
	public static boolean CompareVerSion(Context context, String version) {
		String nowVersion = getCurrentVersionName(context);
		String[] nowS = nowVersion.split("\\.");
		String[] gS = version.split("\\.");
		for (int i = 0; i < nowS.length; i++) {
			if (Integer.parseInt(gS[i]) > Integer.parseInt(nowS[i])) {
				return true;
			} else if (Integer.parseInt(gS[i]) < Integer.parseInt(nowS[i])) {
				return false;
			}
		}

		return false;
	}

	
	/**
	 * 检查升级
	 * @param mContext
	 * @param isshow 是否提示
	 */
	public static void CheckUpdate(final Context mContext, final boolean isshow) {

	/*	AjaxParams params = new AjaxParams();
		params.put("version_code", String.valueOf(getVersionCode(mContext)));
		params.put("app_type", "android");
		
		KTHttpClient fh = new KTHttpClient(true);
		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.APPUpdateUrl), params,
				new AjaxHttpCallBack<AppUpdateBean>(mContext, false) {

					@Override
					public void onStart() {
						if (isshow){
							KingTellerProgressUtils.showProgress(mContext, "正在检查版本...");
						}
					}
					
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						KingTellerProgressUtils.closeProgress();
					}
					
					public void onDo(final AppUpdateBean data) {
						Log.e("版本升级", "是否更新：" + data.isSuccessFul());
						
						if (isshow){
							KingTellerProgressUtils.closeProgress();
						}
						
						if (data.isSuccessFul()) {
							
							 final MaterialDialog dialog_upapp = new MaterialDialog(mContext);
							 
							 dialog_upapp.title("新版本" + data.getTitle())
					        	  .content(Html.fromHtml(data.getContent().trim()).toString())//
					              .btnText("暂不更新", "现在更新")//
					              .show();
							 dialog_upapp.setCancelable(false);
							 dialog_upapp.setCanceledOnTouchOutside(false);
							 dialog_upapp.setOnBtnClickL(
						                new OnBtnClickL() {
						                    @Override
						                    public void onBtnClick() {
						                    	dialog_upapp.dismiss();

						                    	//关闭软件
				                                KingTellerApplication.finishActivity();
				                                mContext.stopService(new Intent(mContext, KingTellerService.class));
						                    }
						                },
						                new OnBtnClickL() {
						                    @Override
						                    public void onBtnClick() {
						                    	dialog_upapp.dismiss();

						                        down(mContext, KingTellerConfigUtils.CreateUrl(mContext, data.getUrl()));
						                    }
						                }
						        );

							KingTellerConfigUtils.setConfigMeta(KingTellerConfigUtils.KEY_APP_NEW, true);
//							KingTellerUtils.MainUpdateStatus(context,
//									KingTellerConfig.MAIN_MORE, 
//									true,true);

						} else {
							KingTellerConfigUtils.setConfigMeta(KingTellerConfigUtils.KEY_APP_NEW, false);
							
//							KingTellerUtils.MainUpdateStatus(context, 
//									KingTellerConfig.MAIN_MORE, 
//									true, false);
							
							if (isshow){
								 T.showShort(mContext, "已经是最新版本");
							}
								
						}

					}

				});*/
	}

	/**
	 * 下载app
	 * @param context
	 * @param url
	 */
	private static void down(final Context context, final String url) {
		//url = ConfigUtils.CreateUrl(context, "/update/KingTellerClient.apk");
		/*
		String filename = KingTellerStaticConfig.CACHE_PATH.SD_DOWNLOAD + "/"
				+ EncroptionUtils.encryptMD5ToString(url) + ".apk";
		File file = new File(filename);

		if (file.exists()) {// 判断文件是否存在
			file.delete();// 删除文件
		}

		final DownloadAPPDialog downapp = new DownloadAPPDialog(context,  R.style.Login_dialog);
		downapp.show();
		downapp.setProgress(0);
		

		KTHttpClient fh = new KTHttpClient(false);
		fh.download(url, filename, new AjaxCallBack<File>() {
			
			@Override
			public void onLoading(long count, long current) {
				int progress = (int)(100 * current/count);
				downapp.setProgress(progress);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				if(downapp.isShowing()){
					downapp.dismiss();
				}
			}

			@Override
			public void onSuccess(File t) {
				if(downapp.isShowing()){
					downapp.dismiss();
				}
				
				T.showShort(context, "下载完成正在安装!");
				
				installApk(context, t);
			}
		});*/

		new DownloadModel().downloadFileWithDynamicUrlSync(url)
					       .compose(RxSchedulers.<ResponseBody>io_main())
				   		   .subscribe(new RxSubscriber<ResponseBody>(context,false) {
							   @Override
							   public void onStart() {
								   super.onStart();

							   }

							   @Override
							   protected void _onNext(ResponseBody responseBody) {
								   if (DownLoadManager.writeResponseBodyToDisk(context, responseBody,url)) {
									   //成功下载

								   }
							   }

							   @Override
							   protected void _onError(String message) {

							   }
						   });
	}

	/***
	 * 安装app
	 * @param context
	 * @param file
	 */
	private static void installApk(Context context, File file) {
		Intent intent = new Intent();
		// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// 执行动作
		intent.setAction(Intent.ACTION_VIEW);
		// 执行的数据类型 	编者按：此处应为android，否则造成安装不了
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		context.startActivity(intent);
	}

}
