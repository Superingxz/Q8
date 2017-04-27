package com.xologood.q8pad.test;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xologood.q8pad.R;
import com.xologood.q8pad.ui.PadActivity;

import butterknife.Bind;

public class TestActivity extends PadActivity {


    @Bind(R.id.state)
    TextView state;
    @Bind(R.id.btn)
    Button btn;
    @Bind(R.id.activity_test)
    RelativeLayout activityTest;


    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }


    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }


    @Override
    public void onResult(int requestCode, int resultCode, Intent data) {
        Log.e("test", "onResult: "+data);
    }

    @Override
    public void PdaBroadcastReceiver(String code) {
        Log.e("test", "PdaBroadcastReceiver: "+code);
    }
}
