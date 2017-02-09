package com.xologood.q8pad.ui.outlibrary.newoutinvoice;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
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
import com.xologood.q8pad.adapter.ScanBarCodeAdpater;
import com.xologood.q8pad.bean.BarCodeLog;
import com.xologood.q8pad.bean.Company;
import com.xologood.q8pad.bean.InvoicingBean;
import com.xologood.q8pad.bean.Warehouse;
import com.xologood.q8pad.ui.invoicingdetail.InvoicingDetailActivity;
import com.xologood.q8pad.utils.QpadConfigUtils;
import com.xologood.q8pad.utils.SharedPreferencesUtils;
import com.xologood.q8pad.utils.StringUtils;
import com.xologood.q8pad.view.TitileView;
import com.xologood.zxing.activity.CaptureActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class NewOutInvoiceActivity extends BaseActivity<NewOutInvoicePresenter, NewOutInvoiceModel>
        implements NewOutInvoiceContract.View {
    @Bind(R.id.title_view)
    TitileView titleView;
    @Bind(R.id.InvNumber)
    QpadEditText InvNumber;
    @Bind(R.id.InvTime)
    QpadEditText InvTime;
    @Bind(R.id.qetQueryCompany)
    QpadEditText qetQueryCompany;
    @Bind(R.id.btnQueryCompany)
    Button btnQueryCompany;
    @Bind(R.id.company)
    QpadEditText company;
    @Bind(R.id.wareHouse)
    QpadEditText wareHouse;
    @Bind(R.id.saveNnit)
    Button saveNnit;
    @Bind(R.id.scanywm)
    Button scanywm;
    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.add)
    Button add;
    @Bind(R.id.et_editywm)
    EditText etEditywm;
    @Bind(R.id.llAdd)
    LinearLayout llAdd;
    @Bind(R.id.rbAdd)
    RadioButton rbAdd;
    @Bind(R.id.rbDelete)
    RadioButton rbDelete;
    @Bind(R.id.information)
    TextView information;
    @Bind(R.id.count)
    TextView count;
    @Bind(R.id.upload)
    Button upload;
    @Bind(R.id.commit)
    Button commit;
    private String LoginName;
    private String SysKey;
    private String ComKey;
    private String ComName;
    private String UserId;
    private String UserName;
    private String IsUse;
    private String sysKeyBase;

    private String mProductId;  //产品id
    private String mProductName;
    private String mBunit;      //单位id
    private String mExpectedQty; //根据获取单位比例 接口 返回的总预计数量
    private String mReceivingWarehouseId;
    private String mReceivingWarehouseName;
    private String mStandardUnit;
    private String mCompanyName;

    private Date date;
    private String InvDate;

    private Map<String, String> options; //保存出库主表请求参数
    private int invId;
    private ArrayAdapter<String> smmAdapter;
    private List<String> smm = new ArrayList<>();
    private String mCompanyId;

    private boolean isNewInvoicing  = false;  //是否新建出库单据
    private boolean isUpload  = false; //是否上传

    //已有出库单据扫码
    private Intent intent;
    private boolean isOld;
    private String oldInvNumber;
    private String oldInvDate;
    private String oldReceivingWarehouseId;
    private String oldReceivingComKey;
    private String oldReceivingComName;

    private List<BarCodeLog> mBarCodeLogList;
    private int SuccessCount;

    private List<Warehouse> mWarehouseList;
    private List<Company> mCompanyList;

    private List<CommonSelectData> mCommonSelectDataCompanyList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_out_invoice;
    }

    @Override
    public void initView() {
        mBarCodeLogList = new ArrayList<>();

        mWarehouseList = new ArrayList<>();
        mCompanyList = new ArrayList<>();

        mCommonSelectDataCompanyList = new ArrayList<>();

        intent = getIntent();
        isOld = intent.getBooleanExtra("isOld",false);
        oldInvNumber = intent.getStringExtra("InvNumber");
        oldInvDate = intent.getStringExtra("InvDate");
        oldReceivingWarehouseId = intent.getStringExtra("ReceivingWarehouseId");
        oldReceivingComKey = intent.getStringExtra("ReceivingComKey");
        oldReceivingComName = intent.getStringExtra("ReceivingComName");

        if (isOld) {
            invId = intent.getIntExtra("invId",0);
        }

        titleView.setTitle("新建订单出库");

        date = new Date();
        InvDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

        SysKey = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.SYSKEY);
        ComKey = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.COMKEY);
        ComName = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.COMNAME);

        LoginName = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.LOGINNAME);
        UserName = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.USERNAME);
        UserId = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.USERID);
        IsUse = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.ISUSE);

        sysKeyBase = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.SYSKEYBASE);



        mPresenter.GetAllCompList(ComKey, "2");
        mPresenter.GetWareHouseList(ComKey, IsUse);
        mPresenter.GetProductList(SysKey, IsUse);

        if (isOld) {
            InvNumber.setFieldTextAndValue(oldInvNumber);
            InvTime.setFieldTextAndValue(oldInvDate);
            company.setFieldEnabled(false);
            wareHouse.setFieldEnabled(false);
            InvNumber.setFieldEnabled(false);
            InvTime.setFieldEnabled(false);
            saveNnit.setEnabled(false);
        } else {
            InvNumber.setFieldTextAndValue(getInvNumber(2, UserId));
            InvTime.setFieldTextAndValue(InvDate);
        }

        //扫码
        smm = new ArrayList<>();
        smmAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, smm);
        lv.setAdapter(smmAdapter);
    }

    @Override
    public void initListener() {
        company.setOnChangeListener(new QpadEditText.OnChangeListener() {
            @Override
            public void onChanged(CommonSelectData data) {
                mCompanyName = data.getText();
                mCompanyId = data.getValue();
                if (mCompanyList.size() == 0) {
                    mPresenter.GetAllCompList(ComKey,"2");
                }
            }
        });

        wareHouse.setOnChangeListener(new QpadEditText.OnChangeListener() {
            @Override
            public void onChanged(CommonSelectData data) {
                mReceivingWarehouseId = data.getValue();
                mReceivingWarehouseName = data.getText();
                if (mWarehouseList.size() == 0) {
                    mPresenter.GetWareHouseList(ComKey,IsUse);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CaptureActivity.RESULT_OK) {
            String ewm_num = data.getStringExtra("ewm_num");
            String ewm_type = data.getStringExtra("ewm_type");
            if (rbAdd.isChecked()) {
                if (!smm.contains(ewm_num)) {
                    smm.add(ewm_num);
                    smmAdapter.notifyDataSetChanged();
                    information.setText(etEditywm.getText().toString().trim() + "添加成功！");
                } else {
                    ToastUitl.showShort("此条码已经扫描，请重新扫码！");
                }
            } else if (rbDelete.isChecked() && smm.contains(ewm_num)) {
                smm.remove(ewm_num);
                smmAdapter.notifyDataSetChanged();
                information.setText(ewm_num+"删除成功！");
            }
            if (SuccessCount > 0) {
                count.setVisibility(View.VISIBLE);
                count.setText("已扫描" + SuccessCount + "条");
            } else {
                count.setVisibility(View.GONE);
            }
         //   ToastUitl.showLong("扫码类型:" + ewm_type + "一维码或者二维码:" + ewm_num);
        }
    }

    /**
     * 设置仓库列表
     *
     * @param warehouseList
     */
    @Override
    public void SetWareHouseList(List<Warehouse> warehouseList) {
        mWarehouseList = warehouseList;
        List<CommonSelectData> commonSelectWarehouse = new ArrayList<>();
        if (warehouseList != null && warehouseList.size() > 0) {
            for (int i = 0; i < warehouseList.size(); i++) {
                String id = warehouseList.get(i).getId() + "";
                String name = warehouseList.get(i).getName();
                commonSelectWarehouse.add(new CommonSelectData(name, id));
            }
            wareHouse.setLists(commonSelectWarehouse);
            if (isOld) {
                wareHouse.setFieldTextAndValueFromValue(oldReceivingWarehouseId);
            } else {
                wareHouse.setFieldTextAndValue(commonSelectWarehouse.get(0));
            }

        }
    }

    @Override
    public void SetAllCompList(List<Company> companyList) {
        mCompanyList = companyList;
        if (companyList != null && companyList.size() > 0) {
            for (int i = 0; i < companyList.size(); i++) {
                Company mCompany = companyList.get(i);
                mCommonSelectDataCompanyList.add(new CommonSelectData(mCompany.getCompanyName(), mCompany.getCompanyId() + ""));
            }
            company.setLists(mCommonSelectDataCompanyList);
            if (isOld) {
                company.setFieldTextAndValue(oldReceivingComName);
            }
        }
    }

    /**
     * 保存出库主表成功
     *
     * @param invoicingBean
     */
    @Override
    public void insertInv(InvoicingBean invoicingBean) {
        isNewInvoicing = true;
        invId = invoicingBean.getInvId();
        final NormalDialog IsNewInvoicing_Dialog =new NormalDialog(mContext);
        QPadPromptDialogUtils.showOnePromptDialog(IsNewInvoicing_Dialog, "新出库单据:"+InvNumber.getFieldText()+"您可以前往扫码！", new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                IsNewInvoicing_Dialog.dismiss();
            }
        });
    }

    /**
     * 保存
     *
     * @param view
     */
    @OnClick(R.id.saveNnit)
    public void setSaveNnit(View view) {
        options = new HashMap<>();
        String CheckDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        if (QpadJudgeUtils.isEmpty(mCompanyName)) {
            final NormalDialog IsEmpty_CompanyName_Dialog = new NormalDialog(mContext);
            QPadPromptDialogUtils.showOnePromptDialog(IsEmpty_CompanyName_Dialog, "请选择机构！", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    IsEmpty_CompanyName_Dialog.dismiss();
                }
            });
        } else {
            options.put("InvId", "0");
            options.put("Pid", "0");
            options.put("InvBy", UserId);
            options.put("InvByName", UserName);
            options.put("InvNumber", InvNumber.getFieldText());
            options.put("InvDate", InvDate);
            options.put("InvReMark", "暂无");
            options.put("InvGet", "APP");
            options.put("InvType", "2");
            options.put("CheckedParty", "true");
            options.put("InvState", "2");
            options.put("CodeType", "false");
            options.put("CheckUserId", UserId);
            options.put("CheckUserName", UserName);
            options.put("CheckDate", CheckDate);
            options.put("ReceivingComKey", mCompanyId);
            options.put("ReceivingComName", mCompanyName);
            options.put("ReceivingWarehouseId", mReceivingWarehouseId);
            options.put("ReceivingWarehouseName", mReceivingWarehouseName);
            options.put("SysKey", SysKey);
            options.put("ComKey", ComKey);
            options.put("ComName", ComName);
            options.put("LastUpdateDate", InvDate);
            options.put("LastUpdateByName", UserName);
            options.put("LastUpdateBy", UserId);
            options.put("CheckMemo", "暂无");
            //保存入库主表
            mPresenter.insertInv(options);
        }
    }

    @Override
    public void setScanBarCodeList(List<BarCodeLog> barCodeLogList) {
        mBarCodeLogList = barCodeLogList;
        if (barCodeLogList != null && barCodeLogList.size() > 0) {
            smm.removeAll(smm);
            smmAdapter.notifyDataSetChanged();
          /*  if (GetSuccessCount(barCodeLogList) > 0) {
                isUpload = true;
            }*/
            isUpload = true;
            SuccessCount = GetSuccessCount(barCodeLogList);
            if (SuccessCount > 0) {
                count.setVisibility(View.VISIBLE);
                count.setText("已扫描" + SuccessCount + "条");
            } else {
                count.setVisibility(View.GONE);
            }
            ScanBarCodeAdpater scanBarCodeAdpater = new ScanBarCodeAdpater(barCodeLogList, mContext);
            View layout_scanbarcode_dialog = LayoutInflater.from(mContext).inflate(R.layout.layout_scanbarcode_dialog, null);
            final AlertDialog barCodeLogDialog = new AlertDialog.Builder(mContext, R.style.Login_dialog).create();
            barCodeLogDialog.setCanceledOnTouchOutside(false);
            barCodeLogDialog.show();
            barCodeLogDialog.getWindow().setContentView(layout_scanbarcode_dialog);
            WindowManager.LayoutParams lp_barCode = barCodeLogDialog.getWindow().getAttributes();
            lp_barCode.width = (int) (QpadConfigUtils.SCREEN.Width * 0.85);
            ListView ScanBarCodeList = (ListView) layout_scanbarcode_dialog.findViewById(R.id.scanbarcodelist);
            ScanBarCodeList.setAdapter(scanBarCodeAdpater);
            Button back = (Button) layout_scanbarcode_dialog.findViewById(R.id.back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (barCodeLogDialog != null && barCodeLogDialog.isShowing()) {
                        barCodeLogDialog.dismiss();
                    }
                }
            });
        }
    }

    /**
     * 扫码
     *
     * @param view
     */
    @OnClick(R.id.scanywm)
    public void setScanywm(View view) {
        startActivityForResult(new Intent(NewOutInvoiceActivity.this, CaptureActivity.class), Config.REQUESTOK);
    }
    /**
     * 手动添加
     *
     * @param view
     */
    @OnClick(R.id.add)
    public void add(View view) {
        String et_ywm = etEditywm.getText().toString().trim();
        if (QpadJudgeUtils.isEmpty(et_ywm)) {
            ToastUitl.showShort("请输入条码！");
        }else if (!smm.contains(et_ywm)) {
            smm.add(et_ywm);
            smmAdapter.notifyDataSetChanged();
            count.setText("已扫描" + smm.size() + "条");
            information.setText(etEditywm.getText().toString().trim() + "添加成功！");
        } else {
            etEditywm.setText("");
            ToastUitl.showShort("已经添加此条码,请重新输入！");
        }
        if (SuccessCount > 0) {
            count.setVisibility(View.VISIBLE);
            count.setText("已扫描" + SuccessCount + "条");
        } else {
            count.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.upload)
    public void upload(View view) {
        if (!isOld) {
            if (!isNewInvoicing) {
                final NormalDialog IsNewInvoicing_Dialog = new NormalDialog(mContext);
                QPadPromptDialogUtils.showOnePromptDialog(IsNewInvoicing_Dialog, "未新建出库单据！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        IsNewInvoicing_Dialog.dismiss();
                    }
                });
            } else if (smm != null && smm.size() > 0) {
                mPresenter.GetScanBarCodeList(GetBarCodeString4List(smm),
                        invId + "",
                        InvNumber.getFieldText(),
                        "2",
                        "APP",
                        "暂无",
                        UserId,
                        UserName,
                        "false",
                        "2",
                        InvDate,
                        UserId,
                        UserName,
                        InvDate,
                        ComKey,
                        ComName,
                        SysKey,
                        "暂无",
                        mCompanyId,
                        mCompanyName,
                        mReceivingWarehouseId,
                        mReceivingWarehouseName,
                        "true",
                        sysKeyBase
                );
            } else if (smm == null || smm.size() == 0) {
                final NormalDialog IsNotScan_Dialog = new NormalDialog(mContext);
                QPadPromptDialogUtils.showOnePromptDialog(IsNotScan_Dialog, "条码列表为空，请先扫码！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        IsNotScan_Dialog.dismiss();
                    }
                });
            }
        } else {
            if (smm != null && smm.size() > 0) {
                mPresenter.GetScanBarCodeList(GetBarCodeString4List(smm),
                        invId + "",
                        InvNumber.getFieldText(),
                        "2",
                        "APP",
                        "暂无",
                        UserId,
                        UserName,
                        "false",
                        "2",
                        InvDate,
                        UserId,
                        UserName,
                        InvDate,
                        ComKey,
                        ComName,
                        SysKey,
                        "暂无",
                        mCompanyId,
                        mCompanyName,
                        mReceivingWarehouseId,
                        mReceivingWarehouseName,
                        "true",
                        sysKeyBase
                );
            }else if (smm == null || smm.size() == 0) {
                final NormalDialog IsNotScan_Dialog = new NormalDialog(mContext);
                QPadPromptDialogUtils.showOnePromptDialog(IsNotScan_Dialog, "条码列表为空，请先扫码！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        IsNotScan_Dialog.dismiss();
                    }
                });
            }
        }
    }

    @OnClick(R.id.commit)
    public void commit(View view) {
        if (!isOld && !isNewInvoicing) {
                final NormalDialog IsEmpty_ywm_Dialog = new NormalDialog(mContext);
                QPadPromptDialogUtils.showOnePromptDialog(IsEmpty_ywm_Dialog, "未新建出库单据，请先新增订单！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        IsEmpty_ywm_Dialog.dismiss();
                    }
                });
        } else {
           if (!isUpload) {
                final NormalDialog IsUpload_Dialog = new NormalDialog(mContext);
                QPadPromptDialogUtils.showOnePromptDialog(IsUpload_Dialog, "请上传条码！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        IsUpload_Dialog.dismiss();
                    }
                });
            } else if (GetSuccessCount(mBarCodeLogList) == 0) {
                final NormalDialog IsUpload_Dialog = new NormalDialog(mContext);
                QPadPromptDialogUtils.showOnePromptDialog(IsUpload_Dialog, "未成功扫码，请继续扫码！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        IsUpload_Dialog.dismiss();
                    }
                });
            } else {
                Intent intent = new Intent(NewOutInvoiceActivity.this, InvoicingDetailActivity.class);
                intent.putExtra("invId", invId + "");
                startActivity(intent);
                finish();
            }
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
     * 查询机构
     * @param view
     */
    @OnClick(R.id.btnQueryCompany)
    public void setQueryCompany(View view) {
        List<CommonSelectData> mCommonSelectDataList = new ArrayList<>();
        company.setFieldTextAndValue("");
        if (mCommonSelectDataCompanyList != null
                && mCommonSelectDataCompanyList.size() > 0) {
            if (!QpadJudgeUtils.isEmpty(qetQueryCompany.getFieldText())) {
                for (int i = 0; i < mCommonSelectDataCompanyList.size(); i++) {
                    CommonSelectData mCommonSelectData = mCommonSelectDataCompanyList.get(i);
                    if (StringUtils.ifIndexOf(mCommonSelectData.getText(), qetQueryCompany.getFieldText())
                            && !QpadJudgeUtils.isEmpty(qetQueryCompany.getFieldText())) {
                        mCommonSelectDataList.add(mCommonSelectData);

                    }
                }
            } else {
                mCommonSelectDataList.clear();
                mCommonSelectDataList.addAll(mCommonSelectDataCompanyList);
            }
            company.setLists(mCommonSelectDataList);
        }
    }



    /**
     * 拼接扫码
     * @param smm
     * @return
     */
    private String GetBarCodeString4List(List<String> smm) {
        StringBuffer sb = new StringBuffer();
        for (String s : smm) {
            sb.append(s).append(",");
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

    public static String getInvNumber(int i, String userId) {
        DecimalFormat df = new DecimalFormat("000000");
        String str = df.format(Integer.parseInt(userId));
        String time = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
        int round = (int) (Math.random() * 90) + 10;

        String invNumber = null;
        if (i == 1) {
            invNumber = "A" + str + time + round;
        } else if (i == 2) {
            invNumber = "L" + str + time + round;
        }
        return invNumber;
    }
}
