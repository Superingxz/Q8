package com.xologood.q8pad.ui.returngoods;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mview.customdialog.view.dialog.NormalDialog;
import com.mview.customdialog.view.dialog.listener.OnBtnClickL;
import com.mview.customdialog.view.dialog.use.QPadPromptDialogUtils;
import com.mview.customdialog.view.dialog.use.QpadProgressUtils;
import com.xologood.mvpframework.base.BaseActivity;
import com.xologood.mvpframework.util.ToastUitl;
import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.R;
import com.xologood.q8pad.adapter.ScanBarCodeAdpater;
import com.xologood.q8pad.bean.BarCodeLog;
import com.xologood.q8pad.bean.ReturnGoodsResponse;
import com.xologood.q8pad.utils.QpadConfigUtils;
import com.xologood.q8pad.utils.SharedPreferencesUtils;
import com.xologood.q8pad.view.TitileView;
import com.xologood.zxing.activity.CaptureActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class ReturnGoodsActivity extends BaseActivity<ReturnGoodsPresenter, ReturnGoodsModel>
        implements ReturnGoodsContract.View {
    @Bind(R.id.title_view)
    TitileView titleView;
    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.rbAdd)
    RadioButton rbAdd;
    @Bind(R.id.rbDelete)
    RadioButton rbDelete;
    @Bind(R.id.information)
    TextView information;
    @Bind(R.id.count)
    TextView count;
    @Bind(R.id.scanywm)
    Button scanywm;
    @Bind(R.id.upload)
    Button upload;

    private Date date;
    private String InvDate;

    private ArrayAdapter<String> smmAdapter;
    private List<String> smm = new ArrayList<>();
    private String ComKey;
    private String ComName;
    private String UserName;
    private String UserId;
    private String SysKey;

    private int SuccessCount;//成功退货数量

    @Override
    public int getLayoutId() {
        return R.layout.activity_return_goods;
    }

    @Override
    public void initView() {

        date = new Date();
        InvDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

        smm = new ArrayList<>();
        smmAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, smm);
        lv.setAdapter(smmAdapter);

        UserName = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.USERNAME);
        UserId = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.USERID);

        SysKey = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.SYSKEY);
        ComKey = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.COMKEY);
        ComName = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.COMNAME);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void SetReturnGoodsResponse(ReturnGoodsResponse returnGoodsResponse) {
        if (returnGoodsResponse != null && returnGoodsResponse.getBarCodeLogList() != null && returnGoodsResponse.getBarCodeLogList().size() > 0) {
            smm.removeAll(smm);
            smmAdapter.notifyDataSetChanged();

            List<BarCodeLog> barCodeLogList = returnGoodsResponse.getBarCodeLogList();

            SuccessCount = GetSuccessCount(barCodeLogList);
            if (SuccessCount > 0) {
                count.setVisibility(View.VISIBLE);
                count.setText("已扫描" + SuccessCount + "条");
            } else {
                count.setVisibility(View.GONE);
            }

            ScanBarCodeAdpater scanBarCodeAdpater = new ScanBarCodeAdpater(barCodeLogList, mContext);
            View layout_scanbarcode_dialog = LayoutInflater.from(mContext).inflate(R.layout.layout_scanbarcode_dialog, null);
            final AlertDialog barCodeLogDialog = new AlertDialog.Builder(mContext, R.style.Login_dialog).create();
            barCodeLogDialog.setCanceledOnTouchOutside(false);
            barCodeLogDialog.show();
            barCodeLogDialog.getWindow().setContentView(layout_scanbarcode_dialog);
            WindowManager.LayoutParams lp_barCode = barCodeLogDialog.getWindow().getAttributes();
            lp_barCode.width = (int) (QpadConfigUtils.SCREEN.Width * 0.85);

            ListView ScanBarCodeList = (ListView) layout_scanbarcode_dialog.findViewById(R.id.scanbarcodelist);
            ScanBarCodeList.setAdapter(scanBarCodeAdpater);

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
                    information.setText(ewm_num + "添加成功！");
                } else {
                    ToastUitl.showShort("此条码已经扫描，请重新扫码！");
                }
            } else if (rbDelete.isChecked() && smm.contains(ewm_num)) {
                smm.remove(ewm_num);
                smmAdapter.notifyDataSetChanged();
                information.setText(ewm_num+"删除成功！");
            }
            if (SuccessCount > 0) {
                count.setVisibility(View.VISIBLE);
                count.setText("已扫描" + SuccessCount + "条");
            } else {
                count.setVisibility(View.GONE);
            }
//            ToastUitl.showLong("扫码类型:" + ewm_type + "一维码或者二维码:" + ewm_num);
        }
    }


    @OnClick(R.id.scanywm)
    public void scanywm(View view){
        startActivityForResult(new Intent(ReturnGoodsActivity.this, CaptureActivity.class), Config.REQUESTOK);
    }



    @OnClick(R.id.upload)
    public void setUpload(View view) {
        String CheckDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        if (smm != null && smm.size() > 0) {
            if ("160530104723186i5ya".equals(SysKey)) {
                mPresenter.ReturnGoodsDefault(UserId,
                                                UserName,
                                                getInvNumber(UserId),
                                                InvDate,
                                                "暂无",
                                                "APP",
                                                "7",
                                                "false",
                                                "4",
                                                "false",
                                                ComKey,
                                                ComName,
                                                SysKey,
                                                ComKey,
                                                CheckDate,
                                                UserId,
                                                UserName,
                                                "暂无",
                                                GetBarCodeString4List(smm));
            }else{
                mPresenter.ReturnGoods(UserId,
                                                UserName,
                                                getInvNumber(UserId),
                                                InvDate,
                                                "暂无",
                                                "APP",
                                                "7",
                                                "false",
                                                "4",
                                                "false",
                                                ComKey,
                                                ComName,
                                                SysKey,
                                                ComKey,
                                                CheckDate,
                                                UserId,
                                                UserName,
                                                "暂无",
                                                GetBarCodeString4List(smm));
            }
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

    @Override
    public void startProgressDialog(String msg) {
        QpadProgressUtils.showProgress(this,msg);
    }

    @Override
    public void stopProgressDialog() {
        QpadProgressUtils.closeProgress();
    }

    /**
     * 计算扫码成功数目
     * @param barCodeLogList
     * @return
     */
    private int GetSuccessCount(List<BarCodeLog> barCodeLogList) {
        int count = 0;
        for (int i = 0; i < barCodeLogList.size(); i++) {
            BarCodeLog barCodeLog = barCodeLogList.get(i);
            if (barCodeLog.isIsOk()) {
                count++;
            }
        }
        return count;
    }

    private String GetBarCodeString4List(List<String> smm) {
        StringBuffer sb = new StringBuffer();
        for (String s : smm) {
            sb.append(s).append(",");
        }
        return sb.toString().substring(0, sb.length() - 1);
    }
    public static String getInvNumber(String userId) {
        DecimalFormat df = new DecimalFormat("000000");
        String str = df.format(Integer.parseInt(userId));
        String time = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
        int round = (int) (Math.random() * 90) + 10;

        return "S" + str + time + round;

    }
}
