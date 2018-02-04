package com.haijun.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.haijun.shop.R;
import com.haijun.shop.bean.Goods;

import org.xutils.ImageManager;
import org.xutils.x;

import java.util.List;

/**
 * Created by haijun on 2018/2/3.
 */

public class ShopcartListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Goods> goodsList;
    private ImageManager mImageManager;

    public ShopcartListAdapter(Context mContext, List<Goods> goodsList) {
        this.mContext = mContext;
        this.goodsList = goodsList;
        mImageManager = x.image();
    }

    @Override
    public int getCount() {
        return goodsList.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Goods getItem(int position) {
        return goodsList == null ? null : goodsList.get(position);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_list_shopcart, null);
            holder.rb_item_select =  view.findViewById(R.id.rb_item_select);
            holder.iv_item_icon =  view.findViewById(R.id.iv_item_icon);
            holder.tv_item_title =  view.findViewById(R.id.tv_item_title);
            holder.tv_item_format =  view.findViewById(R.id.tv_item_format);
            holder.tv_item_price =  view.findViewById(R.id.tv_item_price);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Goods goods = getItem(i);
        mImageManager.bind(holder.iv_item_icon,goods.getLogoUrl());
        holder.tv_item_title.setText(goods.getName());
        holder.tv_item_format.setText("规格：fffsssdsdsdsdsdsdsdsdsds"+(int)goods.getCurPrice());
        holder.tv_item_price.setText("¥"+(int)goods.getCurPrice());

        holder.rb_item_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (onListViewRadioButtonSelected!=null){
                    onListViewRadioButtonSelected.onChecked(goods.getCurPrice(),b);
                }
            }
        });
        return view;
    }

    private class ViewHolder{
        private CheckBox rb_item_select;
        private ImageView iv_item_icon;
        private TextView tv_item_title;
        private TextView tv_item_format;
        private TextView tv_item_price;
    }

    public interface OnListViewRadioCheckedChangeListener{
        void onChecked(float price,boolean checked);
    }

    OnListViewRadioCheckedChangeListener onListViewRadioButtonSelected;

    public void setOnListViewRadioButtonSelected(OnListViewRadioCheckedChangeListener onListViewRadioButtonSelected) {
        this.onListViewRadioButtonSelected = onListViewRadioButtonSelected;
    }
}

