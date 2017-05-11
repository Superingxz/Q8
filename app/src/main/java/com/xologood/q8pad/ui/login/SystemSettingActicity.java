package com.xologood.q8pad.ui.login;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.xologood.mvpframework.base.BaseActivity;
import com.xologood.mvpframework.util.ToastUitl;
import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.R;
import com.xologood.q8pad.utils.SharedPreferencesUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wei on 2017/3/6.
 */
public class SystemSettingActicity extends BaseActivity {
    @Bind(R.id.et_systemUrl)
    EditText etSystemUrl;
    @Bind(R.id.et_userUrl)
    EditText etUserUrl;
    @Bind(R.id.cb_in_invoice)
    CheckBox cbInInvoice;
    @Bind(R.id.cb_out_invoice)
    CheckBox cbOutInvoice;
    @Bind(R.id.cb_fast_out_invoice)
    CheckBox cbFastOutInvoice;
    @Bind(R.id.cb_replace)
    CheckBox cbReplace;
    @Bind(R.id.cb_abolish)
    CheckBox cbAbolish;
    @Bind(R.id.cb_return_goods)
    CheckBox cbReturnGoods;
    @Bind(R.id.cb_logistics)
    CheckBox cbLogistics;

    @Bind(R.id.save)
    Button save;
    @Bind(R.id.close)
    Button close;



    Boolean isOld = false;
    public String systemUrl;
    public String userUrl;


    @Override
    public int getLayoutId() {
        return R.layout.activity_system_setting;
    }

    @Override
    public void initView() {
        etSystemUrl.setText(Config.systemUrl);
        etUserUrl.setText(Config.userUrl);

        isOld = SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.ISSAVE);
        if (isOld == true) {
            setStation();
        }

    }

    private void setStation() {
        etSystemUrl.setText(SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.SPSYSTEMURL));
        etUserUrl.setText(SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.SPUSERURL));

        cbInInvoice.setChecked(SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBININVOICE));
        cbOutInvoice.setChecked(SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBOUTINVOICE));
        cbFastOutInvoice.setChecked(SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBFASTOUTINVOICE));
        cbReplace.setChecked(SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBREPLACE));
        cbAbolish.setChecked(SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBABOLISH));
        cbReturnGoods.setChecked(SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBRETURNGOODS));
        cbLogistics.setChecked(SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(),Config.CBLOGISTICS));
    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.save)
    public void save(View view) {
        systemUrl = etSystemUrl.getText().toString().trim();
        userUrl = etUserUrl.getText().toString().trim();
        if (systemUrl == null | userUrl == null) {
            ToastUitl.showShort("地址不能为空");
            return;
        }

        SharedPreferencesUtils.saveStringData(Qpadapplication.getAppContext(), Config.SPSYSTEMURL, systemUrl);
        SharedPreferencesUtils.saveStringData(Qpadapplication.getAppContext(), Config.SPUSERURL, userUrl);

        SharedPreferencesUtils.saveBooleanData(Qpadapplication.getAppContext(), Config.CBININVOICE, cbInInvoice.isChecked());
        SharedPreferencesUtils.saveBooleanData(Qpadapplication.getAppContext(), Config.CBOUTINVOICE, cbOutInvoice.isChecked());
        SharedPreferencesUtils.saveBooleanData(Qpadapplication.getAppContext(), Config.CBFASTOUTINVOICE, cbFastOutInvoice.isChecked());
        SharedPreferencesUtils.saveBooleanData(Qpadapplication.getAppContext(), Config.CBREPLACE, cbReplace.isChecked());
        SharedPreferencesUtils.saveBooleanData(Qpadapplication.getAppContext(), Config.CBABOLISH, cbAbolish.isChecked());
        SharedPreferencesUtils.saveBooleanData(Qpadapplication.getAppContext(), Config.CBRETURNGOODS, cbReturnGoods.isChecked());
        SharedPreferencesUtils.saveBooleanData(Qpadapplication.getAppContext(), Config.CBLOGISTICS, cbLogistics.isChecked());

        SharedPreferencesUtils.saveBooleanData(Qpadapplication.getAppContext(), Config.ISSAVE, true);

        ToastUitl.showShort("保存成功");
    }

    @OnClick(R.id.close)
    public void close(View view) {
        finish();
    }


}
