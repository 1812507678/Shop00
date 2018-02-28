package com.haijun.shop.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.haijun.shop.R;
import com.haijun.shop.bean.DeliveryAddress;
import com.haijun.shop.util.ToastUtil;
import com.lljjcoder.citypickerview.widget.CityPicker;

public class AddAddressActivity extends BaseActivity {

    private EditText et_address_name;
    private EditText et_address_phone;
    private TextView tv_address_address;
    private EditText et_address_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
    }

    @Override
    protected void initView() {
        setLeftImage(R.drawable.back_normal);
        setCenterText("添加收货地址");
        setRightText("保存");
        getTv_base_rightText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAddress();
            }
        });

        et_address_name = findViewById(R.id.et_address_name);
        et_address_phone = findViewById(R.id.et_address_phone);
        tv_address_address = findViewById(R.id.tv_address_address);
        et_address_detail = findViewById(R.id.et_address_detail);

        tv_address_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseAreaDialog();
            }
        });
    }

    private void showChooseAreaDialog() {
        CityPicker cityPicker = new CityPicker.Builder(this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                //.titleTextColor("#696969")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("江苏省")
                .city("常州市")
                .district("天宁区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
                tv_address_address.setText(province.trim() + " " + city.trim() + " " + district.trim());
            }
        });


    }


    @Override
    protected void initData() {

    }

    private void saveAddress() {
        String name = et_address_name.getText().toString();
        String phone = et_address_phone.getText().toString();
        String address = tv_address_address.getText().toString();
        String detail = et_address_detail.getText().toString();

        if (TextUtils.isEmpty(name)){
            ToastUtil.showToask("姓名未填写");
        }
        else if (TextUtils.isEmpty(phone)){
            ToastUtil.showToask("电话未填写");
        }
        else if (TextUtils.isEmpty(address)){
            ToastUtil.showToask("区域未填写");
        }
        else if (TextUtils.isEmpty(detail)){
            ToastUtil.showToask("详细地址未填写");
        }
        else {
            DeliveryAddress deliveryAddress = new DeliveryAddress(name,phone,address+" "+detail,false);
            ToastUtil.showToask("保存"+deliveryAddress);
            Intent intent = getIntent();
            setResult(RESULT_OK,intent);
            finish();
        }
    }


}
