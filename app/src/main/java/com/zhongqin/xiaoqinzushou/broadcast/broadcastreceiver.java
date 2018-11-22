package com.zhongqin.xiaoqinzushou.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.zhongqin.xiaoqinzushou.model.slotsbean;
import com.zhongqin.xiaoqinzushou.util.Apputil;
import com.zhongqin.xiaoqinzushou.util.Jiaoyutongutil;
import com.zhongqin.xiaoqinzushou.util.SuUtil;
import com.zhongqin.xiaoqinzushou.util.ToastUtil;
import com.zhongqin.xiaoqinzushou.util.Tvchanelutil;
import com.zhongqin.xiaoqinzushou.util.Tvcontrolutil;
import com.zhongqin.xiaoqinzushou.view.MainActivity;

import org.json.JSONObject;

import java.util.List;

public class broadcastreceiver extends BroadcastReceiver {
String command,data,input;
    String value;

    String TAG = "testlog";

    @Override
    public void onReceive(Context context, Intent intent) {

// TODO Auto-generated method stub
//        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
//        {
//            Intent start=new Intent(context,MainActivity.class);
//            start.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(start);
//            Toast.makeText(context,"我自启动成功了哈",Toast.LENGTH_LONG).show();
//        }
        Log.i(TAG, intent.getAction());

        ToastUtil.showShort(context,intent.getAction());

        //asr文本
        if (intent.getAction().equals("aispeech.intent.action.ASRTHROUGH")) {
                //唤醒
                try {
                    SuUtil su = new SuUtil();

                    su.wakeup();
                } catch (Exception e) {
                }

            //透传给果谷
            try
            {
                String context_input_text;
                context_input_text=intent.getStringExtra("context.input.text");
                Log.e("guogu.input.text", context_input_text);
                try {
                    Intent i1 = new Intent();
                    i1.putExtra("guogu", context_input_text);
                    i1.setAction("guogu.control");

                    context.sendBroadcast(i1);
                    Log.e("guogu.input.text", "success");

                }
                catch (Exception e)
                {
                    Log.e("guogu.input.text", "success");

                }
            }
            catch (Exception e)
            {

            }

        }
        //处理后的文本
        if (intent.getAction().equals("aispeech.intent.action.DATATHROUGH")) {
            command = intent.getStringExtra("command");
            data = intent.getStringExtra("data");
            Log.e("command", command);
            Log.e("data", data);
            try {
                //获取input
                JSONObject jsonData = new JSONObject(data);
                String nlu = jsonData.optString("nlu");
                //获取semantics
                JSONObject jsonData1 = new JSONObject(nlu);
                String semantics=jsonData1.optString("semantics");
                System.out.println("wangweiming_semantics" + semantics);
                //获取request
                JSONObject jsonData2 = new JSONObject(semantics);
                String request=jsonData2.optString("request");
                System.out.println("wangweiming_request" + request);

                //获取slots
                JSONObject jsonData3 = new JSONObject(request);
                String slots=jsonData3.optString("slots");
                System.out.println("wangweiming_slots" + slots);

                //获取rawvalue
                List<slotsbean> temp;
                temp = JSON.parseArray(slots, slotsbean.class);
                for(int i=0;i<temp.size();i++)
                {

                     String name=temp.get(i).getName();
                     if(name.equals("应用软件")){
                         value=temp.get(i).getValue();
                     }

                }
                input=value;


            }
            catch (Exception e){}

            switch (command)
            {
                //打开应用
                case "qinjian.control.opendoor":
                    try {
                        Apputil a=new Apputil(context);
                        a.setAppLists();
                        a.gotoapp(input);
                    }
                    catch (Exception e){}
                    break;
                //教育通词库
                case "qinjian.control.closeAirconditioner":
                    try {

                    Jiaoyutongutil jyt=new Jiaoyutongutil(context);
                    jyt.gotoapp(input);
                    }
                    catch (Exception e) {}
                    break;
                //直播搜索+词库
                case "qinjian.control.closedoor":
//                    try {
//                    Tvchanelutil tvm = new Tvchanelutil(context);
//                    tvm.gotochanel(input);
//                    }
//                    catch (Exception e) {}
                    try {
                        SuUtil su=new SuUtil();

                        su.standby();
                    }
                    catch (Exception e){}
                    break;
                //关机
                case "qinjian.control.closelight":
//                    Log.e("wangweiming","here");
//                    try {
//                        Tvcontrolutil tvcm = new Tvcontrolutil(context);
//                        switch (input)
//                        {
//                            case "开机":
//                                tvcm.wakeup();
//                                break;
//                            case "关机":
//                                tvcm.standby();
//                                break;
//
//                        }
//                        tvcm.shutDown();
//                    }
//                    catch (Exception e) {}
                    break;

                case       "qinjian.control.openlight":
                    try {
                        Apputil a=new Apputil(context);
                        a.setAppLists();
                        a.closeapp(input);
                    }
                    catch (Exception e){}
//                    try {
//                        SuUtil su=new SuUtil();
//
//                        su.standby();
//                    }
//                    catch (Exception e){}
                    System.out.println("openlight");
                    break;

                case      "qinjian.control.openwindow":
                    System.out.println("openwindow");
                    break;
                case       "qinjian.control.closewindow"://
                    System.out.println("closewindow");
                    break;
                case       "qinjian.control.openlcok"://锁
                    System.out.println("openlcok");
                    break;
                case        "qinjian.control.closelock"://
                    System.out.println("closelock");
                    break;
                case       "qinjian.control.openIntercalation"://排插
                    System.out.println("openIntercalation");
                    break;
                case       "qinjian.control.closeIntercalation"://
                    System.out.println("closeIntercalation");
                    break;
                case       "qinjian.control.openProjector"://投影仪
                    System.out.println("openProjector");
                    break;
                case       "qinjian.control.closeProjector"://??????????????
                    System.out.println("closeProjector");
                    break;
                case       "qinjian.control.openClothcurtain"://布帘
                    System.out.println("openClothcurtain");
                    break;
                case       "qinjian.control.closeClothcurtain"://
                    System.out.println("closeClothcurtain");
                    break;
                case       "qinjian.control.openGauze"://纱帘
                    System.out.println("openGauze");
                    break;
                case        "qinjian.control.closeGauze"://
                    System.out.println("closeGauze");
                    break;
                case       "qinjian.control.openWindowcurtains"://窗帘
                    System.out.println("openWindowcurtains");
                    break;
                case       "qinjian.control.closeWindowcurtains"://
                    System.out.println("closeWindowcurtains");
                    break;
                case       "qinjian.control.openswitch"://开关
                    System.out.println("openswitch");
                    break;
                case       "qinjian.control.closeswitch"://
                    System.out.println("closeswitch");
                    break;
                case       "qinjian.control.openFan"://风扇
                    System.out.println("openFan");
                    break;
                case       "qinjian.control.closeFan"://
                    System.out.println("closeFan");
                    break;
                case       "qinjian.control.opentelevision"://电视
                    System.out.println("opentelevision");
                    break;
                case       "qinjian.control.closetelevision"://
                    System.out.println("closetelevision");
                    break;
                case       "qinjian.control.openDVD"://DVD
                    System.out.println("openDVD");
                    break;
                case       "qinjian.control.closeDVD":
                    System.out.println("closeDVD");
                    break;
                case       "qinjian.control.openSocket"://插座
                    System.out.println("openSocket");
                    break;
                case       "qinjian.control.closeSocket"://
                    System.out.println("closeSocket");
                    break;
                case       "qinjian.control.openSettopbox"://我要听歌
                    try {
                        Apputil a=new Apputil(context);
                        a.setAppLists();
                        a.gotoapp("QQ音乐");
                    }
                    catch (Exception e){}

                    System.out.println("openSettopbox");
                    break;
                case       "qinjian.control.closeSettopbox"://我要唱歌
                    try {
                        Apputil a=new Apputil(context);
                        a.setAppLists();
                        a.gotoapp("全民k歌");
                    }
                    catch (Exception e){}

                    System.out.println("closeSettopbox");
                    break;
                case       "qinjian.control.openAirconditioner"://空调
                    System.out.println("openAirconditioner");
                    break;
//                case       "qinjian.control.closeAirconditioner"://
//                    break;


            }


        }



    }
}