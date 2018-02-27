package com.haijun.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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


public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    private EditText et_login_account;
    private EditText et_login_pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    protected void initView() {
        setCenterText("登录");
        setLeftImage(R.drawable.back_normal);

        et_login_account = (EditText) findViewById(R.id.et_login_account);
        et_login_pwd = (EditText) findViewById(R.id.et_login_pwd);
    }

    @Override
    protected void initData() {

    }

    public void login(View view){
        String account = et_login_account.getText().toString();
        String password = et_login_pwd.getText().toString();

        //输入信息验证
        if (account.isEmpty()||password.isEmpty()){
            Toast.makeText(this,"请输入用户名或密码",Toast.LENGTH_SHORT).show();
            return;
        }
        //登陆验证
        validate(account,password);
    }

    public void forgetPassword(View view){
        //跳到找回密码页面
        startActivity(new Intent(this,RetrievePasswordActivity.class));
    }

    public void register(View view){
        //跳到注册页面，注册成功，则跳到主页面
        startActivityForResult(new Intent(this,RegisterActivity.class),120);
    }

    //验证是否时注册用户
    private void validate(final String phone, final String password) {
       DialogUtil.showDialog("正在登陆...",this);
        BmobQuery bmobQuery = new BmobQuery();
        bmobQuery.addWhereEqualTo("phone",phone);

        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e==null){
                    DialogUtil.hideDialog(LoginActivity.this);
                    //查询到对应的信息，则注册过，跳到主页面
                    if (list.size()==0){
                        //没有查询到对应的信息，则没注册过，提示该用户未注册
                        Toast.makeText(LoginActivity.this,"还未注册，请先进行注册",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        User user = list.get(0);
                        if (!user.getPassword().equals(password)){
                            Toast.makeText(LoginActivity.this,"密码不正确",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            UserUtil.saveUserToLocal(user);
                            ToastUtil.showToask("登陆成功");
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }
                    }
                }
                else if (e.getErrorCode()==101){
                    Toast.makeText(LoginActivity.this,"还没有注册，请先注册",Toast.LENGTH_SHORT).show();
                }
                else {
                    DialogUtil.hideDialog(LoginActivity.this);
                    Toast.makeText(LoginActivity.this,"登陆失败，请检查网络是否正常"+e,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //注册成功，页面销毁
        if (resultCode==RESULT_OK && requestCode==120){
            if (data.getBooleanExtra("registOK",false)) {
                finish();
            }
        }
    }


}

