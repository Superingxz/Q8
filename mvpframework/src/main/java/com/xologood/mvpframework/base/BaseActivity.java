package com.xologood.mvpframework.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.xologood.mvpframework.baseapp.BaseApplication;
import com.xologood.mvpframework.util.TUtil;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/5.
 */
public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends Activity {
    public boolean isNight;
    public T  mPresenter;
    public E mModel;
    public Context mContext;
    public int width;
    public int height;

    private ImageView ivShadow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels;
        height = metric.heightPixels;
      /*  SpUtil.init(mContext);
        isNight = SpUtil.isNight();
        setTheme(isNight ? R.style.AppThemeNight : R.style.AppThemeDay);*/
        this.setContentView(this.getLayoutId());
        BaseApplication.addActivity(this);
        ButterKnife.bind(this);
        mContext = this;
        initPM();
        this.initView();
        this.initListener();
    }

    protected void initPM(){
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if(mPresenter!=null){
            mPresenter.mContext=this;
        }
        if (this instanceof BaseView) mPresenter.setVM(this, mModel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();
        ButterKnife.unbind(this);
        BaseApplication.removeActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
     //   if (isNight != SpUtil.isNight()) reload();
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }





    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initListener();
}
