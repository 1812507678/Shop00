package com.haijun.shop.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.haijun.shop.R;
import com.haijun.shop.activity.AccountnfoActivity;
import com.haijun.shop.activity.LoginActivity;
import com.haijun.shop.activity.SettingActivity;
import com.haijun.shop.bean.User;
import com.haijun.shop.util.LogUtil;
import com.haijun.shop.util.UserUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment implements View.OnClickListener {


    private View inflate;

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
        }
        return inflate;
    }

    private void initView() {
        RelativeLayout rl_user_center = inflate.findViewById(R.id.rl_user_center);
        LinearLayout ll_me_personinf = inflate.findViewById(R.id.ll_me_personinf);
        LinearLayout ll_me_set = inflate.findViewById(R.id.ll_me_set);

        rl_user_center.setOnClickListener(this);
        ll_me_personinf.setOnClickListener(this);
        ll_me_set.setOnClickListener(this);
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
