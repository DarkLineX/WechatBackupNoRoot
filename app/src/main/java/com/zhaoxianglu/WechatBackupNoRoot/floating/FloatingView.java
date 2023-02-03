package com.zhaoxianglu.WechatBackupNoRoot.floating;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import static android.content.Context.WINDOW_SERVICE;

public class FloatingView {
    private Context mContext;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mParamsWindowManager;
    private ViewGroup.MarginLayoutParams mParamsViewGroup;
    public View rootView;
    private View.OnClickListener onClickListener;
    private boolean isShowing = false;
    private TYPE type = TYPE.OVERLAY_SYSTEM;

    private FloatingViewConfig config;
    private int width, height;

    private enum TYPE{
        OVERLAY_SYSTEM, OVERLAY_ACTIVITY, OVERLAY_VIEWGROUP
    }

    public FloatingView(Context context, int resource, FloatingViewConfig config) {
        this.mContext = context;
        mWindowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        LayoutInflater mInflater = LayoutInflater.from(context);
        rootView = mInflater.inflate(resource, null, false);

        this.config = config;
        if (config.displayWidth == Integer.MAX_VALUE) {
            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            config.displayWidth = metrics.widthPixels;
        }
        if (config.displayHeight == Integer.MAX_VALUE) {
            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            config.displayHeight = (int) (metrics.heightPixels - 25 * metrics.density);
        }
        config.paddingLeft = dp2px(config.paddingLeft);
        config.paddingTop = dp2px(config.paddingTop);
        config.paddingRight = dp2px(config.paddingRight);
        config.paddingBottom = dp2px(config.paddingBottom);

        rootView.measure(0, 0);
        width = rootView.getMeasuredWidth();
        height = rootView.getMeasuredHeight();
    }

    /**
     * 在最上层悬浮，Activity或者APP关闭，都可存在
     * 需要跳转到系统设置中，同意在其他APP上方显示遮盖后，才可以显示
     */
    public void showOverlaySystem() {
        if (isShowing) {
            return;
        }
        type = TYPE.OVERLAY_SYSTEM;
        initParams();
        initPosition();
        initWindowView();

        AndPermission.with(mContext)
                .overlay()
                .onGranted(new Action<Void>() {
                    @Override
                    public void onAction(Void data) {
                        isShowing = true;
                        mWindowManager.addView(rootView, mParamsWindowManager);
                    }
                })
                .onDenied(new Action<Void>() {
                    @Override
                    public void onAction(Void data) {
                        Toast.makeText(mContext, "需要悬浮窗权限", Toast.LENGTH_SHORT).show();
                    }
                })
                .start();
    }

    /**
     * 在当前Activity上方悬浮，可被其他Activity遮挡
     * 无需跳转到系统设置中去同意在其他APP上方显示遮盖
     * 需要等待当前Activity创建完成，如果在onCreate中直接调用会报错
     */
    public void showOverlayActivity() {
        if (isShowing) {
            return;
        }
        type = TYPE.OVERLAY_ACTIVITY;
        initParams();
        initPosition();
        initWindowView();
        isShowing = true;
        mWindowManager.addView(rootView, mParamsWindowManager);
    }

    /**
     * 在当前ViewGroup中悬浮
     * 无需跳转到系统设置中去同意在其他APP上方显示遮盖
     * 无需等待当前Activity创建完成
     * @param parent
     */
    public void showOverlayViewGroup(FrameLayout parent) {
        if (isShowing) {
            return;
        }
        type = TYPE.OVERLAY_VIEWGROUP;
        initParams();
        initPosition();
        initWindowView();
        isShowing = true;
        parent.addView(rootView, mParamsViewGroup);
    }

    /**
     * 隐藏
     */
    public void hide() {
        if (!isShowing) {
            return;
        }
        isShowing = false;
        if (type == TYPE.OVERLAY_VIEWGROUP) {
            if (rootView.getParent() != null) {
                ((ViewGroup)rootView.getParent()).removeView(rootView);
            }
        } else if (type == TYPE.OVERLAY_SYSTEM || type == TYPE.OVERLAY_ACTIVITY){
            mWindowManager.removeView(rootView);
        }
    }

    public boolean isShowing() {
        return isShowing;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private void initParams(){
        if (type == TYPE.OVERLAY_VIEWGROUP) {
            if (mParamsViewGroup == null) {
                mParamsViewGroup = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        } else if (type == TYPE.OVERLAY_SYSTEM){
            if (mParamsWindowManager == null) {
                mParamsWindowManager = new WindowManager.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSLUCENT);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mParamsWindowManager.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                }
                mParamsWindowManager.gravity = Gravity.LEFT | Gravity.TOP;
                mParamsWindowManager.width = WindowManager.LayoutParams.WRAP_CONTENT;
                mParamsWindowManager.height = WindowManager.LayoutParams.WRAP_CONTENT;
            }
        } else if (type == TYPE.OVERLAY_ACTIVITY) {
            if (mParamsWindowManager == null) {
                mParamsWindowManager = new WindowManager.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSLUCENT);
                mParamsWindowManager.gravity = Gravity.LEFT | Gravity.TOP;
                mParamsWindowManager.width = WindowManager.LayoutParams.WRAP_CONTENT;
                mParamsWindowManager.height = WindowManager.LayoutParams.WRAP_CONTENT;
            }
        }
    }

    private void initPosition() {
        int x = 0, y = 0;
        switch (config.gravity) {
            case LEFT_CENTER:
                x = config.paddingLeft;
                y = config.displayHeight / 2 - height / 2;
                break;
            case LEFT_TOP:
                x = config.paddingLeft;
                y = config.paddingTop;
                break;
            case TOP_CENTER:
                x = config.displayWidth / 2 - width / 2;
                y = config.paddingTop;
                break;
            case TOP_RIGHT:
                x = config.displayWidth - width - config.paddingRight;
                y = config.paddingTop;
                break;
            case RIGHT_CENTER:
                x = config.displayWidth - width - config.paddingRight;
                y = config.displayHeight / 2 - height / 2;
                break;
            case RIGHT_BOTTOM:
                x = config.displayWidth - width - config.paddingRight;
                y = config.displayHeight - height - config.paddingBottom;
                break;
            case BOTTOM_CENTER:
                x = config.displayWidth / 2 - width / 2;
                y = config.displayHeight - height - config.paddingBottom;
                break;
            case LEFT_BOTTOM:
                x = config.paddingLeft;
                y = config.displayHeight - height - config.paddingBottom;
                break;
            case CENTER:
                x = config.displayWidth / 2 - width / 2;
                y = config.displayHeight / 2 - height / 2;
                break;
        }
        if (type == TYPE.OVERLAY_SYSTEM || type == TYPE.OVERLAY_ACTIVITY) {
            mParamsWindowManager.x = x;
            mParamsWindowManager.y = y;
        } else if (type == TYPE.OVERLAY_VIEWGROUP){
            int marginLeft = rootView.getLeft() + x;
            marginLeft = marginLeft < 0? 0: marginLeft;
            marginLeft = marginLeft > config.displayWidth - width? config.displayWidth - width: marginLeft;

            int marginTop = rootView.getTop() + y;
            marginTop = marginTop < 0? 0: marginTop;
            marginTop = marginTop > config.displayHeight - height? config.displayHeight - height: marginTop;

            mParamsViewGroup.setMargins(marginLeft, marginTop, 0, 0);
        }
    }

    private void initWindowView(){
        final GestureDetector gestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (onClickListener != null) {
                    onClickListener.onClick(rootView);
                }
                return true;
            }
        });

        rootView.setOnTouchListener(new View.OnTouchListener() {
            float[] temp = new float[]{0, 0};
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (gestureDetector.onTouchEvent(motionEvent)){
                    return true;
                }
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if (type == TYPE.OVERLAY_VIEWGROUP) {
                            temp[0] = motionEvent.getRawX();
                            temp[1] = motionEvent.getRawY();
                        } else if (type == TYPE.OVERLAY_SYSTEM || type == TYPE.OVERLAY_ACTIVITY){
                            temp[0] = motionEvent.getX();
                            temp[1] = motionEvent.getY();
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (type == TYPE.OVERLAY_VIEWGROUP) {
                            int offsetX = (int)(motionEvent.getRawX() - temp[0]);
                            int offsetY = (int)(motionEvent.getRawY() - temp[1]);
                            moveWindow(offsetX, offsetY);
                            temp[0] = motionEvent.getRawX();
                            temp[1] = motionEvent.getRawY();
                        } else if (type == TYPE.OVERLAY_SYSTEM || type == TYPE.OVERLAY_ACTIVITY){
                            int x = (int)(motionEvent.getRawX() - temp[0]);
                            int y = (int)(motionEvent.getRawY() - temp[1]);
                            moveWindow(x, y);
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void moveWindow(int x, int y){

        if (type == TYPE.OVERLAY_VIEWGROUP) {
            // 本方法界面刷新后会复位
//            rootView.layout(rootView.getLeft() + x, rootView.getTop() + y, rootView.getRight() + x, rootView.getBottom() + y);

            int marginLeft = rootView.getLeft() + x;
            marginLeft = marginLeft < 0? 0: marginLeft;
            marginLeft = marginLeft > config.displayWidth - width? config.displayWidth - width: marginLeft;

            int marginTop = rootView.getTop() + y;
            marginTop = marginTop < 0? 0: marginTop;
            marginTop = marginTop > config.displayHeight - height? config.displayHeight - height: marginTop;

            mParamsViewGroup.setMargins(marginLeft, marginTop, 0, 0);
            rootView.requestLayout();
        } else if (type == TYPE.OVERLAY_SYSTEM || type == TYPE.OVERLAY_ACTIVITY){
            Rect r = new Rect();
            rootView.getWindowVisibleDisplayFrame(r);
            int statusBarHeight = r.top;

            mParamsWindowManager.x = x;
            mParamsWindowManager.y = y - statusBarHeight;
            updateWindowSize();
        }
    }

    private void updateWindowSize(){
        mWindowManager.updateViewLayout(rootView, mParamsWindowManager);
    }

    private int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mContext.getResources().getDisplayMetrics());
    }
}
