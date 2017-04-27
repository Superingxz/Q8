package com.xologood.q8pad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xologood.q8pad.R;
import com.xologood.q8pad.bean.ReportInv;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 物流列表
 * Created by wei on 2017/3/14.
 */

public class LogisticsAdapter extends BaseAdapter {
    Context context;
    List<ReportInv.RecordsBean> recordsList;
    LayoutInflater inflater;

    public LogisticsAdapter(Context context, List<ReportInv.RecordsBean> recordsList) {
        this.context = context;
        this.recordsList = recordsList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return recordsList.size();
    }

    @Override
    public Object getItem(int i) {
        return recordsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view==null){
            view = inflater.inflate(R.layout.item_logistic2, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        ReportInv.RecordsBean recordsBean = recordsList.get(i);

        viewHolder.InvDate.setText(recordsBean.getInvDate());
        viewHolder.ReceivingComName.setText(recordsBean.getReceivingComName());
        viewHolder.CompanyNo.setText(recordsBean.getCompanyNo());
        viewHolder.Province.setText(recordsBean.getProvince());
        viewHolder.City.setText(recordsBean.getCity());
        viewHolder.Area.setText(recordsBean.getArea());
        viewHolder.ReceivingWarehouseName.setText(recordsBean.getReceivingWarehouseName());
        viewHolder.InvByName.setText(recordsBean.getInvByName());
        viewHolder.InvNumber.setText(recordsBean.getInvNumber());
        viewHolder.TypeName.setText(recordsBean.getTypeName());
        viewHolder.InvGet.setText(recordsBean.getInvGet());
        viewHolder.InvByName2.setText(recordsBean.getInvByName());
        viewHolder.StateName.setText(recordsBean.getStateName());
        viewHolder.CheckUserName.setText(recordsBean.getCheckUserName());
        viewHolder.ReceivingPeople.setText("");

        return view;
    }

    static class ViewHolder {
        @Bind(R.id.InvDate)
        TextView InvDate;
        @Bind(R.id.ReceivingComName)
        TextView ReceivingComName;
        @Bind(R.id.CompanyNo)
        TextView CompanyNo;
        @Bind(R.id.Province)
        TextView Province;
        @Bind(R.id.City)
        TextView City;
        @Bind(R.id.Area)
        TextView Area;
        @Bind(R.id.ReceivingWarehouseName)
        TextView ReceivingWarehouseName;
        @Bind(R.id.InvByName)
        TextView InvByName;
        @Bind(R.id.InvNumber)
        TextView InvNumber;
        @Bind(R.id.TypeName)
        TextView TypeName;
        @Bind(R.id.InvGet)
        TextView InvGet;
        @Bind(R.id.InvByName2)
        TextView InvByName2;
        @Bind(R.id.StateName)
        TextView StateName;
        @Bind(R.id.CheckUserName)
        TextView CheckUserName;
        @Bind(R.id.ReceivingPeople)
        TextView ReceivingPeople;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
