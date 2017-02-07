package com.xologood.q8pad.ui.login;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mview.customdialog.view.dialog.use.QpadProgressUtils;
import com.mview.medittext.utils.QpadJudgeUtils;
import com.xologood.mvpframework.base.BaseActivity;
import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.R;
import com.xologood.q8pad.bean.Account;
import com.xologood.q8pad.ui.MainActivity;
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

    private int backCount = 1;//返回次数

    @OnClick(R.id.btnLogin)
    public void btnLogin(View view) {
       /* Log.i("superingxz", "btnLogin: 点击了登录！");
        ToastUitl.showLong("点击了登录！");
        mPresenter.getBean();*/

        //测试
        // testBean();

        login();
    }


    private void login() {
        String loginName = etUser.getText().toString().trim();
        String passWord = etPassword.getText().toString().trim();
        mPresenter.login(loginName, MD5.stringToMD5(passWord));
        if (cbStore.isChecked()) {
            SharedPreferencesUtils.saveStringData(Qpadapplication.getAppContext(), Config.LOGINNAME, loginName);
            SharedPreferencesUtils.saveStringData(Qpadapplication.getAppContext(), Config.PASSWORD, passWord);
        }
    }

    @Override
    public int getLayoutId() {

        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        String LoginName = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.LOGINNAME);
        String PassWord = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.PASSWORD);
        if (!QpadJudgeUtils.isEmpty(LoginName) && !QpadJudgeUtils.isEmpty(PassWord)) {
            etUser.setText(LoginName);
            etPassword.setText(PassWord);
        } else {
            etUser.setText("");
            etPassword.setText("");
        }
        if (Qpadapplication.getAppContext().IsLogin()&&!QpadJudgeUtils.isEmpty(LoginName) && !QpadJudgeUtils.isEmpty(PassWord)) {
            Intent intent = new Intent(LoginInActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void initListener() {

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
        QpadProgressUtils.showProgress(this,msg);
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
