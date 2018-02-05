package com.haijun.shop.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.haijun.shop.R;
import com.haijun.shop.bean.Suggestion;
import com.haijun.shop.bean.User;
import com.haijun.shop.util.DialogUtil;
import com.haijun.shop.util.UserUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class ComplaintFeedbackActivity extends BaseActivity {

	private ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complaint_feedback);


	}

	@Override
	protected void initView() {
		setLeftImage(R.drawable.back_normal);
		setCenterText("用户反馈");
	}

	@Override
	protected void initData() {

	}

	public void submitSuggest(View view) {
		EditText et_feedback_input = (EditText) findViewById(R.id.et_feedback_input);
		String msg = et_feedback_input.getText().toString();
		if (!msg.isEmpty()){
			DialogUtil.showDialog("正在提交",this);
			User userInfo = UserUtil.getUserInfo();

			Suggestion suggestion = new Suggestion("",msg);
			if (userInfo!=null){
				suggestion.setUserPhone(userInfo.getPhone());
			}

			suggestion.save(new SaveListener() {
				@Override
				public void done(Object o, BmobException e) {
					if (e==null){
						hideDialog();
						Toast.makeText(ComplaintFeedbackActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
						finish();
					}else {
						hideDialog();
						Toast.makeText(ComplaintFeedbackActivity.this,"提交失败"+e,Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
		else {
			Toast.makeText(this,"请输入内容呀",Toast.LENGTH_SHORT).show();
		}


	}

	void showDialog(String message) {
		try {
			if (dialog == null) {
				dialog = new ProgressDialog(this);
				dialog.setCancelable(true);
			}
			dialog.setMessage(message);
			dialog.show();
		} catch (Exception e) {
			// 在其他线程调用dialog会报错
		}
	}

	void hideDialog() {
		if (dialog != null && dialog.isShowing())
			try {
				dialog.dismiss();
			} catch (Exception e) {
			}
	}


}

