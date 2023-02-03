package com.zhaoxianglu.WechatBackupNoRoot.wechat;

public class WeChatMessage {

    private int person;
    private int msg_type;
    private String msg;

    public WeChatMessage(int person, int msg_type, String msg) {
        this.person = person;
        this.msg_type = msg_type;
        this.msg = msg;
    }

    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    public int getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(int msg_type) {
        this.msg_type = msg_type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
