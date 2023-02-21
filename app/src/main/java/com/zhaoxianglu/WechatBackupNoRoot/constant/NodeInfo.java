package com.zhaoxianglu.WechatBackupNoRoot.constant;

public class NodeInfo {


    public String msg_page_title;
    public String msg_page_list_view;
    public String msg_page_text_msg_info;

    public static NodeInfo NodeInfo(String versionName) {
        NodeInfo nodeInfo = new NodeInfo();
        switch (versionName) {
            case "8.0.0":
                nodeInfo.msg_page_title = "com.tencent.mm:id/ipt";
                nodeInfo.msg_page_list_view = "com.tencent.mm:id/awv";
                nodeInfo.msg_page_text_msg_info = "com.tencent.mm:id/auk";
                break;
            case "8.0.1":
                nodeInfo.msg_page_title = "";
                nodeInfo.msg_page_list_view = "";
                nodeInfo.msg_page_text_msg_info = "";
                break;
            case "8.0.2":
                nodeInfo.msg_page_title = "";
                nodeInfo.msg_page_list_view = "";
                nodeInfo.msg_page_text_msg_info = "";
                break;
            case "8.0.3":
                nodeInfo.msg_page_title = "";
                nodeInfo.msg_page_list_view = "";
                nodeInfo.msg_page_text_msg_info = "";
                break;
            case "8.0.6":
                nodeInfo.msg_page_title = "";
                nodeInfo.msg_page_list_view = "";
                nodeInfo.msg_page_text_msg_info = "";
                break;
            case "8.0.7":
                break;
            case "8.0.9":
                break;
            case "8.0.10":
                break;
            case "8.0.11":
                break;
            case "8.0.14":
                break;
            case "8.0.15":
                break;
            case "8.0.16":
                break;
            case "8.0.18":
                break;
            case "8.0.19":
                break;
            case "8.0.20":
                break;
            case "8.0.21":
                nodeInfo.msg_page_title = "com.tencent.mm:id/kog";
                nodeInfo.msg_page_list_view = "com.tencent.mm:id/b79";
                nodeInfo.msg_page_text_msg_info = "com.tencent.mm:id/b4b";
                break;
            case "8.0.22":
                break;
            case "8.0.23":
                break;
            case "8.0.24":
                break;
            case "8.0.25":
                break;
            case "8.0.27":
                break;
            case "8.0.28":
                break;
            case "8.0.30":
                break;
            case "8.0.31":
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
