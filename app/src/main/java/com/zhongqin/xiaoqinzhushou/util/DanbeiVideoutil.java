package com.zhongqin.xiaoqinzhushou.util;

import android.content.Context;
import android.content.Intent;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class DanbeiVideoutil {
    public Intent intent;
    public Context mContext;
    public DanbeiVideoutil(Context context)
    {
        this.mContext=context;
    }
    public void gotomovie()
    {
        Intent intent = new Intent("com.tv.kuaisou.action.VideosActivity");
        intent.setPackage("com.tv.kuaisou");
        intent.putExtra("topId", "1969");
        intent.putExtra("catName", "电影");
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
    public void gotodongman()
    {

        intent = new Intent("com.tv.kuaisou.action.VideosActivity");
        intent.setPackage("com.tv.kuaisou");
        intent.putExtra("topId", "1972");
        //intent.putExtra("vodid","47");
        intent.putExtra("catName", "动漫");
        intent.putExtra("isAlbum", true);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);

        mContext.startActivity(intent);
    }
    public void gotozhongyi()
    {
        intent = new Intent("com.tv.kuaisou.action.VideosActivity");
        intent.setPackage("com.tv.kuaisou");
        intent.putExtra("topId", "1971");
        //intent.putExtra("vodid","47");
        intent.putExtra("catName", "综艺");
        intent.putExtra("isAlbum", true);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);

        mContext.startActivity(intent);
    }
    public void gotomv()
    {
        intent = new Intent("com.tv.kuaisou.action.VideosActivity");
        intent.setPackage("com.tv.kuaisou");
        intent.putExtra("topId", "2264");
        //intent.putExtra("vodid","47");
        intent.putExtra("catName", "MV");
        intent.putExtra("isAlbum", true);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);

        mContext.startActivity(intent);
    }
    public void gototiyu()
    {
        intent = new Intent("com.tv.kuaisou.action.VideosActivity");
        intent.setPackage("com.tv.kuaisou");
        intent.putExtra("topId", "2263");
        //intent.putExtra("vodid","47");
        intent.putExtra("catName", "体育");
        intent.putExtra("isAlbum", true);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);

        mContext.startActivity(intent);
    }
    public void gotodianshiju()
    {
         intent = new Intent("com.tv.kuaisou.action.VideosActivity");
        intent.setPackage("com.tv.kuaisou");
        intent.putExtra("topId", "1970");
//intent.putExtra("vodid","47");
        intent.putExtra("catName", "电视剧");
        intent.putExtra("isAlbum", true);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);

        mContext.startActivity(intent);
    }
    public void gotojilupian()
    {
        intent = new Intent("com.tv.kuaisou.action.VideosActivity");
        intent.setPackage("com.tv.kuaisou");
        intent.putExtra("topId", "1974");
        //intent.putExtra("vodid","47");
        intent.putExtra("catName", "纪录片");
        intent.putExtra("isAlbum", true);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);

        mContext.startActivity(intent);
    }
    public void gotoshaoer()
    {
        intent = new Intent("com.tv.kuaisou.action.VideosActivity");
        intent.setPackage("com.tv.kuaisou");
        intent.putExtra("topId", "1973");
        //intent.putExtra("vodid","47");
        intent.putExtra("catName", "少儿");
        intent.putExtra("isAlbum", true);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);

        mContext.startActivity(intent);
    }
}
