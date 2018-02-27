package com.haijun.shop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by HP on 2016/11/25.
 */
public class FragmentListAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;
    //private String titleStrings[];

    public FragmentListAdapter(FragmentManager fm) {
        super(fm);
    }

    public FragmentListAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

}
