package com.xologood.q8pad.ui.inlibrary.oldininvoice;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.xologood.mvpframework.util.ToastUitl;
import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.R;
import com.xologood.q8pad.adapter.NewInInvoiceAdpter;
import com.xologood.q8pad.bean.Invoice;
import com.xologood.q8pad.bean.InvoicingBean;
import com.xologood.q8pad.bean.InvoicingDetail;
import com.xologood.q8pad.ui.inlibrary.newininvoice.NewInInvoiceActivity;
import com.xologood.q8pad.ui.invoicingdetail.InvoicingDetailActivity;
import com.xologood.q8pad.ui.scan.ScanActivity;
import com.xologood.q8pad.utils.SharedPreferencesUtils;
import com.xologood.q8pad.utils.StringUtils;
import com.xologood.q8pad.view.TitileView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class OldInInvoiceActivity extends BaseActivity<OldInInvoicePresenter, OldInInvoiceModel>
        implements OldInInvoiceContract.View {
    private static final String TAG = "Superingxz";
    private static final int REQUEST_SCAN = 100;
    private static final int SCAN = 100;
    @Bind(R.id.title_view)
    TitileView titleView;
    @Bind(R.id.queryOrder)
    QpadEditText queryOrder;
    @Bind(R.id.query)
    Button query;
    @Bind(R.id.invoiceInvlist)
    QpadEditText invoiceInvlist;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.wareHouse)
    TextView wareHouse;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.invDate)
    TextView invDate;
    @Bind(R.id.checkDate)
    TextView checkDate;
    @Bind(R.id.consignee)
    TextView consignee;
    @Bind(R.id.checkUserName)
    TextView checkUserName;
    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.orderForm)
    LinearLayout orderForm;
    @Bind(R.id.commit)
    Button commit;


    private String SysKey;
    private String ComKey;

    List<InvoicingBean> mInvoicingBeanList;
    private List<CommonSelectData> mCommonSelectDataList;
    private List<CommonSelectData> commonSelectDataInvoicingBeanList;
    private NewInInvoiceAdpter newInInvoiceAdpter;

    private List<InvoicingDetail> mInvoicingDetailList;
    private Invoice mInvoice;

    private InvoicingBean mInvoicingBean;

    private int mActualQty;

    private String mInvId;
    private boolean IsSelect = false;//是否选择已有
    private List<InvoicingDetail> invoicingDetailList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_old_in_invoice;
    }

    @Override
    public void initView() {
        titleView.setTitle("已有订单入库");

        mInvoicingBeanList = new ArrayList<>();

        SysKey = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.SYSKEY);
        ComKey = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.COMKEY);

        Log.i(TAG, "initView: " + ComKey);
        mPresenter.GetInvoiceInvlist(ComKey, "2", "-2", "1");

        mInvoicingDetailList = new ArrayList<>();
        newInInvoiceAdpter = new NewInInvoiceAdpter(mInvoicingDetailList, this);
        lv.setAdapter(newInInvoiceAdpter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(OldInInvoiceActivity.this, ScanActivity.class);
                InvoicingBean invoicingBean = mInvoice.getInvoicing();
                if (mInvoice != null && invoicingBean != null) {
                    intent.putExtra("ReceivingWarehouseId", invoicingBean.getReceivingWarehouseId());
                }
                InvoicingDetail invoicingDetail = (InvoicingDetail) newInInvoiceAdpter.getItem(position);
                intent.putExtra("InvDetailId", invoicingDetail.getId() + "");
                intent.putExtra("InvId", invoicingDetail.getInvId());
                intent.putExtra("ProductId", invoicingDetail.getProductId() + "");
                intent.putExtra("Batch", invoicingDetail.getBatch() + "");
                intent.putExtra("ProductName", invoicingDetail.getProductName());
                intent.putExtra("BatchNo", invoicingDetail.getBatchNO());
                intent.putExtra("CreationDate",  StringUtils.GetCreationDate(invoicingDetail.getCreationDate()));
                intent.putExtra("StandardUnitName", invoicingDetail.getStandardUnitName());
                int needScan = invoicingDetail.getExpectedQty() - invoicingDetail.getActualQty();
                intent.putExtra("NeedToScan", String.valueOf(needScan));
                if (needScan > 0) {
                    startActivityForResult(intent, SCAN);
                }
                lv.setTag(invoicingDetail);
            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        InvoicingDetail ClickInvoicingDetail = (InvoicingDetail) lv.getTag();
        if (resultCode == ScanActivity.RESULT_OK) {
            int mActualQty = data.getIntExtra("mActualQty", 0);
            newInInvoiceAdpter.updateActualQty(mActualQty, ClickInvoicingDetail.getProductName(), ClickInvoicingDetail.getBatchNO());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * @param invoicingBeanList
     */
    @Override
    public void SetInvoiceInvlist(final List<InvoicingBean> invoicingBeanList) {
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
                mPresenter.GetInvoicingDetail(mInvId);
            }
        });

    }

    @Override
    public void SetInvoicingDetail(Invoice invoice) {
        if (invoice != null) {
            IsSelect = true;
            mInvoice = invoice;
            mInvoicingBean = invoice.getInvoicing();
            invoicingDetailList = invoice.getInvoicingDetail();

            SetInvoicingBean(mInvoicingBean);

            mInvoicingDetailList.clear();
            mInvoicingDetailList.addAll(invoicingDetailList);
            newInInvoiceAdpter.notifyDataSetChanged();
        }
    }

    @Override
    public void GetInvoiceMsg(String Msg) {
        ToastUitl.showLong(Msg);
    }

    @OnClick(R.id.query)
    public void setQuery(View view) {
        mCommonSelectDataList = new ArrayList<>();
        invoiceInvlist.setFieldTextAndValue("");
        if (commonSelectDataInvoicingBeanList != null
                && commonSelectDataInvoicingBeanList.size() > 0) {
            if (!QpadJudgeUtils.isEmpty(queryOrder.getFieldText())) {
                for (int i = 0; i < commonSelectDataInvoicingBeanList.size(); i++) {
                    CommonSelectData mCommonSelectData = commonSelectDataInvoicingBeanList.get(i);
                    if (StringUtils.ifIndexOf(mCommonSelectData.getText(), queryOrder.getFieldText())
                            && !QpadJudgeUtils.isEmpty(queryOrder.getFieldText())) {
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

    private String GetInvId(String InvNumber, List<InvoicingBean> invoicingBeanList) {
        if (invoicingBeanList != null && invoicingBeanList.size() > 0) {
            for (int i = 0; i < invoicingBeanList.size(); i++) {
                InvoicingBean invoicingBean = invoicingBeanList.get(i);
                if (InvNumber.equals(invoicingBean.getInvNumber())) {
                    String invId = invoicingBean.getInvId() + "";
                    return invId;
                }
            }
        }
        return null;
    }

    private void SetInvoicingBean(InvoicingBean invoicingBean) {
        String mReceivingWarehouseName = invoicingBean.getReceivingWarehouseName();
        String mInvDate = invoicingBean.getInvDate();
        String mCheckDate = invoicingBean.getCheckDate();
        String mCheckUserName = invoicingBean.getCheckUserName();
        String mReceivingComName = invoicingBean.getReceivingComName();

        consignee.setText(mReceivingComName);
        wareHouse.setText(mReceivingWarehouseName);
        invDate.setText(mInvDate);
        checkDate.setText(mCheckDate);
        checkUserName.setText(mCheckUserName);
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
                    if (mInvoicingBean != null) {
                        Intent intent = new Intent(OldInInvoiceActivity.this, NewInInvoiceActivity.class);
                        intent.putExtra("IsOld", true);
                        intent.putExtra("InvNumber", mInvoicingBean.getInvNumber());
                        intent.putExtra("InvDate", mInvoicingBean.getInvDate());
                        intent.putExtra("WarehouseId", mInvoicingBean.getReceivingWarehouseId());
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
        Intent intent = new Intent(OldInInvoiceActivity.this, InvoicingDetailActivity.class);
        intent.putExtra("invId", mInvId);
        startActivity(intent);
        finish();

    }

    @Override
    public void startProgressDialog(String msg) {
        QpadProgressUtils.showProgress(this,msg);
    }

    @Override
    public void stopProgressDialog() {
        QpadProgressUtils.closeProgress();
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
