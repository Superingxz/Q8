package com.xologood.q8pad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xologood.q8pad.R;
import com.xologood.q8pad.bean.GridBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wei on 2017/3/7.
 */

public class GridAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<GridBean> data = new ArrayList<>();
    Context context;

    public GridAdapter(List<GridBean> gridBeans, Context context) {
/*        for (int i = 0; i < gridBeans.size(); i++) {
            boolean isViserble = gridBeans.get(i).isViserble();
            if (isViserble==true){
                data.add(gridBeans.get(i));
            }
        }*/

        this.data = gridBeans;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {

        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.gridview_item, viewGroup, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

/*        boolean isViserble = data.get(i).isViserble();
        if(isViserble==false){
            convertView.setVisibility(View.GONE);
//            AbsListView.LayoutParams param = new AbsListView.LayoutParams(0,0);
//            convertView.setLayoutParams(param);
        }*/

        viewHolder.itemImage.setImageResource(data.get(i).getImageId());
        final String title = data.get(i).getTitle();
        viewHolder.itemTitle.setText(title);

/*        viewHolder.rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.equals("入库")){
                    context.startActivity(new Intent(context, InLibraryActivity.class));
                }if(title.equals("出库")){
                    context.startActivity(new Intent(context, OutLibraryActivity.class));
            }
        });*/

        return convertView;
    }

    class ViewHolder {
        RelativeLayout rlRoot;
        ImageView itemImage;
        TextView itemTitle;

        ViewHolder(View view) {
            rlRoot = (RelativeLayout) view.findViewById(R.id.rl_Root);
            itemImage = (ImageView) view.findViewById(R.id.itemImage);
            itemTitle = (TextView) view.findViewById(R.id.itemTitle);
        }
    }
}
