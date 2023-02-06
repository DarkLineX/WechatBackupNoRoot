package com.zhaoxianglu.WechatBackupNoRoot.constant;

public class NodeInfo {

    public String msg_page_title;
    public String msg_page_list_view;
    public String msg_page_text_msg_info;



    public static NodeInfo NodeInfo(String versionName){

        NodeInfo nodeInfo = new NodeInfo();

        switch (versionName){
            case "8.0.24":
                nodeInfo.msg_page_title = "com.tencent.mm:id/kog";
                nodeInfo.msg_page_list_view = "com.tencent.mm:id/b79";
                nodeInfo.msg_page_text_msg_info = "com.tencent.mm:id/b4b";
                break;

            case "8.0.32":
                nodeInfo.msg_page_title = "com.tencent.mm:id/ko4";
                nodeInfo.msg_page_list_view = "com.tencent.mm:id/b79";
                nodeInfo.msg_page_text_msg_info = "com.tencent.mm:id/b4b";
                break;

            default:

                break;
        }
        return nodeInfo;
    }

}
