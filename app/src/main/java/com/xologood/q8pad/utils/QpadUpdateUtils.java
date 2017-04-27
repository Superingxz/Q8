package com.xologood.q8pad.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.text.Html;

import com.mview.customdialog.view.DownloadAPPDialog;
import com.mview.customdialog.view.MaterialDialog;
import com.mview.customdialog.view.dialog.listener.OnBtnClickL;
import com.mview.customdialog.view.dialog.use.QpadProgressUtils;
import com.xologood.mvpframework.util.helper.RxSchedulers;
import com.xologood.mvpframework.util.helper.RxSubscriber;
import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.R;
import com.xologood.q8pad.api.Api;
import com.xologood.q8pad.api.DownloadAPI;
import com.xologood.q8pad.api.HostType;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.Version;
import com.xologood.q8pad.download.DownloadModel;
import com.xologood.q8pad.listener.DownloadProgressListener;
import com.xologood.zxing.utils.T;

import java.io.File;

import okhttp3.ResponseBody;
import rx.Subscriber;

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
		 String recorderBase = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.RECORDERBASE);
		 String sysKeyBase = "150623155902966stlt";
		Api.getLoginInInstance(HostType.USERURL, recorderBase, sysKeyBase).CheckVersion()
																			 .compose(RxSchedulers.<BaseResponse<Version>>io_main())
																			 .subscribe(new RxSubscriber<BaseResponse<Version>>(mContext,false) {
																				 @Override
																				 public void onStart() {
																					 super.onStart();
																					 if (isshow){
																						 QpadProgressUtils.showProgress(mContext, "正在检查版本...");
																					 }
																				 }
																				 @Override
																				 protected void _onNext(final BaseResponse<Version> versionBaseResponse) {
																					 String m1 = versionBaseResponse.getData().getM1();
//																					 !CompareVerSion(mContext, m1)
																					 int m2 = versionBaseResponse.getData().getM2();
																					 int versionCode = getVersionCode(mContext);
																					 if (versionCode <m2) {
																						 final MaterialDialog dialog_upapp = new MaterialDialog(mContext);
																						 dialog_upapp.title("新版本" + m1)
																								 .content(Html.fromHtml(versionBaseResponse.getData().getTitle().trim()).toString())//
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
																										 Qpadapplication.finishActivity();
																									 }
																								 },
																								 new OnBtnClickL() {
																									 @Override
																									 public void onBtnClick() {
//																										 String downloadUrl = versionBaseResponse.getData().getUrl();
																										 String downloadUrl = "http://120.24.208.159/yueliang/app/haiwangapp/HWAGENT.20.apk";

																										 String path = QpadStaticConfig.CACHE_PATH.SD_DOWNLOAD + "/"
																												 + EncroptionUtils.encryptMD5ToString(downloadUrl) + ".apk";
																										 final File outputFile  = new File(path);

																										 if (outputFile .exists()) {// 判断文件是否存在
																											 outputFile .delete();// 删除文件
																										 }
																										 dialog_upapp.dismiss();
																										 final DownloadAPPDialog downapp = new DownloadAPPDialog(mContext,  R.style.Login_dialog);
																										 downapp.show();
																										 downapp.setProgress(0);
//																										 down(mContext, versionBaseResponse.getData().getUrl());
																										 new DownloadAPI(downloadUrl, new DownloadProgressListener() {
																											 @Override
																											 public void update(long bytesRead, long contentLength, boolean done) {
																												 int progress = (int) (100 * contentLength / bytesRead);
																												 downapp.setProgress(progress);
																											 }
																										 }).downloadAPK(downloadUrl, outputFile , new Subscriber() {
																											 @Override
																											 public void onCompleted() {
																												 if(downapp.isShowing()){
																													 downapp.dismiss();
																												 }
																											//	 T.showShort(mContext, "下载完成正在安装!");
																												 if (outputFile.exists()) {
																													 installApk(mContext,outputFile);
																												 }
																											 }
																											 @Override
																											 public void onError(Throwable e) {
																												 if(downapp.isShowing()){
																													 downapp.dismiss();
																												 }
																											 }
																											 @Override
																											 public void onNext(Object o) {

																											 }
																										 });
																									 }
																								 }
																						 );
																					 } else {
																						 if (isshow){
																							 T.showShort(mContext, "已经是最新版本");
																						 }
																					 }
																				 }
																				 @Override
																				 protected void _onError(String message) {

																				 }
																			 });
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
		final DownloadAPPDialog downapp = new DownloadAPPDialog(context,  R.style.Login_dialog);
		downapp.show();
		downapp.setProgress(0);
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
//		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// 执行动作
		intent.setAction(Intent.ACTION_VIEW);
		// 执行的数据类型 	编者按：此处应为android，否则造成安装不了
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		context.startActivity(intent);
	}
}
