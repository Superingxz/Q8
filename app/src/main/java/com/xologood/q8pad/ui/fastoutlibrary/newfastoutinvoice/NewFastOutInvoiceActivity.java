package com.xologood.q8pad.ui.fastoutlibrary.newfastoutinvoice;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.xologood.q8pad.bean.Product;
import com.xologood.q8pad.bean.ProductBatch;
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
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class NewFastOutInvoiceActivity extends  BaseActivity<NewFastOutInvoicePresenter,NewFastOutInvoiceModel>
        implements NewFastOutInvoiceContract.View{
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
    @Bind(R.id.Warehouse)
    QpadEditText wareHouse;
    @Bind(R.id.produceName)
    QpadEditText produceName;
    @Bind(R.id.addProduceBatch)
    QpadEditText addProduceBatch;
    @Bind(R.id.saveBatch)
    Button saveBatch;
    @Bind(R.id.produceBatch)
    QpadEditText produceBatch;
    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.rbAdd)
    RadioButton rbAdd;
    @Bind(R.id.rbDelete)
    RadioButton rbDelete;
    @Bind(R.id.information)
    TextView information;
    @Bind(R.id.count)
    TextView count;
    @Bind(R.id.scanywm)
    Button scanywm;
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

    private Date date;

    private String InvDate;

    private ArrayAdapter<String> smmAdapter;
    private List<String> smm = new ArrayList<>();

    private String mCompanyName;
    private String mCompanyId;
    private String mReceivingWarehouseId;
    private String mReceivingWarehouseName;
    private String mProductId;
    private String mProductName;
    private String mBatch;
    private String mBatchNo;
    private String mInvNumber;

    private boolean IsUpload = false;
    private int mInvId;

    private boolean isOld = false;  //是否是旧快捷出库单据
    private String oldInvNumber;
    private String oldInvDate;
    private String oldReceivingWarehouseId;
    private String oldReceivingComKey;
    private String oldReceivingComName;

    private int invId;  //快捷出库单据id (1.旧的2.新的重新获取)
    private List<BarCodeLog> mBarCodeLogList;
    private int SuccessCount;

    private List<Company> mCompanyList;
    private ArrayList<Warehouse> mWarehouseList;
    private List<Product> mProductList;
    private List<ProductBatch> mProductBatchList;
    private List<CommonSelectData> mCommonSelectDataCompanyList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fast_new_out_invoice;
    }

    @Override
    public void initView() {
        titleView.setTitle("新建订单出库");

        mBarCodeLogList = new ArrayList<>();

        mCompanyList = new ArrayList<>();
        mProductList = new ArrayList<>();
        mProductBatchList = new ArrayList<>();

        mCommonSelectDataCompanyList = new ArrayList<>();

        date = new Date();
        InvDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

        SysKey = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.SYSKEY);
        ComKey = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.COMKEY);
        ComName = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.COMNAME);
        sysKeyBase = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.SYSKEYBASE);

        LoginName = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.LOGINNAME);
        UserName = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.USERNAME);
        UserId = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.USERID);
        IsUse = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.ISUSE);


        Intent intent = getIntent();
        isOld = intent.getBooleanExtra("isOld",false);
        oldInvNumber = intent.getStringExtra("InvNumber");
        oldInvDate = intent.getStringExtra("InvDate");
        oldReceivingComKey = intent.getStringExtra("ReceivingComKey");
        oldReceivingComName = intent.getStringExtra("ReceivingComName");
        oldReceivingWarehouseId = intent.getStringExtra("ReceivingWarehouseId");

        if (isOld) {
            invId = intent.getIntExtra("invId",0);
        }

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
                    mPresenter.GetAllCompList(ComKey, "2");
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

        produceName.setOnChangeListener(new QpadEditText.OnChangeListener() {
            @Override
            public void onChanged(CommonSelectData data) {
                mProductId = data.getValue();
                mProductName = data.getText();
                if (!QpadJudgeUtils.isEmpty(mProductId)) {
                    mPresenter.GetProductBatchByProductId(mProductId);
                } else if (mProductList.size() == 0) {
                    mPresenter.GetProductList(SysKey, IsUse);
                }
            }
        });

        produceBatch.setOnChangeListener(new QpadEditText.OnChangeListener() {
            @Override
            public void onChanged(CommonSelectData data) {
                mBatch = data.getValue();
                mBatchNo = data.getText();
                if (mProductBatchList.size() == 0 && !QpadJudgeUtils.isEmpty(mProductId)) {
                    mPresenter.GetProductBatchByProductId(mProductId);
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
                    information.setText(ewm_num + "添加成功！");
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
           // ToastUitl.showLong("扫码类型:" + ewm_type + "一维码或者二维码:" + ewm_num);
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

    @Override
    public void SetWareHouseList(List<Warehouse> warehouseList) {
        mWarehouseList = new ArrayList<>();
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
    public void SetProductList(List<Product> productList) {
        mProductList = productList;
        List<CommonSelectData> commonSelectProductList = new ArrayList<>();
        if (productList != null && productList.size() > 0) {
            for (int i = 0; i < productList.size(); i++) {
                String id = productList.get(i).getId() + "";
                String productName = productList.get(i).getProductName();
                commonSelectProductList.add(new CommonSelectData(productName, id));
            }
            produceName.setLists(commonSelectProductList);
        }
    }

    @Override
    public void SetProductBatch(List<ProductBatch> productBatchList) {
        mProductBatchList = productBatchList;
        List<CommonSelectData> commonSelectProductBatchList = new ArrayList<>();
        if (productBatchList != null && productBatchList.size() > 0) {
            for (int i = 0; i < productBatchList.size(); i++) {
                String id = productBatchList.get(i).getId() + "";
                String batchNO = productBatchList.get(i).getBatchNO();
                commonSelectProductBatchList.add(new CommonSelectData(batchNO, id));
            }
            produceBatch.setLists(commonSelectProductBatchList);
            produceBatch.setFieldTextAndValue(commonSelectProductBatchList.get(0));
        }
    }

    /**
     * 添加产品批次
     *
     * @param msg
     */
    @Override
    public void InsertProductBatch(String msg) {
        ToastUitl.showLong(msg);
    }

    /**
     * 添加产品批次异常失败
     *
     * @param msg
     */
    @Override
    public void InsertProductBatchFailed(String msg) {
        ToastUitl.showLong(msg);
    }

    @Override
    public void SetBarCodeList(List<BarCodeLog> barCodeLogList) {
        mBarCodeLogList = barCodeLogList;
        if (barCodeLogList != null && barCodeLogList.size() > 0) {
            smm.removeAll(smm);
            smmAdapter.notifyDataSetChanged();
           /* if (GetSuccessCount(barCodeLogList) > 0) {
                IsUpload = true;
            }*/
            IsUpload = true;

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
                    if(barCodeLogDialog!=null && barCodeLogDialog.isShowing()){
                        barCodeLogDialog.dismiss();
                    }
                }
            });

            mPresenter.insertInv(SysKey,mInvNumber);
        }
    }

    @Override
    public void SetInvid(int invId) {
        mInvId = invId;
        ToastUitl.showLong("成功扫码："+invId+"");
    }

    /**
     * 保存批次
     *
     * @param view
     */
    @OnClick(R.id.saveBatch)
    public void setSaveBatch(View view) {
        String addProductBatch = addProduceBatch.getFieldText();
        if (QpadJudgeUtils.isEmpty(mProductName)) {
            final NormalDialog IsEmpty_ProductName_Dialog = new NormalDialog(mContext);
            QPadPromptDialogUtils.showOnePromptDialog(IsEmpty_ProductName_Dialog, "请选择产品！", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    IsEmpty_ProductName_Dialog.dismiss();
                }
            });
        }else if (QpadJudgeUtils.isEmpty(addProductBatch)) {
            final NormalDialog IsEmpty_ProductBatch_Dialog = new NormalDialog(mContext);
            QPadPromptDialogUtils.showOnePromptDialog(IsEmpty_ProductBatch_Dialog, "请填写批次！", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    IsEmpty_ProductBatch_Dialog.dismiss();
                }
            });
        } else  {
            mPresenter.InsertProductBatch(addProductBatch, mProductId, SysKey, InvDate, LoginName);
            if (!QpadJudgeUtils.isEmpty(mProductId)) {
                mPresenter.GetProductBatchByProductId(mProductId);
            }
        }
    }

    /**
     * 扫码
     *
     * @param view
     */
    @OnClick(R.id.scanywm)
    public void setScanywm(View view) {
        startActivityForResult(new Intent(NewFastOutInvoiceActivity.this, CaptureActivity.class), Config.REQUESTOK);
    }

    @OnClick(R.id.upload)
    public void upload(View view){
        mInvNumber = InvNumber.getFieldText();
        if (!isOld) {
            if (QpadJudgeUtils.isEmpty(mCompanyName)) {
                final NormalDialog IsEmpty_CompanyName_Dialog = new NormalDialog(mContext);
                QPadPromptDialogUtils.showOnePromptDialog(IsEmpty_CompanyName_Dialog, "请选择机构！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        IsEmpty_CompanyName_Dialog.dismiss();
                    }
                });
            } else if (QpadJudgeUtils.isEmpty(mReceivingWarehouseName)) {
                final NormalDialog IsEmpty_WarehouseName_Dialog = new NormalDialog(mContext);
                QPadPromptDialogUtils.showOnePromptDialog(IsEmpty_WarehouseName_Dialog, "请选择仓库！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        IsEmpty_WarehouseName_Dialog.dismiss();
                    }
                });
            }else if (QpadJudgeUtils.isEmpty(mProductName)) {
                final NormalDialog IsEmpty_ProductName_Dialog = new NormalDialog(mContext);
                QPadPromptDialogUtils.showOnePromptDialog(IsEmpty_ProductName_Dialog, "请选择产品！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        IsEmpty_ProductName_Dialog.dismiss();
                    }
                });
            }else if (smm == null || smm.size() == 0) {
                final NormalDialog IsNotScan_Dialog = new NormalDialog(mContext);
                QPadPromptDialogUtils.showOnePromptDialog(IsNotScan_Dialog, "条码列表为空，请先扫码！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        IsNotScan_Dialog.dismiss();
                    }
                });
            } else if (smm != null && smm.size() > 0) {
                mPresenter.NewQuickScanBarCode(GetBarCodeString4List(smm),
                        mInvNumber,
                        "1",
                        "APP",
                        "暂无",
                        UserId,
                        UserName,
                        "true",
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
                        sysKeyBase,
                        mProductId,
                        mBatch
                );
            }
        } else {
            if (QpadJudgeUtils.isEmpty(mProductName)) {
                final NormalDialog IsEmpty_ProductName_Dialog = new NormalDialog(mContext);
                QPadPromptDialogUtils.showOnePromptDialog(IsEmpty_ProductName_Dialog, "请选择产品！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        IsEmpty_ProductName_Dialog.dismiss();
                    }
                });
            }else if (smm == null || smm.size() == 0) {
                final NormalDialog IsNotScan_Dialog = new NormalDialog(mContext);
                QPadPromptDialogUtils.showOnePromptDialog(IsNotScan_Dialog, "条码列表为空，请先扫码！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        IsNotScan_Dialog.dismiss();
                    }
                });
            } else if (smm != null && smm.size() > 0) {
                mPresenter.NewQuickScanBarCode(GetBarCodeString4List(smm),
                        mInvNumber,
                        "1",
                        "APP",
                        "暂无",
                        UserId,
                        UserName,
                        "true",
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
                        sysKeyBase,
                        mProductId,
                        mBatch
                );
            }
        }
    }


    @Override
    public void insertInv2(InvoicingBean invoicingBean) {

    }

    @OnClick(R.id.commit)
    public void setCommit(View view) {
        if (!IsUpload) {
            final NormalDialog IsUpload_Dialog = new NormalDialog(mContext);
            QPadPromptDialogUtils.showOnePromptDialog(IsUpload_Dialog, "请上传条码！", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    IsUpload_Dialog.dismiss();
                }
            });
        } else if (GetSuccessCount(mBarCodeLogList) == 0) {
            final NormalDialog IsUpload_Dialog = new NormalDialog(mContext);
            QPadPromptDialogUtils.showOnePromptDialog(IsUpload_Dialog, "未成功上传扫码，请继续扫码！", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    IsUpload_Dialog.dismiss();
                }
            });
        }else {
                Intent intent = new Intent(this, InvoicingDetailActivity.class);
                intent.putExtra("invId", mInvId);
                startActivity(intent);
            }
    }

    /**
     * 查询机构
     * @param view
     */
    @OnClick(R.id.btnQueryCompany)
    public void setQueryCompany(View view) {
        company.setFieldTextAndValue("");
        List<CommonSelectData> commonSelectDataCompanyList = new ArrayList<>();
        if (mCommonSelectDataCompanyList != null
                && mCommonSelectDataCompanyList.size() > 0) {
            if (!QpadJudgeUtils.isEmpty(qetQueryCompany.getFieldText())) {
                for (int i = 0; i < mCommonSelectDataCompanyList.size(); i++) {
                    CommonSelectData mCommonSelectData = mCommonSelectDataCompanyList.get(i);
                    if (StringUtils.ifIndexOf(mCommonSelectData.getText(), qetQueryCompany.getFieldText())) {
                        commonSelectDataCompanyList.add(mCommonSelectData);
                    }
                }
            } else {
                commonSelectDataCompanyList.clear();
                commonSelectDataCompanyList.addAll(mCommonSelectDataCompanyList);
            }
            company.setLists(commonSelectDataCompanyList);
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
