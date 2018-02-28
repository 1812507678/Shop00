package com.haijun.shop.util;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Process;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.haijun.shop.R;
import com.haijun.shop.activity.MainActivity;
import com.haijun.shop.bean.Apk;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by HP on 2017/4/19.
 */

public class ApkUtil {

    private static final String TAG = "ApkUtil";


    //获取app的版本名称
    public static String getVersionName(Context context) {
        try{
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取app的版本号
    public static int getVersionCode(Context context) {
        try{
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    //检查最新版本
    public static void checkUpdate(final Activity context) {
        final boolean isSplashActivityInstance = context.getClass().isInstance(new MainActivity());
        Log.i(TAG,"isSplashActivityInstance:"+isSplashActivityInstance);

        if (!isSplashActivityInstance){
            DialogUtil.showDialog("检查版本信息",context);
        }

        BmobQuery<Apk> apkBmobQuery = new BmobQuery<>();
        apkBmobQuery.addWhereEqualTo("platform",context.getResources().getString(R.string.apk_platform));   // 平台注释：0（人你还乐分期），1（拍来贷花租来）
        apkBmobQuery.findObjects(new FindListener<Apk>() {
            @Override
            public void done(List<Apk> list, BmobException e) {
                DialogUtil.hideDialog(context);
                if (e==null){
                    if (list.size()>0){
                        Apk apk = list.get(0);
                        ApkUtil.saveApkToLocal(apk);
                        if (FormatUtil.isNumeric(apk.getVersionCode())){
                            int version = Integer.parseInt(apk.getVersionCode());
                            String path = apk.getApkUrl();
                            String remark = apk.getUpdateDescription();
                            boolean isShowToask = !isSplashActivityInstance;
                            checkAndUpdateVersion(version, path,context, isShowToask, remark,apk.isForceWhenUpdate());
                        }
                        else if (!isSplashActivityInstance){
                            ToastUtil.showToask("版本信息获取失败");
                        }
                    }
                }
            }
        });

    }

    //和本地对比，判断更新状态
    public static void checkAndUpdateVersion(int version, final String path, final Activity context, boolean isShowToask, final String remark, final boolean isForceUpdate) {
        int versionCode = getVersionCode(context);
        if (version>versionCode){
            //有更新
            AlertDialog alertDialog = new AlertDialog.Builder(context)

                    .setTitle("发现新版本")
                    .setMessage(remark)
                    .setPositiveButton("更新", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //openBrowserDownLoadApp(context,path);
                            downLoadAppAndShowProgereeNoitfy(context,path);
                        }
                    })
                    .setNegativeButton("稍后", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (isForceUpdate){
                                Process.killProcess(Process.myPid());
                            }
                        }
                    })
                    .create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();

        }
        else {
            if (isShowToask){
                ToastUtil.showToask("当前已是最新版本");
            }
        }
    }

    private static void downLoadAppAndShowProgereeNoitfy(Activity context, String path) {
        createNotification(context);
        downloadAndInstallApp(context,path);
    }

    private static void createNotification(Activity context) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notification = new Notification.Builder(context)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                //.setContentText("倾听体语正在运行")
                .setSmallIcon(R.drawable.logo)
                .setTicker(context.getResources().getString(R.string.app_name)+"开始下载")
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.logo))
                .build();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        //notification.defaults |= Notification.DEFAULT_SOUND;

        /*notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification = new Notification();
        notification.icon = R.drawable.logo_icon;
        // 这个参数是通知提示闪出来的值.
        notification.tickerText = "开始下载";*/

        // pendingIntent = PendingIntent.getActivity(this, 0, updateIntent, 0);

        // 这里面的参数是通知栏view显示的内容
        //notification.setLatestEventInfo(this, getResources().getString(R.string.app_name), "下载：0%", pendingIntent);
        //nm.notify(1,notification);



        // notificationManager.notify(notification_id, notification);

        /***
         * 在这里我们用自定的view来显示Notification
         */
        contentView = new RemoteViews(context.getPackageName(), R.layout.notification_item);
        contentView.setTextViewText(R.id.notificationTitle, context.getResources().getString(R.string.app_name)+"开始下载");
        contentView.setTextViewText(R.id.notificationPercent, "0%");
        contentView.setProgressBar(R.id.notificationProgress, 100, 0, false);

        notification.contentView = contentView;

        //updateIntent = new Intent(this, MainActivity.class);
        //updateIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //pendingIntent = PendingIntent.getActivity(this, 0, updateIntent, 0);

        //notification.contentIntent = pendingIntent;
        notificationManager.notify(notification_id, notification);
    }

    //下载和安装app
    private static void downloadAndInstallApp(final Activity context, String path) {
        ToastUtil.showToask("开始下载");
        String name = "/app"+System.currentTimeMillis()+".apk";
        BmobFile file = new BmobFile(name,"",path);
        downloadFile(file,context);
    }

    static int preDownloadProgress;
    private static void downloadFile(BmobFile file, final Activity context){
        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
        File saveFile = new File(Environment.getExternalStorageDirectory(), file.getFilename());
        file.download(saveFile, new DownloadFileListener() {

            @Override
            public void onStart() {
                ToastUtil.showToask("开始下载...");
            }

            @Override
            public void done(String savePath,BmobException e) {
                if(e==null){
                    ToastUtil.showToask("下载成功:"+savePath);
                    notificationManager.cancel(notification_id);
                    installApp(context,savePath);
                }else{
                    ToastUtil.showToask("下载失败："+e.getErrorCode()+","+e.getMessage());
                    ToastUtil.showToask("下载失败:"+savePath);
                }
            }

            @Override
            public void onProgress(Integer value, long newworkSpeed) {
                Log.i("bmob","下载进度："+value+","+newworkSpeed);
                if (preDownloadProgress!=value){
                    preDownloadProgress = value;
                    contentView.setTextViewText(R.id.notificationPercent, value + "%");
                    contentView.setProgressBar(R.id.notificationProgress, 100, value, false);
                    notificationManager.notify(notification_id, notification);
                }
            }

        });
    }


    /***
     * 创建通知栏
     */
    static RemoteViews contentView;
    private static NotificationManager notificationManager;
    private static Notification notification;

    private Intent updateIntent;
    private PendingIntent pendingIntent;
    private String updateFile;

    private static int notification_id = 0;
    long totalSize = 0;// 文件总大小


    //安装app
    private static void installApp(Activity context,String path) {
        File file = new File(path);
        //启动系统中专门安装app的组件进行安装app
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    //在浏览器打开下载文件
    private static void openBrowserDownLoadApp(Activity activity, String path) {
        Intent intent= new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(path);
        intent.setData(content_url);
        activity.startActivity(intent);
    }

    public static void saveApkToLocal(Apk apk) {
        Gson gson = new Gson();
        String apkString = gson.toJson(apk);
        SPUtil.putStringValueToSP("apk",apkString);
    }

    static Apk getApkFromSP() {
        Gson gson = new Gson();
        String apkString = SPUtil.getStringValueFromSP("apk");
        if (!TextUtils.isEmpty(apkString)){
            return gson.fromJson(apkString,Apk.class);
        }
        return null;
    }




}