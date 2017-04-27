package com.xologood.q8pad.ui.abolishcode;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mview.customdialog.view.dialog.NormalDialog;
import com.mview.customdialog.view.dialog.listener.OnBtnClickL;
import com.mview.customdialog.view.dialog.use.QPadPromptDialogUtils;
import com.mview.customdialog.view.dialog.use.QpadProgressUtils;
import com.mview.medittext.utils.QpadJudgeUtils;
import com.xologood.mvpframework.util.ToastUitl;
import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.R;
import com.xologood.q8pad.bean.BarCodeLog;
import com.xologood.q8pad.ui.PadActivity;
import com.xologood.q8pad.utils.SharedPreferencesUtils;
import com.xologood.q8pad.view.TitleView;
import com.xologood.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static com.xologood.q8pad.R.id.et_editywm;

public class AbolishCodeActivity extends PadActivity<AbolishPresenter, AbolishModel>
        implements AbolishCodeContract.View{
    @Bind(R.id.title_view)
    TitleView titleView;
    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.add)
    Button add;
    @Bind(et_editywm)
    EditText etEditywm;
    @Bind(R.id.llAdd)
    LinearLayout llAdd;
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
    @Bind(R.id.isContinous)
    CheckBox isContinous;

    private ArrayAdapter<String> smmAdapter;
    private List<String> smm = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_abolish_code;
    }

    @Override
    public void initView() {
        smm = new ArrayList<>();

        isContinous.setChecked(SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.ISCONTINOUS));

        smmAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, smm);
        lv.setAdapter(smmAdapter);
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        isContinous.setChecked(SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.ISCONTINOUS));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CaptureActivity.RESULT_OK) {
            List<String> continousSmm = new ArrayList<>();
            if (isContinous.isChecked()) {
                String ewm_nums = data.getStringExtra("ewm_num");
                continousSmm = GetContinousSmm(ewm_nums,smm,rbAdd.isChecked());
                smmAdapter.notifyDataSetChanged();
                if (continousSmm.size() > 0) {
                    if (rbAdd.isChecked()) {
                        information.setText(GetBarCodeString4List2(continousSmm) + "\n添加成功！");
                    } else {
                        information.setText(GetBarCodeString4List2(continousSmm) + "\n删除成功！");
                    }
                }

                return;
            }

            String ewm_num = data.getStringExtra("ewm_num");
            String ewm_type = data.getStringExtra("ewm_type");
            if (rbAdd.isChecked()) {
                if (!smm.contains(ewm_num)) {
                    smm.add(0, ewm_num);
                    smmAdapter.notifyDataSetChanged();
                    information.setText(ewm_num + "添加成功！");
                } else {
                    ToastUitl.showShort("此条码已经扫描，请重新扫码！");
                }
            } else if (rbDelete.isChecked() && smm.contains(ewm_num)) {
                smm.remove(ewm_num);
                smmAdapter.notifyDataSetChanged();
                information.setText(ewm_num + "删除成功！");
            }
            if (smm.size() > 0) {
                count.setVisibility(View.VISIBLE);
                count.setText("已扫描" + smm.size() + "条");
            } else {
                count.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void PdaBroadcastReceiver(String code) {
        if (rbAdd.isChecked()) {
            if (!smm.contains(code)) {
                smm.add(0, code);
                smmAdapter.notifyDataSetChanged();
                information.setText(code + "添加成功！");
            } else {
                ToastUitl.showShort("此条码已经扫描，请重新扫码！");
            }
        } else if (rbDelete.isChecked() && smm.contains(code)) {
            smm.remove(code);
            smmAdapter.notifyDataSetChanged();
            information.setText(code + "删除成功！");
        }
        if (smm.size() > 0) {
            count.setVisibility(View.VISIBLE);
            count.setText("已扫描" + smm.size() + "条");
        } else {
            count.setVisibility(View.GONE);
        }
    }

    /**
     * 连续扫码，如果选择添加就从已有扫码列表里添加不重复的，否则删除
     * @param ewm_nums 拼凑起来的新的扫码
     * @param smm  旧的扫码列表
     * @param isAdd
     * @return
     */
    private List<String> GetContinousSmm(String ewm_nums,List<String> smm,boolean isAdd) {
        List<String> mIscontinousSmm = new ArrayList<>();//记录成功添加或者删除的条码
        String[] mSmm = ewm_nums.split(",");
        mSmm = condition(mSmm);//删除重复的
        if (ewm_nums != null && smm != null && mSmm.length > 0) {
            for (int i = 0; i < mSmm.length; i++) {
                if (isAdd) {
                    if (!smm.contains(mSmm[i])) {
                        smm.add(0, mSmm[i]);
                        mIscontinousSmm.add(mSmm[i]);
                    }
                } else {
                    if (smm.contains(mSmm[i])) {
                        smm.remove(mSmm[i]);
                        mIscontinousSmm.add(mSmm[i]);
                    }
                }
            }
        }
        return mIscontinousSmm;
    }


    private String[] condition(String[] mSmm) {
        List<String> result = new ArrayList<>();
        boolean flag = false;
        for (int i = 0; i < mSmm.length; i++) {
            flag = false;
            for (int j = 0; j < result.size(); j++) {
                if (mSmm[i].equals(result.get(j))) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                result.add(mSmm[i]);
            }
        }
        return (String[]) result.toArray(new String[result.size()]);

    }

    @Override
    public void AbolishCodeResult(String msg) {
        if (!QpadJudgeUtils.isEmpty(msg)) {
            smm.removeAll(smm);
            smmAdapter.notifyDataSetChanged();
            final NormalDialog IsAbolishSuccess_Dialog = new NormalDialog(mContext);
            QPadPromptDialogUtils.showOnePromptDialog(IsAbolishSuccess_Dialog, msg, new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    IsAbolishSuccess_Dialog.dismiss();
                }
            });
        }
    }

    @OnClick(R.id.scanywm)
    public void scanywm(View view) {
        SharedPreferencesUtils.saveBooleanData(Qpadapplication.getAppContext(), Config.ISCONTINOUS, isContinous.isChecked());
        Intent intent = new Intent(AbolishCodeActivity.this, CaptureActivity.class);
        intent.putExtra("isContinous", isContinous.isChecked());
        startActivityForResult(intent, Config.REQUESTOK);
    }

    @OnClick(R.id.add)
    public void add(View view) {
        String edit_smm = etEditywm.getText().toString().trim();
        if (QpadJudgeUtils.isEmpty(edit_smm)) {
            ToastUitl.showShort("请输入条码！");
        } else if (!smm.contains(edit_smm)) {
            smm.add(0, edit_smm);
            smmAdapter.notifyDataSetChanged();
            count.setText("已扫描" + smm.size() + "条");
            information.setText(etEditywm.getText().toString().trim() + "添加成功！");
        } else {
            etEditywm.setText("");
            ToastUitl.showShort("已经添加此条码,请重新输入！");
        }
        etEditywm.setText("");

        if (smm.size() > 0) {
            count.setVisibility(View.VISIBLE);
            count.setText("已扫描" + smm.size() + "条");
        } else {
            count.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.upload)
    public void setUpload(View view) {
        if (smm != null && smm.size() > 0) {
            mPresenter.InvoicingAbolishCode(GetBarCodeString4List(smm));
        } else {
            final NormalDialog IsNotScan_Dialog = new NormalDialog(mContext);
            QPadPromptDialogUtils.showOnePromptDialog(IsNotScan_Dialog, "条码列表为空，请先扫码！", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    IsNotScan_Dialog.dismiss();
                }
            });
        }
        if (smm.size() > 0) {
            count.setVisibility(View.VISIBLE);
            count.setText("已扫描" + smm.size() + "条");
        } else {
            count.setVisibility(View.GONE);
        }
    }

    @Override
    public void startProgressDialog(String msg) {
        QpadProgressUtils.showProgress(this, msg);
    }

    @Override
    public void stopProgressDialog() {
        QpadProgressUtils.closeProgress();
    }

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
    /**
     * 非连续扫码时拼接
     * @param smm
     * @return
     */
    private String GetBarCodeString4List(List<String> smm) {
        StringBuffer sb = new StringBuffer();
        for (String s : smm) {
            sb.append(s).append(",");
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

    /**
     * 连续扫码时拼接
     * @param smm
     * @return
     */
    private String GetBarCodeString4List2(List<String> smm) {
        StringBuffer sb = new StringBuffer();
        for (String s : smm) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }


}
