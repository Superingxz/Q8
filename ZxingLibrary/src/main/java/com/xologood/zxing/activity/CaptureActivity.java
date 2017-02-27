package com.xologood.zxing.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.client.result.ResultParser;
import com.xologood.zxing.R;
import com.xologood.zxing.camera.CameraManager;
import com.xologood.zxing.core.BitmapQRCodeUtils;
import com.xologood.zxing.core.CaptureActivityHandler;
import com.xologood.zxing.executor.AmbientLightManager;
import com.xologood.zxing.executor.BeepManager;
import com.xologood.zxing.executor.FinishListener;
import com.xologood.zxing.executor.InactivityTimer;
import com.xologood.zxing.utils.ConfigUtils;
import com.xologood.zxing.utils.ProgressUtils;
import com.xologood.zxing.utils.T;
import com.xologood.zxing.view.BarCodeEditDialog;
import com.xologood.zxing.view.BarCodeEditDialog.EditOnClickL;
import com.xologood.zxing.view.ViewfinderView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;




/**
 * 二维码扫描
 * @author 王定波
 *
 */
public final class CaptureActivity extends Activity implements
		SurfaceHolder.Callback, OnClickListener {

	private static final String TAG = CaptureActivity.class.getSimpleName();
	
	private CameraManager cameraManager;
	/**
	 * 声音震动管理器。如果扫描成功后可以播放一段音频，也可以震动提醒，可以通过配置来决定扫描成功后的行为。
	 */
	private BeepManager beepManager;
	/**
	 * 闪光灯调节器。自动检测环境光线强弱并决定是否开启闪光灯
	 */
	private AmbientLightManager ambientLightManager;
	
	private Result lastResult;
	
	//是否有预览
	private boolean hasSurface;
	
	private Collection<BarcodeFormat> decodeFormats;

	private Map<DecodeHintType, ?> decodeHints;

	private String characterSet;

	private IntentSource source;
	
	private InactivityTimer inactivityTimer;
	
	private CaptureActivityHandler handler;
	
	private ViewfinderView viewfinderView;
	
	static final int PARSE_BARCODE_SUC = 3035;
	static final int PARSE_BARCODE_FAIL = 3036;
	
	static final int EDIT_BARCODE_SUC = 4035;
	
	String photoPath;
	
	private String mMsgNum; // 返回二维码或条形码
	private String mMsgType; // 返回 条码类型
	private String mTextType; // 获取标识
	
	private Button mCapture_back, mCapture_qrcode, mCapture_picture, mCapture_txm, mCapture_ewm, mCapture_sgd;
	private TextView mCapture_MsgText, mCapture_EwmText, mCapture_TxmText, mCapture_SgdText;
	
	private RelativeLayout mCapture_layout_ewm, mCapture_layout_txm, mCapture_layout_sgd;

	private TextView tv_count;

	private List<Button> mBottomBTLists;
	private List<TextView> mBottomTVLists;

	private Boolean [] mBottomChosenLists;
	private Context mContext;

	enum IntentSource {
		  NATIVE_APP_INTENT,
		  PRODUCT_SEARCH_LINK,
		  ZXING_LINK,
		  NONE
	}

	private boolean isContinous = false;
	private List<String> smm;
	private StringBuffer sb ;
	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public CameraManager getCameraManager() {
		return cameraManager;
	}


	Handler barHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case PARSE_BARCODE_SUC:  //扫描成功
				Log.e("扫描结果", msg.getData().getString("mMsgType") + ", " +msg.getData().getString("mMsgNum"));
				Intent intent;
				if("more".equals(mTextType)){
					intent = new Intent(CaptureActivity.this, CaptureShowActivity.class);
					intent.putExtra("ewm_type", msg.getData().getString("mMsgType"));
					intent.putExtra("pict_width", CameraManager.width);
					intent.putExtra("pict_height", CameraManager.height);
					intent.putExtra("pict_bundle", msg.getData().getBundle("mBundle"));
					startActivity(intent);
				}else{
					intent = new Intent();
					mMsgNum = msg.getData().getString("mMsgNum");
					if (isContinous) {
						sb.append(mMsgNum + ",");
						String result = sb.toString().substring(0, sb.length() - 1);
						intent.putExtra("ewm_num", result);
						smm =  Arrays.asList(result.split(","));
						if (smm.size() > 0) {
							tv_count.setVisibility(View.VISIBLE);
							tv_count.setText("已经扫描" + smm.size() + "条");
						} else {
							tv_count.setVisibility(View.GONE);
						}
					} else {
						intent.putExtra("ewm_num", mMsgNum);
					}
					intent.putExtra("ewm_type", msg.getData().getString("mMsgType"));
					intent.putExtra("pict_width", CameraManager.width);
					intent.putExtra("pict_height", CameraManager.height);
					intent.putExtra("pict_bundle", msg.getData().getBundle("mBundle"));
					setResult(RESULT_OK, intent);
				}
				CameraManager.scanframe = 2;
				chosenIVSrcAndTVColor(1);
				unChosenIVSrcAndTVColor(0);
				unChosenIVSrcAndTVColor(2);
				onPause();
				onResume();
				T.showShort(mContext, "扫描成功!");
				if (!isContinous) {
					finish();
				}
				break;
			case PARSE_BARCODE_FAIL:  //扫描失败
				Log.e("扫描结果","扫描失败！");
				T.showShort(mContext, "扫描失败!");
				break;
				
			case EDIT_BARCODE_SUC:   //输入成功
				if("more".equals(mTextType)){
					intent = new Intent(CaptureActivity.this, CaptureShowActivity.class);
					intent.putExtra("ewm_num", msg.getData().getString("mMsgNum"));
					intent.putExtra("ewm_type", msg.getData().getString("mMsgType"));
					intent.putExtra("pict_width", CameraManager.width);
					intent.putExtra("pict_height", CameraManager.height);
					intent.putExtra("pict_bundle", msg.getData().getBundle("mBundle"));
					startActivity(intent);
				}else{
					intent = new Intent();
					String mMsgNum = msg.getData().getString("mMsgNum");
					if (isContinous) {
						sb.append(mMsgNum + ",");
						String result = sb.toString().substring(0, sb.length() - 1);
						smm =  Arrays.asList(result.split(","));
						if (smm.size() > 0) {
							tv_count.setVisibility(View.VISIBLE);
							tv_count.setText("已经扫描" + smm.size() + "条");
						} else {
							tv_count.setVisibility(View.GONE);
						}
						intent.putExtra("ewm_num", result);
					} else {
						intent.putExtra("ewm_num", mMsgNum);
					}
					intent.putExtra("ewm_type", msg.getData().getString("mMsgType"));
					intent.putExtra("pict_width", CameraManager.width);
					intent.putExtra("pict_height", CameraManager.height);
					intent.putExtra("pict_bundle", msg.getData().getBundle("mBundle"));
					setResult(RESULT_OK, intent);
				}

				CameraManager.scanframe = 2;
				chosenIVSrcAndTVColor(1);
				unChosenIVSrcAndTVColor(0);
				unChosenIVSrcAndTVColor(2);
				onPause();
				onResume();
				T.showShort(mContext, "输入成功!");
				if (!isContinous) {
					finish();
				}
				break;

			}
			super.handleMessage(msg);
		}

	};
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		Window window = getWindow();  
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.layout_capture);
		mContext = CaptureActivity.this;
		initUI();
		
		initData();
	}
	
	private void initUI() {
		mBottomTVLists = new ArrayList<TextView>();
		mBottomBTLists = new ArrayList<Button>();
		mBottomChosenLists = new Boolean [] {false, false, false};
				
		mCapture_MsgText = (TextView) findViewById(R.id.capture_tex_msg);
		mCapture_EwmText = (TextView) findViewById(R.id.capture_tex_ewm);
		mCapture_TxmText = (TextView) findViewById(R.id.capture_tex_txm);
		mCapture_SgdText = (TextView) findViewById(R.id.capture_tex_sgd);
		mBottomTVLists.add(mCapture_EwmText);
        mBottomTVLists.add(mCapture_TxmText);
        mBottomTVLists.add(mCapture_SgdText);
        
        mCapture_ewm = (Button) findViewById(R.id.capture_btn_ewm);
		mCapture_txm = (Button) findViewById(R.id.capture_btn_txm);
		mCapture_sgd = (Button) findViewById(R.id.capture_btn_sgd);
		
		mBottomBTLists.add(mCapture_ewm);
		mBottomBTLists.add(mCapture_txm);
		mBottomBTLists.add(mCapture_sgd);
		
		mCapture_layout_ewm = (RelativeLayout) findViewById(R.id.capture_relayout_smewm);
		mCapture_layout_txm = (RelativeLayout) findViewById(R.id.capture_relayout_smtxm);
		mCapture_layout_sgd = (RelativeLayout) findViewById(R.id.capture_relayout_dksgd);
		
		
		mCapture_back = (Button) findViewById(R.id.capture_back);
		mCapture_picture = (Button) findViewById(R.id.capture_btn_picture);
		mCapture_qrcode = (Button) findViewById(R.id.capture_btn_qrcode);

		tv_count = (TextView) findViewById(R.id.tv_count);

		mCapture_back.setOnClickListener(this);
		mCapture_picture.setOnClickListener(this);
		mCapture_qrcode.setOnClickListener(this);
		
		mCapture_layout_ewm.setOnClickListener(this);
		mCapture_layout_txm.setOnClickListener(this);
		mCapture_layout_sgd.setOnClickListener(this);
	}
	
	private void initData() {
		smm = new ArrayList<>();
		sb = new StringBuffer();
		//是否连续扫码
		isContinous = getIntent().getBooleanExtra("isContinous", false);

		mTextType = (String) getIntent().getSerializableExtra("cameType"); 

		if("0".equals(mTextType)){
			mCapture_MsgText.setText("正处于：服务站物料登记'出库'扫描中！");
		}else if("1".equals(mTextType)){
			mCapture_MsgText.setText("正处于：服务站物料登记'入库'扫描中！");
		}else if("2".equals(mTextType)){
			mCapture_MsgText.setText("正处于：网点机器部件更换'旧物料'描中！");
		}else if("3".equals(mTextType)){
			mCapture_MsgText.setText("正处于：网点机器部件更换'新物料'描中！");
		}else{
			mCapture_MsgText.setText("");
			mCapture_MsgText.setVisibility(View.GONE);
		}
		
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		ambientLightManager = new AmbientLightManager(this);
		beepManager = new BeepManager(this);  
		
		chosenIVSrcAndTVColor(1);
		CameraManager.scanframe = 2;
		
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		cameraManager = new CameraManager(getApplication()); 
		
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		viewfinderView.setCameraManager(cameraManager);
		
		handler = null;
		lastResult = null;
		
		resetStatusView();
		
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
		}
		// 加载声音配置，其实在BeemManager的构造器中也会调用该方法，即在onCreate的时候会调用一次
		beepManager.updatePrefs();
		// 启动闪光灯调节器
		ambientLightManager.start(cameraManager);
		// 恢复活动监控器
		inactivityTimer.onResume();

		source = IntentSource.NONE;
		decodeFormats = null;
		characterSet = null;

	}

	@Override
	public void onPause() {
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		
		inactivityTimer.onPause();
		ambientLightManager.stop();
		cameraManager.closeDriver();
		if (!hasSurface) {
			SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
			SurfaceHolder surfaceHolder = surfaceView.getHolder();
			surfaceHolder.removeCallback(this);
		}
		super.onPause();
	}

	@Override
	public void onDestroy() {
		inactivityTimer.shutdown();
		barHandler = null;
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
	        if ((source == IntentSource.NONE || source == IntentSource.ZXING_LINK) && lastResult != null) {
	          restartPreviewAfterDelay(0L);
	          return true;
	        }
			break;
		case KeyEvent.KEYCODE_VOLUME_UP:
			cameraManager.zoomIn();
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			cameraManager.zoomOut();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	// 这里初始化界面，调用初始化相机
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (holder == null) {
			Log.e(TAG,"*** WARNING *** surfaceCreated() gave us a null surface!");
		}
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	// 返回解析二维码结果
	public void handleDecode(Result rawResult, Bitmap barcode, final Bundle bundle, float scaleFactor) {
		// 重新计时
		inactivityTimer.onActivity();
		
		lastResult = rawResult;
		
		if(barcode != null){
			
			mMsgNum = ResultParser.parseResult(rawResult).toString();
			mMsgType = rawResult.getBarcodeFormat().toString();
					
			beepManager.playBeepSoundAndVibrate();
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					if (mMsgNum != null) {
						Bundle bundleData = new Bundle();  
						Message m = Message.obtain();
						m.what = PARSE_BARCODE_SUC;
						bundleData.putString("mMsgNum", mMsgNum);
						bundleData.putString("mMsgType", mMsgType);
						bundleData.putBundle("mBundle", bundle);
						m.setData(bundleData);  
						barHandler.sendMessage(m);

					} else {
						Bundle bundleData = new Bundle();  
						Message m = Message.obtain();
						m.what = PARSE_BARCODE_FAIL;
						bundleData.putString("mMsgNum", "扫描失败！");
						bundleData.putString("mMsgType", "扫描失败！"); 
						bundleData.putBundle("mBundle", bundle);
						m.setData(bundleData);  
						barHandler.sendMessage(m);
					}
				}
			}).start();
		}else{
			Log.e("steven", "resultHandler.getType().toString():" +
					rawResult.getBarcodeFormat().toString());
		}
	}
	
	// 选择图片返回解析二维码
	public void onActivityResult(int requestCode, int resultCode, final Intent intent) {
		if (resultCode == RESULT_OK) {
			
			switch (requestCode) {
				case ConfigUtils.QRCODE_PICTURE_NUM:
					// 获取选中图片的路径
					Cursor cursor = getContentResolver().query(intent.getData(), null, null, null, null);
					if (cursor.moveToFirst()) {
						photoPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
					}
					cursor.close();

					ProgressUtils.showProgress(mContext, "正在获取...");
					
					new Thread(new Runnable() {

						@Override
						public void run() {
							
							//获取解析结果  
							Bitmap img = BitmapQRCodeUtils.getPictureBitmap(photoPath);
							BitmapQRCodeUtils decoder_pict = new BitmapQRCodeUtils(CaptureActivity.this);
			                Result result_pict = decoder_pict.getRawResult(img);  
						 
							if (result_pict != null) {
								mMsgNum = ResultParser.parseResult(result_pict).toString();
								mMsgType = result_pict.getBarcodeFormat().toString();
							
								Bundle bundleData = new Bundle();  
								Message m = Message.obtain();
								if(mMsgNum != null){
									m.what = PARSE_BARCODE_SUC;
									bundleData.putString("mMsgNum", mMsgNum);
									bundleData.putString("mMsgType", mMsgType);
								}else{
									m.what = PARSE_BARCODE_FAIL;
									bundleData.putString("mMsgNum", "解析失败！");
									bundleData.putString("mMsgType", "解析失败！");
								}
								m.setData(bundleData);  
								barHandler.sendMessage(m);
								
							}else {
								Bundle bundleData = new Bundle();  
								Message m = Message.obtain();
								m.what = PARSE_BARCODE_FAIL;
								bundleData.putString("mMsgNum", "解析失败！");
								bundleData.putString("mMsgType", "解析失败！");
								m.setData(bundleData);  
								barHandler.sendMessage(m);
							}
							
							ProgressUtils.closeProgress();
						}
					}).start();
					break;
			}
		}
	}


	// 初始化照相机，CaptureActivityHandler解码
	private void initCamera(SurfaceHolder surfaceHolder) {
		if (surfaceHolder == null) {
			throw new IllegalStateException("No SurfaceHolder provided");
		}
		if (cameraManager.isOpen()) {
			Log.w(TAG,"initCamera() while already open -- late SurfaceView callback?");
			return;
		}
		try {
			cameraManager.openDriver(surfaceHolder);
			if (handler == null) {
				handler = new CaptureActivityHandler(this, decodeFormats,
						decodeHints, characterSet, cameraManager);
			}
		} catch (IOException ioe) {
			Log.w(TAG, ioe);
			displayFrameworkBugMessageAndExit();
		} catch (RuntimeException e) {
			Log.w(TAG, "Unexpected error initializing camera", e);
			displayFrameworkBugMessageAndExit();
		}
	}
	
	private void displayFrameworkBugMessageAndExit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.app_name));
		builder.setMessage(getString(R.string.msg_camera_framework_bug));
		builder.setPositiveButton(R.string.confirm, new FinishListener(this));
		builder.setOnCancelListener(new FinishListener(this));
		builder.show();
	}

	public void restartPreviewAfterDelay(long delayMS) {
		if (handler != null) {
			handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
		}
		resetStatusView();
	}

	private void resetStatusView() {
		viewfinderView.setVisibility(View.VISIBLE);
		lastResult = null;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		int i = view.getId();
		if (i == R.id.capture_back) {
			finish();

		} else if (i == R.id.capture_btn_picture) {// 打开手机中的相册
			Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT); // "android.intent.action.GET_CONTENT"
			innerIntent.setType("image/*");
			Intent wrapperIntent = Intent.createChooser(innerIntent, "选择二维码图片");
			this.startActivityForResult(wrapperIntent, ConfigUtils.QRCODE_PICTURE_NUM);

		} else if (i == R.id.capture_btn_qrcode) {  //手动输入
			onPause();
			final BarCodeEditDialog edit_dialog = new BarCodeEditDialog(mContext, R.style.Login_dialog);
			edit_dialog.setEditOnClickL(new EditOnClickL() {
				@Override
				public void EditOnClick(final String str) {
					if ("".equals(str)) {
						edit_dialog.dismiss();
						onResume();
					} else {
						edit_dialog.dismiss();

						new Thread(new Runnable() {
							@Override
							public void run() {
								Bundle bundleData = new Bundle();
								Message m = Message.obtain();
								m.what = EDIT_BARCODE_SUC;
								bundleData.putString("mMsgNum", str);
								bundleData.putString("mMsgType", "shuru");
								bundleData.putBundle("mBundle", null);
								m.setData(bundleData);
								barHandler.sendMessage(m);
							}
						}).start();

						T.showShort(mContext, str);
					}
				}
			});
			edit_dialog.show();

		} else if (i == R.id.capture_relayout_smewm) {  //扫描二维码
			if (!mBottomChosenLists[0] == true) {
				CameraManager.scanframe = 1;
				chosenIVSrcAndTVColor(0);
				unChosenIVSrcAndTVColor(1);
				unChosenIVSrcAndTVColor(2);
				onPause();
				onResume();
			}


		} else if (i == R.id.capture_relayout_smtxm) { //扫描条形码
			if (!mBottomChosenLists[1] == true) {
				CameraManager.scanframe = 2;
				chosenIVSrcAndTVColor(1);
				unChosenIVSrcAndTVColor(0);
				unChosenIVSrcAndTVColor(2);
				onPause();
				onResume();
			}

		} else if (i == R.id.capture_relayout_dksgd) { //闪光灯
			if (mBottomChosenLists[2] == false) {
				chosenIVSrcAndTVColor(2);
				cameraManager.setTorch(true);
			} else {
				unChosenIVSrcAndTVColor(2);
				cameraManager.setTorch(false);
			}

		} else {
		}
		
	}
	
    /**
     * 设置不选中图标与字体
     */
    private void unChosenIVSrcAndTVColor(int arg) {
        mBottomTVLists.get(arg).setTextColor(getResources().getColor(R.color.capture_buttom_color_nomal));
        mBottomBTLists.get(arg).setSelected(false);
        mBottomChosenLists[arg] = false;
    }
	
	/**
     * 设置选中图标与字体
     */
    private void chosenIVSrcAndTVColor(int arg) {
        mBottomTVLists.get(arg).setTextColor(getResources().getColor(R.color.capture_buttom_color_pressde));
        mBottomBTLists.get(arg).setSelected(true);
        mBottomChosenLists[arg] = true;
    }

}