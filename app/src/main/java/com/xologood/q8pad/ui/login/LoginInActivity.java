package com.xologood.q8pad.ui.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mview.customdialog.view.dialog.use.QpadProgressUtils;
import com.mview.medittext.utils.QpadJudgeUtils;
import com.xologood.mvpframework.base.BaseActivity;
import com.xologood.mvpframework.util.ToastUitl;
import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.R;
import com.xologood.q8pad.bean.Account;
import com.xologood.q8pad.ui.MainActivity;
import com.xologood.q8pad.utils.AppUtils;
import com.xologood.q8pad.utils.MD5;
import com.xologood.q8pad.utils.SharedPreferencesUtils;
import com.xologood.zxing.utils.T;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginInActivity extends BaseActivity<LoginPresenter, LoginModel> implements LoginContract.View {
    private static final String TAG = "LoginInActivity";
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.etUser)
    EditText etUser;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.cbStore)
    CheckBox cbStore;
    @Bind(R.id.btnLogin)
    Button btnLogin;
    @Bind(R.id.tvVersionName)
    TextView tvVersionName;
    @Bind(R.id.tvSettings)
    TextView tvSettings;
    @Bind(R.id.tvRights)
    TextView tvRights;

    private int backCount = 1;//返回次数


    @OnClick(R.id.btnLogin)
    public void btnLogin(View view) {
        login();
    }

    @OnClick(R.id.tvSettings)
    public void tvSettings(View view){
        final EditText et = new EditText(mContext);
        et.setSingleLine();
        et.setHint("请输入系统设置密码");

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("系统设置")
//                .setMessage("请输入系统设置密码")
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String systemPassword = et.getText().toString().trim();
                        if("".equals(systemPassword)){
                            ToastUitl.showShort("密码不能为空");
                            return;
                        }else if (Config.SYSTEMSETTINGPASSWORD.equals(systemPassword)){
                            Intent intent = new Intent(mContext,SystemSettingActicity.class);
                            startActivity(intent);
                        }else {
                            ToastUitl.showShort("密码错误");
                        }
                    }
                })
                .setNegativeButton("取消",null)
                .create()
                .show();

    }

    private void login() {
        String loginName = etUser.getText().toString().trim();
        String passWord = etPassword.getText().toString().trim();
        mPresenter.login(loginName, MD5.stringToMD5(passWord));
        if (cbStore.isChecked()) {
            SharedPreferencesUtils.saveStringData(Qpadapplication.getAppContext(), Config.LOGINNAME, loginName);
            SharedPreferencesUtils.saveStringData(Qpadapplication.getAppContext(), Config.PASSWORD, passWord);
        }
        SharedPreferencesUtils.saveBooleanData(Qpadapplication.getAppContext(), Config.ISCHECK, cbStore.isChecked());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        tvVersionName.setText("当前版本：" + AppUtils.getVersionName(Qpadapplication.getAppContext()));
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        String LoginName = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.LOGINNAME);
        String PassWord = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.PASSWORD);
        boolean isChecked = SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.ISCHECK);
        cbStore.setChecked(isChecked);
        if (!QpadJudgeUtils.isEmpty(LoginName) && !QpadJudgeUtils.isEmpty(PassWord)
                && cbStore.isChecked()) {
            etUser.setText(LoginName);
            etPassword.setText(PassWord);
        }



    }

    @Override
    public void initData(Account account) {
        String loginMsg = account.getUserLogin().getLoginMsg();
        Log.e(TAG, "loadData: 登录信息:" + loginMsg);
        boolean isLoginOK = account.getUserLogin().isIsLoginOK();
        if (loginMsg.equals("登录成功") || isLoginOK) {
            SharedPreferencesUtils.saveStringData(Qpadapplication.getAppContext(), Config.COMKEY, account.getUserMsg().getComKey());
            SharedPreferencesUtils.saveStringData(Qpadapplication.getAppContext(), Config.COMNAME, account.getUserMsg().getCompanyName());

            SharedPreferencesUtils.saveStringData(Qpadapplication.getAppContext(), Config.SYSKEY, account.getUserMsg().getSysKey());
            SharedPreferencesUtils.saveStringData(Qpadapplication.getAppContext(), Config.ISUSE, account.getUserMsg().isUserIsUse() + "");
            SharedPreferencesUtils.saveStringData(Qpadapplication.getAppContext(), Config.USERNAME, account.getUserMsg().getUserName());
            SharedPreferencesUtils.saveStringData(Qpadapplication.getAppContext(), Config.USERID, account.getUserMsg().getUserId() + "");
            SharedPreferencesUtils.saveStringData(Qpadapplication.getAppContext(),
                    Config.RECORDERBASE,
                    account.getUserMsg().getUserId() + "");
            SharedPreferencesUtils.saveStringData(Qpadapplication.getAppContext(),
                    Config.SYSKEYBASE,
                    account.getUserMsg().getSysKey());

            Intent intent = new Intent(LoginInActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(LoginInActivity.this, "帐号密码错误！", Toast.LENGTH_LONG).show();
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

    /**
     * 重设返回次数
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    backCount = 1;
                    break;
            }
        }
    };

    @Override
    //相当于HOME键
    public void onBackPressed() {
        if (backCount == 2) {
            moveTaskToBack(true);
        } else {
            T.showShort(mContext, "再按一次退出到桌面");
            backCount++;
            mHandler.sendEmptyMessageDelayed(0, 2000);
        }
    }

}
