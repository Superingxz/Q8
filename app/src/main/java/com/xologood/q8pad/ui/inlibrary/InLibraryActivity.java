package com.xologood.q8pad.ui.inlibrary;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.xologood.mvpframework.base.BaseActivity;
import com.xologood.q8pad.R;
import com.xologood.q8pad.ui.inlibrary.newininvoice.NewInInvoiceActivity;
import com.xologood.q8pad.ui.inlibrary.oldininvoice.OldInInvoiceActivity;
import com.xologood.q8pad.view.TitleView;

import butterknife.Bind;
import butterknife.OnClick;

public class InLibraryActivity extends BaseActivity {


    @Bind(R.id.title_view)
    TitleView titleView;
    @Bind(R.id.btnOrderIn)
    Button btnOrderIn;
    @Bind(R.id.btnNewOrder)
    Button btnNewOrder;


    @Override
    public int getLayoutId() {
        return R.layout.activity_in_library;
    }

    @Override
    public void initView() {
        titleView.setTitle("产品入库");
    }

    @OnClick(R.id.btnOrderIn)
    public void onBtnOrderInClick(View view) {
        Intent intent = new Intent(this, OldInInvoiceActivity.class);
        startActivity(intent);
    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.btnNewOrder)
    public void onBtnNewOrder(View view) {
        Intent intent = new Intent(this, NewInInvoiceActivity.class);
        startActivity(intent);
    }


}
