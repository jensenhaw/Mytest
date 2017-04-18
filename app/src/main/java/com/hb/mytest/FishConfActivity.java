package com.hb.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Administrator on 2017/4/12.
 */

public class FishConfActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fishconf);
        final Intent service = new Intent(FishConfActivity.this, FxService.class);
        //启动悬浮窗口
        findViewById(R.id.btnopen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FishConfActivity.this.startService(service);
            }
        });
        //关闭悬浮窗口
        findViewById(R.id.btnclose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FishConfActivity.this.stopService(service);
            }
        });

    }

}
