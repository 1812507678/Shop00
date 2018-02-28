package com.haijun.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.haijun.shop.R;
import com.haijun.shop.adapter.DeliveryAddressAdapter;
import com.haijun.shop.bean.DeliveryAddress;
import com.haijun.shop.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class DeliveryAddressActivity extends BaseActivity{

    private ListView lv_address_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);
    }

    @Override
    protected void initView() {
        setLeftImage(R.drawable.back_normal);
        setCenterText("管理收货地址");

        lv_address_item = findViewById(R.id.lv_address_item);

        final List<DeliveryAddress> deliveryAddressList = new ArrayList<>();
        deliveryAddressList.add(new DeliveryAddress("马海军","18689463192","广东省深圳市西丽深圳大学城学范大道1068号中科院深圳先进技术研究院F栋5楼",true));
        deliveryAddressList.add(new DeliveryAddress("马海军","18689463192","广东省深圳市西丽深圳大学城学范大道1068号中科院深圳先进技术研究院F栋5楼",false));

        int curSelectedPosition = 0;
        if (deliveryAddressList.size()>1){
            for (int i=0;i<deliveryAddressList.size();i++){
                if (deliveryAddressList.get(i).isDefault()){
                    curSelectedPosition = i;
                    break;
                }
            }
        }

        final DeliveryAddressAdapter adapter = new DeliveryAddressAdapter(this, deliveryAddressList);
        adapter.setmCurSelectedPosition(curSelectedPosition);
        lv_address_item.setAdapter(adapter);

        adapter.setOnListViewContentClickListener(new DeliveryAddressAdapter.OnListViewContentClickListener() {
            @Override
            public void onClick(int position, int type) {
                DeliveryAddress deliveryAddress = deliveryAddressList.get(position);
                switch (type){
                    case DeliveryAddressAdapter.OnListViewContentClickListener.type_setdefault:
                        ToastUtil.showToask("setdefault:"+position);
                        updateDefalutSelectAddress(position,adapter);
                        break;
                    case DeliveryAddressAdapter.OnListViewContentClickListener.type_delete:
                        ToastUtil.showToask("delete:"+position);
                        break;
                    case DeliveryAddressAdapter.OnListViewContentClickListener.type_edit:
                        ToastUtil.showToask("edit:"+position);
                        break;
                }
            }
        });
    }

    private void updateDefalutSelectAddress(int position, DeliveryAddressAdapter adapter) {
        if (adapter.getmCurSelectedPosition()==position){
            return;
        }
        adapter.setmCurSelectedPosition(position);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void initData() {

    }


    public void addAddress(View view) {
        startActivityForResult(new Intent(this,AddAddressActivity.class),100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100 && resultCode==RESULT_OK){

        }
    }
}
