package com.haijun.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.haijun.shop.R;
import com.haijun.shop.bean.User;
import com.haijun.shop.util.UserUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ModifyAccountItemActivity extends BaseActivity {

    private EditText ed_modifyuser_vlaue;
    private int type;
    private String initValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_account_item);
    }


    protected void initView() {
        setCenterText("修改信息");
        ed_modifyuser_vlaue = (EditText) findViewById(R.id.ed_modifyuser_vlaue);

        User userInfo = UserUtil.getUserInfo();
        Intent intent = getIntent();
        type = intent.getIntExtra("type",0);
        if (type==1){
            setCenterText("修改昵称");
            ed_modifyuser_vlaue.setText(userInfo.getNickname());
        }

        initValue = ed_modifyuser_vlaue.getText().toString();

    }

    @Override
    protected void initData() {

    }

    public void modify(View view){
        final String value = ed_modifyuser_vlaue.getText().toString();
        if (initValue.equals(value)) {
            finish();
            return;
        }

        final User userInfo = UserUtil.getUserInfo();
        userInfo.setNickname(value);

        userInfo.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    Toast.makeText(ModifyAccountItemActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                    UserUtil.putUserToSP(userInfo);
                    Intent intent = getIntent();
                    intent.putExtra("result",value);
                    setResult(RESULT_OK,intent);
                    finish();
                }else {
                    Toast.makeText(ModifyAccountItemActivity.this,"修改失败"+e,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void back(View view){
        finish();
    }
}
