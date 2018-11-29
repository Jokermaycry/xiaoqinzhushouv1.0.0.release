package com.zhongqin.xiaoqinzhushou.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhongqin.xiaoqinzhushou.R;

public class ProgressDialog extends Dialog {


    private TextView titleTv;//消息标题文本
    private ProgressBar progressBar;
    private String titleStr;//从外界设置的title文本
    private String messageStr;//从外界设置的消息文本
    private int progresspercent;
    //确定文本和取消文本的显示内容
    private String yesStr, noStr;
    private  boolean taghere;

    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器



    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param str
     * @param onNoOnclickListener
     */
    public void setNoOnclickListener(String str, onNoOnclickListener onNoOnclickListener) {
        if (str != null) {
            noStr = str;
        }
        this.noOnclickListener = onNoOnclickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *  @param str
     * @param onYesOnclickListener*/
    public void setYesOnclickListener(String str, onYesOnclickListener onYesOnclickListener) {
        if (str != null) {
            yesStr = str;
        }
        this.yesOnclickListener = onYesOnclickListener;
    }

    public ProgressDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressdialog);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();

    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {

    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //如果用户自定了title和message
        if (titleStr != null) {
            titleTv.setText(titleStr);
        }



        if(progressBar!=null)
        {
            if(taghere) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setMax(100);
                progressBar.setProgress(100);
            }
        }
    }

    /**
     * 初始化界面控件
     */
    private void initView() {


        titleTv = (TextView) findViewById(R.id.title);

        progressBar=(ProgressBar)findViewById(R.id.progressbar);
    }

    /**
     * 从外界Activity为Dialog设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        titleStr = title;
    }


    /**
     * 从外界Activity为Dialog设置dialog的message
     *
     * @param message
     */
    public void setMessage(String message) {
        messageStr = message;
    }

    public void setProgress(int x)
    {
        if (progressBar != null) {
         progressBar.setProgress(x);

        }
    }
    public void setProgressBarvisible(boolean tag)
    {
        taghere=tag;

    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onYesOnclickListener {
        public void onYesClick();
    }

    public interface onNoOnclickListener {
        public void onNoClick();
    }
}

