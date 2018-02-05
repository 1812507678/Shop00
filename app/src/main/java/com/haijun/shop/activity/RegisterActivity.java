package com.haijun.shop.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.haijun.shop.R;
import com.haijun.shop.bean.User;
import com.haijun.shop.util.DialogUtil;
import com.haijun.shop.util.ToastUtil;
import com.haijun.shop.util.UserUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class RegisterActivity extends BaseActivity {

    private static final String TAG = "RegisterActivity";
    private ProgressDialog dialog;
    private EditText et_register_phone;
    private EditText et_register_password;
    private EditText et_register_repassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        initData();
    }

    protected void initView() {
        setCenterText("注册");
        et_register_phone = (EditText) findViewById(R.id.et_register_phone);
        et_register_password = (EditText) findViewById(R.id.et_register_password);
        et_register_repassword = (EditText) findViewById(R.id.et_register_repassword);
    }


    protected void initData() {

    }

    public void registerConfirm(View view){
        String phone = et_register_phone.getText().toString();
        String password = et_register_password.getText().toString();
        String confirmpassword = et_register_repassword.getText().toString();

        if (phone.isEmpty()){
            Toast.makeText(this,"请输入手机号",Toast.LENGTH_SHORT).show();
        }
        else if(password.isEmpty()){
            Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
        }

        else if(!password.isEmpty() && !confirmpassword.isEmpty() && !password.equals(confirmpassword)){
            Toast.makeText(this,"密码不一致",Toast.LENGTH_SHORT).show();
        }
        else {
            User user = new User(password,phone);
            insertToDB(user);
        }

    }

    //将数据插入到数据库
    private void insertToDB(final User user) {
        DialogUtil.showDialog("正在注册....",this);
        user.save(new SaveListener() {
            @Override
            public void done(Object o, BmobException e) {
                DialogUtil.hideDialog(RegisterActivity.this);
                if (e==null){
                    UserUtil.putUserToSP(user);
                    ToastUtil.showToask("注册成功");
                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                }
                else if (e.getErrorCode()==111){
                    ToastUtil.showToask("该用户已注册，请登录");
                }
                else {
                    Log.i(TAG,"onFailure:"+e);
                    ToastUtil.showToask("注册失败");
                }
            }
        });
    }

}
