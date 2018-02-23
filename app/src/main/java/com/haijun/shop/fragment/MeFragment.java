package com.haijun.shop.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haijun.shop.R;
import com.haijun.shop.activity.AccountnfoActivity;
import com.haijun.shop.activity.LoginActivity;
import com.haijun.shop.activity.SettingActivity;
import com.haijun.shop.bean.User;
import com.haijun.shop.util.LogUtil;
import com.haijun.shop.util.UserUtil;
import com.haijun.shop.view.CircleImageView;

import org.xutils.x;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment implements View.OnClickListener {


    private View inflate;
    private CircleImageView cv_me_icon;
    private TextView tv_me_nickname;

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null != inflate) {
            ViewGroup parent = (ViewGroup) inflate.getParent();
            if (null != parent) {
                parent.removeView(inflate);
            }
        } else {
            inflate = inflater.inflate(R.layout.fragment_me, container, false);
            LogUtil.i("MeFragment","onCreateView");
            initView();// 控件初始化
            initData();
        }
        return inflate;
    }

    private void initView() {
        RelativeLayout rl_user_center = inflate.findViewById(R.id.rl_user_center);
        LinearLayout ll_me_personinf = inflate.findViewById(R.id.ll_me_personinf);
        LinearLayout ll_me_set = inflate.findViewById(R.id.ll_me_set);

        cv_me_icon = inflate.findViewById(R.id.cv_me_icon);
        tv_me_nickname = inflate.findViewById(R.id.tv_me_nickname);

        rl_user_center.setOnClickListener(this);
        ll_me_personinf.setOnClickListener(this);
        ll_me_set.setOnClickListener(this);
    }

    private void initData() {
        User userInfo = UserUtil.getUserInfo();
        if (userInfo!=null){
            if (!TextUtils.isEmpty(userInfo.getPhone())){
                tv_me_nickname.setText(userInfo.getPhone());
            }

            if (!TextUtils.isEmpty(userInfo.getNickname())){
                tv_me_nickname.setText(userInfo.getNickname());
            }

            if (!TextUtils.isEmpty(userInfo.getIconUrl())){
                x.image().bind(cv_me_icon,userInfo.getIconUrl());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_user_center:
            case R.id.ll_me_personinf:
                User userInfo = UserUtil.getUserInfo();
                if (userInfo!=null){
                    startActivity(new Intent(getActivity(), AccountnfoActivity.class));
                }
                else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;

            case R.id.ll_me_set:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }
}
