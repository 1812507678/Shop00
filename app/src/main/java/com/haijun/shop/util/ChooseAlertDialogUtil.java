package com.haijun.shop.util;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.haijun.shop.R;
import com.haijun.shop.bean.Apk;


/**
 * Created by HP on 2017/4/5.
 */

//自定义AlertDialog选择框

public class ChooseAlertDialogUtil {
    private static final String TAG = ChooseAlertDialogUtil.class.getSimpleName();
    Context context;

    public ChooseAlertDialogUtil(Context context) {
        this.context = context;
    }


    public void setAlertDialogTextContact(){
        View inflate = View.inflate(context, R.layout.copy_contactinfo_dialog, null);
        ImageView iv_dialog_close = inflate.findViewById(R.id.iv_dialog_close);

        final TextView tv_dialog_qq = inflate.findViewById(R.id.tv_dialog_qq);
        TextView bt_choose_copyqq = inflate.findViewById(R.id.bt_choose_copyqq);
        final TextView tv_dialog_weixin = inflate.findViewById(R.id.tv_dialog_weixin);
        TextView bt_choose_copyweixin = inflate.findViewById(R.id.bt_choose_copyweixin);
        final TextView tv_dialog_gongzhonghao = inflate.findViewById(R.id.tv_dialog_gongzhonghao);
        TextView bt_choose_copygongzhonghao = inflate.findViewById(R.id.bt_choose_copygongzhonghao);


        Apk apkFromSP = ApkUtil.getApkFromSP();
        String qq = context.getResources().getString(R.string.contanct_qq);
        String weixin = context.getResources().getString(R.string.contanct_weixin);
        String gongzhonghao = context.getResources().getString(R.string.contanct_gongzhonghao);

        if (apkFromSP==null){
            apkFromSP = new Apk();
            apkFromSP.setQqContactInfo(qq);
            apkFromSP.setWeixinContactInfo(weixin);
            apkFromSP.setWeixinGZHContactInfo(gongzhonghao);
        }

        if (!TextUtils.isEmpty(apkFromSP.getQqContactInfo())){
             tv_dialog_qq.setText("qq号："+apkFromSP.getQqContactInfo());
        }
        if (!TextUtils.isEmpty(apkFromSP.getWeixinContactInfo())){
            tv_dialog_weixin.setText("微信号："+apkFromSP.getWeixinContactInfo());
        }
        if (!TextUtils.isEmpty(apkFromSP.getWeixinGZHContactInfo())){
            tv_dialog_gongzhonghao.setText("公众号："+apkFromSP.getWeixinGZHContactInfo());
        }


        final AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.myCorDialog).setView(inflate).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        Window dialogWindow = alertDialog.getWindow();
        dialogWindow.setBackgroundDrawable(new ColorDrawable(0));//设置window背景
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();//获取屏幕尺寸
        lp.width = (int) (d.widthPixels * 0.85); //宽度为屏幕80%
        lp.gravity = Gravity.CENTER;  //中央居中
        dialogWindow.setAttributes(lp);

        iv_dialog_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        bt_choose_copyqq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                ToastUtil.showToask("复制qq号成功");
                CopyUtil.copyText(tv_dialog_qq.getText().toString().split("：")[1],context);
            }
        });

        bt_choose_copyweixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                ToastUtil.showToask("复制微信号号成功");
                CopyUtil.copyText(tv_dialog_weixin.getText().toString().split("：")[1],context);
            }
        });

        bt_choose_copygongzhonghao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                ToastUtil.showToask("复制公众号成功");
                CopyUtil.copyText(tv_dialog_gongzhonghao.getText().toString().split("：")[1],context);
            }
        });

    }


}
