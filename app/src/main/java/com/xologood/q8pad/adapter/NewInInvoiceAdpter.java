package com.xologood.q8pad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xologood.q8pad.R;
import com.xologood.q8pad.bean.InvoicingDetail;
import com.xologood.q8pad.utils.StringUtils;

import java.util.List;

/**
 * Created by xiao on 2017/1/5 0005.
 */

public class NewInInvoiceAdpter extends BaseAdapter {
    private List<InvoicingDetail> invoiceList;
    private LayoutInflater inflater;
    private Context context;

    public NewInInvoiceAdpter(List<InvoicingDetail> invoiceList, Context context) {
        this.invoiceList = invoiceList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return invoiceList.size();
    }

    @Override
    public Object getItem(int position) {
        return invoiceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InvoiceViewHolder invoiceViewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_invoice, parent, false);
            invoiceViewHolder = new InvoiceViewHolder();
            invoiceViewHolder.productName = (TextView) convertView.findViewById(R.id.productName);
            invoiceViewHolder.batchNO = (TextView) convertView.findViewById(R.id.batchNO);
            invoiceViewHolder.creationDate = (TextView) convertView.findViewById(R.id.creationDate);
            invoiceViewHolder.expectedQty = (TextView) convertView.findViewById(R.id.expectedQty);
            invoiceViewHolder.actualQty = (TextView) convertView.findViewById(R.id.actualQty);
            invoiceViewHolder.standardUnitName = (TextView) convertView.findViewById(R.id.standardUnitName);

            convertView.setTag(invoiceViewHolder);
        } else {
            invoiceViewHolder = (InvoiceViewHolder) convertView.getTag();
        }
        if (invoiceList != null && invoiceList.size() > 0) {
            InvoicingDetail invoicingDetail = invoiceList.get(position);
            invoiceViewHolder.productName.setText(invoicingDetail.getProductName());
            invoiceViewHolder.batchNO.setText(invoicingDetail.getBatchNO());

            if (invoicingDetail.getCreationDate() != null) {
                invoiceViewHolder.creationDate.setText(StringUtils.GetCreationDate(invoicingDetail.getCreationDate()));
            }
            invoiceViewHolder.expectedQty.setText(invoicingDetail.getExpectedQty() + "");
            invoiceViewHolder.actualQty.setText(invoicingDetail.getActualQty() + "");
            invoiceViewHolder.standardUnitName.setText(invoicingDetail.getStandardUnitName());
        }
        return convertView;
    }

    /**
     * 更新指定位置的item
     *
     * @param mExpectedQty 预期数量
     * @param ProductName  产品名称
     * @param BatchNo      批次名称
     */
    public boolean update(String mExpectedQty, String ProductName, String BatchNo) {
        if (invoiceList != null && invoiceList.size() > 0) {
            for (int i = 0; i < invoiceList.size(); i++) {
                InvoicingDetail invoicingDetail = invoiceList.get(i);
                String mProductName = invoicingDetail.getProductName();
                String mBatchNO = invoicingDetail.getBatchNO();
                if (ProductName.equals(mProductName) && BatchNo.equals(mBatchNO)) {
                    invoicingDetail.setExpectedQty(invoicingDetail.getExpectedQty() + Integer.valueOf(mExpectedQty).intValue());
                }
            }
        }
        notifyDataSetChanged();
        return true;
    }

    /**
     * 更新指定位置的item或者增加item
     *
     * @param invoicingDetail 产品名称
     */
    public boolean AddOrUpdate(InvoicingDetail invoicingDetail) {
        String productName = invoicingDetail.getProductName();
        int ExpectedQty = invoicingDetail.getExpectedQty();   //预期数量
        if (invoiceList != null && invoiceList.size() > 0) {
            for (int i = 0; i < invoiceList.size(); i++) {
                InvoicingDetail mInvoicingDetail = invoiceList.get(i);
                String mProductName = mInvoicingDetail.getProductName();
                int mExpectedQty = mInvoicingDetail.getExpectedQty();
                if (productName.equals(mProductName)) {
                    invoicingDetail.setExpectedQty(mExpectedQty + ExpectedQty);
                    notifyDataSetChanged();
                    return true;
                }
            }
        } else {
            invoiceList.add(invoicingDetail);
            notifyDataSetChanged();
            return true;
        }
        return false;
    }


    /**
     * 更新指定位置的item
     *
     * @param actualQty   实际数量
     * @param ProductName 产品名称
     * @param BatchNo     批次名称
     */
    public void updateActualQty(int actualQty, String ProductName, String BatchNo) {
        if (invoiceList != null && invoiceList.size() > 0) {
            for (int i = 0; i < invoiceList.size(); i++) {
                InvoicingDetail invoicingDetail = invoiceList.get(i);
                String mProductName = invoicingDetail.getProductName();
                String mBatchNO = invoicingDetail.getBatchNO();
                if (ProductName.equals(mProductName) && BatchNo.equals(mBatchNO)) {
                    invoicingDetail.setActualQty(invoicingDetail.getActualQty() + actualQty);
                }
            }
        }
        notifyDataSetChanged();
    }

    class InvoiceViewHolder {
        TextView productName;
        TextView batchNO;
        TextView expectedQty;
        TextView actualQty;
        TextView creationDate;
        TextView standardUnitName;
    }


}
