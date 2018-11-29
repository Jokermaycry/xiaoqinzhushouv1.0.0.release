package com.zhongqin.xiaoqinzhushou.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.zhongqin.xiaoqinzhushou.model.slotsbean;
import com.zhongqin.xiaoqinzhushou.util.Apputil;
import com.zhongqin.xiaoqinzhushou.util.DanbeiVideoutil;
import com.zhongqin.xiaoqinzhushou.util.Jiaoyutongutil;
import com.zhongqin.xiaoqinzhushou.util.SuUtil;
import com.zhongqin.xiaoqinzhushou.util.ToastUtil;
import com.zhongqin.xiaoqinzhushou.util.Tvchanelutil;
import com.zhongqin.xiaoqinzhushou.util.Tvcontrolutil;
import com.zhongqin.xiaoqinzhushou.util.Zhishiyuanutil;
import com.zhongqin.xiaoqinzhushou.view.MainActivity;

import org.json.JSONObject;

import java.util.List;

public class broadcastreceiver extends BroadcastReceiver {
String command,data,input;
    String value;
    String rawvalue;
    String TAG = "testlog";
    Zhishiyuanutil z;
    @Override
    public void onReceive(Context context, Intent intent) {

        //ToastUtil.showShort(context,intent.getAction());

        //asr文本
        if (intent.getAction().equals("aispeech.intent.action.ASRTHROUGH")) {
//                //唤醒
//                try {
//                    SuUtil su = new SuUtil();
//
//                    su.wakeup();
//                } catch (Exception e) {
//                }

            //透传给果谷
            try
            {
                String context_input_text;
                context_input_text=intent.getStringExtra("context.input.text");
                Log.e("guogu.input.text", context_input_text);
                //DanbeiVideoutil dbu=new DanbeiVideoutil(context);

//                switch (context_input_text)
//                {
//                    case "我要看电影":
//                        dbu.gotomovie();
//                        break;
//                    case "我要看动漫":
//                        dbu.gotodongman();
//                        break;
//
//                    case "我要看综艺":
//                        dbu.gotozhongyi();
//                        break;
//
//                    case "我要看MV":
//                        dbu.gotomv();
//                        break;
//
//                    case "我要看体育":
//                        dbu.gototiyu();
//                        break;
//
//                    case "我要看电视剧":
//                        dbu.gotodianshiju();
//                        break;
//
//                    case "我要看纪录片":
//                        dbu.gotojilupian();
//                        break;
//
//                    case "我要看少儿":
//                        dbu.gotoshaoer();
//                        break;
//
//                }

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

//                for(int i=0;i<temp.size();i++)
//                {
//
//                     String name=temp.get(i).getName();
//                     if(name.equals("应用软件")){
//                         value=temp.get(i).getValue();
//                     }
//
//                }
                value=temp.get(0).getValue();
                rawvalue=temp.get(0).getRawvalue();
                Log.e("frank",value);
                Log.e("frank",rawvalue);
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

                    break;
                    //关闭应用
                case       "qinjian.control.openlight":
                    try {
                        Apputil a=new Apputil(context);
                        a.setAppLists();
                        a.closeapp(input);
                    }
                    catch (Exception e){}

                    System.out.println("openlight");
                    break;

                case      "qinjian.control.openwindow":
                    System.out.println("openwindow");
                    break;


                case       "qinjian.control.closewindow":// //分享到微信orqq


                    z=new Zhishiyuanutil(context);
                    z.dosomething(0x010,rawvalue);
                    break;


                case       "qinjian.control.openlcok":          //动作


                     z=new Zhishiyuanutil(context);
                    z.dosomething(Integer.parseInt(value),rawvalue);
                    break;

                case        "qinjian.control.closelock":     //退出


                     z=new Zhishiyuanutil(context);
                    z.dosomething(0x007);

                    break;

                case       "qinjian.control.openIntercalation":      //搜索课程

                    z=new Zhishiyuanutil(context);
                    z.dosomething(0x002,rawvalue);

                    break;

                case       "qinjian.control.closeIntercalation"://切换


                    z=new Zhishiyuanutil(context);
                    z.dosomething(0x003,rawvalue);

                    break;

                case       "qinjian.control.openProjector":////分享直播到微信orqq


                    z=new Zhishiyuanutil(context);
                    z.dosomething(0x115,rawvalue);

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
                    try {
                    Tvchanelutil tvm = new Tvchanelutil(context);
                    tvm.gotochanel(input);
                    }
                    catch (Exception e) {}


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
                    System.out.println("openSettopbox");
                    break;
                case       "qinjian.control.closeSettopbox"://我要唱歌
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