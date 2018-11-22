package com.zhongqin.xiaoqinzushou.util;



import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.zhongqin.xiaoqinzushou.R;
import com.zhongqin.xiaoqinzushou.view.SelfDialog;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class Updateutil {
    private String TAG = Updateutil.class.getSimpleName();
     com.zhongqin.xiaoqinzushou.view.ProgressDialog builder;
    protected static HttpUtils mHttpUtils;
    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    /* 保存解析的XML信息 */
    HashMap<String, String> mHashMap;
    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数量 */
    private int progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;
     ProgressDialog progressDialog;

    private  String url="http://www.tuo-jiang.com/qinjian/xiaoqinzushou.xml";
    private   Context mContext;
    /* 更新进度条 */
    private ProgressBar mProgress;
    private Dialog mDownloadDialog;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 正在下载
                case DOWNLOAD:
                    // 设置进度条位置
                    builder.setProgress(progress);
                    break;
                case DOWNLOAD_FINISH:
                    // 安装文件
                    installApk();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    public Updateutil(Context context) {
        this.mContext = context;
    }

    /**
     * 检查软件是否有更新版本
     *
     * @return
     */
    public void checkUpdate() {
        mHttpUtils = new HttpUtils();
        RequestParams params = new RequestParams();

        mHttpUtils.send(HttpMethod.GET, url, params,
                mRequestUpdataCallBack);
    }

    private RequestCallBack<String> mRequestUpdataCallBack = new RequestCallBack<String>() {

        @Override
        public void onStart() {
            super.onStart();
            Log.i(TAG, "请求的路径：" + this.getRequestUrl());
        }

        @Override
        public void onFailure(HttpException arg0, String arg1) {

            Log.i(TAG, "onFailure:" + arg1);
        }

        @Override
        public void onSuccess(ResponseInfo<String> arg0) {
            String json = arg0.result;
            Log.i(TAG, "onSuccess:" + ":" + json);

            InputStream in_nocode = new ByteArrayInputStream(json.getBytes());

            // 获取当前软件版本
            int versionCode = getVersionCode(mContext);
            Log.e("versionCode",String.valueOf(versionCode));

            // 解析XML文件。 由于XML文件比较小，因此使用DOM方式进行解析
            ParseXmlutil service = new ParseXmlutil();
            try {
                mHashMap = service.parseXml(in_nocode);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (null != mHashMap) {
                int serviceCode = Integer.valueOf(mHashMap.get("version"));
                // 版本判断
                if (serviceCode > versionCode) {
                    showNoticeDialog();
                }
                else
                {
                    //showCurrentDialog();
                }
            }
        }
    };

    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    private int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            PackageManager pm = context.getPackageManager();//context为当前Activity上下文
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode= pi.versionCode;
            Log.e("versionCode",String.valueOf(versionCode));
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
    /**
     * 显示软件已经是最新版本
     */
    private void showCurrentDialog() {
        // 构造对话框
         final SelfDialog builder3 = new SelfDialog(mContext);

        builder3.setTitle("已经是最新版本");
        builder3.setMessage(" ");
        //
        // 确认
        builder3.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
              builder3.dismiss();
            }
        });

        builder3.show();
    }
    /**
     * 显示软件更新对话框
     */
    private void showNoticeDialog() {
        // 构造对话框
        final SelfDialog builder1 = new SelfDialog(mContext);
        builder1.setTitle("检测到最新版本");
        builder1.setMessage(" ");
        // 更新
        builder1.setYesOnclickListener("更新", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                builder1.dismiss();
                // 显示下载对话框
                showDownloadDialog();
            }
        });
        // 稍后更新
        builder1.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick( ) {
                builder1.dismiss();
            }
        });
        builder1.show();
    }

    /**
     * 显示软件下载对话框
     */
    private void showDownloadDialog() {
         builder = new com.zhongqin.xiaoqinzushou.view.ProgressDialog(mContext);
        builder.setProgressBarvisible(true);
        builder.setTitle("正在下载");
        // 更新

        builder.show();
        downloadApk();


    }


    /**
     * 下载apk文件
     */
    private void downloadApk() {
        // 启动新线程下载软件
        new downloadApkThread().start();
    }

    /**
     * 下载文件线程
     *
     * @author coolszy
     * @date 2012-4-26
     * @blog http://blog.92coding.com
     */
    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    mSavePath = sdpath + "download";
                    URL url = new URL(mHashMap.get("url"));
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();

                    File file = new File(mSavePath);
                    // 判断文件目录是否存在
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    File apkFile = new File(mSavePath, mHashMap.get("name"));
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        mHandler.sendEmptyMessage(DOWNLOAD);
                        if (numread <= 0) {
                            // 下载完成
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate);// 点击取消就停止下载.
                    fos.close();
                    is.close();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 取消下载对话框显示
            builder.dismiss();
        }
    }

    ;

    /**
     * 安装APK文件
     */
    private void installApk() {
        File apkfile = new File(mSavePath, mHashMap.get("name"));
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);
    }
}
