package com.xologood.q8pad.ui.outlibrary.oldoutinvoice;

import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
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
import com.xologood.q8pad.adapter.NewOutInvoiceAdapter;
import com.xologood.q8pad.bean.FirstUser;
import com.xologood.q8pad.bean.Invoice;
import com.xologood.q8pad.bean.InvoicingBean;
import com.xologood.q8pad.bean.InvoicingDetail;
import com.xologood.q8pad.ui.invoicingdetail.InvoicingDetailActivity;
import com.xologood.q8pad.ui.outlibrary.newoutinvoice.NewOutInvoiceActivity;
import com.xologood.q8pad.utils.QpadConfigUtils;
import com.xologood.q8pad.utils.SharedPreferencesUtils;
import com.xologood.q8pad.utils.StringUtils;
import com.xologood.q8pad.view.TitileView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class OldOutInvoiceActivity extends BaseActivity<OldOutInvoicePresenter, OldOutInvoiceModel>
        implements OldOutInvoiceContract.View {
    private static final int SCAN = 100;
    @Bind(R.id.title_view)
    TitileView titleView;
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
    ListView lv;
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

    private boolean IsSelect = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_old_out_invoice;
    }

    @Override
    public void initView() {
        titleView.setTitle("已有订单出库");

        mInvoicingBeanList = new ArrayList<>();

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
                    intent.putExtra("ReceivingComName", invoicingBean.getComName());
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public void initListener() {

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
                mPresenter.GetInvoicingDetail(mInvId + "");
            }
        });

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
        View layout_get_first_user_by_comkey_dialog = LayoutInflater.from(mContext).inflate(R.layout.layout_get_first_user_by_comkey, null);
        final AlertDialog firstUserDialog = new AlertDialog.Builder(mContext, R.style.Login_dialog).create();
        firstUserDialog.setCanceledOnTouchOutside(false);
        firstUserDialog.show();
        firstUserDialog.getWindow().setContentView(layout_get_first_user_by_comkey_dialog);
        WindowManager.LayoutParams lp_firstUser = firstUserDialog.getWindow().getAttributes();
        lp_firstUser.width = (int) (QpadConfigUtils.SCREEN.Width * 0.85);
        Button back = (Button) layout_get_first_user_by_comkey_dialog.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstUserDialog != null && firstUserDialog.isShowing()) {
                    firstUserDialog.dismiss();
                }
            }
        });
        if (firstUser != null) {
            final NormalDialog IsNotFirstUser_Dialog = new NormalDialog(mContext);
            String mWeChat =  firstUser.getWeChat();
            String mTel = firstUser.getTel();
            String mAddress = firstUser.getAddres();
            if (!QpadJudgeUtils.isEmpty(mWeChat)) {
                ((TextView) layout_get_first_user_by_comkey_dialog.findViewById(R.id.weChat)).setText(mWeChat);
            } else {
                firstUserDialog.dismiss();
                QPadPromptDialogUtils.showOnePromptDialog(IsNotFirstUser_Dialog, "暂无信息！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        IsNotFirstUser_Dialog.dismiss();
                    }
                });
            }
            if (!QpadJudgeUtils.isEmpty(mTel)) {
                firstUserDialog.dismiss();
                ((TextView) layout_get_first_user_by_comkey_dialog.findViewById(R.id.tel)).setText(mTel);
            }else {
                QPadPromptDialogUtils.showOnePromptDialog(IsNotFirstUser_Dialog, "暂无信息！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        IsNotFirstUser_Dialog.dismiss();
                    }
                });
            }
            if (!QpadJudgeUtils.isEmpty(mAddress)) {
                ((TextView) layout_get_first_user_by_comkey_dialog.findViewById(R.id.address)).setText(mAddress);
            }else {
                firstUserDialog.dismiss();
                QPadPromptDialogUtils.showOnePromptDialog(IsNotFirstUser_Dialog, "暂无信息！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        IsNotFirstUser_Dialog.dismiss();
                    }
                });
            }
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
                        intent.putExtra("ReceivingComName", invoicingBean.getComName());
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
        startActivity(intent);
        finish();
    }

    /**
     * 详细信息
     * @param view
     */
    @OnClick(R.id.information)
    public void information(View view){
        mPresenter.GetFirstUserByComKey(ComKey);
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