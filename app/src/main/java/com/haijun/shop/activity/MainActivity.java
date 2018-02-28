package com.haijun.shop.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.haijun.shop.R;
import com.haijun.shop.bean.Apk;
import com.haijun.shop.fragment.HomeFragment;
import com.haijun.shop.fragment.MeFragment;
import com.haijun.shop.fragment.ShopCartFragment;
import com.haijun.shop.fragment.WithDrawFragment;
import com.haijun.shop.util.ApkUtil;
import com.haijun.shop.util.ChooseAlertDialogUtil;
import com.haijun.shop.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private RadioGroup radioGroup;
    private List<Fragment> fragmentList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogUtil.i(TAG,"onCreate");
    }

    //初始化页面
    @Override
    protected void initView() {
        setRightImage(R.drawable.chat);
        getIv_base_rightimage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseAlertDialogUtil chooseAlertDialogUtil = new ChooseAlertDialogUtil(MainActivity.this);
                chooseAlertDialogUtil.setAlertDialogTextContact();
            }
        });

        radioGroup =  findViewById(R.id.radioGroup);
        changeRadioGroupImageSize();

        //找到四个按钮
        RadioButton button_1 = findViewById(R.id.button_1);
        RadioButton button_2 = findViewById(R.id.button_2);
        RadioButton button_3 = findViewById(R.id.button_3);
        RadioButton button_4 = findViewById(R.id.button_4);

        //将Fragment对象添加到list中
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new WithDrawFragment());
        fragmentList.add(new ShopCartFragment());
        fragmentList.add(new MeFragment());

        //设置RadioGroup开始时设置的按钮，设置第一个按钮为默认值
        radioGroup.check(R.id.button_1);
        setCenterText(button_1.getText().toString());


        //设置按钮点击监听
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);

        //初始时向容器中添加第一个Fragment对象
        addFragment(0);


    }

    @Override
    protected void initData() {
        ApkUtil.checkUpdate(this);

    }

    private void changeRadioGroupImageSize() {
        int[] imgsID = new int[]{R.drawable.radiobutton_image1,R.drawable.radiobutton_image2,R.drawable.radiobutton_image3,R.drawable.radiobutton_image4};
        for(int i=0;i<4;i++){
            RadioButton rb=(RadioButton) radioGroup.getChildAt(i);
            int width = (int) getResources().getDimension(R.dimen.x80);
            Drawable d=getResources().getDrawable(imgsID[i]);
            d.setBounds(0, 0,width, width);
            rb.setCompoundDrawables(null,d , null, null);
        }
    }

    @Override
    public void finish() {
        ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
        viewGroup.removeAllViews();
        super.finish();
    }

    //点击事件处理,不同按钮对应着不同的Fragment对象页面
    @Override
    public void onClick(View v) {
        RadioButton v1 = (RadioButton) v;
        setCenterText(v1.getText().toString());
        addFragment(radioGroup.indexOfChild(v1));
    }

    //向Activity中添加Fragment的方法
    public void addFragment(int position) {
        Fragment fragment = fragmentList.get(position);
        //获得Fragment管理器
        FragmentManager fragmentManager = getSupportFragmentManager();
        //使用管理器开启事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //使用事务替换Fragment容器中Fragment对象
        fragmentTransaction.replace(R.id.framelayout,fragment);
        //提交事务，否则事务不生效
        fragmentTransaction.commit();
    }



}
