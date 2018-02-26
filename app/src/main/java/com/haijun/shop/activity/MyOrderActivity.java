package com.haijun.shop.activity;

import android.os.Bundle;

import com.haijun.shop.R;

public class MyOrderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
    }

    @Override
    protected void initView() {
        setCenterText("我的订单");
        setLeftImage(R.drawable.back_normal);
    }

    @Override
    protected void initData() {

    }
}
