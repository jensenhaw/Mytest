package com.hb.mytest;


import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;


/**
 * Created by Administrator on 2017/4/13.
 */

public class TestActivity extends AppCompatActivity
{
    private Button mSuspensionView, mCancel_View;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams wmParams;
    private LinearLayout mFloatLayout;
    /**
     * 屏幕的宽度和高度
     */
    protected int mScreenWidth;
    protected int mScreenHeight;

    void initData()
    {

        //获取屏幕宽高
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;
        Log.e("tag", "mScreenWidth : " + mScreenWidth + "mScreenHeight : " + mScreenHeight);
    }

    //创建悬浮View
    void createSuspensionView()
    {
        wmParams = new WindowManager.LayoutParams();
        mWindowManager = this.getWindowManager();
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.BOTTOM;
        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        LayoutInflater inflater = this.getLayoutInflater();
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.suspension_view, null);
        mWindowManager.addView(mFloatLayout, wmParams);
        Button mFloatView = (Button) mFloatLayout.findViewById(R.id.suspension_button);
        mFloatView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.e("tag", "你点我了 操");
            }
        });
    }

    void initView()
    {
        setContentView(R.layout.activity_main1);
        mCancel_View = (Button) this.findViewById(R.id.cancel_view);
        mSuspensionView = (Button) this.findViewById(R.id.suspension_view);
        mCancel_View.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(TestActivity.this, Test.class);
                startActivity(intent);
            }
        });
        mSuspensionView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                createSuspensionView();
            }
        });
    }
}
