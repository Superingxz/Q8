package com.xologood.q8pad.ui.inlibrary.newininvoice;

import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
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
import com.xologood.q8pad.adapter.ProductListAdpater;
import com.xologood.q8pad.bean.Invoice;
import com.xologood.q8pad.bean.InvoicingBean;
import com.xologood.q8pad.bean.InvoicingDetail;
import com.xologood.q8pad.bean.Product;
import com.xologood.q8pad.bean.ProductBatch;
import com.xologood.q8pad.bean.ProportionConversion;
import com.xologood.q8pad.bean.StandardUnit;
import com.xologood.q8pad.bean.SupplierBean;
import com.xologood.q8pad.bean.Warehouse;
import com.xologood.q8pad.ui.invoicingdetail.InvoicingDetailActivity;
import com.xologood.q8pad.ui.scan.ScanActivity;
import com.xologood.q8pad.utils.SharedPreferencesUtils;
import com.xologood.q8pad.utils.StringUtils;
import com.xologood.q8pad.view.ScrollListView;
import com.xologood.q8pad.view.TitleView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

import static com.xologood.q8pad.R.id.productName;

public class NewInInvoiceActivity extends BaseActivity<NewInInvoicePresenter, NewInInvoiceModel>
        implements NewInInvoiceContract.View {
    private static final String TAG = "test";
    private static final int SCAN = 100;
    private static final int REQUEST_OK = 101;
    @Bind(R.id.title_view)
    TitleView titleView;
    @Bind(R.id.InvNumber)
    QpadEditText InvNumber;
    @Bind(R.id.InvTime)
    QpadEditText InvTime;
    @Bind(R.id.wareHouse)
    QpadEditText wareHouse;
    @Bind(R.id.produceName)
    QpadEditText produceName;
    //    @Bind(R.id.queryName)
//    Button queryName;
    @Bind(R.id.addProduceBatch)
    QpadEditText addProduceBatch;
    @Bind(R.id.saveBatch)
    Button saveBatch;
    @Bind(R.id.produceBatch)
    QpadEditText produceBatch;
    @Bind(R.id.expectedQty)
    EditText expectedQty;
    @Bind(R.id.standardUnit)
    QpadEditText standardUnit;
    @Bind(R.id.saveNnit)
    Button saveNnit;
    @Bind(R.id.lv)
    ScrollListView lv;
    @Bind(R.id.commit)
    Button commit;
    @Bind(R.id.new_in_invoice_ll)
    LinearLayout newInInvoiceLl;
    @Bind(R.id.btnAddProduceBatch)
    Button btnAddProduceBatch;
    @Bind(R.id.et_supplierList)
    QpadEditText etSupplierList;


    //已有入库传过来数据
    private Intent intent;
    private boolean IsOld = false;
    private String oldInvNumber;
    private String oldInvDate;
    private String oldWarehouseId;

    private String LoginName;
    private String SysKey;
    private String ComKey;
    private String ComName;
    private String UserId;
    private String UserName;
    private String IsUse;

    private Date date;

    private String mCreationDate;//生产日期
    private String mProductId;  //产品id
    private String mProductName;
    private String mBatch;
    private String mBatchNo;
    private String mBunit;      //单位id
    private String mExpectedQty; //根据获取单位比例 接口 返回的总预计数量
    private String mStandardUnit;
    private String mInvNumber;
    private String mReceivingWarehouseId;
    private String mReceivingWarehouseName;
    private String InvDate;//开单时间
    private NewInInvoiceAdpter newInInvoiceAdpter;//保存后明细列表adapter
    private Map<String, String> options; //保存入库主表请求参数
    private int invId; //入库单号
    private String ExpectedQty;

    String CompleteBy, CompleteDate, mSupplierId, mSupplierName;


    private List<Warehouse> mWarehouseList;
    private List<Product> mProductList;
    private List<ProductBatch> mProductBatchList;
    private List<SupplierBean> mSupplierBeanList;
    private List<StandardUnit> mStandardUnitList;
    private List<InvoicingDetail> mInvoicingDetailList;
    private boolean isSave = false; //是否保存成功

    private boolean IsCommitSuccess = false;//是否成功确认提交单据
    private boolean isClickSava = false; //是否点击保存按钮

    private ProductListAdpater productAdapter;
    private List<Product> queryProductList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_in_invoice;
    }


    @Override
    public void initView() {
        titleView.setTitle("新建订单入库");

        mWarehouseList = new ArrayList<>();
        mSupplierBeanList = new ArrayList<>();

        mProductList = new ArrayList<>();
        mProductBatchList = new ArrayList<>();
        mStandardUnitList = new ArrayList<>();
        mInvoicingDetailList = new ArrayList();
        queryProductList = new ArrayList<>();

        date = new Date();
        InvDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        options = new HashMap<>();

        SysKey = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.SYSKEY);
        ComKey = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.COMKEY);
        ComName = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.COMNAME);

        LoginName = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.LOGINNAME);
        UserName = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.USERNAME);
        UserId = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.USERID);
        IsUse = SharedPreferencesUtils.getStringData(Qpadapplication.getAppContext(), Config.ISUSE);

        intent = getIntent();
        IsOld = intent.getBooleanExtra("isOld", false);
        oldInvNumber = intent.getStringExtra("InvNumber");
        oldInvDate = intent.getStringExtra("InvDate");
        oldWarehouseId = intent.getStringExtra("WarehouseId");
        //初始化单号 创建时间
        if (IsOld) { //如果是已有入库
            invId = intent.getIntExtra("invId", 0);
            InvNumber.setFieldTextAndValue(oldInvNumber);
            InvTime.setFieldTextAndValue(oldInvDate);
            wareHouse.setFieldEnabled(false);
            InvNumber.setFieldEnabled(false);
            InvTime.setFieldEnabled(false);
        } else {
            InvNumber.setFieldTextAndValue(getInvNumber(1, UserId));
            InvTime.setFieldTextAndValue(InvDate);
        }

        //获取仓库名称列表
        mPresenter.GetWareHouseList(ComKey, IsUse);
        //获取产品列表
        mPresenter.GetProductList(SysKey, IsUse);

        //宾氏
        if ("1703271033178204uh0".equals(SysKey)) {
            etSupplierList.setVisibility(View.VISIBLE);

            //获取供应商列表
            Map supplierMap = new HashMap();
            supplierMap.put("pageIndex","0");
            supplierMap.put("pageSize","0");
            mPresenter.GetSupplierList(supplierMap);
        }

        newInInvoiceAdpter = new NewInInvoiceAdpter(mInvoicingDetailList, this);
        lv.setAdapter(newInInvoiceAdpter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NewInInvoiceActivity.this, ScanActivity.class);
                InvoicingDetail invoicingDetail = mInvoicingDetailList.get(position);
                intent.putExtra("InvDetailId", invoicingDetail.getId() + "");
                intent.putExtra("InvId", invoicingDetail.getInvId());
                intent.putExtra("ProductId", invoicingDetail.getProductId() + "");
                intent.putExtra("Batch", invoicingDetail.getBatch() + "");
                intent.putExtra("ProductName", invoicingDetail.getProductName());
                intent.putExtra("BatchNo", invoicingDetail.getBatchNO());
                intent.putExtra("ReceivingWarehouseId", wareHouse.getFieldValue());
                intent.putExtra("CreationDate", StringUtils.GetCreationDate(invoicingDetail.getCreationDate()));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == InvoicingDetailActivity.INVOICINGDETAIL_OK) {
            IsCommitSuccess = data.getBooleanExtra("isCommitSuccess", false);
            if (IsCommitSuccess) {
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //监听事件
    @Override
    public void initListener() {
        wareHouse.setOnChangeListener(new QpadEditText.OnChangeListener() {
            @Override
            public void onChanged(CommonSelectData data) {
                mReceivingWarehouseId = data.getValue();
                mReceivingWarehouseName = data.getText();
            }
        });


        etSupplierList.setOnChangeListener(new QpadEditText.OnChangeListener() {
            @Override
            public void onChanged(CommonSelectData data) {
                mSupplierId = data.getValue();
                mSupplierName = data.getText();
            }
        });
        
        /*produceName.setOnChangeListener(new QpadEditText.OnChangeListener() {
            @Override
            public void onChanged(CommonSelectData data) {
                mProductId = data.getValue();
                mProductName = data.getText();
                if (!QpadJudgeUtils.isEmpty(mProductId)) {
                    mPresenter.GetProductBatchByProductId(mProductId);
                    mPresenter.GetStandardUnitByProductId(mProductId, SysKey);
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
                                mPresenter.GetStandardUnitByProductId(mProductId, SysKey);
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
                ProductBatch productBatch = (ProductBatch) data.getObj();
                if (productBatch != null) {
                    mCreationDate = productBatch.getCreationDate();
                }
            }
        });

        standardUnit.setOnChangeListener(new QpadEditText.OnChangeListener() {
            @Override
            public void onChanged(CommonSelectData data) {
                Log.i("superingxz", "onChanged: 选择单位id:" + data.getValue() + "选择单位名称:" + data.getText());
                mBunit = data.getValue();
                mStandardUnit = data.getText();
            }
        });



        //没有数据会重新加载
        wareHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWarehouseList.size() == 0) {
                    mPresenter.GetWareHouseList(ComKey, IsUse);
                }
            }
        });

        //供应商
        etSupplierList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSupplierBeanList.size() == 0) {
                    //获取供应商列表
                    Map supplierMap = new HashMap();
                    supplierMap.put("pageIndex","0");
                    supplierMap.put("pageSize","0");
                    mPresenter.GetSupplierList(supplierMap);
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

        standardUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStandardUnitList.size() == 0 && !QpadJudgeUtils.isEmpty(mProductId)) {
                    mPresenter.GetStandardUnitByProductId(mProductId, SysKey);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isSave) {
            mPresenter.GetInvoicingDetail(invId + "");
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
            if (IsOld) {
                wareHouse.setFieldTextAndValueFromValue(oldWarehouseId);
            } else {
                wareHouse.setFieldTextAndValue(commonSelectWarehouse.get(0));
            }
        }
    }



    /**
     * 设置供应商列表
     * @param supplierBeanList
     */
    @Override
    public void SetSupplierList(List<SupplierBean> supplierBeanList) {
        mSupplierBeanList = supplierBeanList;
        List<CommonSelectData> commonSelectSupplierBean = new ArrayList<>();
        if (supplierBeanList != null && supplierBeanList.size() > 0) {
            for (int i = 0; i < supplierBeanList.size(); i++) {
                String supplierId = supplierBeanList.get(i).getSupplierId() + "";
                String supplierName = supplierBeanList.get(i).getSupplierName();

                commonSelectSupplierBean.add(new CommonSelectData(supplierName, supplierId));
            }
            etSupplierList.setLists(commonSelectSupplierBean);
        }
    }

    /**
     * 设置产品列表
     *
     * @param productList
     */
    @Override
    public void SetProductList(final List<Product> productList) {
        if (mProductList.size() > 0) {
            mProductList.removeAll(mProductList);
        }
        mProductList.addAll(productList);
      /*  List<CommonSelectData> commonSelectProductList = new ArrayList<>();
        if (productList != null && productList.size() > 0) {
            for (int i = 0; i < productList.size(); i++) {
                String id = productList.get(i).getId() + "";
                String productName = productList.get(i).getProductName();
                commonSelectProductList.add(new CommonSelectData(productName, id));
            }
            produceName.setLists(commonSelectProductList);
        }*/
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
     * 设置单位列表
     *
     * @param standardUnitList
     */
    @Override
    public void SetStandardUnitByProductId(List<StandardUnit> standardUnitList) {
        mStandardUnitList = standardUnitList;
        List<CommonSelectData> commonSelectStandardUnitList = new ArrayList<>();
        if (standardUnitList != null && standardUnitList.size() > 0) {
            for (int i = 0; i < standardUnitList.size(); i++) {
                String id = standardUnitList.get(i).getId() + "";
                String standardUnitName = standardUnitList.get(i).getStandardUnitName();
                commonSelectStandardUnitList.add(new CommonSelectData(standardUnitName, id));
            }
            standardUnit.setLists(commonSelectStandardUnitList);
        }
    }

    /**
     * 添加入库主表成功后回调
     *
     * @param invoicingBean
     */
    @Override
    public void insertInv(InvoicingBean invoicingBean) {
        mPresenter.Invoicing(SysKey, InvNumber.getFieldText());
    }

    /**
     * 查询入库主表成功后回调
     *
     * @param invoicingBean
     */
    @Override
    public void Invoicing(InvoicingBean invoicingBean) {
        wareHouse.setFieldEnabled(false);    //一个入库单只在一个仓库里(保存完后禁用)
        String produceId = this.produceName.getFieldValue();
        String batchNo = this.produceBatch.getFieldValue();      //批次Id
        invId = invoicingBean.getInvId();
        CompleteBy = invoicingBean.getInvByName();
        CompleteDate = invoicingBean.getInvDate();

        //宾氏入库保存主表后调用
        if ("1703271033178204uh0".equals(SysKey)) {

            Map<String, String> invSupplierMap = new HashMap<String, String>();
            invSupplierMap.put("CompleteBy", CompleteBy);
            invSupplierMap.put("CompleteDate", CompleteDate);
            invSupplierMap.put("IsUse", IsUse);
            invSupplierMap.put("SysKey", SysKey);
            invSupplierMap.put("InvId", invId + "");
            invSupplierMap.put("SupplierId", mSupplierId);
            invSupplierMap.put("SupplierName", mSupplierName);
            invSupplierMap.put("pageIndex","0");
            invSupplierMap.put("pageSize","0");

            Log.e(TAG, "invSupplierMap: "+invSupplierMap);
            mPresenter.insertInvSupplier(invSupplierMap);
        }


        if (invId != -1 && invId > 0) {
            mPresenter.GetInvoiceDetail("0", invId + "", produceId, batchNo, "0", mExpectedQty, ComKey, SysKey);
        }
    }

    /**
     * 宾氏入库回调
     *
     * @param invoicingBean
     */
    @Override
    public void insertInvSupplier(InvoicingBean invoicingBean) {

    }

    /**
     * 验证入库明细成功后回调(根据返回Id  "0" 则新增入库明细数据 否则 更新修改入库明细数据)
     *
     * @param Id    明细id  写死0
     * @param InvId 单号
     */
    @Override
    public void GetInvoiceDetailSuccess(int Id, String InvId, String ExpectedQty) {
        if (mExpectedQty != null && ExpectedQty != null) {
            mExpectedQty = (Integer.valueOf(mExpectedQty) + Integer.valueOf(ExpectedQty)) + "";
        }
        if (Id == 0) {
            mPresenter.InsertInvoiceDetail(Id + "", invId + "", mProductId, mBatch, "0", mExpectedQty, ComKey, SysKey);
        } else {
            mPresenter.UpdateInvoiceDetail(Id + "", invId + "", mProductId, mBatch, "0", mExpectedQty, ComKey, SysKey);
        }
    }

    /**
     * 增加入库明细成功后回调
     *
     * @param Id
     */
    @Override
    public void InsertInvoiceDetailSuccess(int Id) {
        mPresenter.GetInvoicingDetail(invId + "");
    }

    /**
     * 更新入库明细成功后回调
     *
     * @param Id
     */
    @Override
    public void UpdateInvoiceDetailSuccess(int Id) {
        mPresenter.GetInvoicingDetail(invId + "");
    }

    @Override
    public void SetInvoicingDetail(Invoice invoice) {
        if (invoice != null && invoice.getInvoicingDetail() != null) {
            mInvoicingDetailList.clear();
            mInvoicingDetailList.addAll(invoice.getInvoicingDetail());
            newInInvoiceAdpter.notifyDataSetChanged();
            isSave = true; //这里（更新 增加 和扫描后回来都执行到这里  isSave设置为保存成功）
            final NormalDialog IsSava_Success_Dialog = new NormalDialog(mContext);
            if (isClickSava) {
                QPadPromptDialogUtils.showOnePromptDialog(IsSava_Success_Dialog, "保存成功！", new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        IsSava_Success_Dialog.dismiss();
                    }
                });
            }
        }
    }

    /**
     * 获取到单位比例成功后回调
     *
     * @param proportionConversion
     */
    @Override
    public void SetProportionConversion(ProportionConversion proportionConversion) {
        mExpectedQty = proportionConversion.getSum();
    }

    /**
     * 获取到单位比例成功后回调，然后再保存入库主表
     *
     * @param proportionConversion
     */
    @Override
    public void SetProportionConversion(String proportionConversion) {
        ProportionConversion pc = new Gson().fromJson(proportionConversion, ProportionConversion.class);
        mExpectedQty = pc.getSum();
        String CheckDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        options.put("InvId", "0");
        options.put("Pid", "0");
        options.put("InvBy", UserId);
        options.put("InvByName", UserName);
        options.put("InvNumber", mInvNumber);
        options.put("InvDate", InvDate);
        options.put("InvReMark", "暂无");
        options.put("InvGet", "APP");
        options.put("InvType", "1");
        options.put("CheckedParty", "false");
        options.put("InvState", "1");
        options.put("CodeType", "true");
        options.put("CheckUserId", UserId);
        options.put("CheckUserName", UserName);
        options.put("CheckDate", CheckDate);
        options.put("ReceivingComKey", ComKey);
        options.put("ReceivingComName", ComName);
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

//    /**
//     * 搜索
//     *
//     * @param view
//     */
//    @OnClick(R.id.queryName)
//    public void setQueryName(View view) {
//    }

    /**
     * 保存批次
     *
     * @param view
     */
    @OnClick(R.id.saveBatch)
    public void setSaveBatch(View view) {
        isClickSava = true;
        String addProductBatch = addProduceBatch.getFieldText();
        if (IsOld && QpadJudgeUtils.isEmpty(mProductName)) {
            final NormalDialog IsEmpty_Product_Dialog = new NormalDialog(mContext);
            QPadPromptDialogUtils.showOnePromptDialog(IsEmpty_Product_Dialog, "产品不能空！", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    IsEmpty_Product_Dialog.dismiss();
                }
            });
            return;
        }
        if (IsOld && QpadJudgeUtils.isEmpty(addProductBatch)) {
            final NormalDialog IsEmpty_addProductBatch_Dialog = new NormalDialog(mContext);
            QPadPromptDialogUtils.showOnePromptDialog(IsEmpty_addProductBatch_Dialog, "产品批次不能空！", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    IsEmpty_addProductBatch_Dialog.dismiss();
                }
            });
            return;
        }
        if (QpadJudgeUtils.isEmpty(addProductBatch)) {
            final NormalDialog IsEmpty_ProductBatch_Dialog = new NormalDialog(mContext);
            QPadPromptDialogUtils.showOnePromptDialog(IsEmpty_ProductBatch_Dialog, "请选择批次！", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    IsEmpty_ProductBatch_Dialog.dismiss();
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
        } else {
            mPresenter.InsertProductBatch(addProductBatch, mProductId, SysKey, InvDate, LoginName);
        }
    }

    /**
     * 保存单个单据
     *
     * @param view
     */
    @OnClick(R.id.saveNnit)
    public void setSaveNnit(View view) {
        ExpectedQty = expectedQty.getText().toString().trim(); //保存时候再去获取填写的预计数量和单号
        mInvNumber = InvNumber.getFieldText();

        if (QpadJudgeUtils.isEmpty(mProductName)) {
            final NormalDialog IsEmpty_ProductName_Dialog = new NormalDialog(mContext);
            QPadPromptDialogUtils.showOnePromptDialog(IsEmpty_ProductName_Dialog, "请选择产品！", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    IsEmpty_ProductName_Dialog.dismiss();
                }
            });
        } else if (QpadJudgeUtils.isEmpty(ExpectedQty) || "0".equals(ExpectedQty)) {
            final NormalDialog IsEmpty_ExpectedQty_Dialog = new NormalDialog(mContext);
            QPadPromptDialogUtils.showOnePromptDialog(IsEmpty_ExpectedQty_Dialog, "入库数量不能为空或者不能为0！", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    IsEmpty_ExpectedQty_Dialog.dismiss();
                }
            });
        } else if (!StringUtils.IsDigitNumber(expectedQty.getText().toString().trim())) {
            final NormalDialog IsEmpty_NotZero_Dialog = new NormalDialog(mContext);
            QPadPromptDialogUtils.showOnePromptDialog(IsEmpty_NotZero_Dialog, "入库数量不能为空或者不能为0！", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    IsEmpty_NotZero_Dialog.dismiss();
                }
            });
        } else if (QpadJudgeUtils.isEmpty(mStandardUnit)) {
            final NormalDialog IsEmpty_StandardUnit_Dialog = new NormalDialog(mContext);
            QPadPromptDialogUtils.showOnePromptDialog(IsEmpty_StandardUnit_Dialog, "请选择单位！", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    IsEmpty_StandardUnit_Dialog.dismiss();
                }
            });
        } else {
            //先获取预计数量和单号   在调用获取单位比例接口获取实际的预计数量
            //在获取到单位比例回调时，保存入库主表
            mPresenter.GetProportionConversionString(mProductId, mBunit, ExpectedQty);
        }
    }

    /**
     * 确定提交  成功
     *
     * @param msg
     */
    @Override
    public void CompleteSaveSuccess(String msg) {
        ToastUitl.showLong(msg);
    }

    /**
     * 确定完成
     *
     * @param view
     */
    @OnClick(R.id.commit)
    public void setCommit(View view) {
        if (isHasZero(mInvoicingDetailList)) {
            final NormalDialog IsZero_Dialog = new NormalDialog(mContext);
            QPadPromptDialogUtils.showOnePromptDialog(IsZero_Dialog, "实际数量不能为0", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    IsZero_Dialog.dismiss();
                }
            });
            return;
        }
        if (isSave) {
            Intent intent = new Intent(NewInInvoiceActivity.this, InvoicingDetailActivity.class);
            intent.putExtra("invId", invId + "");
            startActivityForResult(intent, REQUEST_OK);
        } else {
            final NormalDialog IsSava_Dialog = new NormalDialog(mContext);
            QPadPromptDialogUtils.showOnePromptDialog(IsSava_Dialog, "数据未保存，请先保存数据！", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    IsSava_Dialog.dismiss();
                }
            });
        }
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

    private boolean isHasZero(List<InvoicingDetail> mInvoicingDetailList) {
        boolean isZero = false;
        if (mInvoicingDetailList.size() > 0) {
            for (int i = 0; i < mInvoicingDetailList.size(); i++) {
                InvoicingDetail invoicingDetail = mInvoicingDetailList.get(i);
                if (invoicingDetail.getActualQty() == 0) {
                    return true;
                }
            }
        }
        return false;
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
     * 生成单号
     *
     * @param i
     * @param userId
     * @return
     */
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


    @OnClick(R.id.btnAddProduceBatch)
    public void btnAddProduceBatch(View view) {
        addProduceBatch.setVisibility(View.VISIBLE);
        saveBatch.setVisibility(View.VISIBLE);
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

}


