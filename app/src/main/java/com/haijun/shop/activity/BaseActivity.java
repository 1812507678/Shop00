package com.haijun.shop.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haijun.shop.R;
import com.haijun.shop.util.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;


public abstract class BaseActivity extends FragmentActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();
    public ImageView iv_base_leftimage;
    public TextView tv_base_rightText;
    public TextView tv_base_centerText;
    public ImageView iv_base_rightimage;
    private TextView tv_base_lefttext;
    private LinearLayout parentLinearLayout;//把父类activity和子类activity的view都add到这里


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.base_activity_head);

    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
        initView();
        initData();
    }

    /**
     * 初始化contentview
     */
    private void initContentView(int layoutResID) {
        StatusBarUtil.StatusBarLightMode(this);

        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        parentLinearLayout = new LinearLayout(this);
        parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
        parentLinearLayout.setClickable(true);
        parentLinearLayout.setFitsSystemWindows(true);
        viewGroup.addView(parentLinearLayout);
        View actionbarLayout = LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);

        iv_base_leftimage = (ImageView) actionbarLayout.findViewById(R.id.iv_base_leftimage);
        tv_base_centerText = (TextView) actionbarLayout.findViewById(R.id.tv_base_centerText);
        tv_base_lefttext = (TextView) actionbarLayout.findViewById(R.id.tv_base_lefttext);
        tv_base_rightText = (TextView) actionbarLayout.findViewById(R.id.tv_base_rightText);
        iv_base_rightimage = (ImageView) actionbarLayout.findViewById(R.id.iv_base_rightimage);

        iv_base_leftimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    //布局初始化
    protected abstract void initView();

    //数据初始化
    protected abstract void initData();

    public void back(){
        finish();
    }

    public void setLeftImage(int resource) {
        iv_base_leftimage.setImageResource(resource);
        iv_base_leftimage.setVisibility(View.VISIBLE);
    }

    public void setRightImage(int resource) {
        iv_base_rightimage.setImageResource(resource);
        iv_base_rightimage.setVisibility(View.VISIBLE);
    }

    public void setCenterText(String text) {
        tv_base_centerText.setText(text);
    }

    public void setLeftText(String text) {
        tv_base_lefttext.setText(text);
        tv_base_lefttext.setVisibility(View.VISIBLE);
    }

    public void setRightText(String text) {
        tv_base_rightText.setText(text);
        tv_base_rightText.setVisibility(View.VISIBLE);
    }


    public ImageView getIv_base_leftimage() {
        return iv_base_leftimage;
    }

    public void setIv_base_leftimage(ImageView iv_base_leftimage) {
        this.iv_base_leftimage = iv_base_leftimage;
    }

    public TextView getTv_base_leftText() {
        return tv_base_lefttext;
    }


    public TextView getTv_base_rightText() {
        return tv_base_rightText;
    }

    public void setTv_base_rightText(TextView tv_base_rightText) {
        this.tv_base_rightText = tv_base_rightText;
    }

    public TextView getTv_base_centerText() {
        return tv_base_centerText;
    }

    public void setTv_base_centerText(TextView tv_base_centerText) {
        this.tv_base_centerText = tv_base_centerText;
    }

    public ImageView getIv_base_rightimage() {
        return iv_base_rightimage;
    }

    public void setIv_base_rightimage(ImageView iv_base_rightimage) {
        this.iv_base_rightimage = iv_base_rightimage;
    }

    public void setHeadBackgroudColor(String color){
        RelativeLayout rl_base_head = (RelativeLayout) findViewById(R.id.rl_base_head);
        rl_base_head.setBackgroundColor(Color.parseColor(color));
    }

    public void setHeadViewDisable(){
        RelativeLayout rl_base_head = (RelativeLayout) findViewById(R.id.rl_base_head);
        rl_base_head.setVisibility(View.GONE);
    }

    public void setCenterTextColor(String color){
        tv_base_centerText.setTextColor(Color.parseColor(color));
    }

}
