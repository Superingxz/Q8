package com.xologood.q8pad.ui.fastoutlibrary.FastOutLibrary;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mview.medittext.bean.common.CommonSelectData;
import com.mview.medittext.utils.QpadJudgeUtils;
import com.mview.medittext.view.QpadEditText;
import com.xologood.mvpframework.base.BaseActivity;
import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.R;
import com.xologood.q8pad.adapter.NewOutInvoiceAdapter;
import com.xologood.q8pad.bean.Invoice;
import com.xologood.q8pad.bean.InvoicingBean;
import com.xologood.q8pad.bean.InvoicingDetail;
import com.xologood.q8pad.ui.fastoutlibrary.newfastoutinvoice.NewFastOutInvoiceActivity;
import com.xologood.q8pad.ui.invoicingdetail.InvoicingDetailActivity;
import com.xologood.q8pad.utils.SharedPreferencesUtils;
import com.xologood.q8pad.utils.StringUtils;
import com.xologood.q8pad.view.TitileView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class FastOutLibraryActivity extends BaseActivity<FastOutPresenter, FastOutModel>
        implements FastOutContract.View {


    @Bind(R.id.title_view)
    TitileView titleView;
    @Bind(R.id.qetQueryInv)
    QpadEditText qetQueryInv;
    @Bind(R.id.btQueryInv)
    Button btQueryInv;
    @Bind(R.id.addInv)
    Button addInv;
    @Bind(R.id.invoiceInvlist)
    QpadEditText invoiceInvlist;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.warehouse)
    TextView warehouse;
    @Bind(R.id.invTime)
    TextView invTime;
    @Bind(R.id.checkDate)
    TextView checkDate;
    @Bind(R.id.consignee)
    TextView consignee;
    @Bind(R.id.checkUserName)
    TextView checkUserName;
    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.addProduce)
    Button addProduce;
    @Bind(R.id.commit)
    Button commit;
    private String SysKey;
    private String ComKey;

    List<InvoicingBean> mInvoicingBeanList;
    private List<CommonSelectData> commonSelectDataInvoicingBeanList;

    private List<CommonSelectData> mCommonSelectDataList;
    private NewOutInvoiceAdapter newOutInvoiceAdpter;
    private List<InvoicingDetail> mInvoicingDetailList;
    private Invoice mInvoice;

    private int mInvId;
    private InvoicingBean invoicingBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fast_out_library;
    }

    @Override
    public void initView() {
        titleView.setTitle("快捷出库");

        mInvoicingBeanList = new ArrayList<>();

        SysKey = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.SYSKEY);
        ComKey = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.COMKEY);

        mPresenter.InvoicingQuickInvList(SysKey, ComKey);

        mInvoicingDetailList = new ArrayList<>();
        newOutInvoiceAdpter = new NewOutInvoiceAdapter(mInvoicingDetailList, this);
        lv.setAdapter(newOutInvoiceAdpter);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void SetFastOutInvoicingBean(final List<InvoicingBean> invoicingBeanList) {
        this.mInvoicingBeanList = invoicingBeanList;
        commonSelectDataInvoicingBeanList = new ArrayList<>();
        if (invoicingBeanList != null && invoicingBeanList.size() > 0) {
            for (int i = 0; i < invoicingBeanList.size(); i++) {
                InvoicingBean invoicingBean = invoicingBeanList.get(i);
                CommonSelectData commonSelectData = new CommonSelectData(invoicingBean.getInvNumber(), invoicingBean.getInvId() + "");
                commonSelectDataInvoicingBeanList.add(commonSelectData);
            }
            commonSelectDataInvoicingBeanList.add(new CommonSelectData("请选择", "-1"));
            invoiceInvlist.setLists(commonSelectDataInvoicingBeanList);
        }
        invoiceInvlist.setOnChangeListener(new QpadEditText.OnChangeListener() {
            @Override
            public void onChanged(CommonSelectData data) {
                mInvId = GetInvId(data.getText(), invoicingBeanList);
                Log.i("superingxz", "onChanged: " + mInvId);
                //获取快捷出库单据
                mPresenter.GetInvoicingDetail(mInvId + "");
            }
        });

    }

    @Override
    public void SetInvoicingDetail(Invoice invoice) {
        mInvoice = invoice;
        invoicingBean = invoice.getInvoicing();
        List<InvoicingDetail> invoicingDetailList = invoice.getInvoicingDetail();

        //设置基本信息
        SetInvoicingBean(invoicingBean);

        //设置出库明细信息
        mInvoicingDetailList.clear();
        mInvoicingDetailList.addAll(invoicingDetailList);
        newOutInvoiceAdpter.notifyDataSetChanged();
    }

    @OnClick(R.id.addInv)
    public void addInv(View view) {
        Intent intent = new Intent(this, NewFastOutInvoiceActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.addProduce)
    public void addProduce(View view) {
        if (invoicingBean != null) {
            Intent intent = new Intent(this, NewFastOutInvoiceActivity.class);
            intent.putExtra("isOld", true);
            intent.putExtra("InvNumber", invoicingBean.getInvNumber());
            intent.putExtra("InvDate", invoicingBean.getInvDate());
            intent.putExtra("ReceivingComKey", invoicingBean.getReceivingComKey());
            intent.putExtra("ReceivingComName", invoicingBean.getReceivingComName());
            intent.putExtra("ReceivingWarehouseId", invoicingBean.getReceivingWarehouseId());
            startActivity(intent);
        }
    }

    @OnClick(R.id.commit)
    public void commit(View view) {
        if(invoicingBean != null){
            Intent intent = new Intent(this, InvoicingDetailActivity.class);
            intent.putExtra("invId", mInvId + "");
            startActivity(intent);
        }
    }

    @OnClick(R.id.btQueryInv)
    public void setQuery(View view) {
        mCommonSelectDataList = new ArrayList<>();
        invoiceInvlist.setFieldTextAndValue("");
        if (commonSelectDataInvoicingBeanList != null
                && commonSelectDataInvoicingBeanList.size() > 0) {
            if (!QpadJudgeUtils.isEmpty(qetQueryInv.getFieldText())) {
                for (int i = 0; i < commonSelectDataInvoicingBeanList.size(); i++) {
                    CommonSelectData mCommonSelectData = commonSelectDataInvoicingBeanList.get(i);
                    if (StringUtils.ifIndexOf(mCommonSelectData.getText(), qetQueryInv.getFieldText())
                            && !QpadJudgeUtils.isEmpty(qetQueryInv.getFieldText())) {
                        mCommonSelectDataList.add(mCommonSelectData);

                    }
                }
            } else {
                mCommonSelectDataList.clear();
                mCommonSelectDataList.addAll(commonSelectDataInvoicingBeanList);
            }
            invoiceInvlist.setLists(mCommonSelectDataList);
        }
    }

    private int GetInvId(String InvNumber, List<InvoicingBean> invoicingBeanList) {
        if (invoicingBeanList != null && invoicingBeanList.size() > 0) {
            for (int i = 0; i < invoicingBeanList.size(); i++) {
                InvoicingBean invoicingBean = invoicingBeanList.get(i);
                if (InvNumber.equals(invoicingBean.getInvNumber())) {
                    int invId = invoicingBean.getInvId();
                    return invId;
                }
            }
        }
        return 0;
    }

    private void SetInvoicingBean(InvoicingBean invoicingBean) {
        String mReceivingWarehouseName = invoicingBean.getReceivingWarehouseName();
        String mInvDate = invoicingBean.getInvDate();
        String mCheckDate = invoicingBean.getCheckDate();
        String mCheckUserName = invoicingBean.getCheckUserName();
        String mReceivingComName = invoicingBean.getReceivingComName();

        warehouse.setText(mReceivingWarehouseName);
        invTime.setText(mInvDate);
        checkDate.setText(mCheckDate);
        checkUserName.setText(mCheckUserName);
        consignee.setText(mReceivingComName);
    }


}
