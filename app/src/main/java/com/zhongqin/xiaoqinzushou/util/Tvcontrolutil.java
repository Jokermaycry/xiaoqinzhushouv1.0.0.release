package com.zhongqin.xiaoqinzushou.util;

import android.content.Context;
import android.content.Intent;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

public class Tvcontrolutil {
    public Context mContext;
    public Tvcontrolutil(Context context)
    {
        this.mContext=context;
    }
    //返回首页
    public void gohome() {
        Intent intent = new Intent();
        // 为Intent设置Action、Category属性
        intent.setAction(Intent.ACTION_MAIN);// "android.intent.action.MAIN"
        intent.addCategory(Intent.CATEGORY_HOME); //"android.intent.category.HOME"
        mContext.startActivity(intent);

    }


    //待机

    public void standby()
    {
        try {
            createSuProcess("adb input keyevent POWER").waitFor(); //关机命令
            //createSuProcess("reboot").waitFor(); //这个部分代码是用来重启的
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //唤醒
    public void wakeup()
    {
        try {
            createSuProcess("adb input keyevent POWER").waitFor(); //关机命令
            //createSuProcess("reboot").waitFor(); //这个部分代码是用来重启的
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //关机
    public void shutDown() {
        try {
            createSuProcess("reboot -p").waitFor(); //关机命令
            //createSuProcess("reboot").waitFor(); //这个部分代码是用来重启的
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    static Process createSuProcess() throws IOException {
        File rootUser = new File("/system/xbin/ru");
        if (rootUser.exists()) {
            return Runtime.getRuntime().exec(rootUser.getAbsolutePath());
        } else {
            return Runtime.getRuntime().exec("su");
        }
    }

    static Process createSuProcess(String cmd) throws IOException {

        DataOutputStream os = null;
        Process process = createSuProcess();

        try {
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit $?\n");
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }

        return process;
    }

}
