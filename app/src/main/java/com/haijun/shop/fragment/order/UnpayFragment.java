package com.haijun.shop.fragment.order;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haijun.shop.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnpayFragment extends Fragment {


    public UnpayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_unpay, container, false);
    }

}