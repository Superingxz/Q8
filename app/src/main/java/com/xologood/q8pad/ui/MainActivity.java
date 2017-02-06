package com.xologood.q8pad.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.mview.customdialog.view.dialog.NormalDialog;
import com.mview.customdialog.view.dialog.listener.OnBtnClickL;
import com.mview.customdialog.view.dialog.use.QPadPromptDialogUtils;
import com.xologood.mvpframework.baseapp.BaseApplication;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.R;
import com.xologood.q8pad.ui.abolishcode.AbolishCodeActivity;
import com.xologood.q8pad.ui.fastoutlibrary.FastOutLibrary.FastOutLibraryActivity;
import com.xologood.q8pad.ui.inlibrary.InLibraryActivity;
import com.xologood.q8pad.ui.login.LoginInActivity;
import com.xologood.q8pad.ui.outlibrary.OutLibraryActivity;
import com.xologood.q8pad.ui.replace.ReplaceActivity;
import com.xologood.q8pad.ui.returngoods.ReturnGoodsActivity;
import com.xologood.q8pad.utils.SharedPreferencesUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {


    @Bind(R.id.ivInLibrary)
    ImageView ivInLibrary;
    @Bind(R.id.ivReplace)
    ImageView ivReplace;
    @Bind(R.id.ivLogOff)
    ImageView ivLogOff;
    @Bind(R.id.ivOutLibrary)
    ImageView ivOutLibrary;
    @Bind(R.id.abolish)
    ImageView abolish;
    @Bind(R.id.ivExit)
    ImageView ivExit;
    @Bind(R.id.ivFast)
    ImageView ivFast;
    @Bind(R.id.ivReturnGoods)
    ImageView ivReturnGoods;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(getLayoutId());
        BaseApplication.addActivity(this);
        ButterKnife.bind(this);
        initView();
    }

    public int getLayoutId() {
        return R.layout.activity_main;
    }

    public void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.ivInLibrary)
    public void ivInLibrary(View view) {
        startActivity(new Intent(this, InLibraryActivity.class));
    }

    @OnClick(R.id.ivOutLibrary)
    public void ivOutLibrary(View view) {
        startActivity(new Intent(this, OutLibraryActivity.class));
    }

    @OnClick(R.id.ivFast)
    public void ivFast(View view) {
        startActivity(new Intent(this, FastOutLibraryActivity.class));
    }


    @OnClick(R.id.ivReplace)
    public void ivReplace(View view) {
        startActivity(new Intent(this, ReplaceActivity.class));
    }

    @OnClick(R.id.abolish)
    public void abolish(View view) {
        startActivity(new Intent(this, AbolishCodeActivity.class));
    }

    @OnClick(R.id.ivReturnGoods)
    public void ivReturnGoods(View view) {
        startActivity(new Intent(this, ReturnGoodsActivity.class));
    }

    @OnClick(R.id.ivLogOff)
    public void ivLogOff(View view) {
        final NormalDialog normalDialog = new NormalDialog(this);
        String InvidMsg = "是否注销当前用户？";
        QPadPromptDialogUtils.showTwoPromptDialog(normalDialog, InvidMsg, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                normalDialog.dismiss();
            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                normalDialog.dismiss();
                SharedPreferencesUtils.clearData(Qpadapplication.getAppContext());
               Intent intent = new Intent(MainActivity.this, LoginInActivity.class);
               startActivity(intent);
                finish();
            }
        });
    }

    @OnClick(R.id.ivExit)
    public void ivExit(View view) {
        final NormalDialog normalDialog = new NormalDialog(this);
        String InvidMsg = "是否退出程序？";
        QPadPromptDialogUtils.showTwoPromptDialog(normalDialog, InvidMsg, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                normalDialog.dismiss();
            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                normalDialog.dismiss();
                BaseApplication.finishActivity();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
