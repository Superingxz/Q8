package com.xologood.q8pad.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mview.medittext.utils.QpadJudgeUtils;
import com.xologood.q8pad.R;
import com.xologood.q8pad.bean.FirstUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstUserDetail extends AppCompatActivity {

    @Bind(R.id.weChat)
    TextView weChat;
    @Bind(R.id.tel)
    TextView tel;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.back)
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_get_first_user_by_comkey);
        ButterKnife.bind(this);
        FirstUser firstUser = (FirstUser) getIntent().getSerializableExtra("firstUser");
        SetData(firstUser);
    }

    @OnClick(R.id.back)
    public void back(View view){
        finish();
    }

    private void SetData(FirstUser firstUser) {
        if (firstUser != null) {
            String mWeChat =  firstUser.getWeChat();
            String mTel = firstUser.getTel();
            String mAddress = firstUser.getAddres();
            if (!QpadJudgeUtils.isEmpty(mWeChat)) {
                weChat.setText(mWeChat);
            }
            if (!QpadJudgeUtils.isEmpty(mTel)) {
                tel.setText(mTel);
            }
            if (!QpadJudgeUtils.isEmpty(mAddress)) {
                address.setText(mAddress);
            }
        }
    }

}
