package com.xologood.q8pad.ui.scan;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mview.customdialog.view.dialog.NormalDialog;
import com.mview.customdialog.view.dialog.listener.OnBtnClickL;
import com.mview.customdialog.view.dialog.use.QPadPromptDialogUtils;
import com.mview.medittext.utils.QpadJudgeUtils;
import com.xologood.mvpframework.base.BaseActivity;
import com.xologood.mvpframework.util.ToastUitl;
import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.R;
import com.xologood.q8pad.adapter.ScanBarCodeAdpater;
import com.xologood.q8pad.bean.BarCodeLog;
import com.xologood.q8pad.utils.QpadConfigUtils;
import com.xologood.q8pad.utils.SharedPreferencesUtils;
import com.xologood.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class ScanActivity extends BaseActivity<ScanPresenter,ScanModel> implements ScanContract.View {
     static final int SUCCESS_SCAN = 100;
    @Bind(R.id.add)
    Button add;
    @Bind(R.id.btn_editywm)
    EditText btnEditywm;
    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.close)
    Button close;
    @Bind(R.id.scanywm)
    Button scanywm;
    @Bind(R.id.upload)
    Button upload;


    @Bind(R.id.productName)
    TextView productName;
    @Bind(R.id.batchNo)
    TextView batchNo;
    @Bind(R.id.creationDate)
    TextView creationDate;
    @Bind(R.id.needToScan)
    TextView needToScan;
    @Bind(R.id.standardUnitName)
    TextView standardUnitName;
    @Bind(R.id.rbAdd)
    RadioButton rbAdd;
    @Bind(R.id.rbDelete)
    RadioButton rbDelete;

    @Bind(R.id.scan_count)
    TextView scan_count;
    @Bind(R.id.scan_msg)
    TextView scan_msg;

    private ArrayAdapter<String> smmAdapter;
    private List<String> smm = new ArrayList<>();

    private String mProductName;
    private String mBatchNo;
    private String mCreationDate;
    private String mBreationDate;
    private String mStandardUnitName;
    private String mNeedToScan;

    private String mSysKey;
    private String mComKey;
    private String mComName;
    private String mInvDetailId;
    private String mInvId;
    private String mProductId;
    private String mBatch;
    private String mReceivingWarehouseId;

    private int SuccessCount; //成功扫描数目

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan;
    }

    @Override
    public void initView() {
        mSysKey = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.SYSKEY);
        mComKey = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.COMKEY);
        mComName = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.COMNAME);

        smm = new ArrayList<>();
        smmAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, smm);
        lv.setAdapter(smmAdapter);

        Intent intent = getIntent();
        mInvDetailId = intent.getStringExtra("InvDetailId");
        mInvId = intent.getStringExtra("InvId");
        mProductId = intent.getStringExtra("ProductId");
        mBatch = intent.getStringExtra("Batch");
        mProductName = intent.getStringExtra("ProductName");
        mBatchNo = intent.getStringExtra("BatchNo");
        mReceivingWarehouseId = intent.getStringExtra("ReceivingWarehouseId");
        mCreationDate = intent.getStringExtra("CreationDate");
        mStandardUnitName = intent.getStringExtra("StandardUnitName");
        mNeedToScan = intent.getStringExtra("NeedToScan");

        productName.setText(mProductName);
        batchNo.setText(mBatchNo);
        creationDate.setText(mCreationDate);
        standardUnitName.setText(mStandardUnitName);
        needToScan.setText(mNeedToScan);

    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.scanywm)
    public void scanywm(View view) {
        startActivityForResult(new Intent(ScanActivity.this, CaptureActivity.class), Config.REQUESTOK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CaptureActivity.RESULT_OK) {
            String ewm_num = data.getStringExtra("ewm_num");
            String ewm_type = data.getStringExtra("ewm_type");
            if (rbAdd.isChecked()) {
                if (!smm.contains(ewm_num)) {
                    smm.add(ewm_num);
                    smmAdapter.notifyDataSetChanged();
                    scan_msg.setText(ewm_num + "添加成功！");
                } else {
                    ToastUitl.showShort("此条码已经扫描，请重新扫码！");
                }
            } else if (rbDelete.isChecked() && smm.contains(ewm_num)) {
                smm.remove(ewm_num);
                smmAdapter.notifyDataSetChanged();
                scan_msg.setText(ewm_num+"删除成功！");
            }
            if (smm.size() > 0) {
                scan_count.setVisibility(View.VISIBLE);
                scan_count.setText("已扫描" + smm.size() + "条");
            } else {
                scan_count.setVisibility(View.GONE);
            }
//            ToastUitl.showLong("扫码类型:" + ewm_type + "一维码或者二维码:" + ewm_num);
        }
    }

    @Override
    public void SetBarCodeList(List<BarCodeLog> barCodeLogList) {
        if (barCodeLogList != null && barCodeLogList.size() > 0) {
            smm.removeAll(smm);
            smmAdapter.notifyDataSetChanged();
            SuccessCount = GetSuccessCount(barCodeLogList);
            ScanBarCodeAdpater scanBarCodeAdpater = new ScanBarCodeAdpater(barCodeLogList, mContext);
            View layout_scanbarcode_dialog = LayoutInflater.from(mContext).inflate(R.layout.layout_scanbarcode_dialog, null);
            final AlertDialog barCodeLogDialog = new AlertDialog.Builder(mContext, R.style.Login_dialog).create();
            barCodeLogDialog.setCanceledOnTouchOutside(false);
            barCodeLogDialog.show();
            barCodeLogDialog.getWindow().setContentView(layout_scanbarcode_dialog);
            WindowManager.LayoutParams lp_barCode = barCodeLogDialog.getWindow().getAttributes();
            lp_barCode.width = (int) (QpadConfigUtils.SCREEN.Width * 0.85);
            ListView ScanBArCodeList = (ListView) layout_scanbarcode_dialog.findViewById(R.id.scanbarcodelist);
            ScanBArCodeList.setAdapter(scanBarCodeAdpater);

            Button back = (Button) layout_scanbarcode_dialog.findViewById(R.id.back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(barCodeLogDialog!=null && barCodeLogDialog.isShowing()){
                        barCodeLogDialog.dismiss();
                    }
                }
            });
        }
    }

    private int GetSuccessCount(List<BarCodeLog> barCodeLogList) {
        int count = 0;
        for (int i = 0; i < barCodeLogList.size(); i++) {
            BarCodeLog barCodeLog = barCodeLogList.get(i);
            if ("true".equals(barCodeLog.isIsOk())) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void UploadBarCodeError(String msg) {
        ToastUitl.showLong(msg);
    }

    @OnClick(R.id.add)
    public void add(View view){
        String edit_smm = btnEditywm.getText().toString().trim();
        if (QpadJudgeUtils.isEmpty(edit_smm)) {
            ToastUitl.showShort("请输入条码！");
        }else if (!smm.contains(edit_smm)) {
            smm.add(edit_smm);
            smmAdapter.notifyDataSetChanged();
            scan_count.setText("已扫描" + smm.size() + "条");
            scan_msg.setText(btnEditywm.getText().toString().trim() + "添加成功！");
        } else {
            btnEditywm.setText("");
            ToastUitl.showShort("已经添加此条码,请重新输入！");
        }
        if (smm.size() > 0) {
            scan_count.setVisibility(View.VISIBLE);
            scan_count.setText("已扫描" + smm.size() + "条");
        } else {
            scan_count.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.upload)
    public void setUpload(View view) {
        if (smm != null && smm.size() > 0) {
            mPresenter.GetBarCodeLogList(GetBarCodeString4List(smm),
                    mInvId,
                    mInvDetailId,
                    mProductId,
                    mBatch,
                    mComKey,
                    mComName,
                    mSysKey,
                    mReceivingWarehouseId);
        } else {
            final NormalDialog IsNotScan_Dialog = new NormalDialog(mContext);
            QPadPromptDialogUtils.showOnePromptDialog(IsNotScan_Dialog, "条码列表为空，请先扫码！", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    IsNotScan_Dialog.dismiss();
                }
            });
        }
    }

    @OnClick(R.id.close)
    public void close(View view){
        Intent intent = new Intent();
        intent.putExtra("mActualQty", SuccessCount);
        setResult(RESULT_OK,intent);
        finish();
    }

    private String GetBarCodeString4List(List<String> smm) {
        StringBuffer sb = new StringBuffer();
        for (String s : smm) {
            sb.append(s).append(",");
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ||keyCode == KeyEvent.KEYCODE_HOME) {
            Intent intent = new Intent();
            intent.putExtra("mActualQty", SuccessCount);
            setResult(RESULT_OK,intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
