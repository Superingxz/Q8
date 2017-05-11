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

import static com.xologood.q8pad.R.id.expectedQty;

/**
 * Created by Administrator on 2017/1/8.
 */

public class NewOutInvoiceAdapter extends BaseAdapter {

    private List<InvoicingDetail> invoiceList;
    private LayoutInflater inflater;
    private Context context;

    public NewOutInvoiceAdapter(List<InvoicingDetail> invoiceList, Context context) {
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
        OutInvoiceViewHolder invoiceViewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_out_invoice, parent, false);
            invoiceViewHolder = new OutInvoiceViewHolder();
            invoiceViewHolder.productId = (TextView) convertView.findViewById(R.id.productId);
            invoiceViewHolder.productName = (TextView) convertView.findViewById(R.id.productName);
            invoiceViewHolder.creationDate = (TextView) convertView.findViewById(R.id.creationDate);
            invoiceViewHolder.expectedQty = (TextView) convertView.findViewById(expectedQty);
            invoiceViewHolder.actualQty = (TextView) convertView.findViewById(R.id.actualQty);
            invoiceViewHolder.standardUnitName = (TextView) convertView.findViewById(R.id.standardUnitName);
            convertView.setTag(invoiceViewHolder);
        } else {
            invoiceViewHolder = (OutInvoiceViewHolder) convertView.getTag();
        }
        if (invoiceList != null && invoiceList.size() > 0) {
            InvoicingDetail invoicingDetail = invoiceList.get(position);
            invoiceViewHolder.productId.setText(invoicingDetail.getProductCode()+"");
            invoiceViewHolder.productName.setText(invoicingDetail.getProductName());
            if (invoicingDetail.getCreationDate() != null) {
                invoiceViewHolder.creationDate.setText(StringUtils.GetCreationDate(invoicingDetail.getCreationDate()));
            }
            invoiceViewHolder.expectedQty.setText(invoicingDetail.getExpectedQty()+"");
            invoiceViewHolder.actualQty.setText(invoicingDetail.getActualQty()+"");
            invoiceViewHolder.standardUnitName.setText(invoicingDetail.getStandardUnitName());
        }
        return convertView;
    }

    /**
     * 更新指定位置的item或者增加item
     * @param invoicingDetail   产品名称
     */
    public void AddOrUpdate(InvoicingDetail invoicingDetail){
        String productName = invoicingDetail.getProductName();
        int ExpectedQty = invoicingDetail.getExpectedQty();   //预期数量
        if (invoiceList != null && invoiceList.size() > 0) {
            for (int i = 0; i < invoiceList.size(); i++) {
                InvoicingDetail mInvoicingDetail = invoiceList.get(i);
                String mProductName = mInvoicingDetail.getProductName();
                int mExpectedQty = mInvoicingDetail.getExpectedQty();
                if (productName.equals(mProductName)) {
                    invoicingDetail.setExpectedQty(mExpectedQty + ExpectedQty);
                } else {
                    invoiceList.add(invoicingDetail);
                }
            }
        } else if (invoiceList != null && invoiceList.size() == 0) {
            invoiceList.add(invoicingDetail);
        }
        notifyDataSetChanged();
    }

    /**
     * 更新指定位置的item
     * @param actualQty     实际数量
     * @param ProductName   产品名称
     */
    public void updateActualQty(int actualQty,String ProductName){
        if (invoiceList != null && invoiceList.size() > 0) {
            for (int i = 0; i < invoiceList.size(); i++) {
                InvoicingDetail invoicingDetail = invoiceList.get(i);
                String mProductName = invoicingDetail.getProductName();
                if (ProductName.equals(mProductName) ) {
                    int mActualQty = invoicingDetail.getActualQty();
                    invoicingDetail.setActualQty(mActualQty +actualQty);
                }
            }
        }
        notifyDataSetChanged();
    }


    class OutInvoiceViewHolder {
        TextView productId;
        TextView productName;
        TextView expectedQty;
        TextView actualQty;
        TextView creationDate;
        TextView standardUnitName;
    }

}
