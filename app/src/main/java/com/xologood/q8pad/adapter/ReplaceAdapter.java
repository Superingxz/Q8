package com.xologood.q8pad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/1/8.
 */

public class ReplaceAdapter extends BaseAdapter {

    private List<String> smm;
    private LayoutInflater inflater;
    private Context context;

    public ReplaceAdapter(List<String> smm, Context context) {
        this.smm = smm;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return smm.size();
    }

    @Override
    public Object getItem(int position) {
        return smm.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return convertView;
    }



}
