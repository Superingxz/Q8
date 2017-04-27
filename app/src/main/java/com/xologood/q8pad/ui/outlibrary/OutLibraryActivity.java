package com.xologood.q8pad.ui.outlibrary;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.xologood.mvpframework.base.BaseActivity;
import com.xologood.q8pad.R;
import com.xologood.q8pad.ui.outlibrary.newoutinvoice.NewOutInvoiceActivity;
import com.xologood.q8pad.ui.outlibrary.oldoutinvoice.OldOutInvoiceActivity;
import com.xologood.q8pad.view.TitleView;

import butterknife.Bind;
import butterknife.OnClick;

public class OutLibraryActivity extends BaseActivity {

    @Bind(R.id.btnOrderOut)
    Button btnOrderOut;
    @Bind(R.id.btnNewOrderOut)
    Button btnNewOrderOut;
    @Bind(R.id.title_view)
    TitleView titleView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_out_library;
    }

    @Override
    public void initView() {
        titleView.setTitle("产品出库");
    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.btnOrderOut)
    public void setBtnOrderOut(View view) {
        startActivity(new Intent(OutLibraryActivity.this, OldOutInvoiceActivity.class));

    }

    @OnClick(R.id.btnNewOrderOut)
    public void setBtnNewOrderOut(View view) {
        startActivity(new Intent(OutLibraryActivity.this, NewOutInvoiceActivity.class));
    }
}
