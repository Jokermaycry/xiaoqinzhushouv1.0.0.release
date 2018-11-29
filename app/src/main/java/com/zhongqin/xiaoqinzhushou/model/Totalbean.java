package com.zhongqin.xiaoqinzhushou.model;

import java.util.List;

public class Totalbean {
    private String status;
    private String msg;
    private String timestamp;
    private List<Tvbean> data;//购买详情 	string

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<Tvbean> getData() {
        return data;
    }

    public void setData(List<Tvbean> data) {
        this.data = data;
    }
}
