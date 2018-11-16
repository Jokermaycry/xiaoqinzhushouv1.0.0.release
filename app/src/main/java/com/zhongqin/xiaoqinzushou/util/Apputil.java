package com.zhongqin.xiaoqinzushou.util;



import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.zhongqin.xiaoqinzushou.model.AppInfo;

import java.util.ArrayList;
import java.util.List;

public class Apputil {
    public Context mContext;
    public  ArrayList<AppInfo> appList;
    private Intent intent;
    private String need;
    public Apputil(Context context)
    {
        this.mContext=context;
    }

    public void setAppList(ArrayList<AppInfo> appList) {
        this.appList = appList;
    }

    public ArrayList<AppInfo> getAppList() {
        return appList;
    }
    public void gotoapp(String input)
    {

//        try {
//            if (input.indexOf("我要打开") != -1) {
//                need = input.substring(4);
//                System.out.println("需要的字符串" + need);
//
//            } else if (input.indexOf("打开") != -1 && input.indexOf("我要") == -1) {
//                need = input.substring(2);
//                System.out.println("需要的字符串" + need);
//
//            } else {
//                need = input;
//                System.out.println("需要的字符串" + need);
//            }
//        }
//        catch (Exception e){}

        for(int i=0;i<appList.size();i++)
        {



            if(input.equals(appList.get(i).appName))
            {
                try {
                    intent = mContext.getPackageManager().getLaunchIntentForPackage(appList.get(i).packageName);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
                catch ( Exception e)
                {
                    ToastUtil.toast(mContext, appList.get(i).appName+"未下载");


                }
            }
        }


    }

    public void setAppLists() {
        appList = new ArrayList<AppInfo>(); //用来存储获取的应用信息数据
        PackageManager pm = mContext.getPackageManager();
        // Return a List of all packages that are installed on the device.
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : packages) {
            // 判断系统/非系统应用
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) // 非系统应用
            {
                System.out.println("MainActivity.getAppList, packageInfo=" + packageInfo.packageName);
                AppInfo tmpInfo = new AppInfo();
                tmpInfo.appName = packageInfo.applicationInfo.loadLabel(mContext.getPackageManager()).toString();
                System.out.println("wangweiming.getAppList, packageInfo=" + tmpInfo.appName);
                tmpInfo.packageName = packageInfo.packageName;
                tmpInfo.versionName = packageInfo.versionName;
                tmpInfo.versionCode = packageInfo.versionCode;
                tmpInfo.appIcon = packageInfo.applicationInfo.loadIcon(mContext.getPackageManager());
                appList.add(tmpInfo);
                setAppList(appList);
            } else {
                // 系统应用
            }
        }

    }
}
