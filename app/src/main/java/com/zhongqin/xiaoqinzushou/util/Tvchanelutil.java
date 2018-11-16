package com.zhongqin.xiaoqinzushou.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.zhongqin.xiaoqinzushou.model.Tvbean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Tvchanelutil {
    private Context mContext;


    public Tvchanelutil(Context context)
    {
        this.mContext=context;

    }
    public void gotochanel(String input)
    {
        try {
            String fileName = "test.json";
            String foodJson = LocalJsonResolutionUtils.getJson(mContext, fileName);
            JSONObject jsonObject = new JSONObject(foodJson);
            String intentName = jsonObject.optString("data");
            Log.e("wwwm_input", input);
            List<String> temp;
            temp = JSON.parseArray(intentName, String.class);
            List<Tvbean> temp2;
            temp2 = JSON.parseArray(intentName, Tvbean.class);
            for(int i=0;i<temp2.size();i++)
            {
                //Log.e("wwwm_getTitle()", temp2.get(i).getTitle());

                if(input.equals(temp2.get(i).getTitle()))

                {

                    Log.e(temp2.get(i).getTitle(),temp2.get(i).getTitle());
                    try {
                        sendChannelNum(temp2.get(i).getNum());
                        //Log.e("temp2.get(i).second)", String.valueOf(temp2.get(i).getNum()));
                    }
                    catch (Exception e)
                    {

                    }
                }
                else {
                    //Log.e("temp2.get(i).not)", String.valueOf(temp2.get(i).getNum()));

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

        public void sendChannelNum(int num)
        {
        Intent intent = new Intent() ;
        intent.setAction("com.ashlive.GO_CHANNEL") ;
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("channel_num" , num) ;
        mContext.startActivity(intent);
        Log.e("wangweiming",String.valueOf(num));
        }




}
