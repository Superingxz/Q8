package com.xologood.q8pad.ui.invoicingdetail;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.mview.customdialog.view.dialog.NormalDialog;
import com.mview.customdialog.view.dialog.listener.OnBtnClickL;
import com.mview.customdialog.view.dialog.use.QPadPromptDialogUtils;
import com.mview.customdialog.view.dialog.use.QpadProgressUtils;
import com.mview.medittext.view.QpadEditText;
import com.xologood.mvpframework.base.BaseActivity;
import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.R;
import com.xologood.q8pad.adapter.InvoicingDetailAdpter;
import com.xologood.q8pad.bean.Invoice;
import com.xologood.q8pad.bean.InvoicingBean;
import com.xologood.q8pad.bean.InvoicingDetail;
import com.xologood.q8pad.utils.SharedPreferencesUtils;
import com.xologood.q8pad.view.ScrollListView;
import com.xologood.q8pad.view.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class InvoicingDetailActivity extends BaseActivity<InvoicingDetailPresenter, InvoicingDetailModel>
        implements InvoicingDetailContract.View {
    public static final int INVOICINGDETAIL_OK = 105;
    @Bind(R.id.title_view)
    TitleView titleView;
    @Bind(R.id.InvId)
    QpadEditText InvId;
    @Bind(R.id.ComName)
    QpadEditText ComName;
    @Bind(R.id.receivingComName)
    QpadEditText receivingComName;
    @Bind(R.id.stateName)
    QpadEditText stateName;
    @Bind(R.id.InvByName)
    QpadEditText InvByName;
    @Bind(R.id.invTime)
    QpadEditText invTime;
    @Bind(R.id.lv)
    ScrollListView lv;
    @Bind(R.id.commit)
    Button commit;
/*    @Bind(R.id.swipe_ly)
    SwipeRefreshLayout swipeLy;*/

    private String mInvId;
    private String mUserId;
    private String mUserName;

    private List<InvoicingDetail> mNewInInvoiceList;
    private List<InvoicingDetail> invoicingDetailList;
    private InvoicingDetailAdpter invoicingDetailAdpter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public void initView() {
        mUserName = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.USERNAME);
        mUserId = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.USERID);

        mNewInInvoiceList = new ArrayList<>();

        InvId.setFieldEnabled(false);
        InvByName.setFieldEnabled(false);
        ComName.setFieldEnabled(false);
        receivingComName.setFieldEnabled(false);
        stateName.setFieldEnabled(false);
        invTime.setFieldEnabled(false);

        Intent intent = getIntent();
        mInvId = intent.getStringExtra("invId");
        mPresenter.GetInvoicingDetail(mInvId);

        invoicingDetailAdpter = new InvoicingDetailAdpter(mNewInInvoiceList, this);
        lv.setAdapter(invoicingDetailAdpter);

/*        swipeLy.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.GetInvoicingDetail(mInvId);
                swipeLy.setRefreshing(false);//标记刷新结束
            }
        });*/

    }

    @Override
    public void initListener() {

    }

    @Override
    public void SetInvoicingDetail(Invoice invoice) {
        InvoicingBean invoicingBean = invoice.getInvoicing();
        invoicingDetailList = invoice.getInvoicingDetail();

        String invIsStop = (String) invoicingBean.getInvIsStop();
        stateName.setFieldTextAndValue(invoice.getStateName());

        InvId.setFieldTextAndValue(invoicingBean.getInvNumber());
        ComName.setFieldTextAndValue(invoicingBean.getComName());
        receivingComName.setFieldTextAndValue(invoicingBean.getReceivingComName());
        InvByName.setFieldTextAndValue(invoicingBean.getInvByName());
        invTime.setFieldTextAndValue(invoicingBean.getInvDate());

        if (mNewInInvoiceList != null && mNewInInvoiceList.size() > 0) {
            mNewInInvoiceList.removeAll(mNewInInvoiceList);
        } else {
            mNewInInvoiceList.addAll(invoicingDetailList);
        }
        invoicingDetailAdpter.notifyDataSetChanged();
    }

    @Override
    public void CompliteSavaSuccess(String msg) {
        Intent intent = new Intent();
        boolean IsCommit = true;
        intent.putExtra("isCommitSuccess", IsCommit);
        setResult(INVOICINGDETAIL_OK,intent);
        final NormalDialog dialog_detail = new NormalDialog(mContext);
        QPadPromptDialogUtils.showOnePromptDialog(dialog_detail, msg, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                dialog_detail.dismiss();
                finish();
            }
        });
    }

    @OnClick(R.id.commit)
    public void commit(View view) {
        String InvidMsg;
        //判断是否一致
        if (isRight(invoicingDetailList)) {
            InvidMsg = "是否提交确认单据？";
        } else {
            InvidMsg = "实际和预计不一致，是否提交确认单据？";
        }
        final NormalDialog dialog_detail_invid = new NormalDialog(mContext);
        QPadPromptDialogUtils.showTwoPromptDialog(dialog_detail_invid, InvidMsg, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                dialog_detail_invid.dismiss();
            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                dialog_detail_invid.dismiss();
                mPresenter.CompleteSave(mInvId, mUserId, mUserName);
            }
        });
    }

/*    @Override
    public void onRefresh() {
        mPresenter.GetInvoicingDetail(mInvId);

        swipeLy.setRefreshing(false);
    }*/

    @Override
    public void startProgressDialog(String msg) {
        QpadProgressUtils.showProgress(this, msg);
    }

    @Override
    public void stopProgressDialog() {
        QpadProgressUtils.closeProgress();
    }

    private boolean isRight(List<InvoicingDetail> invoicingDetailList) {
        if (invoicingDetailList != null && invoicingDetailList.size() > 0) {
            for (int i = 0; i < invoicingDetailList.size(); i++) {
                InvoicingDetail invoicingDetail = invoicingDetailList.get(i);
                int expectedQty = invoicingDetail.getExpectedQty();
                int actualQty = invoicingDetail.getActualQty();
                if (expectedQty != actualQty) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }


}
