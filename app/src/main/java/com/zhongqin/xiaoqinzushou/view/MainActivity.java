package com.zhongqin.xiaoqinzushou.view;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.zhongqin.xiaoqinzushou.R;
import com.zhongqin.xiaoqinzushou.util.ToastUtil;
import com.zhongqin.xiaoqinzushou.service.myreceiver;
import com.zhongqin.xiaoqinzushou.util.Tvcontrolutil;
import com.zhongqin.xiaoqinzushou.util.Updateutil;
import com.zhongqin.xiaoqinzushou.util.Apputil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.guide)
    FrameLayout guide;
    @BindView(R.id.zhidengjiaju)
    FrameLayout zhidengjiaju;
    @BindView(R.id.checkupdata)
    FrameLayout checkupdata;
    private myreceiver recevier;
    private IntentFilter intentFilter;
    private Intent intent;
    private Button b1, b2, b3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recevier = new myreceiver();
        intentFilter = new IntentFilter();
        registerReceiver(recevier, intentFilter);


    }






    @OnClick({R.id.guide, R.id.zhidengjiaju, R.id.checkupdata})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.guide:
//                Intent i=new Intent(MainActivity.this,GuideActivity.class);
//                startActivity(i);
//                Apputil a=new Apputil(this);
//                a.setAppLists();
//                a.gotoapp("电视家2.0");
                Tvcontrolutil tvc=new Tvcontrolutil(this);
                tvc.standby();
                break;
            case R.id.zhidengjiaju:

                try {
                    ComponentName componentName = new ComponentName("com.qinjian.zhinengjiaju", "com.qinjian.zhinengjiaju.view.MainActivity");
                    intent = new Intent(intent.ACTION_MAIN);
                    intent.setComponent(componentName);
                    startActivity(intent);
                } catch (Exception e) {
                    ToastUtil.toast(getApplicationContext(), "智能家居未下载");
                }
//

                break;
            case R.id.checkupdata:
                try {
                    //获取版本更新信息
                    Updateutil manager = new Updateutil(MainActivity.this);
                    // 检查软件更新
                    manager.checkUpdate();
                } catch (Exception e) {
                    ToastUtil.toast(getApplicationContext(), "小秦向导下载");
                }
                break;
        }
    }
}
