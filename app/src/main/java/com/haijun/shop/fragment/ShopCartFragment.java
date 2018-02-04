package com.haijun.shop.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.haijun.shop.R;
import com.haijun.shop.activity.GoodsDetailActivity;
import com.haijun.shop.adapter.ShopcartListAdapter;
import com.haijun.shop.bean.Goods;
import com.haijun.shop.util.LogUtil;
import com.haijun.shop.util.ShowToaskDialogUtil;
import com.haijun.shop.util.ToastUtil;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopCartFragment extends Fragment implements View.OnClickListener{


    private View inflate;
    private ListView lv_shopcart_list;
    private TextView tv_shopcart_money;

    private float allMoney;

    public ShopCartFragment() {
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
            inflate = inflater.inflate(R.layout.fragment_shopcart, container, false);
            LogUtil.i("ShopCartFragment","onCreateView");
            initView();// 控件初始化
            initData();
        }
        return inflate;
    }

    private void initView() {
        lv_shopcart_list = inflate.findViewById(R.id.lv_shopcart_list);
        tv_shopcart_money = inflate.findViewById(R.id.tv_shopcart_money);
        TextView tv_shopcart_pay = inflate.findViewById(R.id.tv_shopcart_pay);

        tv_shopcart_pay.setOnClickListener(this);

    }

    private void initData() {
        final ArrayList<Goods> goodsArrayList = new ArrayList<>();

        ShopcartListAdapter adapter = new ShopcartListAdapter(getContext(), goodsArrayList);
        lv_shopcart_list.setAdapter(adapter);

        lv_shopcart_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getActivity().startActivity(new Intent(getActivity(), GoodsDetailActivity.class));
            }
        });

        lv_shopcart_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShowToaskDialogUtil.showTipDialog(getContext(), "确定要移除购物车吗", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ToastUtil.showToask("已移除");
                    }
                });
                return true;
            }
        });

       adapter.setOnListViewRadioButtonSelected(new ShopcartListAdapter.OnListViewRadioCheckedChangeListener() {
           @Override
           public void onChecked(float price, boolean checked) {
               if (checked){
                   allMoney += price;
               }
               else {
                   allMoney -= price;
               }

               tv_shopcart_money.setText("总计："+(int)allMoney);
           }
       });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_shopcart_pay:
                ToastUtil.showToask("去付款");
                break;
        }
    }


}
