package com.haijun.shop.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.haijun.shop.R;
import com.haijun.shop.bean.ApplyInfo;
import com.haijun.shop.util.Constant;
import com.haijun.shop.util.DialogUtil;
import com.haijun.shop.util.SPUtil;
import com.haijun.shop.util.ToastUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class WithDrawApplyActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "WithDrawApplyActivity";
    private EditText et_commit_money;
    private EditText et_commit_mouth;
    private EditText et_commit_name;
    private EditText et_commit_phone;
    private EditText et_commit_qq;
    private EditText et_commit_certif;
    private EditText et_commit_acount;
    private EditText et_commit_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_draw);
    }

    @Override
    protected void initView() {
        setCenterText("额度申请");
        setLeftImage(R.drawable.back_normal);

        et_commit_money = (EditText) findViewById(R.id.et_commit_money);
        et_commit_name = (EditText) findViewById(R.id.et_commit_name);
        et_commit_mouth = (EditText) findViewById(R.id.et_commit_mouth);
        et_commit_phone = (EditText) findViewById(R.id.et_commit_phone);
        et_commit_qq = (EditText) findViewById(R.id.et_commit_qq);
        et_commit_certif = (EditText) findViewById(R.id.et_commit_certif);
        et_commit_acount = (EditText) findViewById(R.id.et_commit_acount);
        et_commit_password = (EditText) findViewById(R.id.et_commit_password);

        Button bt_withdraw_submit = findViewById(R.id.bt_withdraw_submit);
        bt_withdraw_submit.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    public void commitInfo(){
        String name = et_commit_name.getText().toString();
        String money = et_commit_money.getText().toString();
        String mouth = et_commit_mouth.getText().toString();
        String phone = et_commit_phone.getText().toString();
        String qq = et_commit_qq.getText().toString();
        String certificate = et_commit_certif.getText().toString();
        String account = et_commit_acount.getText().toString();
        String password = et_commit_password.getText().toString();


        if (money.equals("")){
            ToastUtil.showToask("请输入金额");
        }
        else if (mouth.equals("")){
            ToastUtil.showToask( "请输入月数");
        }
        else if (name.equals("")){
            ToastUtil.showToask("请输入姓名");
        }
        else if (phone.equals("")){
            ToastUtil.showToask( "请输入电话");
        }
        else if (qq.equals("")){
            ToastUtil.showToask("请输入qq");
        }
        else if (certificate.equals("")){
            ToastUtil.showToask("请输入身份证号");
        }
        /*else if (account.equals("")){
            Toast.makeText(CommitMaterialStep2Activity.this, "请输入学信网账号", Toast.LENGTH_SHORT).show();
        }
        else if (password.equals("")){
            Toast.makeText(CommitMaterialStep2Activity.this, "请输入学信网密码", Toast.LENGTH_SHORT).show();
        }*/

        else {
            //数据提交

            ApplyInfo applyInfo = new ApplyInfo();
            applyInfo.setName(name);
            applyInfo.setMoney(money);
            applyInfo.setMouth(mouth);
            applyInfo.setPhone(phone);
            applyInfo.setQq(qq);
            applyInfo.setCertificate(certificate);
            applyInfo.setAccount(account);
            applyInfo.setPassword(password);
            uploadData(applyInfo);
        }

    }

    private void uploadData(ApplyInfo applyInfo) {
        DialogUtil.showDialog("正在提交",this);
        applyInfo.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    DialogUtil.hideDialog(WithDrawApplyActivity.this);
                    ToastUtil.showToask("申请成功");
                    SPUtil.putBooleanValueToSP(Constant.isApplyWithDraw,true);
                    finish();
                }
                else {
                    DialogUtil.hideDialog(WithDrawApplyActivity.this);
                    ToastUtil.showToask("申请失败"+s);
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_withdraw_submit:
                commitInfo();
                break;
        }
    }
}
