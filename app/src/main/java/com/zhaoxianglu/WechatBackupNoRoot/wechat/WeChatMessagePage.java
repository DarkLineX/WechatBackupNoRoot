package com.zhaoxianglu.WechatBackupNoRoot.wechat;

import java.util.List;

public class WeChatMessagePage {
    private List<WeChatMessage> chatMessages;
    private int index;

    public WeChatMessagePage(int index) {
        this.index = index;
    }

    public List<WeChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(List<WeChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
