package com.xologood.q8pad.ui.login;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.xologood.mvpframework.util.helper.RxSchedulers;
import com.xologood.mvpframework.util.helper.RxSubscriber;
import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.R;
import com.xologood.q8pad.api.Api;
import com.xologood.q8pad.api.HostType;
import com.xologood.q8pad.bean.Account;
import com.xologood.q8pad.bean.BaseResponse;
import com.xologood.q8pad.bean.Version;
import com.xologood.q8pad.runtimepermissions.PermissionsManager;
import com.xologood.q8pad.runtimepermissions.PermissionsResultAction;
import com.xologood.q8pad.ui.MainActivity;
import com.xologood.q8pad.utils.AppUtils;
import com.xologood.q8pad.utils.MD5;
import com.xologood.q8pad.utils.SharedPreferencesUtils;
import com.xologood.q8pad.utils.ToastUtil;
import com.xologood.q8pad.view.LoadDialog;
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
        String loginName = etUser.getText().toString().trim();
        String passWord = etPassword.getText().toString().trim();

        mPresenter.login(loginName, MD5.stringToMD5(passWord));
        if (cbStore.isChecked()) {
            SharedPreferencesUtils.saveStringData(Qpadapplication.getAppContext(), Config.LOGINNAME, loginName);
            SharedPreferencesUtils.saveStringData(Qpadapplication.getAppContext(), Config.PASSWORD, passWord);
        }
        SharedPreferencesUtils.saveBooleanData(Qpadapplication.getAppContext(), Config.ISCHECK, cbStore.isChecked());
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


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }


    protected LoadDialog mLoad;
    /*
     * 版本升级接口,获取当前最新版本号
     */
    private void getnewversionno() {
        mLoad.show(mContext);

        String recorderBase = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.RECORDERBASE);
        String sysKeyBase = "150623155902966stlt";
        Api.getLoginInInstance(HostType.USERURL, recorderBase, sysKeyBase).CheckVersion()
                .compose(RxSchedulers.<BaseResponse<Version>>io_main())
                .subscribe(new RxSubscriber<BaseResponse<Version>>(mContext,false) {
                    @Override
                    protected void _onNext(BaseResponse<Version> versionBaseResponse) {
                        View inflate = LayoutInflater.from(mContext).inflate(R.layout.dialog_updata, null);
                        TextView textView = (TextView) inflate.findViewById(R.id.tv_message);
                        textView.setText(versionBaseResponse.getData().getTitle());

                        int versionCode = AppUtils.getVersionCode(mContext);
                        int newVerson = versionBaseResponse.getData().getM2();
                        final String downloadurl = versionBaseResponse.getData().getUrl();

                        if(versionCode <newVerson){
                            new AlertDialog.Builder(mContext).setView(inflate).setPositiveButton("下载安装", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ToastUtil.showShort(mContext, getString(R.string.downloading_apk));
/*                                    UpdateService.Builder.create(downloadurl)
                                            .setStoreDir("update/flag")
                                            .setDownloadSuccessNotificationFlag(Notification.DEFAULT_ALL)
                                            .setDownloadErrorNotificationFlag(Notification.DEFAULT_ALL)
                                            .build(mContext);*/

                                    //让系统自带的浏览器去下载
                                    Intent intent = new Intent();
                                    intent.setAction("android.intent.action.VIEW");
                                    Uri content_url = Uri.parse(downloadurl);
                                    intent.setData(content_url);
                                    startActivity(intent);
                                }
                            }).setNegativeButton("取消", null).show();
                        }else {
//                            ToastUtil.showShort(mContext, "当前已经是最新版本！");
                        }
                        mLoad.dismiss(mContext);
                    }

                    @Override
                    protected void _onError(String message) {
                        mLoad.dismiss(mContext);
                    }
                });
    }

    @Override
    public void initView() {
        tvVersionName.setText("当前版本：" + AppUtils.getVersionName(Qpadapplication.getAppContext()));


/*        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions();
            int checkPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkPermission == PackageManager.PERMISSION_GRANTED) {
                getnewversionno();
            }
        } else {
            getnewversionno();
        }*/

    }


    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
                getnewversionno();
            }

            @Override
            public void onDenied(String permission) {
//                Toast.makeText(mContext, "缺少" + permission + "会导致部分功能无法使用", Toast.LENGTH_SHORT).show();
            }
        });
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
