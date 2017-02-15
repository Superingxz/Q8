package com.xologood.q8pad.ui.fastoutlibrary.FastOutLibrary;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mview.customdialog.view.dialog.NormalDialog;
import com.mview.customdialog.view.dialog.listener.OnBtnClickL;
import com.mview.customdialog.view.dialog.use.QPadPromptDialogUtils;
import com.mview.customdialog.view.dialog.use.QpadProgressUtils;
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


    private static final int REQUEST_OK = 100;
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
    private boolean IsSelect = false;
    private boolean IsCommitSuccess = false;//是否确认完成成功

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
        mPresenter.InvoicingQuickInvList(SysKey, ComKey);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == InvoicingDetailActivity.RESULT_OK) {
            IsCommitSuccess = data.getBooleanExtra("isCommitSuccess", false);
        }
        if (IsCommitSuccess) {
            warehouse.setText("");
            invTime.setText("");
            checkDate.setText("");
            consignee.setText("");
            checkUserName.setText("");
            invoiceInvlist.setFieldTextAndValue("");
            mInvoicingDetailList.removeAll(mInvoicingDetailList);
            newOutInvoiceAdpter.notifyDataSetChanged();
            IsSelect = false;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.InvoicingQuickInvList(SysKey, ComKey);
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
        IsSelect = true;
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
        if (invoicingBean != null && IsSelect) {
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
        if (!IsSelect) {
            final NormalDialog IsSelect_Dialog = new NormalDialog(mContext);
            QPadPromptDialogUtils.showOnePromptDialog(IsSelect_Dialog, "未选择！", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    IsSelect_Dialog.dismiss();
                }
            });
            return;
        }
        if (mInvoicingDetailList.size() <= 0) {
            final NormalDialog IsNoDetail_Dialog = new NormalDialog(mContext);
            QPadPromptDialogUtils.showTwoPromptDialog(IsNoDetail_Dialog, "此单据没有明细，请先添加明细！", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    IsNoDetail_Dialog.dismiss();
                }
            }, new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    //添加明细
                    if (invoicingBean != null) {
                        Intent intent = new Intent(FastOutLibraryActivity.this, NewFastOutInvoiceActivity.class);
                        intent.putExtra("isOld", true);
                        intent.putExtra("InvNumber", invoicingBean.getInvNumber());
                        intent.putExtra("InvDate", invoicingBean.getInvDate());
                        intent.putExtra("ReceivingComKey", invoicingBean.getReceivingComKey());
                        intent.putExtra("ReceivingComName", invoicingBean.getReceivingComName());
                        intent.putExtra("ReceivingWarehouseId", invoicingBean.getReceivingWarehouseId());
                        startActivity(intent);
                    }
                    IsNoDetail_Dialog.dismiss();
                }
            });
            return;
        }
        if (IsZero(mInvoicingDetailList)) {
            final NormalDialog IsZero_Dialog = new NormalDialog(mContext);
            QPadPromptDialogUtils.showOnePromptDialog(IsZero_Dialog, "预计数量或者实际数量为0，不能确认完成！", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    IsZero_Dialog.dismiss();
                }
            });
            return;
        }
        Intent intent = new Intent(this, InvoicingDetailActivity.class);
        intent.putExtra("invId", mInvId + "");
        startActivityForResult(intent,REQUEST_OK);
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

    @Override
    public void startProgressDialog(String msg) {
        QpadProgressUtils.showProgress(this,msg);
    }

    @Override
    public void stopProgressDialog() {
        QpadProgressUtils.closeProgress();
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

    /**
     * 预计数量和实际数量是否为0
     * @param mInvoicingDetailList
     * @return
     */
    private boolean IsZero(List<InvoicingDetail> mInvoicingDetailList) {
        if (mInvoicingDetailList != null && mInvoicingDetailList.size() > 0) {
            for (int i = 0; i < mInvoicingDetailList.size(); i++) {
                InvoicingDetail invoicingDetail = mInvoicingDetailList.get(i);
                if (invoicingDetail.getActualQty() == 0 || invoicingDetail.getExpectedQty() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

}
