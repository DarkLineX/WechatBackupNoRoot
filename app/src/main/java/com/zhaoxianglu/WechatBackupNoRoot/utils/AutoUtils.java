package com.zhaoxianglu.WechatBackupNoRoot.utils;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.view.accessibility.AccessibilityNodeInfo;

import com.blankj.utilcode.util.ScreenUtils;
import com.zhaoxianglu.WechatBackupNoRoot.service.MyAccessibilityService;

import java.util.ArrayList;
import java.util.List;


/**
 * @author
 * @date
 * @action 所有的做空指针校验
 **/
public class AutoUtils {

    public static MyAccessibilityService service;

    public static void setMyAccessibilityService(MyAccessibilityService a) {
        service = a;
    }

    public final static int SLIDE_UP = 0, SLIDE_DOWN = 1, SLIDE_LEFT = 2, SLIDE_RIGHT = 3;

    public static List<AccessibilityNodeInfo> getAutoElementListById(AccessibilityService accessibilityService, String id) {


        if (accessibilityService == null) {
            return new ArrayList<>();
        }

        AccessibilityNodeInfo accessibilityNodeInfo = accessibilityService.getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return new ArrayList<>();
        }

        List<AccessibilityNodeInfo> list = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(id);
        if (null != list && list.size() > 0) {
            return list;
        }

        return new ArrayList<>();
    }

    public static AccessibilityNodeInfo getAutoElementById(AccessibilityService accessibilityService, String id, int index) {

        if (accessibilityService == null) {
            return null;
        }


        List<AccessibilityNodeInfo> infos = getAutoElementListById(accessibilityService, id);
        if (infos != null) {
            for (AccessibilityNodeInfo info : infos) {
                if (info != null && infos.indexOf(info) == index) {
                    return info;
                }
            }
        }
        return null;
    }

    public static boolean performGestureSlide(Point point1, Point point2, long startTime, long duration) {

        if (service == null) {
            return false;
        }

        try {
            // android7.0 及以上用无障碍模式手势点击
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                GestureDescription.Builder builder = new GestureDescription.Builder();
                Path path = new Path();
                path.moveTo(point1.x, point1.y);
                path.lineTo(point2.x, point2.y);
                GestureDescription gestureDescription = builder
                        .addStroke(new GestureDescription.StrokeDescription(path, startTime, duration))
                        .build();

                service.dispatchGesture(gestureDescription, null, null);
                return true;
            } else { // 7.0 以下用adb 操作
                //AdbUtil.swipe(point1.x, point1.y, point2.x, point2.y);
                return true;
            }
        } catch (Exception e) {
            //手势滑动异常
        }

        return false;
    }

    public static boolean slideScreenCenter(int margin, int time, int direction, long duration, long pause) {

        Point point1 = new Point(ScreenUtils.getScreenWidth() / 2, margin); //高点
        Point point2 = new Point(ScreenUtils.getScreenWidth() / 2, ScreenUtils.getScreenHeight() - margin); //低点

        Point point3 = new Point(margin, ScreenUtils.getScreenHeight() / 2); //左边点
        Point point4 = new Point(ScreenUtils.getScreenWidth() - margin, ScreenUtils.getScreenHeight() / 2); //右边点


        for (int i = 0; i < time; i++) {
            if (direction == SLIDE_UP) {
                if (!performGestureSlide(point2, point1, 0L, duration)) {
                    return false;
                }
            } else if (direction == SLIDE_DOWN) {
                if (!performGestureSlide(point1, point2, 0L, duration)) {
                    return false;
                }
            } else if (direction == SLIDE_RIGHT) {
                if (!performGestureSlide(point4, point3, 0L, duration)) {
                    return false;
                }
            } else if (direction == SLIDE_LEFT) {
                if (!performGestureSlide(point3, point4, 0L, duration)) {
                    return false;
                }
            }
        }

        appPause(pause);
        return true;
    }

    public static void appPause(long millis) {
        if (millis > 0) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
