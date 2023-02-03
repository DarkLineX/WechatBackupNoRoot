package com.zhaoxianglu.WechatBackupNoRoot.service;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

import com.zhaoxianglu.WechatBackupNoRoot.utils.AutoUtils;


/**
 * @author
 * @date
 * @action 核心类
 **/
public class MyAccessibilityService extends AccessibilityService {


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        AutoUtils.setMyAccessibilityService(this);
    }

}
