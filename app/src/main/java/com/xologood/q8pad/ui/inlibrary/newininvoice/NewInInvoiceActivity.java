package com.xologood.q8pad.ui.inlibrary.newininvoice;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.xologood.q8pad.bean.InvoicingBean;
import com.xologood.q8pad.bean.InvoicingDetail;
import com.xologood.q8pad.bean.Product;
import com.xologood.q8pad.bean.ProductBatch;
import com.xologood.q8pad.bean.ProportionConversion;
import com.xologood.q8pad.bean.StandardUnit;
import com.xologood.q8pad.bean.Warehouse;
import com.xologood.q8pad.ui.invoicingdetail.InvoicingDetailActivity;
import com.xologood.q8pad.ui.scan.ScanActivity;
import com.xologood.q8pad.utils.SharedPreferencesUtils;
import com.xologood.q8pad.utils.StringUtils;
import com.xologood.q8pad.view.TitileView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class NewInInvoiceActivity extends BaseActivity<NewInInvoicePresenter, NewInInvoiceModel>
        implements NewInInvoiceContract.View {
    private static final String TAG = "Superingxz";
    private static final int SCAN = 100;
    @Bind(R.id.title_view)
    TitileView titleView;
    @Bind(R.id.InvNumber)
    QpadEditText InvNumber;
    @Bind(R.id.InvTime)
    QpadEditText InvTime;
    @Bind(R.id.wareHouse)
    QpadEditText wareHouse;
    @Bind(R.id.produceName)
    QpadEditText produceName;
    @Bind(R.id.queryName)
    Button queryName;
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
    ListView lv;
    @Bind(R.id.commit)
    Button commit;
    @Bind(R.id.new_in_invoice_ll)
    LinearLayout newInInvoiceLl;


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

    private NewInInvoiceAdpter newInInvoiceAdpter;
    private List<InvoicingDetail> newInInvoiceList;

    private Map<String, String> options; //保存入库主表请求参数
    private int invId; //入库单号

    private boolean HasSava = false;
    private String ExpectedQty;

    private boolean isSave = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_in_invoice;
    }


    @Override
    public void initView() {
        titleView.setTitle("新建订单入库");

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

        //获取仓库名称列表 产品列表
        mPresenter.GetWareHouseList(ComKey, IsUse);
        mPresenter.GetProductList(SysKey, IsUse);


        //初始化单号 创建时间
        InvNumber.setFieldTextAndValue(getInvNumber(1, UserId));
        InvTime.setFieldTextAndValue(InvDate);


        newInInvoiceList = new ArrayList<>();
        newInInvoiceAdpter = new NewInInvoiceAdpter(newInInvoiceList, this);
        lv.setAdapter(newInInvoiceAdpter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NewInInvoiceActivity.this, ScanActivity.class);
                InvoicingDetail invoicingDetail = newInInvoiceList.get(position);
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
        InvoicingDetail ClickInvoicingDetail = (InvoicingDetail) lv.getTag();
        if (resultCode == ScanActivity.RESULT_OK) {
            int mActualQty = data.getIntExtra("mActualQty", 0);
            newInInvoiceAdpter.updateActualQty(mActualQty, ClickInvoicingDetail.getProductName(), ClickInvoicingDetail.getBatchNO());
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

        produceName.setOnChangeListener(new QpadEditText.OnChangeListener() {
            @Override
            public void onChanged(CommonSelectData data) {
                String value = data.getValue();
                if (value != null) {
                    mPresenter.GetProductBatchByProductId(value);
                    mPresenter.GetStandardUnitByProductId(value, SysKey);
                    mProductId = data.getValue();
                    mProductName = data.getText();
                }
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

    }

    /**
     * 设置仓库列表
     *
     * @param warehouseList
     */
    @Override
    public void SetWareHouseList(List<Warehouse> warehouseList) {
        List<CommonSelectData> commonSelectWarehouse = new ArrayList<>();
        if (warehouseList != null && warehouseList.size() > 0) {
            for (int i = 0; i < warehouseList.size(); i++) {
                String id = warehouseList.get(i).getId() + "";
                String name = warehouseList.get(i).getName();
                commonSelectWarehouse.add(new CommonSelectData(name, id));
            }
            wareHouse.setLists(commonSelectWarehouse);
            wareHouse.setFieldTextAndValue(commonSelectWarehouse.get(0));
        }
    }

    /**
     * 设置产品列表
     *
     * @param productList
     */
    @Override
    public void SetProductList(List<Product> productList) {
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
     * 添加入库主表成功回调
     *
     * @param invoicingBean
     */
    @Override
    public void insertInv(InvoicingBean invoicingBean) {
        mPresenter.insertInv(SysKey, InvNumber.getFieldText());
    }

    @Override
    public void insertInv2(InvoicingBean invoicingBean) {
        wareHouse.setFieldEnabled(false);    //一个入库单只在一个仓库里(保存完后禁用)
        String produceId = this.produceName.getFieldValue();
        String batchNo = this.produceBatch.getFieldValue();      //批次Id
        invId = invoicingBean.getInvId();
        if (invId != -1 && invId > 0) {
            mPresenter.GetInvoiceDetail("0", invId + "", produceId, batchNo, "0", mExpectedQty, ComKey, SysKey);
        }
    }

    /**
     * 验证入库明细(根据返回Id  "0" 则新增入库明细数据 否则 更新修改入库明细数据)
     *
     * @param Id 明细id  写死0
     */
    @Override
    public void GetInvoiceDetailSuccess(int Id) {
        if (Id == 0) {
            // ToastUitl.showLong("增加入库明细:" + Id);
            mPresenter.InsertInvoiceDetail(Id + "", invId + "", mProductId, mBatch, "0", mExpectedQty, ComKey, SysKey);
        } else {
            mPresenter.UpdateInvoiceDetail(Id + "", invId + "", mProductId, mBatch, "0", mExpectedQty, ComKey, SysKey);
        }
        final NormalDialog IsSava_Success_Dialog = new NormalDialog(mContext);
        QPadPromptDialogUtils.showOnePromptDialog(IsSava_Success_Dialog, "保存成功！", new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                IsSava_Success_Dialog.dismiss();
            }
        });
    }

    /**
     * 增加入库明细成功
     *
     * @param Id
     */
    @Override
    public void InsertInvoiceDetailSuccess(int Id) {
        //AddOrUpdate(Id);
        int batch = Integer.valueOf(mBatch).intValue();
        int expectedQty = Integer.valueOf(mExpectedQty).intValue();
        InvoicingDetail newInInvoice = new InvoicingDetail(Id, invId, mProductId, mProductName, batch,
                mBatchNo, expectedQty, 0, mCreationDate, mStandardUnit);
        newInInvoiceList.add(newInInvoice);
        newInInvoiceAdpter.notifyDataSetChanged();
        isSave = true;
    }

    /**
     * 更新入库明细成功
     *
     * @param Id
     */
    @Override
    public void UpdateInvoiceDetailSuccess(int Id) {
        //AddOrUpdate(Id);
        isSave = newInInvoiceAdpter.update(mExpectedQty, mProductName, mBatchNo);
    }

    private void AddOrUpdate(int Id) {
        int batch = Integer.valueOf(mBatch).intValue();
        int expectedQty = Integer.valueOf(mExpectedQty).intValue();
        InvoicingDetail newInInvoice = new InvoicingDetail(Id, invId, mProductId, mProductName, batch,
                mBatchNo, expectedQty, 0, mCreationDate, mStandardUnit);
        HasSava = newInInvoiceAdpter.AddOrUpdate(newInInvoice);
    }

    /**
     * 获取到单位比例
     *
     * @param proportionConversion
     */
    @Override

    public void SetProportionConversion(ProportionConversion proportionConversion) {
        mExpectedQty = proportionConversion.getSum();
    }

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
        options.put("InvGet", "在线PDA");
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
     * 搜索
     *
     * @param view
     */
    @OnClick(R.id.queryName)
    public void setQueryName(View view) {
    }

    /**
     * 保存批次
     *
     * @param view
     */
    @OnClick(R.id.saveBatch)
    public void setSaveBatch(View view) {
        String addProductBatch = addProduceBatch.getFieldText();
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
            mPresenter.InsertProductBatch(addProductBatch, mProductName, SysKey, InvDate, LoginName);
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
        if (isSave) {
            Intent intent = new Intent(NewInInvoiceActivity.this, InvoicingDetailActivity.class);
            intent.putExtra("invId", invId + "");
            startActivity(intent);
            finish();
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

    @Override
    public void startProgressDialog(String msg) {
        QpadProgressUtils.showProgress(this,msg);
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



}


