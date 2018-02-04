package com.haijun.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.haijun.shop.R;
import com.haijun.shop.util.ConfigUtil;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void initView() {
        setHeadViewDisable();

        if (ConfigUtil.isInnerUpdateAllowed){
            //ApkUtil.checkUpdate(this);
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        }.start();
    }

    @Override
    protected void initData() {

    }


}
