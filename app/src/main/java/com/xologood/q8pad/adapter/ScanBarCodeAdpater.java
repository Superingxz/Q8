package com.xologood.q8pad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xologood.q8pad.R;
import com.xologood.q8pad.bean.BarCodeLog;

import java.util.List;

/**
 * Created by xiao on 2017/1/9 0009.
 */

public class ScanBarCodeAdpater extends BaseAdapter {
    private List<BarCodeLog> barCodeLogList;
    private Context context;
    private LayoutInflater inflater;

    public ScanBarCodeAdpater(List<BarCodeLog> barCodeLogList, Context context) {
        this.barCodeLogList = barCodeLogList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return barCodeLogList.size();
    }

    @Override
    public Object getItem(int position) {
        return barCodeLogList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ScanBarCodeViewHolder scanBarCodeViewHolder = null;
        if (convertView == null) {
            scanBarCodeViewHolder = new ScanBarCodeViewHolder();
            convertView = inflater.inflate(R.layout.item_scanbarcode, parent, false);
            scanBarCodeViewHolder.Remark = (TextView) convertView.findViewById(R.id.Remark);
            convertView.setTag(scanBarCodeViewHolder);
        } else {
            scanBarCodeViewHolder = (ScanBarCodeViewHolder) convertView.getTag();
        }
        if (barCodeLogList != null && barCodeLogList.size() > 0) {
            scanBarCodeViewHolder.Remark.setText(barCodeLogList.get(position).getBarCode()+barCodeLogList.get(position).getRemark());
        }
        return convertView;
    }

    class ScanBarCodeViewHolder{
        TextView Remark;
    }
}
