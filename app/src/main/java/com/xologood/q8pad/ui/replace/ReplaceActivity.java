package com.xologood.q8pad.ui.replace;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mview.customdialog.view.dialog.use.QpadProgressUtils;
import com.xologood.mvpframework.util.ToastUitl;
import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.R;
import com.xologood.q8pad.adapter.ReplaceAdapter;
import com.xologood.q8pad.ui.PadActivity;
import com.xologood.q8pad.utils.SharedPreferencesUtils;
import com.xologood.q8pad.view.TitleView;
import com.xologood.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class ReplaceActivity extends PadActivity<ReplacePresenter, ReplaceModel> implements ReplaceContract.View {


    @Bind(R.id.title_view)
    TitleView titleView;
    @Bind(R.id.lvOld)
    ListView lvOld;
    @Bind(R.id.lvNew)
    ListView lvNew;
    @Bind(R.id.information)
    TextView information;
    @Bind(R.id.count)
    TextView count;
    @Bind(R.id.rbAdd)
    RadioButton rbAdd;
    @Bind(R.id.rbDelete)
    RadioButton rbDelete;
    @Bind(R.id.rg)
    RadioGroup rg;
    @Bind(R.id.rbOld)
    RadioButton rbOld;
    @Bind(R.id.rbNew)
    RadioButton rbNew;
    @Bind(R.id.scanywm)
    Button scanywm;
    @Bind(R.id.replace)
    Button replace;
    @Bind(R.id.repalce_ll)
    LinearLayout repalceLl;

    boolean b = false;
    private List<String> smm;
    private ArrayAdapter<String> lvOldAdapter;
    private ArrayAdapter<String> lvNewAdapter;

    List<String> oldSmm = new ArrayList<>();
    List<String> newSmm = new ArrayList<>();
    private ReplaceAdapter replaceAdapter;
    private String code;


    @Override
    public int getLayoutId() {
        return R.layout.activity_replace;
    }

    @Override
    public void initView() {
        //扫码列表
        smm = new ArrayList<>();

        lvOldAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, oldSmm);
        lvOld.setAdapter(lvOldAdapter);

        lvNewAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, newSmm);
        lvNew.setAdapter(lvNewAdapter);

    }

    @Override
    public void initListener() {

    }

    @Override
    public void InvoicingReplaceCode(String msg) {
        ToastUitl.showLong(msg);
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
     * 扫码
     *
     * @param view
     */
    @OnClick(R.id.scanywm)
    public void setScanywm(View view) {
        startActivityForResult(new Intent(ReplaceActivity.this, CaptureActivity.class), Config.REQUESTOK);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == CaptureActivity.RESULT_OK) {
            String ewm_num = data.getStringExtra("ewm_num");
            String ewm_type = data.getStringExtra("ewm_type");

            if (rbAdd.isChecked()) {
                if(rbOld.isChecked()){
                    if (!oldSmm.contains(ewm_num)&&!newSmm.contains(ewm_num)){
                        oldSmm.add(ewm_num);
                        lvOldAdapter.notifyDataSetChanged();
                        information.setText(ewm_num + "添加成功！");
                    }else {
                        ToastUitl.showShort("改条码已在列表中");
                    }
                }if(rbNew.isChecked()){
                    if (!oldSmm.contains(ewm_num)&&!newSmm.contains(ewm_num)){
                        newSmm.add(ewm_num);
                        lvNewAdapter.notifyDataSetChanged();
                        information.setText(ewm_num + "添加成功！");
                    }else {
                        ToastUitl.showShort("改条码已在列表中");
                    }
                }else {
//                    ToastUitl.showLong("请选择条码类型！");
                }

            } else if (rbDelete.isChecked()) {
                if(oldSmm.contains(ewm_num)){
                    oldSmm.remove(ewm_num);
                    lvOldAdapter.notifyDataSetChanged();
                    information.setText(ewm_num + "删除成功！");
                }else if(newSmm.contains(ewm_num)){
                    newSmm.remove(ewm_num);
                    lvNewAdapter.notifyDataSetChanged();
                    information.setText(ewm_num + "删除成功！");
                }
            }

            count.setText("已扫描" + (oldSmm.size()+newSmm.size()) + "条");

        }
    }

    @Override
    public void onResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void PdaBroadcastReceiver(String code) {
        if (rbAdd.isChecked()) {
            if(rbOld.isChecked()){
                if (!oldSmm.contains(code)&&!newSmm.contains(code)){
                    oldSmm.add(code);
                    lvOldAdapter.notifyDataSetChanged();
                    information.setText(code + "添加成功！");
                }else {
                    ToastUitl.showShort("改条码已在列表中");
                }
            }if(rbNew.isChecked()){
                if (!oldSmm.contains(code)&&!newSmm.contains(code)){
                    newSmm.add(code);
                    lvNewAdapter.notifyDataSetChanged();
                    information.setText(code + "添加成功！");
                }else {
                    ToastUitl.showShort("改条码已在列表中");
                }
            }else {
//                ToastUitl.showLong("请选择条码类型！");
            }

        } else if (rbDelete.isChecked()) {
            if(oldSmm.contains(code)){
                oldSmm.remove(code);
                lvOldAdapter.notifyDataSetChanged();
                information.setText(code + "删除成功！");
            }else if(newSmm.contains(code)){
                newSmm.remove(code);
                lvNewAdapter.notifyDataSetChanged();
                information.setText(code + "删除成功！");
            }
        }

        count.setText("已扫描" + (oldSmm.size()+newSmm.size()) + "条");
    }

    @OnClick(R.id.replace)
    public void replace(View view){
        if(oldSmm.size()==0||newSmm.size()==0){
            ToastUitl.showShort("请输入条码！");
        }else if (oldSmm.size()!=newSmm.size()){
            ToastUitl.showShort("旧条码和新条码的数量不一致,无法替换!");
        }else {
            code = GetReplaceBarCodeString(oldSmm, newSmm);
            Log.i("code", "GetReplaceBarCodeString4List: "+code);

            String UserId = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.USERID);
            mPresenter.InvoicingReplaceCode(code,UserId);
        }
}


    private String GetReplaceBarCodeString(List<String> oldSmm,List<String> newSmm) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < oldSmm.size(); i++) {
            sb.append(oldSmm.get(i)).append(",");
            sb.append(newSmm.get(i)).append(",");
        }
        String str = sb.toString().substring(0, sb.length() - 1);
        return str;
    }


}
