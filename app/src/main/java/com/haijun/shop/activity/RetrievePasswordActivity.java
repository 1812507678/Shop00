package com.haijun.shop.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.haijun.shop.R;
import com.haijun.shop.bean.User;
import com.haijun.shop.util.DialogUtil;
import com.haijun.shop.util.ToastUtil;
import com.haijun.shop.util.UserUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
public class RetrievePasswordActivity extends BaseActivity {

    private static final String TAG = "RetrievePassword";
    private EditText tv_retrive_phone;
    private TextView et_retrive_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password);
    }

    protected void initView() {
        setCenterText("找回密码");
        setLeftImage(R.drawable.back_normal);

        tv_retrive_phone = (EditText) findViewById(R.id.tv_retrive_phone);
        et_retrive_password = (TextView) findViewById(R.id.et_retrive_password);
    }

    @Override
    protected void initData() {

    }

    public void getPassword(View view){
        String phone = tv_retrive_phone.getText().toString();
        if (phone.isEmpty()){
            Toast.makeText(this,"请输入手机号",Toast.LENGTH_SHORT).show();
        }
        else {
            retrievePassword(phone);
        }
    }

    private void retrievePassword(final String phone) {
        DialogUtil.showDialog("正在查找",this);
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("phone",phone);
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e==null){
                    DialogUtil.hideDialog(RetrievePasswordActivity.this);
                    et_retrive_password.setVisibility(View.VISIBLE);
                    if (list.size()>0){
                        User userInfo = list.get(0);
                        Log.i(TAG,"userInfo:"+userInfo.toString());
                        et_retrive_password.setText(userInfo.getPassword());
                        UserUtil.putUserToSP(userInfo);
                        ToastUtil.showToask("密码已显示，请登陆");
                    }
                    else {
                        et_retrive_password.setText("用户不存在或输入号码错误");
                        ToastUtil.showToask("用户不存在或输入号码错误");
                    }
                }else {
                    DialogUtil.hideDialog(RetrievePasswordActivity.this);
                    ToastUtil.showToask("网络异常");
                }
            }
        });
    }

}