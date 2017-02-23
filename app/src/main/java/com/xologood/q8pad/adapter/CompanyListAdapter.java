package com.xologood.q8pad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xologood.q8pad.R;
import com.xologood.q8pad.bean.Company;

import java.util.List;

import static com.xologood.q8pad.utils.DisplayUtil.dip2px;

/**
 * Created by Administrator on 17-2-22.
 */

public class CompanyListAdapter extends BaseAdapter {
    private List<Company> companyList;
    private LayoutInflater inflater;
    private Context context;

    public CompanyListAdapter(List<Company> companyList, Context context) {
        this.companyList = companyList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return companyList.size();
    }

    @Override
    public Object getItem(int position) {
        return companyList.get(position);
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
        CompanyViewHelper companyViewHelper = null;
        if (convertView == null) {
            companyViewHelper = new CompanyViewHelper();
            convertView = inflater.inflate(R.layout.item_company, parent, false);
            companyViewHelper.companyName = (TextView) convertView.findViewById(R.id.companyName);
            companyViewHelper.companyName.setPadding(left,top,right,bottom);
            convertView.setTag(companyViewHelper);
        } else {
            companyViewHelper = (CompanyViewHelper) convertView.getTag();
        }

        if (companyList != null && companyList.size() > 0 && companyList.get(position) != null) {
            companyViewHelper.companyName.setText(companyList.get(position).getCompanyName());
        }
        return convertView;
    }

    class CompanyViewHelper{
        TextView companyName;
    }
}
