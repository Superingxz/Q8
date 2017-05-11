package com.xologood.q8pad.ui.fastoutlibrary.newfastoutinvoice;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.xologood.mvpframework.util.ToastUitl;
import com.xologood.q8pad.Config;
import com.xologood.q8pad.Qpadapplication;
import com.xologood.q8pad.R;
import com.xologood.q8pad.adapter.CompanyListAdapter;
import com.xologood.q8pad.adapter.ProductListAdpater;
import com.xologood.q8pad.adapter.ScanBarCodeAdpater;
import com.xologood.q8pad.bean.BarCodeLog;
import com.xologood.q8pad.bean.Company;
import com.xologood.q8pad.bean.InvoicingBean;
import com.xologood.q8pad.bean.Product;
import com.xologood.q8pad.bean.ProductBatch;
import com.xologood.q8pad.bean.Warehouse;
import com.xologood.q8pad.ui.PadActivity;
import com.xologood.q8pad.ui.invoicingdetail.InvoicingDetailActivity;
import com.xologood.q8pad.utils.QpadConfigUtils;
import com.xologood.q8pad.utils.SharedPreferencesUtils;
import com.xologood.q8pad.utils.StringUtils;
import com.xologood.q8pad.view.TitleView;
import com.xologood.zxing.activity.CaptureActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

import static com.xologood.q8pad.R.id.productName;

public class NewFastOutInvoiceActivity extends PadActivity<NewFastOutInvoicePresenter, NewFastOutInvoiceModel>
        implements NewFastOutInvoiceContract.View {
    private static final int REQUEST_OK = 100;
    public static final int NEWFASTOUTINVOICE_OK = 102;
    @Bind(R.id.title_view)
    TitleView titleView;
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
    @Bind(R.id.add)
    Button add;
    @Bind(R.id.et_editywm)
    EditText etEditywm;
    @Bind(R.id.llAdd)
    LinearLayout llAdd;
    @Bind(R.id.scanNumber)
    TextView scanNumber;
    @Bind(R.id.isContinous)
    CheckBox isContinous;
    @Bind(R.id.btnAddProduceBatch)
    Button btnAddProduceBatch;

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
    private String mComkey;
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

    private ArrayList<Company> queryCompanyList;
    private CompanyListAdapter companyAdapter;

    private Map options;


    private ProductListAdpater productAdapter;
    private List<Product> queryProductList;
    private boolean IsCommitSuccess = false;


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
        queryProductList = new ArrayList<>();
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

        isContinous.setChecked(SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.ISCONTINOUS));//初始化是否连续扫码状态


        Intent intent = getIntent();
        isOld = intent.getBooleanExtra("isOld", false);
        oldInvNumber = intent.getStringExtra("InvNumber");
        oldInvDate = intent.getStringExtra("InvDate");
        oldReceivingComKey = intent.getStringExtra("ReceivingComKey");
        oldReceivingComName = intent.getStringExtra("ReceivingComName");
        oldReceivingWarehouseId = intent.getStringExtra("ReceivingWarehouseId");
        if (isOld) {
            mInvId = intent.getIntExtra("invId", 0);
            InvNumber.setFieldTextAndValue(oldInvNumber);
            InvTime.setFieldTextAndValue(oldInvDate);
            mCompanyName = oldReceivingComName;
            company.setFieldEnabled(false);
            wareHouse.setFieldEnabled(false);
            InvNumber.setFieldEnabled(false);
            InvTime.setFieldEnabled(false);
            qetQueryCompany.setEnabled(false);
            btnQueryCompany.setEnabled(false);
        } else {
            InvNumber.setFieldTextAndValue(getInvNumber(2, UserId));
            InvTime.setFieldTextAndValue(InvDate);
        }

        mPresenter.GetAllCompList(ComKey, "2");
        mPresenter.GetWareHouseList(ComKey, IsUse);
        mPresenter.GetProductList(SysKey, IsUse);

        //扫码
        smm = new ArrayList<>();
        smmAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, smm);
        lv.setAdapter(smmAdapter);
    }

    @Override
    public void initListener() {
       /* company.setOnChangeListener(new QpadEditText.OnChangeListener() {
            @Override
            public void onChanged(CommonSelectData data) {
                mCompanyName = data.getText();
                mComkey = data.getValue();
            }
        });*/
        company.setOnDialogClickLister(new QpadEditText.OnDialogClickLister() {
            @Override
            public void OnDialogClick() {
                queryCompanyList = new ArrayList<>();
                //根据机构编号，机构名称，电话号码查询机构
                if (queryCompanyList.size() > 0) {
                    queryCompanyList.removeAll(queryCompanyList);
                }
                queryCompanyList.addAll(mCompanyList);
                companyAdapter = new CompanyListAdapter(queryCompanyList, mContext);
                View layout_layout_queryCompanyNameList = LayoutInflater.from(mContext).inflate(R.layout.layout_companylist, null);
                final AlertDialog companyDialog = new AlertDialog.Builder(mContext, R.style.Login_dialog).create();
                companyDialog.setView(new EditText(mContext));
                companyDialog.setCanceledOnTouchOutside(false);
                companyDialog.show();
                companyDialog.getWindow().setContentView(layout_layout_queryCompanyNameList);
                WindowManager.LayoutParams lp_company = companyDialog.getWindow().getAttributes();
                lp_company.width = (int) (width * 0.85);
                lp_company.height = (int) (height * 0.85);
                companyDialog.getWindow().setAttributes(lp_company);
                final QpadEditText etCompanyNo = (QpadEditText) layout_layout_queryCompanyNameList.findViewById(R.id.companyNo);
                final QpadEditText etcompanyName = (QpadEditText) layout_layout_queryCompanyNameList.findViewById(R.id.companyName);
                final QpadEditText etcomTel = (QpadEditText) layout_layout_queryCompanyNameList.findViewById(R.id.comTel);
                Button queryCompany = (Button) layout_layout_queryCompanyNameList.findViewById(R.id.queryCompany);
                ListView companyListView = (ListView) layout_layout_queryCompanyNameList.findViewById(R.id.companyList);
                companyListView.setAdapter(companyAdapter);
                queryCompany.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //按条件查询机构
                        if (queryCompanyList.size() > 0) {
                            queryCompanyList.removeAll(queryCompanyList);
                        }
                        if (mCompanyList != null && mCompanyList.size() > 0) {
                            queryCompanyList.addAll(QueryCompanyList(etCompanyNo.getFieldText(), etcompanyName.getFieldText(), etcomTel.getFieldText(), mCompanyList));
                            companyAdapter.notifyDataSetChanged();
                        }
                    }
                });
                companyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        companyDialog.dismiss();
                        if (companyAdapter.getCount() > 0) {
                            Company company = queryCompanyList.get(position);
                            mComkey = company.getKeyValue();
                            mCompanyName = queryCompanyList.get(position).getCompanyName();
                            NewFastOutInvoiceActivity.this.company.setFieldTextAndValue(company.getCompanyName(), ((String) company.getComKey()));
                        }
                    }
                });
            }
        });

        wareHouse.setOnChangeListener(new QpadEditText.OnChangeListener() {
            @Override
            public void onChanged(CommonSelectData data) {
                mReceivingWarehouseId = data.getValue();
                mReceivingWarehouseName = data.getText();
            }
        });

       /* produceName.setOnChangeListener(new QpadEditText.OnChangeListener() {
            @Override
            public void onChanged(CommonSelectData data) {
                mProductId = data.getValue();
                mProductName = data.getText();
                if (!QpadJudgeUtils.isEmpty(mProductId)) {
                    mPresenter.GetProductBatchByProductId(mProductId);
                }
            }
        });*/

        produceName.setOnDialogClickLister(new QpadEditText.OnDialogClickLister() {
            @Override
            public void OnDialogClick() {
                //根据产品编号，产品名称查询
                //初始化产品名称列表
                if (queryProductList.size() > 0) {
                    queryProductList.removeAll(queryProductList);
                }
                queryProductList.addAll(mProductList);
                productAdapter = new ProductListAdpater(queryProductList, mContext);
                View layout_queryProductNameList = LayoutInflater.from(mContext).inflate(R.layout.layout_productlist, null);
                final AlertDialog productDialog = new AlertDialog.Builder(mContext, R.style.Login_dialog).create();
                productDialog.setView(new EditText(mContext));
                productDialog.setCanceledOnTouchOutside(false);
                productDialog.show();
                productDialog.getWindow().setContentView(layout_queryProductNameList);
                WindowManager.LayoutParams lp_product = productDialog.getWindow().getAttributes();
                lp_product.width = (int) (width * 0.85);
                lp_product.height = (int) (height * 0.85);
                productDialog.getWindow().setAttributes(lp_product);
                final QpadEditText etProductCode = (QpadEditText) layout_queryProductNameList.findViewById(R.id.productCode);
                final QpadEditText etProductName = (QpadEditText) layout_queryProductNameList.findViewById(productName);
                Button queryProduct = (Button) layout_queryProductNameList.findViewById(R.id.queryProduct);
                ListView productListView = (ListView) layout_queryProductNameList.findViewById(R.id.productList);
                productListView.setAdapter(productAdapter);
                queryProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击查询按钮查询
                        if (queryProductList.size() > 0) {
                            queryProductList.removeAll(queryProductList);
                        }
                        if (mProductList != null && mProductList.size() > 0) {
                            queryProductList.addAll(QueryProductList(etProductCode.getFieldText(), etProductName.getFieldText(), mProductList));
                        }
                        productAdapter.notifyDataSetChanged();
                    }
                });
                productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        productDialog.dismiss();
                        //根据产品名称查找id
                        if (productAdapter.getCount() > 0 && mProductList.size() > 0) {
                            mProductId = queryProductList.get(position).getId() + "";
                            mProductName = queryProductList.get(position).getProductName();
                            produceName.setFieldTextAndValue(mProductName, mProductId);
                            if (!QpadJudgeUtils.isEmpty(mProductId)) {
                                mPresenter.GetProductBatchByProductId(mProductId);
                            }
                        }
                    }
                });
            }

        });

        produceBatch.setOnChangeListener(new QpadEditText.OnChangeListener() {
            @Override
            public void onChanged(CommonSelectData data) {
                mBatch = data.getValue();
                mBatchNo = data.getText();
            }
        });

        //没有数据时候重新加载
        company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCompanyList.size() == 0) {
                    mPresenter.GetAllCompList(ComKey, "2");
                }
            }
        });
        wareHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWarehouseList.size() == 0) {
                    mPresenter.GetWareHouseList(ComKey, IsUse);
                }
            }
        });
        produceName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mProductList.size() == 0) {
                    mPresenter.GetProductList(SysKey, IsUse);
                }
            }
        });
        produceBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mProductBatchList.size() == 0 && !QpadJudgeUtils.isEmpty(mProductId)) {
                    mPresenter.GetProductBatchByProductId(mProductId);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isContinous.setChecked(SharedPreferencesUtils.getBooleanData(Qpadapplication.getAppContext(), Config.ISCONTINOUS));//初始化是否连续扫码状态
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == InvoicingDetailActivity.INVOICINGDETAIL_OK) {
            IsCommitSuccess = data.getBooleanExtra("isCommitSuccess", false);
            Intent intent = new Intent();
            intent.putExtra("isCommitSuccess", IsCommitSuccess);
            setResult(NEWFASTOUTINVOICE_OK, intent);
            if (IsCommitSuccess) {
                finish();
            }
        }
        if (resultCode == CaptureActivity.RESULT_OK) {
            //连续扫码
            List<String> continousSmm = new ArrayList<>();
            if (isContinous.isChecked()) {
                String ewm_nums = data.getStringExtra("ewm_num");
                continousSmm = GetContinousSmm(ewm_nums, smm, rbAdd.isChecked());
                smmAdapter.notifyDataSetChanged();
                if (continousSmm.size() > 0) {
                    if (rbAdd.isChecked()) {
                        information.setText(GetBarCodeString4List2(continousSmm) + "\n添加成功！");
                    } else {
                        information.setText(GetBarCodeString4List2(continousSmm) + "\n删除成功！");
                    }
                }

                if (smm.size() > 0) {
                    scanNumber.setVisibility(View.VISIBLE);
                    scanNumber.setText("已扫描" + smm.size() + "条");
                } else {
                    scanNumber.setVisibility(View.GONE);
                }
                if (smm.size() > 0) {
                    scanNumber.setVisibility(View.VISIBLE);
                    scanNumber.setText("已扫描" + smm.size() + "条");
                } else {
                    scanNumber.setVisibility(View.GONE);
                }
                return;
            }

            String ewm_num = data.getStringExtra("ewm_num");
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
                information.setText(ewm_num + "删除成功！");
            }

            if (SuccessCount > 0) {
                count.setVisibility(View.VISIBLE);
                count.setText("已成功扫描" + SuccessCount + "条");
            } else {
                count.setVisibility(View.GONE);
            }

            if (smm.size() > 0) {
                scanNumber.setVisibility(View.VISIBLE);
                scanNumber.setText("已扫描" + smm.size() + "条");
            } else {
                scanNumber.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void PdaBroadcastReceiver(String code) {
        if (rbAdd.isChecked()) {
            if (!smm.contains(code)) {
                smm.add(code);
                smmAdapter.notifyDataSetChanged();
                information.setText(code + "添加成功！");
            } else {
                ToastUitl.showShort("此条码已经扫描，请重新扫码！");
            }
        } else if (rbDelete.isChecked() && smm.contains(code)) {
            smm.remove(code);
            smmAdapter.notifyDataSetChanged();
            information.setText(code + "删除成功！");
        }

        if (SuccessCount > 0) {
            count.setVisibility(View.VISIBLE);
            count.setText("已成功扫描" + SuccessCount + "条");
        } else {
            count.setVisibility(View.GONE);
        }

        if (smm.size() > 0) {
            scanNumber.setVisibility(View.VISIBLE);
            scanNumber.setText("已扫描" + smm.size() + "条");
        } else {
            scanNumber.setVisibility(View.GONE);
        }
    }

    /**
     * 连续扫码，如果选择添加就从已有扫码列表里添加不重复的，否则删除
     *
     * @param ewm_nums 拼凑起来的新的扫码
     * @param smm      旧的扫码列表
     * @param isAdd
     * @return
     */
    private List<String> GetContinousSmm(String ewm_nums, List<String> smm, boolean isAdd) {
        List<String> mIscontinousSmm = new ArrayList<>();//记录成功添加或者删除的条码
        String[] mSmm = ewm_nums.split(",");
        mSmm = condition(mSmm);//删除重复的
        if (ewm_nums != null && smm != null && mSmm.length > 0) {
            for (int i = 0; i < mSmm.length; i++) {
                if (isAdd) {
                    if (!smm.contains(mSmm[i])) {
                        smm.add(0, mSmm[i]);
                        mIscontinousSmm.add(mSmm[i]);
                    }
                } else {
                    if (smm.contains(mSmm[i])) {
                        smm.remove(mSmm[i]);
                        mIscontinousSmm.add(mSmm[i]);
                    }
                }
            }
        }
        return mIscontinousSmm;
    }

    @Override
    public void SetAllCompList(List<Company> companyList) {
        if (companyList.size() > 0) {
            mCompanyList.removeAll(mCompanyList);
        }
        for (int i = 0; i < companyList.size(); i++) {
            if (ComKey.equals(companyList.get(i).getKeyValue())) {
                companyList.remove(i);
            }
        }
        mCompanyList.addAll(companyList);
       /* if (companyList != null && companyList.size() > 0) {
            for (int i = 0; i < companyList.size(); i++) {
                Company mCompany = companyList.get(i);
                mCommonSelectDataCompanyList.add(new CommonSelectData(mCompany.getCompanyName(), mCompany.getKeyValue() + ""));
            }
            company.setLists(mCommonSelectDataCompanyList);
            if (isOld) {
                company.setFieldTextAndValue(oldReceivingComName);
            }
        }*/
        if (isOld) {
            company.setFieldTextAndValue(oldReceivingComName);
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


    /**
     * 设置产品批次列表
     *
     * @param productBatchList
     */
    @Override
    public void SetProductBatch(List<ProductBatch> productBatchList) {
        mProductBatchList = productBatchList;
        List<CommonSelectData> commonSelectProductBatchList = new ArrayList<>();
        if (productBatchList != null && productBatchList.size() > 0) {
            for (int i = 0; i < productBatchList.size(); i++) {
                ProductBatch productBatch = productBatchList.get(i);
                String id = productBatch.getId() + "";
                String batchNO = productBatch.getBatchNO();
                CommonSelectData commonSelectData = new CommonSelectData(batchNO, id);
                commonSelectData.setObj(productBatch);
                commonSelectProductBatchList.add(commonSelectData);
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
        if (!QpadJudgeUtils.isEmpty(mProductId)) {
            mPresenter.GetProductBatchByProductId(mProductId);
        }
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


    /**
     * 上传条码成功
     */
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
                count.setText("已成功上传" + SuccessCount + "条");
            } else {
                count.setVisibility(View.GONE);
            }
            if (smm.size() > 0) {
                scanNumber.setVisibility(View.VISIBLE);
                scanNumber.setText("已扫描" + smm.size() + "条");
            } else {
                scanNumber.setVisibility(View.GONE);
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
            //查询主表信息，获取Invid
            mPresenter.insertInv(SysKey, InvNumber.getFieldText());
        }
    }

    @Override
    public void SetInvid(int invId) {
        mInvId = invId;
//        ToastUitl.showLong("成功扫码：" + invId + "");
    }


    @OnClick(R.id.btnAddProduceBatch)
    public void btnAddProduceBatch(View view){
        btnAddProduceBatch.setVisibility(View.GONE);
        addProduceBatch.setVisibility(View.VISIBLE);
        saveBatch.setVisibility(View.VISIBLE);
    }

    /**
     * 保存批次
     *
     * @param view
     */
    @OnClick(R.id.saveBatch)
    public void setSaveBatch(View view) {
        btnAddProduceBatch.setVisibility(View.VISIBLE);
        addProduceBatch.setVisibility(View.INVISIBLE);
        saveBatch.setVisibility(View.INVISIBLE);

        String addProductBatch = addProduceBatch.getFieldText();
        if (QpadJudgeUtils.isEmpty(mProductName)) {
            final NormalDialog IsEmpty_ProductName_Dialog = new NormalDialog(mContext);
            QPadPromptDialogUtils.showOnePromptDialog(IsEmpty_ProductName_Dialog, "请选择产品！", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    IsEmpty_ProductName_Dialog.dismiss();
                }
            });
        } else if (QpadJudgeUtils.isEmpty(addProductBatch)) {
            final NormalDialog IsEmpty_ProductBatch_Dialog = new NormalDialog(mContext);
            QPadPromptDialogUtils.showOnePromptDialog(IsEmpty_ProductBatch_Dialog, "请填写批次！", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    IsEmpty_ProductBatch_Dialog.dismiss();
                }
            });
        } else {
            mPresenter.InsertProductBatch(addProductBatch, mProductId, SysKey, InvDate, LoginName);
        }
    }

    /**
     * 扫码
     *
     * @param view
     */
    @OnClick(R.id.scanywm)
    public void setScanywm(View view) {
        SharedPreferencesUtils.saveBooleanData(Qpadapplication.getAppContext(), Config.ISCONTINOUS, isContinous.isChecked());
        Intent intent = new Intent(NewFastOutInvoiceActivity.this, CaptureActivity.class);
        intent.putExtra("isContinous", isContinous.isChecked());
        startActivityForResult(intent, Config.REQUESTOK);
    }

    @OnClick(R.id.upload)
    public void upload(View view) {
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
            } else if (QpadJudgeUtils.isEmpty(mProductName)) {
                final NormalDialog IsEmpty_ProductName_Dialog = new NormalDialog(mContext);
                QPadPromptDialogUtils.showOnePromptDialog(IsEmpty_ProductName_Dialog, "请选择产品！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        IsEmpty_ProductName_Dialog.dismiss();
                    }
                });
            } else if (smm == null || smm.size() == 0) {
                final NormalDialog IsNotScan_Dialog = new NormalDialog(mContext);
                QPadPromptDialogUtils.showOnePromptDialog(IsNotScan_Dialog, "条码列表为空，请先扫码！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        IsNotScan_Dialog.dismiss();
                    }
                });
            } else if (smm != null && smm.size() > 0) {
                mPresenter.NewQuickScanBarCode(GetBarCodeString4List(smm),
                        InvNumber.getFieldText(),
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
                        mComkey,
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
            if (isOld && QpadJudgeUtils.isEmpty(mCompanyName)) {
                final NormalDialog IsEmpty_CompanyName_Dialog = new NormalDialog(mContext);
                QPadPromptDialogUtils.showOnePromptDialog(IsEmpty_CompanyName_Dialog, "机构不能空！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        IsEmpty_CompanyName_Dialog.dismiss();
                    }
                });
                return;
            }
            if (isOld && QpadJudgeUtils.isEmpty(mReceivingWarehouseId)) {
                final NormalDialog IsEmpty_Warehouse_Dialog = new NormalDialog(mContext);
                QPadPromptDialogUtils.showOnePromptDialog(IsEmpty_Warehouse_Dialog, "仓库不能为空！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        IsEmpty_Warehouse_Dialog.dismiss();
                    }
                });
                return;
            }
            if (isOld && QpadJudgeUtils.isEmpty(mReceivingWarehouseId)) {
                final NormalDialog IsEmpty_Product_Dialog = new NormalDialog(mContext);
                QPadPromptDialogUtils.showOnePromptDialog(IsEmpty_Product_Dialog, "产品不能为空！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        IsEmpty_Product_Dialog.dismiss();
                    }
                });
                return;
            }
            if (QpadJudgeUtils.isEmpty(mProductName)) {
                final NormalDialog IsEmpty_ProductName_Dialog = new NormalDialog(mContext);
                QPadPromptDialogUtils.showOnePromptDialog(IsEmpty_ProductName_Dialog, "请选择产品！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        IsEmpty_ProductName_Dialog.dismiss();
                    }
                });
            } else if (smm == null || smm.size() == 0) {
                final NormalDialog IsNotScan_Dialog = new NormalDialog(mContext);
                QPadPromptDialogUtils.showOnePromptDialog(IsNotScan_Dialog, "条码列表为空，请先扫码！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        IsNotScan_Dialog.dismiss();
                    }
                });
            } else if (smm != null && smm.size() > 0) {
                mPresenter.NewQuickScanBarCode(GetBarCodeString4List(smm),
                        InvNumber.getFieldText(),
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
                        mComkey,
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

    /**
     * 手动添加条码
     *
     * @param view
     */
    @OnClick(R.id.add)
    public void add(View view) {
        String et_ywm = etEditywm.getText().toString().trim();
        if (QpadJudgeUtils.isEmpty(et_ywm)) {
            ToastUitl.showShort("请输入条码！");
        } else if (!smm.contains(et_ywm)) {
            smm.add(0, et_ywm);
            smmAdapter.notifyDataSetChanged();
            information.setText(etEditywm.getText().toString().trim() + "添加成功！");
        } else {
            etEditywm.setText("");
            ToastUitl.showShort("已经添加此条码,请重新输入！");
        }
        etEditywm.setText("");//清空
        if (SuccessCount > 0) {
            count.setVisibility(View.VISIBLE);
            count.setText("已成功上传" + SuccessCount + "条");
        } else {
            count.setVisibility(View.GONE);
        }

        if (smm.size() > 0) {
            scanNumber.setVisibility(View.VISIBLE);
            scanNumber.setText("已扫描" + smm.size() + "条");
        } else {
            scanNumber.setVisibility(View.GONE);
        }
    }


    //查询InvId成功
    @Override
    public void insertInv(InvoicingBean invoicingBean) {
        mInvId = invoicingBean.getInvId();
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
        } else {
            Intent intent = new Intent(this, InvoicingDetailActivity.class);
            intent.putExtra("invId", mInvId + "");
            startActivityForResult(intent, REQUEST_OK);
        }
    }

    /**
     * 查询机构
     *
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
        QpadProgressUtils.showProgress(this, msg);
    }

    @Override
    public void stopProgressDialog() {
        QpadProgressUtils.closeProgress();
    }

    /**
     * 根据条件查询机构
     *
     * @param companyNo
     * @param companyName
     * @param comTel
     * @param companyList
     * @return
     */
    private List<Company> QueryCompanyList(String companyNo, String companyName, String comTel, List<Company> companyList) {
        List<Company> companys = new ArrayList<>();
        if (companyList == null) {
            return null;
        }
        if (companyList.size() == 0) {
            return null;
        }
        for (int i = 0; i < companyList.size(); i++) {
            if (StringUtils.ifIndexOf(companyList.get(i).getComTel() + "", comTel)
                    && StringUtils.ifIndexOf(companyList.get(i).getCompanyNo() + "", companyNo)
                    && StringUtils.ifIndexOf(companyList.get(i).getCompanyName() + "", companyName)) {
                companys.add(companyList.get(i));
            }
        }
        return companys;
    }

    /**
     * 根据条件查询产品
     *
     * @param productCode 产品编号
     * @param productName 产品名称
     * @param productList 产品列表
     * @return
     */
    private List<Product> QueryProductList(String productCode, String productName, List<Product> productList) {
        List<Product> products = new ArrayList<>();
        if (productList == null) {
            return null;
        } else if (productList.size() == 0) {
            return null;
        }
        if (QpadJudgeUtils.isEmpty(productCode) && !QpadJudgeUtils.isEmpty(productName)) {
            for (int i = 0; i < productList.size(); i++) {
                if (StringUtils.ifIndexOf(productList.get(i).getProductName(), productName)) {
                    products.add(productList.get(i));
                }
            }
        } else if (!QpadJudgeUtils.isEmpty(productCode) && QpadJudgeUtils.isEmpty(productName)) {
            for (int i = 0; i < productList.size(); i++) {
                if (StringUtils.ifIndexOf(productList.get(i).getProductCode(), productCode)) {
                    products.add(productList.get(i));
                }
            }
        } else if (!QpadJudgeUtils.isEmpty(productCode) && !QpadJudgeUtils.isEmpty(productName)) {
            for (int i = 0; i < productList.size(); i++) {
                if (StringUtils.ifIndexOf(productList.get(i).getProductCode(), productCode)
                        && StringUtils.ifIndexOf(productList.get(i).getProductName(), productName)) {
                    products.add(productList.get(i));
                }
            }
        } else {
            for (int i = 0; i < productList.size(); i++) {
                products.add(productList.get(i));
            }
        }
        return products;
    }




    /**
     * 计算扫码成功数目
     *
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
     * 非连续扫码时拼接
     *
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

    /**
     * 连续扫码时拼接
     *
     * @param smm
     * @return
     */
    private String GetBarCodeString4List2(List<String> smm) {
        StringBuffer sb = new StringBuffer();
        for (String s : smm) {
            sb.append(s).append("\n");
        }
        return sb.toString();
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

    /**
     * 去除重复
     *
     * @param mSmm
     * @return
     */
    private String[] condition(String[] mSmm) {
        List<String> result = new ArrayList<>();
        boolean flag = false;
        for (int i = 0; i < mSmm.length; i++) {
            flag = false;
            for (int j = 0; j < result.size(); j++) {
                if (mSmm[i].equals(result.get(j))) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                result.add(mSmm[i]);
            }
        }
        return (String[]) result.toArray(new String[result.size()]);

    }

}
