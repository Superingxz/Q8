package com.xologood.q8pad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xologood.q8pad.R;
import com.xologood.q8pad.bean.InvoicingBean;

import java.util.List;

import static com.xologood.q8pad.utils.DisplayUtil.dip2px;

/**
 * Created by Administrator on 17-2-22.
 */

public class InvoicingBeanAdapter extends BaseAdapter {
    private List<InvoicingBean> invoicingBeanList;
    private LayoutInflater inflater;
    private Context context;

    public InvoicingBeanAdapter(List<InvoicingBean> invoicingBeanList, Context context) {
        this.invoicingBeanList = invoicingBeanList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return invoicingBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return invoicingBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int left = 18;
        int top = dip2px(10);
        int right = 0;
        int bottom = dip2px(10);
        InvoicingBeanViewHelper invoicingBeanViewHelper = null;
        if (convertView == null) {
            invoicingBeanViewHelper = new InvoicingBeanViewHelper();
            convertView = inflater.inflate(R.layout.item_invoicintbean, parent, false);
            invoicingBeanViewHelper.InvNumber = (TextView) convertView.findViewById(R.id.InvNumber);
            invoicingBeanViewHelper.InvNumber.setPadding(left,top,right,bottom);
            convertView.setTag(invoicingBeanViewHelper);
        } else {
            invoicingBeanViewHelper = (InvoicingBeanViewHelper) convertView.getTag();
        }

        if (invoicingBeanList != null && invoicingBeanList.size() > 0 && invoicingBeanList.get(position) != null) {
            invoicingBeanViewHelper.InvNumber.setText(invoicingBeanList.get(position).getInvNumber());
        }
        return convertView;
    }

    class InvoicingBeanViewHelper{
        TextView InvNumber;
    }
}
