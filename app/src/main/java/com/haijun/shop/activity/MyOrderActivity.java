package com.haijun.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.haijun.shop.R;
import com.haijun.shop.adapter.FragmentListAdapter;
import com.haijun.shop.fragment.order.FinshedFragment;
import com.haijun.shop.fragment.order.UnevaluateFragment;
import com.haijun.shop.fragment.order.UnpayFragment;
import com.haijun.shop.fragment.order.UnreceiveFragment;
import com.haijun.shop.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private RadioGroup radioGroup;
    private ViewPager vp_analysis_content;
    private View v_analysis_select;
    private float mOneTableWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
    }

    @Override
    protected void initView() {
        setCenterText("我的订单");
        setLeftImage(R.drawable.back_normal);

        radioGroup = findViewById(R.id.radioGroup);
        vp_analysis_content = findViewById(R.id.vp_analysis_content);
        v_analysis_select = findViewById(R.id.v_analysis_select);

        //找到四个按钮
        RadioButton rb_order_unpay = findViewById(R.id.rb_order_unpay);
        RadioButton rb_order_unreceive =  findViewById(R.id.rb_order_unreceive);
        RadioButton rb_order_unevaluate =  findViewById(R.id.rb_order_unevaluate);
        RadioButton rb_order_finish =  findViewById(R.id.rb_order_finish);

        //将Fragment对象添加到list中
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new UnpayFragment());
        fragmentList.add(new UnreceiveFragment());
        fragmentList.add(new UnevaluateFragment());
        fragmentList.add(new FinshedFragment());

        //设置RadioGroup开始时设置的按钮，设置第一个按钮为默认值
        radioGroup.check(R.id.button_1);


        //设置按钮点击监听
        rb_order_unpay.setOnClickListener(this);
        rb_order_unreceive.setOnClickListener(this);
        rb_order_unevaluate.setOnClickListener(this);
        rb_order_finish.setOnClickListener(this);

        vp_analysis_content.setAdapter(new FragmentListAdapter(getSupportFragmentManager(), fragmentList));

        mOneTableWidth = ScreenUtil.getScreenWidth(this)/fragmentList.size();

        vp_analysis_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v_analysis_select.getLayoutParams();
                int floatWidth=  (int) (mOneTableWidth *(positionOffset+position));  //view向左的偏移量
                layoutParams.setMargins(floatWidth,0,0,0); //4个参数按顺序分别是左上右下
                v_analysis_select.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageSelected(int position) {
                radioGroup.check(radioGroup.getChildAt(position).getId());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);
        if (position!=-1){
            setViewPageItem(position);
        }


    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        setViewPageItem(radioGroup.indexOfChild(v));
    }

    //点击时设置选中条目
    public void setViewPageItem(int viewPageItem) {
        vp_analysis_content.setCurrentItem(viewPageItem);
        RelativeLayout.LayoutParams layoutParams =   (RelativeLayout.LayoutParams) v_analysis_select.getLayoutParams();
        int floatWidth= (int) (mOneTableWidth*viewPageItem);  //view向左的偏移量
        layoutParams.setMargins(floatWidth,0,0,0); //4个参数按顺序分别是左上右下
        v_analysis_select.setLayoutParams(layoutParams);
        radioGroup.check(radioGroup.getChildAt(viewPageItem).getId());
    }
}
