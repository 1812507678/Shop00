package com.haijun.shop.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haijun.shop.R;
import com.haijun.shop.util.LogUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {


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

    }

}
