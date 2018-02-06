package com.haijun.shop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.haijun.shop.R;
import com.haijun.shop.adapter.HorizontalListViewAdapter;
import com.haijun.shop.bean.Goods;
import com.haijun.shop.bean.ProductCategory;
import com.haijun.shop.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class GoodsListActivity extends BaseActivity {

    private static final String TAG = GoodsListActivity.class.getSimpleName();
    private GridView mgv_goodslist_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
    }

    @Override
    protected void initView() {
        setCenterText("商品列表");
        setLeftImage(R.drawable.back_normal);

        mgv_goodslist_list = findViewById(R.id.mgv_goodslist_list);
    }

    @Override
    protected void initData() {
        final ArrayList<Goods> goodsArrayList = new ArrayList<>();
        final HorizontalListViewAdapter adapter = new HorizontalListViewAdapter(getApplicationContext(), goodsArrayList);
        mgv_goodslist_list.setAdapter(adapter);

        Intent intent = getIntent();
        ProductCategory.ProductCategoryType productCategory = intent.getParcelableExtra("productCategory");
        LogUtil.i(TAG,"productCategory:"+productCategory);

        BmobQuery<Goods> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("categoryType",productCategory);
        //bmobQuery.setLimit(5);
        bmobQuery.findObjects(new FindListener<Goods>() {
            @Override
            public void done(List<Goods> list, BmobException e) {
                if (e==null){
                    goodsArrayList.addAll(list);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        mgv_goodslist_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Goods goods = goodsArrayList.get(i);
                LogUtil.i(TAG,"goods:"+goods);
                Intent intent = new Intent(GoodsListActivity.this,GoodsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("goods",goods);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });

    }
}
