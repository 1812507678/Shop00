package com.haijun.shop.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haijun.shop.R;
import com.haijun.shop.bean.Goods;

import org.xutils.ImageManager;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by haijun on 2018/2/2.
 */

public class HorizontalListViewAdapter extends BaseAdapter {
    /** 上下文 */
    private Context mContext;
    /** 图像数据源 */
    private ArrayList<Goods> mImageList;

    /** 数据源 */
    private ArrayList<Map<String, Integer>> mTextList;
    /** Image */
    private static String IMAGE = "ic_";

    private Map<String, Integer> mMap = null;
    int mSelectIndex;
    private ImageManager mImageManager;

    /** 构造方法 */
    public HorizontalListViewAdapter(Context context, ArrayList<Goods> imageList) {
        this.mContext = context;
        this.mImageList = imageList;
        mImageManager = x.image();
    }

    @Override
    public int getCount() {
        return mImageList.size();
    }

    @Override
    public Goods getItem(int position) {
        return mImageList == null ? null : mImageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.horizontal_list_item, null);
            holder.iv_goods_image = (ImageView) convertView.findViewById(R.id.iv_goods_image);
            holder.iv_goods_name = (TextView) convertView.findViewById(R.id.iv_goods_name);
            holder.iv_goods_curprice = (TextView) convertView.findViewById(R.id.iv_goods_curprice);
            holder.iv_goods_oldprice = (TextView) convertView.findViewById(R.id.iv_goods_oldprice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Goods goods = getItem(position);
        mImageManager.bind(holder.iv_goods_image,goods.getLogoUrl());
        holder.iv_goods_name.setText(goods.getName());
        holder.iv_goods_curprice.setText("¥"+(int)goods.getCurPrice()+"");
        holder.iv_goods_oldprice.setText("¥"+(int)goods.getOldPrice()+"");
        holder.iv_goods_oldprice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        return convertView;
    }

    private class ViewHolder {
        /** 图像 */
        private ImageView iv_goods_image;
        private TextView iv_goods_name;
        private TextView iv_goods_curprice;
        private TextView iv_goods_oldprice;
    }
}