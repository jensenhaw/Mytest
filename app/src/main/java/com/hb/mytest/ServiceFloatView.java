package com.hb.mytest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/4/13.
 */

public class ServiceFloatView extends Service {
    private WindowManager wm;
    private View floatView;
    private WindowManager.LayoutParams params;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        showFloatWindow();
    }

    /**
     * 显示悬浮窗口
     */
    private void showFloatWindow() {
        wm = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        //窗口类型为系统窗口
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.RGBA_8888;
        //窗口起点位置
        params.x = 0;
        params.y = 0;
        //窗口宽高
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //窗口对齐方式
        params.gravity = Gravity.CENTER;
        floatView = createFloatView();
        //添加窗口
        wm.addView(floatView, params);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //移除窗口
        wm.removeViewImmediate(floatView);
    }

    private View createFloatView() {
        FloatView floatView = new FloatView(this);
        Button button = new Button(this);
        button.setText("点我啊！");
        button.setTextColor(Color.BLACK);
        button.setBackgroundResource(R.drawable.fish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ServiceFloatView.this.getApplicationContext(),
                        "Hi,我是悬浮窗口", Toast.LENGTH_SHORT).show();
            }
        });
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        floatView.addView(button, params);
        return floatView;
    }

    /**
     * 重写dispatchTouch事件，使得窗口随着手指滑动而移动
     */
    private class FloatView extends FrameLayout {

        public FloatView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public FloatView(Context context) {
            super(context);
        }

        public FloatView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            float x, y;
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    x = ev.getX();
                    y = ev.getY();
                    params.x = (int) x;
                    params.y = (int) y;
                    wm.updateViewLayout(floatView, params);
                    break;
            }
            return super.dispatchTouchEvent(ev);
        }
    }
}
