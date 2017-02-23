package com.xologood.q8pad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xologood.q8pad.R;
import com.xologood.q8pad.bean.Product;

import java.util.List;

import static com.xologood.q8pad.utils.DisplayUtil.dip2px;

/**
 * Created by Administrator on 17-2-22.
 */

public class ProductListAdpater extends BaseAdapter {
    private List<Product> productList;
    private LayoutInflater inflater;
    private Context context;

    public ProductListAdpater(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
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
        ProductViewHelper productViewHelper = null;
        if (convertView == null) {
            productViewHelper = new ProductViewHelper();
            convertView = inflater.inflate(R.layout.item_product, parent, false);
            productViewHelper.productName = (TextView) convertView.findViewById(R.id.productName);
            productViewHelper.productName.setPadding(left,top,right,bottom);
            convertView.setTag(productViewHelper);
        } else {
            productViewHelper = (ProductViewHelper) convertView.getTag();
        }
        if (productList != null && productList.size() > 0 && productList.get(position) != null) {
            productViewHelper.productName.setText(productList.get(position).getProductName());
        }
        return convertView;
    }

    class ProductViewHelper{
        TextView productName;
    }
}
