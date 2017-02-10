package com.xologood.q8pad.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

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

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.gridview)
    GridView gridview;


    private Context mContext;
    private GridView gridView;

    private int[] images = {R.mipmap.in_library, R.mipmap.out_library, R.mipmap.fast,
            R.mipmap.replace, R.mipmap.abolish, R.mipmap.return_goods, R.mipmap.log_off, R.mipmap.exit};

    private String[] texts = new String[]
            {"入库", "出库", "快捷出库", "替换", "作废", "退货", "注销", "退出"};


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
        gridView = (GridView) findViewById(R.id.gridview);

        ArrayList<HashMap<String, Object>> listImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < images.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", images[i]);
            map.put("itemText", texts[i]);
            listImageItem.add(map);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listImageItem, R.layout.gridview_item,
                new String[]{"itemImage", "itemText"}, new int[]{R.id.itemImage, R.id.itemText});

        gridView.setAdapter(simpleAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(mContext, InLibraryActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(mContext, OutLibraryActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(mContext, FastOutLibraryActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(mContext, ReplaceActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(mContext, AbolishCodeActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(mContext, ReturnGoodsActivity.class));
                        break;
                    case 6:
                        logOff();
                        break;
                    case 7:
                        exit();
                        break;
                }
            }
        });
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
                SharedPreferencesUtils.clearData(Qpadapplication.getAppContext());
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
