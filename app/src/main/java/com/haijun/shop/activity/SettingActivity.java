package com.haijun.shop.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.haijun.shop.R;
import com.haijun.shop.util.ApkUtil;
import com.haijun.shop.util.SPUtil;
import com.haijun.shop.util.UserUtil;

import java.io.File;



public class SettingActivity extends BaseActivity {

	private static final String TAG = "SettingActivity";
	private ProgressBar progressBar;
	private AlertDialog downProcess;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
	}

	protected void initView() {
		setCenterText("系统设置");
		setLeftImage(R.drawable.back_normal);

		RelativeLayout ll_me_password = (RelativeLayout) findViewById(R.id.ll_me_password);
		RelativeLayout ll_me_suggest = (RelativeLayout) findViewById(R.id.ll_me_suggest);
		RelativeLayout ll_me_update = (RelativeLayout) findViewById(R.id.ll_me_update);
		RelativeLayout ll_me_aboutus = (RelativeLayout) findViewById(R.id.ll_me_aboutus);
		LinearLayout ll_me_exit = (LinearLayout) findViewById(R.id.ll_me_exit);

		MyOnClickListener myOnClickListener = new MyOnClickListener();

		ll_me_password.setOnClickListener(myOnClickListener);
		ll_me_suggest.setOnClickListener(myOnClickListener);
		ll_me_update.setOnClickListener(myOnClickListener);
		ll_me_aboutus.setOnClickListener(myOnClickListener);
		ll_me_exit.setOnClickListener(myOnClickListener);

		progressBar  = new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);
		progressBar.setMinimumHeight(10);

		downProcess = new AlertDialog.Builder(SettingActivity.this)
				.setTitle("下载进度")
				.setView(progressBar)
				.create();
	}

	@Override
	protected void initData() {

	}

	class MyOnClickListener implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			switch (v.getId()){
				case R.id.ll_me_password:
					startActivity(new Intent(SettingActivity.this,RetrievePasswordActivity.class));
					break;
				case R.id.ll_me_suggest:
					startActivity(new Intent(SettingActivity.this,ComplaintFeedbackActivity.class));
					break;
				case R.id.ll_me_update:
					checkAndUpdateVersion();
					break;
				case R.id.ll_me_aboutus:
					//startActivity(new Intent(SettingActivity.this,AboutAsActivity.class));
					break;

				case R.id.ll_me_exit:
					startActivity(new Intent(SettingActivity.this,LoginActivity.class));
					SPUtil.clearAllSPData();
					UserUtil.user = null;
					finish();
					break;
			}
		}
	}

	private void checkAndUpdateVersion() {
		ApkUtil.checkUpdate(this);
	}

	//安装app
	private void installApp(String path) {
		File file = new File(path);
		//启动系统中专门安装app的组件进行安装app
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		startActivity(intent);
	}

	public void back(View view){
		finish();
	}
}
