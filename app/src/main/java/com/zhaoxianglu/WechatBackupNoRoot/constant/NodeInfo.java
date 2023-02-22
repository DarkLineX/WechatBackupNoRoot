package com.zhaoxianglu.WechatBackupNoRoot.constant;

public class NodeInfo {


    public String msg_page_title;
    public String msg_page_list_view;
    public String msg_page_text_msg_info;

    public static NodeInfo NodeInfo(String versionName) {
        NodeInfo nodeInfo = new NodeInfo();
        switch (versionName) {
            case "8.0.0":
            case "8.0.1":
            case "8.0.2":
            case "8.0.3":
            case "8.0.6":
            case "8.0.7":
            case "8.0.9":
            case "8.0.11":
            case "8.0.14":
            case "8.0.10":
                nodeInfo.msg_page_title = "com.tencent.mm:id/ipt";
                nodeInfo.msg_page_list_view = "com.tencent.mm:id/awv";
                nodeInfo.msg_page_text_msg_info = "com.tencent.mm:id/auk";
                break;
            case "8.0.15":
            case "8.0.16":
                nodeInfo.msg_page_title = "com.tencent.mm:id/ipv";
                nodeInfo.msg_page_list_view = "com.tencent.mm:id/awv";
                nodeInfo.msg_page_text_msg_info = "com.tencent.mm:id/auk";
                break;
            case "8.0.18":
            case "8.0.20":
            case "8.0.19":
            case "8.0.21":
            case "8.0.23":
            case "8.0.22":
                nodeInfo.msg_page_title = "com.tencent.mm:id/kog";
                nodeInfo.msg_page_list_view = "com.tencent.mm:id/b79";
                nodeInfo.msg_page_text_msg_info = "com.tencent.mm:id/b4b";
                break;
            case "8.0.24":
                nodeInfo.msg_page_title = "com.tencent.mm:id/ko6";
                nodeInfo.msg_page_list_view = "com.tencent.mm:id/b79";
                nodeInfo.msg_page_text_msg_info = "com.tencent.mm:id/b4b";
                break;
            case "8.0.25":
            case "8.0.32":
            case "8.0.31":
            case "8.0.27":
            case "8.0.28":
            case "8.0.30":
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
