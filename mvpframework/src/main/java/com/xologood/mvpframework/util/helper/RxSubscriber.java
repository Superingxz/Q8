package com.xologood.mvpframework.util.helper;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.xologood.mvpframework.R;
import com.xologood.mvpframework.baseapp.BaseApplication;
import com.xologood.mvpframework.util.NetWorkUtils;
import com.xologood.mvpframework.widget.LoadingDialog;

import rx.Subscriber;

/**
 * des:订阅封装
 * Created by xsf
 * on 2016.09.10:16
 */
/********************使用例子********************/
/*_apiService.login(mobile, verifyCode)
        .//省略
        .subscribe(new RxSubscriber<User user>(mContext,false) {
@Override
public void _onNext(User user) {
        // 处理user
        }

@Override
public void _onError(String msg) {
        ToastUtil.showShort(mActivity, msg);
        });*/
public abstract class RxSubscriber<T> extends Subscriber<T> {

    private Context mContext;
    private String msg;
    private boolean showDialog=true;

    /**
     * 是否显示浮动dialog
     */
    public void showDialog() {
        this.showDialog= true;
    }
    public void hideDialog() {
        this.showDialog= true;
    }

    public RxSubscriber(Context context, String msg,boolean showDialog) {
        this.mContext = context;
        this.msg = msg;
        this.showDialog=showDialog;
    }
    public RxSubscriber(Context context) {
        this(context, BaseApplication.getAppContext().getString(R.string.loading),true);
    }
    public RxSubscriber(Context context,boolean showDialog) {
        this(context, BaseApplication.getAppContext().getString(R.string.loading),showDialog);
    }

    @Override
    public void onCompleted() {
        if (showDialog)
            LoadingDialog.cancelDialogForLoading();
    }
    @Override
    public void onStart() {
        super.onStart();
        if (showDialog) {
            try {
                LoadingDialog.showDialogForLoading((Activity) mContext,msg,true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onNext(T t) {
        _onNext(t);
    }
    @Override
    public void onError(Throwable e) {
        if (showDialog)
            LoadingDialog.cancelDialogForLoading();
        e.printStackTrace();
        Log.e("log", "onError: "+e.toString());
        //网络
        if (!NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
            _onError(BaseApplication.getAppContext().getString(R.string.no_net));
            Toast.makeText(BaseApplication.getAppContext(),BaseApplication.getAppContext().getString(R.string.no_net),Toast.LENGTH_SHORT).show();
        }
        //服务器
        else if (e instanceof ServerException) {
            _onError(e.getMessage());
            Toast.makeText(BaseApplication.getAppContext(),"服务器出错！",Toast.LENGTH_SHORT).show();
        }
        else if(e.toString().contains("SocketTimeoutException")){
            _onError("连接服务器超时！");
            Toast.makeText(BaseApplication.getAppContext(),"连接服务器超时！",Toast.LENGTH_SHORT).show();
        }
        //其它
        else {
//            _onError(BaseApplication.getAppContext().getString(R.string.net_error));
            _onError(e.toString());
            Toast.makeText(BaseApplication.getAppContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }

    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);

}
