package com.xologood.q8pad.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.mview.customdialog.view.dialog.NormalDialog;
import com.mview.customdialog.view.dialog.listener.OnBtnClickL;
import com.mview.customdialog.view.dialog.use.QPadPromptDialogUtils;
import com.xologood.mvpframework.baseapp.BaseApplication;
import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.R;
import com.xologood.q8pad.adapter.GridAdapter;
import com.xologood.q8pad.bean.GridBean;
import com.xologood.q8pad.test.TestActivity;
import com.xologood.q8pad.ui.Logistics.LogisticsActivity;
import com.xologood.q8pad.ui.abolishcode.AbolishCodeActivity;
import com.xologood.q8pad.ui.fastoutlibrary.FastOutLibrary.FastOutLibraryActivity;
import com.xologood.q8pad.ui.inlibrary.InLibraryActivity;
import com.xologood.q8pad.ui.login.LoginInActivity;
import com.xologood.q8pad.ui.outlibrary.OutLibraryActivity;
import com.xologood.q8pad.ui.replace.ReplaceActivity;
import com.xologood.q8pad.ui.returngoods.ReturnGoodsActivity;
import com.xologood.q8pad.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "test";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.gridview)
    GridView gridview;
    private Context mContext;
    private GridView gridView;

    private int[] images = {R.mipmap.in_library, R.mipmap.out_library, R.mipmap.fast,
            R.mipmap.replace, R.mipmap.abolish, R.mipmap.return_goods, R.mipmap.log_off, R.mipmap.exit,R.mipmap.ic_launcher};

    private String[] titles = new String[]
            {"入库", "出库", "快捷出库", "替换", "作废", "退货", "注销", "退出","测试"};

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

    Boolean isOld = false;

    public void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        gridView = (GridView) findViewById(R.id.gridview);
        final List<GridBean> gridBeens = new ArrayList<>();

/*        gridBeens.add(new GridBean(R.mipmap.in_library,"入库",SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBININVOICE)));
        gridBeens.add(new GridBean(R.mipmap.out_library,"出库",SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBOUTINVOICE)));
        gridBeens.add(new GridBean(R.mipmap.fast,"快捷出库",SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBFASTOUTINVOICE)));
        gridBeens.add(new GridBean(R.mipmap.replace,"替换",SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBREPLACE)));
        gridBeens.add(new GridBean(R.mipmap.abolish,"作废",SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBABOLISH)));
        gridBeens.add(new GridBean(R.mipmap.return_goods,"退货",SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBRETURNGOODS)));
        gridBeens.add(new GridBean(R.mipmap.log_off,"注销",true));
        gridBeens.add(new GridBean(R.mipmap.exit,"退出",true));*/

        initModule(gridBeens);
        Log.i(TAG, "gridBeens: "+gridBeens.toString());
        GridAdapter gridAdapter = new GridAdapter(gridBeens,mContext);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(gridBeens.size()<=position){
                    return;
                }
                String title = gridBeens.get(position).getTitle();
                switch(title){
                    case "入库":
                        startActivity(new Intent(mContext, InLibraryActivity.class));
                        break;
                    case "快捷出库":
                        startActivity(new Intent(mContext, FastOutLibraryActivity.class));
                        break;
                    case "出库":
                        startActivity(new Intent(mContext, OutLibraryActivity.class));
                        break;
                    case "替换":
                        startActivity(new Intent(mContext, ReplaceActivity.class));
                        break;
                    case "作废":
                        startActivity(new Intent(mContext, AbolishCodeActivity.class));
                        break;
                    case "退货":
                        startActivity(new Intent(mContext, ReturnGoodsActivity.class));
                        break;
                    case "物流查询":
                        startActivity(new Intent(mContext, LogisticsActivity.class));
                        break;
                    case "注销":
                        logOff();
                        break;
                    case "退出":
                        exit();
                        break;
/*                    case "测试":
                        startActivity(new Intent(mContext, TestActivity.class));
                        break;*/
                }
            }
        });

    }

    /**
     * 设置显示哪些功能模块
     * @param gridBeens
     */
    private void initModule(List<GridBean> gridBeens) {
        isOld = SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.ISSAVE);
        if(isOld==true){
            if(SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBININVOICE)==true){
                gridBeens.add(new GridBean(R.mipmap.in_library,"入库",SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBININVOICE)));
            }
            if(SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBOUTINVOICE)==true){
                gridBeens.add(new GridBean(R.mipmap.out_library,"出库",SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBOUTINVOICE)));
            }
            if(SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBFASTOUTINVOICE)==true){
                gridBeens.add(new GridBean(R.mipmap.fast,"快捷出库",SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBFASTOUTINVOICE)));
            }
            if(SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBREPLACE)==true){
                gridBeens.add(new GridBean(R.mipmap.replace,"替换",SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBREPLACE)));
            }
            if(SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBABOLISH)==true){
                gridBeens.add(new GridBean(R.mipmap.abolish,"作废",SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBABOLISH)));
            }
            if(SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBRETURNGOODS)==true){
                gridBeens.add(new GridBean(R.mipmap.return_goods,"退货",SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBRETURNGOODS)));
            }
            if(SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(),Config.CBLOGISTICS)==true){
                gridBeens.add(new GridBean(R.mipmap.logistics,"物流查询",SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.CBLOGISTICS)));
            }
        }else {
            gridBeens.add(new GridBean(R.mipmap.in_library,"入库",true));
            gridBeens.add(new GridBean(R.mipmap.out_library,"出库",true));
//            gridBeens.add(new GridBean(R.mipmap.fast,"快捷出库",true));
            gridBeens.add(new GridBean(R.mipmap.replace,"替换",true));
            gridBeens.add(new GridBean(R.mipmap.abolish,"作废",true));
            gridBeens.add(new GridBean(R.mipmap.return_goods,"退货",true));
            gridBeens.add(new GridBean(R.mipmap.logistics,"物流查询"));
        }
        gridBeens.add(new GridBean(R.mipmap.log_off,"注销",true));
        gridBeens.add(new GridBean(R.mipmap.exit,"退出",true));
//        gridBeens.add(new GridBean(R.mipmap.ic_launcher,"测试",true));
    }

    public void logOff() {
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
              //  SharedPreferencesUtils.clearData(Qpadapplication.getAppContext());
                Intent intent = new Intent(MainActivity.this, LoginInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public void exit() {
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
