package com.xologood.q8pad.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.device.ScanDevice;
import android.device.ScanManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.xologood.mvpframework.base.BaseActivity;
import com.xologood.mvpframework.base.BaseModel;
import com.xologood.mvpframework.base.BasePresenter;
import com.xologood.mvpframework.base.BaseView;
import com.xologood.mvpframework.util.TUtil;

/**
 * Created by sa on 2016/4/27.
 */
public abstract class PadActivity<T extends BasePresenter, E extends BaseModel> extends BaseActivity {
    public T  mPresenter;
    public E mModel;

    protected final int SCANNIN_CODE = 04343;

    private boolean isDPA;// 判断是否是DPA
    private ScanManager mScanManager;
    private Vibrator vibrator;

    private  SeuicScanReceiver mSeuicReceiver;
    private boolean isBrano;

    private static  final  String  brano_model="u8000s";

    ScanDevice sm;

    @Override
    protected void initPM() {
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if(mPresenter!=null){
            mPresenter.mContext=this;
        }
        if (this instanceof BaseView) mPresenter.setVM(this, mModel);
    }

    private boolean ispda(){
        String model = Build.MODEL;
        if ("i6200S".equals(model))return true;
        if ("i6300A".equals(model))return true;
        if ("AUTOID9".equals(model))return true;
//        if ("(全新)A9-S8".equals(model))return true;
//        if ("(全新)a9-S8".equals(model))return true;
        Log.i("model","pda:"+model+"-----"+Build.FINGERPRINT );
        return false;
    }
    /**
     * brano
     */
    private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            byte[] barocode = intent.getByteArrayExtra("barocode");
            int barocodelen = intent.getIntExtra("length", 0);
            byte temp = intent.getByteExtra("barcodeType", (byte) 0);
            Log.i("debug", "----codetype--" + temp);
           String barcodeStr = new String(barocode, 0, barocodelen);
            PdaBroadcastReceiver(handleCodeString(barcodeStr.trim()));
            sm.stopScan();
        }

    };

    /**
     *  Seuic
     */
    class SeuicScanReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String name = intent.getExtras().getString("scannerdata");
            PdaBroadcastReceiver(handleCodeString(name.trim()));
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(savedInstanceState);
        isDPA=ispda();
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        String  model = Build.MODEL.toLowerCase();
        if (model.equals(brano_model)){
            isBrano =true;
        }

        Seuic();

        /**
         * brano无列入pad
         */
        if (isBrano){
            sm = new ScanDevice();
            sm.setOutScanMode(0);
        }

    }

    private void  Seuic(){
        mSeuicReceiver = new SeuicScanReceiver();
        IntentFilter filter = new IntentFilter();// 创建IntentFilter对象
        filter.addAction("a001");
//        filter.addAction("com.android.server.scannerservice.broadcast");
        registerReceiver(mSeuicReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mSeuicReceiver);
        super.onDestroy();
    }


//    public abstract void setContentView(Bundle savedInstanceState);


    @Override
    protected void onResume() {
        super.onResume();
        if (isDPA) {
            mScanManager = new ScanManager();
            mScanManager.openScanner();
            mScanManager.switchOutputMode(0);

            IntentFilter filter = new IntentFilter();
            filter.addAction("urovo.rcv.message");
            registerReceiver(scanBroad, filter);

        }

        if(isBrano){
            IntentFilter filter1 = new IntentFilter();
            filter1.addAction("scan.rcv.message");
            registerReceiver(mScanReceiver, filter1);
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (isDPA) {
            unregisterReceiver(scanBroad);
            if (mScanManager != null) {
                mScanManager.stopDecode();
            }
        }

        if (isBrano){
            if(sm != null) {
                sm.stopScan();
            }
            unregisterReceiver(mScanReceiver);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCANNIN_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String code = bundle.getString("result").trim();
            PdaBroadcastReceiver(handleCodeString(code));
        } else {
            onResult(requestCode, resultCode, data);
        }
    }

    public abstract void onResult(int requestCode, int resultCode, Intent data);

    //接收scan按钮扫描的二维码
    private BroadcastReceiver scanBroad = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                PdaBroadcastReceiver(intent);
            }catch (Exception e){
                Toast.makeText(PadActivity.this,"出现异常："+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }

    };

    // 让子类重新的方法，pda”scan“按钮的扫描结果处理方法
    public void PdaBroadcastReceiver(Intent intent) {
        byte[] mcode = intent.getByteArrayExtra("barcode");
        int barocodelen = intent.getIntExtra("length", 0);
        String barcodeStr = new String(mcode, 0, barocodelen);
        PdaBroadcastReceiver(handleCodeString(barcodeStr));
    }

    // 让子类重新的方法，pda”scan“按钮的扫描结果处理方法
    public abstract void PdaBroadcastReceiver(String code);

    // 处理扫描链接二维码的字符串
    public String handleCodeString(String code) {
        String barcode;
        if (code.indexOf("http://") == 0) {
            String temp = code.substring(code.indexOf("=") + 1);
            barcode = temp;
        } else {
            barcode = code;
        }
        return barcode;
    }

    public void setVibrator(long time) {
        if(vibrator == null){
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        }
        if(vibrator == null){
            return;
        }
        vibrator.vibrate(time);
    }

}
