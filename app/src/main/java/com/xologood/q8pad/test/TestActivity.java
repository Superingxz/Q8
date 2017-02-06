package com.xologood.q8pad.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xologood.mvpframework.base.RxManager;
import com.xologood.q8pad.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {

    @Bind(R.id.state)
    TextView state;
    @Bind(R.id.activity_test)
    RelativeLayout activityTest;

    private RxManager mRxManager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
      /*  mRxManager = new RxManager();
        mRxManager.add(Api.getInstance().apiService
                .getBean()
                .compose(RxSchedulers.<bean>io_main())
                .subscribe(new Action1<bean>() {
                    @Override
                    public void call(bean bean) {
                        if (bean != null) {
                            ToastUitl.showLong(bean.getStatus());
                            state.setText(bean.getParamz().getFeeds().get(0).getData().getSubject());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUitl.showLong("请求失败！");
                    }
                }));*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRxManager.clear();
    }
}
