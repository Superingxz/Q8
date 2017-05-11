package com.xologood.q8pad.ui.outlibrary.oldoutinvoice;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import com.xologood.q8pad.adapter.InvoicingBeanAdapter;
import com.xologood.q8pad.adapter.NewOutInvoiceAdapter;
import com.xologood.q8pad.bean.BarCodeLog;
import com.xologood.q8pad.bean.FirstUser;
import com.xologood.q8pad.bean.Invoice;
import com.xologood.q8pad.bean.InvoicingBean;
import com.xologood.q8pad.bean.InvoicingDetail;
import com.xologood.q8pad.ui.invoicingdetail.InvoicingDetailActivity;
import com.xologood.q8pad.ui.outlibrary.newoutinvoice.NewOutInvoiceActivity;
import com.xologood.q8pad.utils.QpadConfigUtils;
import com.xologood.q8pad.utils.SharedPreferencesUtils;
import com.xologood.q8pad.utils.StringUtils;
import com.xologood.q8pad.view.ScrollListView;
import com.xologood.q8pad.view.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class OldOutInvoiceActivity extends BaseActivity<OldOutInvoicePresenter, OldOutInvoiceModel>
        implements OldOutInvoiceContract.View {
    private static final int SCAN = 100;
    private static final int REQUEST_OK = 101;
    @Bind(R.id.title_view)
    TitleView titleView;
    @Bind(R.id.queryOrder)
    QpadEditText queryOrder;
    @Bind(R.id.query)
    Button query;
    @Bind(R.id.invoiceInvlist)
    QpadEditText invoiceInvlist;
    @Bind(R.id.information)
    Button information;
    @Bind(R.id.textView4)
    TextView textView4;
    @Bind(R.id.wareHouse)
    TextView wareHouse;
    @Bind(R.id.textView6)
    TextView textView6;
    @Bind(R.id.invDate)
    TextView invDate;
    @Bind(R.id.checkDate)
    TextView checkDate;
    @Bind(R.id.consignee)
    TextView consignee;
    @Bind(R.id.checkUserName)
    TextView checkUserName;
    @Bind(R.id.lv)
    ScrollListView lv;
    @Bind(R.id.orderForm)
    LinearLayout orderForm;
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

    private boolean IsSelect = false;//是否已经选择单据

    //选择后的获取数据
    private String mComKey;
    private String mReceivingComKey;
    private String mRecorderBase;
    private String mSysKeyBase;
    private boolean IsCommitSuccess = false;//是否确认完成成功

    private InvoicingBeanAdapter invoicingBeanAdapter;
    private List<InvoicingBean> queryInvoicingBeanList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_old_out_invoice;
    }

    @Override
    public void initView() {
        titleView.setTitle("已有订单出库");

        mInvoicingBeanList = new ArrayList<>();
        queryInvoicingBeanList = new ArrayList<>();

        SysKey = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.SYSKEY);
        ComKey = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.COMKEY);


        mPresenter.GetInvoiceInvlist(ComKey, "2", "-2", "2");

        mInvoicingDetailList = new ArrayList<>();
        newOutInvoiceAdpter = new NewOutInvoiceAdapter(mInvoicingDetailList, this);
        lv.setAdapter(newOutInvoiceAdpter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(OldOutInvoiceActivity.this, NewOutInvoiceActivity.class);
                InvoicingBean invoicingBean = mInvoice.getInvoicing();
                if (mInvoice != null && invoicingBean != null) {
                    intent.putExtra("isOld", true);
                    intent.putExtra("invId", mInvId);
                    intent.putExtra("InvNumber", invoicingBean.getInvNumber());
                    intent.putExtra("InvDate", invoicingBean.getInvDate());
                    intent.putExtra("ReceivingWarehouseId", invoicingBean.getReceivingWarehouseId());
                    intent.putExtra("ReceivingComKey", invoicingBean.getReceivingComKey());
                    intent.putExtra("ReceivingComName", invoicingBean.getReceivingComName());
                }
                startActivityForResult(intent,REQUEST_OK);
            }
        });
        //没有数据重新获取
        invoiceInvlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commonSelectDataInvoicingBeanList.size() == 0) {
                    mPresenter.GetInvoiceInvlist(ComKey, "2", "-2", "2");
                }
            }
        });
    }

    @Override
    public void initListener() {
     /*   invoiceInvlist.setOnChangeListener(new QpadEditText.OnChangeListener() {
            @Override
            public void onChanged(CommonSelectData data) {
                mInvId = GetInvId(data.getText(), mInvoicingBeanList);
                Log.i("superingxz", "onChanged: " + mInvId);
                mPresenter.GetInvoicingDetail(mInvId + "");
            }
        });*/

        invoiceInvlist.setOnDialogClickLister(new QpadEditText.OnDialogClickLister() {
            @Override
            public void OnDialogClick() {
                //清空然后重新添加
                if (queryInvoicingBeanList.size() > 0) {
                    queryInvoicingBeanList.removeAll(queryInvoicingBeanList);
                }
                queryInvoicingBeanList.addAll(mInvoicingBeanList);

                invoicingBeanAdapter = new InvoicingBeanAdapter(queryInvoicingBeanList, mContext);
                View layout_queryinvoicingbeanlist = LayoutInflater.from(mContext).inflate(R.layout.layout_invoicingbeanlist, null);
                final AlertDialog invoicingbeanDialog = new AlertDialog.Builder(mContext, R.style.Login_dialog).create();
                invoicingbeanDialog.setView(new EditText(mContext));
                invoicingbeanDialog.setCanceledOnTouchOutside(false);
                invoicingbeanDialog.show();
                invoicingbeanDialog.getWindow().setContentView(layout_queryinvoicingbeanlist);
                WindowManager.LayoutParams lp_invoicingbean = invoicingbeanDialog.getWindow().getAttributes();
                lp_invoicingbean.width = (int) (width * 0.85);
                lp_invoicingbean.height = (int) (height * 0.85);
                invoicingbeanDialog.getWindow().setAttributes(lp_invoicingbean);
                final QpadEditText etInvNumber = (QpadEditText) layout_queryinvoicingbeanlist.findViewById(R.id.InvNumber);
                Button queryInvoicingBean = (Button) layout_queryinvoicingbeanlist.findViewById(R.id.queryInvNumber);
                final ListView invoicingBeanListView = (ListView) layout_queryinvoicingbeanlist.findViewById(R.id.InvoicingBeanList);
                invoicingBeanListView.setAdapter(invoicingBeanAdapter);
                queryInvoicingBean.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击查询按钮查询
                        if (queryInvoicingBeanList.size() > 0) {
                            queryInvoicingBeanList.removeAll(queryInvoicingBeanList);
                        }
                        if (mInvoicingBeanList != null && mInvoicingBeanList.size() > 0) {
                            queryInvoicingBeanList.addAll(queryInvoicingBeanList(etInvNumber.getFieldText(), mInvoicingBeanList));
                        }
                        invoicingBeanAdapter.notifyDataSetChanged();
                    }
                });
                invoicingBeanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        invoicingbeanDialog.dismiss();
                        InvoicingBean invoicingBean = queryInvoicingBeanList.get(position);
                        mInvId = invoicingBean.getInvId();
                        invoiceInvlist.setFieldTextAndValue(invoicingBean.getInvNumber(), invoicingBean.getInvId() + "");
                        mPresenter.GetInvoicingDetail(mInvId+"");
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == NewOutInvoiceActivity.NEWOUTINVOICE_OK
                ||resultCode == InvoicingDetailActivity.INVOICINGDETAIL_OK) {
            IsCommitSuccess = data.getBooleanExtra("isCommitSuccess", false);
            if (IsCommitSuccess) {
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!IsCommitSuccess && mInvId != 0) {
            mPresenter.GetInvoicingDetail(mInvId + "");
        }
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
            invoiceInvlist.setLists(commonSelectDataInvoicingBeanList);
        }
    }

    @Override
    public void SetInvoicingDetail(Invoice invoice) {
        if (invoice != null) {
            IsSelect = true;
            mInvoice = invoice;
            InvoicingBean invoicingBean = invoice.getInvoicing();
            List<InvoicingDetail> invoicingDetailList = invoice.getInvoicingDetail();

            //设置基本信息
            SetInvoicingBean(invoicingBean);

            //设置出库明细信息
            mInvoicingDetailList.clear();
            mInvoicingDetailList.addAll(invoicingDetailList);
            newOutInvoiceAdpter.notifyDataSetChanged();
        }

    }

    @Override
    public void GetInvoiceMsg(String Msg) {
        ToastUitl.showLong(Msg);
    }

    @Override
    public void SetFirstUserByComKey(FirstUser firstUser) {
        /*Intent intent = new Intent(OldOutInvoiceActivity.this, FirstUserDetail.class);
        intent.putExtra("firstUser", firstUser);
        startActivity(intent);*/
        if (!IsSelect || QpadJudgeUtils.isEmpty(mInvId)) {
            final NormalDialog IsNotSelect_Dialog = new NormalDialog(mContext);
            QPadPromptDialogUtils.showOnePromptDialog(IsNotSelect_Dialog, "未选择！", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    IsNotSelect_Dialog.dismiss();
                }
            });
            return;
        }

        final NormalDialog IsNotFirstUser_Dialog = new NormalDialog(mContext);
        if (firstUser != null) {
            String mWeChat = firstUser.getWeChat();
            String mTel = firstUser.getTel();
            String mAddress = firstUser.getAddres();
            if (QpadJudgeUtils.isEmpty(mWeChat) && QpadJudgeUtils.isEmpty(mTel) && QpadJudgeUtils.isEmpty(mAddress)) {
                QPadPromptDialogUtils.showOnePromptDialog(IsNotFirstUser_Dialog, "暂无信息！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        IsNotFirstUser_Dialog.dismiss();
                    }
                });
                return;
            }
            View layout_get_first_user_by_comkey_dialog = LayoutInflater.from(mContext).inflate(R.layout.layout_get_first_user_by_comkey, null);
            final AlertDialog firstUserDialog = new AlertDialog.Builder(mContext, R.style.Login_dialog).create();
            firstUserDialog.setCanceledOnTouchOutside(false);
            firstUserDialog.show();
            firstUserDialog.getWindow().setContentView(layout_get_first_user_by_comkey_dialog);
            WindowManager.LayoutParams lp_firstUser = firstUserDialog.getWindow().getAttributes();
            lp_firstUser.width = (int) (QpadConfigUtils.SCREEN.Width * 0.85);
            Button back = (Button) layout_get_first_user_by_comkey_dialog.findViewById(R.id.back);
            TextView weChat = (TextView) layout_get_first_user_by_comkey_dialog.findViewById(R.id.weChat);
            TextView tel = (TextView) layout_get_first_user_by_comkey_dialog.findViewById(R.id.tel);
            TextView address = (TextView) layout_get_first_user_by_comkey_dialog.findViewById(R.id.address);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (firstUserDialog != null && firstUserDialog.isShowing()) {
                        firstUserDialog.dismiss();
                    }
                }
            });
            if (!QpadJudgeUtils.isEmpty(mWeChat)) {
                weChat.setVisibility(View.VISIBLE);
                weChat.setText("微信号:" + mWeChat);
            } else {
                weChat.setVisibility(View.GONE);
            }

            if (!QpadJudgeUtils.isEmpty(mTel)) {
                tel.setVisibility(View.VISIBLE);
                tel.setText("电话:" + mTel);
            } else {
                tel.setVisibility(View.GONE);
            }

            if (!QpadJudgeUtils.isEmpty(mAddress)) {
                address.setVisibility(View.VISIBLE);
                address.setText("地址:" + mAddress);
            } else {
                address.setVisibility(View.GONE);
            }
        } else {
            QPadPromptDialogUtils.showOnePromptDialog(IsNotFirstUser_Dialog, "暂无信息！", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    IsNotFirstUser_Dialog.dismiss();
                }
            });
        }
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

        mComKey = invoicingBean.getComKey();
        mReceivingComKey = invoicingBean.getReceivingComKey();
        mRecorderBase = String.valueOf(invoicingBean.getRecorderBase());
        mSysKeyBase = String.valueOf(invoicingBean.getSysKeyBase());

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
                    Intent intent = new Intent(OldOutInvoiceActivity.this, NewOutInvoiceActivity.class);
                    InvoicingBean invoicingBean = mInvoice.getInvoicing();
                    if (mInvoice != null && invoicingBean != null) {
                        intent.putExtra("isOld", true);
                        intent.putExtra("invId", mInvId);
                        intent.putExtra("InvNumber", invoicingBean.getInvNumber());
                        intent.putExtra("InvDate", invoicingBean.getInvDate());
                        intent.putExtra("ReceivingWarehouseId", invoicingBean.getReceivingWarehouseId());
                        intent.putExtra("ReceivingComKey", invoicingBean.getReceivingComKey());
                        intent.putExtra("ReceivingComName", invoicingBean.getReceivingComName());
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
        Intent intent = new Intent(OldOutInvoiceActivity.this, InvoicingDetailActivity.class);
        intent.putExtra("invId", mInvId + "");
        startActivityForResult(intent,REQUEST_OK);
    }

    /**
     * 详细信息
     * @param view
     */
    @OnClick(R.id.information)
    public void information(View view){
        mPresenter.GetFirstUserByComKey(mReceivingComKey,mSysKeyBase);
    }

    @Override
    public void startProgressDialog(String msg) {
        QpadProgressUtils.showProgress(this, msg);
    }

    @Override
    public void stopProgressDialog() {
        QpadProgressUtils.closeProgress();
    }

    /**
     * 计算扫码成功数目
     * @param barCodeLogList
     * @return
     */
    private int GetSuccessCount(List<BarCodeLog> barCodeLogList) {
        int count = 0;
        for (int i = 0; i < barCodeLogList.size(); i++) {
            BarCodeLog barCodeLog = barCodeLogList.get(i);
            if (barCodeLog.isIsOk()) {
                count++;
            }
        }
        return count;
    }

    /**
     * 根据InvNumber查询单据
     * @param InvNumber
     * @param mInvoicingBeans
     * @return
     */
    private List<InvoicingBean> queryInvoicingBeanList(String InvNumber, List<InvoicingBean> mInvoicingBeans) {
        List<InvoicingBean> InvoicingBeans = new ArrayList<>();
        if (mInvoicingBeans == null) {
            return null;
        }
        if (mInvoicingBeans.size() == 0) {
            return null;
        }

        for (int i = 0; i < mInvoicingBeans.size(); i++) {
            if (StringUtils.ifIndexOf(mInvoicingBeans.get(i).getInvNumber()+"",InvNumber)) {
                InvoicingBeans.add(mInvoicingBeans.get(i));
            }
        }
        return InvoicingBeans;
    }


    /**
     * 预计数量和实际数量是否为0
     *
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