package com.haijun.shop.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.haijun.shop.R;
import com.haijun.shop.bean.ApplyInfo;
import com.haijun.shop.util.Constant;
import com.haijun.shop.util.DialogUtil;
import com.haijun.shop.util.LogUtil;
import com.haijun.shop.util.SPUtil;
import com.haijun.shop.util.ToastUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class WithDrawFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "WithDrawFragment";
    private EditText et_commit_money;
    private EditText et_commit_mouth;
    private EditText et_commit_name;
    private EditText et_commit_phone;
    private EditText et_commit_qq;
    private EditText et_commit_certif;
    private EditText et_commit_acount;
    private EditText et_commit_password;

    private View inflate;
    private View ll_applytext;
    private View tv_showtext;

    public WithDrawFragment() {
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
            inflate = inflater.inflate(R.layout.fragment_withdraw, container, false);
            LogUtil.i("WithDrawFragment","onCreateView");
            initView();// 控件初始化
        }
        return inflate;
    }

    private void initView() {
        et_commit_money = (EditText) inflate.findViewById(R.id.et_commit_money);
        et_commit_name = (EditText) inflate.findViewById(R.id.et_commit_name);
        et_commit_mouth = (EditText) inflate.findViewById(R.id.et_commit_mouth);
        et_commit_phone = (EditText) inflate.findViewById(R.id.et_commit_phone);
        et_commit_qq = (EditText) inflate.findViewById(R.id.et_commit_qq);
        et_commit_certif = (EditText) inflate.findViewById(R.id.et_commit_certif);
        et_commit_acount = (EditText) inflate.findViewById(R.id.et_commit_acount);
        et_commit_password = (EditText) inflate.findViewById(R.id.et_commit_password);

        Button bt_withdraw_submit = inflate.findViewById(R.id.bt_withdraw_submit);
        bt_withdraw_submit.setOnClickListener(this);

        ll_applytext = inflate.findViewById(R.id.ll_applytext);
        tv_showtext = inflate.findViewById(R.id.tv_showtext);

        boolean booleanValueFromSP = SPUtil.getBooleanValueFromSP(Constant.isApplyWithDraw);
        if (booleanValueFromSP){
            ll_applytext.setVisibility(View.GONE);
            tv_showtext.setVisibility(View.VISIBLE);
        }
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


        /*if (money.equals("")){
            ToastUtil.showToask("请输入金额");
        }
        else if (mouth.equals("")){
            ToastUtil.showToask( "请输入月数");
        }*/

        if (name.equals("")){
            ToastUtil.showToask("请输入姓名");
        }
        else if (phone.equals("")){
            ToastUtil.showToask( "请输入电话");
        }
        else if (qq.equals("")){
            ToastUtil.showToask("请输入qq");
        }
        /*else if (certificate.equals("")){
            ToastUtil.showToask("请输入身份证号");
        }
        else if (account.equals("")){
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
        DialogUtil.showDialog("正在提交",getActivity());
        applyInfo.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    DialogUtil.hideDialog(getActivity());
                    ToastUtil.showToask("申请成功");
                    SPUtil.putBooleanValueToSP(Constant.isApplyWithDraw,true);
                    ll_applytext.setVisibility(View.GONE);
                    tv_showtext.setVisibility(View.VISIBLE);
                }
                else {
                    DialogUtil.hideDialog(getActivity());
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
