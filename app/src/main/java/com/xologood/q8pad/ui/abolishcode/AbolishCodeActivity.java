package com.xologood.q8pad.ui.abolishcode;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.xologood.mvpframework.base.BaseActivity;
import com.xologood.mvpframework.util.ToastUitl;
import com.xologood.q8pad.Config;
import com.xologood.q8pad.R;
import com.xologood.q8pad.view.TitileView;
import com.xologood.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static com.xologood.q8pad.R.id.et_editywm;

public class AbolishCodeActivity extends BaseActivity<AbolishPresenter, AbolishModel>
        implements AbolishCodeContract.View {
    @Bind(R.id.title_view)
    TitileView titleView;
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

    private ArrayAdapter<String> smmAdapter;
    private List<String> smm = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_abolish_code;
    }

    @Override
    public void initView() {
        smm = new ArrayList<>();
        smmAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, smm);
        lv.setAdapter(smmAdapter);
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CaptureActivity.RESULT_OK) {
            String ewm_num = data.getStringExtra("ewm_num");
            String ewm_type = data.getStringExtra("ewm_type");
            if (rbAdd.isChecked()) {
                if (!smm.contains(ewm_num)) {
                    smm.add(0,ewm_num);
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
            if (smm.size() > 0) {
                count.setVisibility(View.VISIBLE);
                count.setText("已扫描" + smm.size() + "条");
            } else {
                count.setVisibility(View.GONE);
            }
         //   ToastUitl.showLong("扫码类型:" + ewm_type + "一维码或者二维码:" + ewm_num);
        }
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
    public void scanywm(View view){
        startActivityForResult(new Intent(AbolishCodeActivity.this, CaptureActivity.class), Config.REQUESTOK);
    }

    @OnClick(R.id.add)
    public void add(View view){
        String edit_smm = etEditywm.getText().toString().trim();
        if (QpadJudgeUtils.isEmpty(edit_smm)) {
            ToastUitl.showShort("请输入条码！");
        }else if (!smm.contains(edit_smm)) {
            smm.add(0,edit_smm);
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
        QpadProgressUtils.showProgress(this,msg);
    }

    @Override
    public void stopProgressDialog() {
        QpadProgressUtils.closeProgress();
    }


    private String GetBarCodeString4List(List<String> smm) {
        StringBuffer sb = new StringBuffer();
        for (String s : smm) {
            sb.append(s).append(",");
        }
        return sb.toString().substring(0, sb.length() - 1);
    }
}
