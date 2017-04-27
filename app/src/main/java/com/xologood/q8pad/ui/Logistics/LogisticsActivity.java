package com.xologood.q8pad.ui.Logistics;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mview.customdialog.view.dialog.use.QpadProgressUtils;
import com.xologood.mvpframework.base.BaseActivity;
import com.xologood.q8pad.R;
import com.xologood.q8pad.adapter.LogisticsAdapter;
import com.xologood.q8pad.bean.ReportInfo;
import com.xologood.q8pad.bean.ReportInv;
import com.xologood.q8pad.utils.ToastUtil;
import com.xologood.q8pad.view.TitleView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 物流查询
 */
public class LogisticsActivity extends BaseActivity<LogisticsPresenter, LogisticsModel> implements LogisticsContract.View {


    private static final String TAG = "test";
    @Bind(R.id.title_view)
    TitleView titleView;
    @Bind(R.id.barCode)
    EditText barCode;
    @Bind(R.id.ProductName)
    TextView ProductName;
    @Bind(R.id.BatchNo)
    TextView BatchNo;
    @Bind(R.id.ProductDate)
    TextView ProductDate;
    @Bind(R.id.ProductTypeName)
    TextView ProductTypeName;
    @Bind(R.id.BrandName)
    TextView BrandName;
    @Bind(R.id.bt_search)
    Button btSearch;

    @Bind(R.id.lv_Report)
    ListView lvReport;
    @Bind(R.id.ll_info)
    LinearLayout llInfo;
    @Bind(R.id.ll_noIndfo)
    LinearLayout llNoIndfo;
    @Bind(R.id.ll_noIndfo2)
    LinearLayout llNoIndfo2;

    String mBarCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_logistics;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.bt_search)
    public void search(View view) {
        mBarCode = barCode.getText().toString().trim();
        Log.i(TAG, "search: "+mBarCode);
        if (mBarCode==null|"".equals(mBarCode)) {
            Log.i(TAG, "search: "+"条码不能为空");
            ToastUtil.showShort(mContext,"条码不能为空!");
        } else {
            mPresenter.getProductDetailByBarcode(mBarCode);
            mPresenter.getInvByBarCodeList(mBarCode);
        }

    }

    @Override
    public void setProductDetailByBarcode(ReportInfo reportInfo) {
        if (reportInfo != null) {
            llInfo.setVisibility(View.VISIBLE);
            llNoIndfo.setVisibility(View.GONE);
            String productName = reportInfo.getProductName();
            ProductName.setText(productName);
            BatchNo.setText(reportInfo.getBatchNo());
            ProductDate.setText(reportInfo.getProductDate());
            ProductTypeName.setText(reportInfo.getProductTypeName());
            BrandName.setText(reportInfo.getBrandName());
        } else {
            llInfo.setVisibility(View.GONE);
            llNoIndfo.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void setInvByBarCodeList(ReportInv reportInv) {
        if(reportInv.getRecords()!=null){
            lvReport.setVisibility(View.VISIBLE);
            llNoIndfo2.setVisibility(View.GONE);
            List<ReportInv.RecordsBean> recordsList = reportInv.getRecords();
            Log.i(TAG, "setInvByBarCodeList: "+recordsList);
            LogisticsAdapter adapter = new LogisticsAdapter(mContext,recordsList);
            lvReport.setAdapter(adapter);
        }else {
            lvReport.setVisibility(View.GONE);
            llNoIndfo2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void startProgressDialog(String msg) {
        QpadProgressUtils.showProgress(this, msg);
    }

    @Override
    public void stopProgressDialog() {
        QpadProgressUtils.closeProgress();
    }

}
