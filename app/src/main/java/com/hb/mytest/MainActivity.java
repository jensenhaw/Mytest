package com.hb.mytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Cloudy/ShareActivity";
    private ListView mListView = null;
    private BaseAdapter mAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listview_share_id);

        List<String> data=new ArrayList<String>();
        for(int i=0;i<50;i++){
            data.add("Item"+i);
        }
        //final dymicAdapter myadapter=new dymicAdapter(data,this);
        final ViewHolderAdapter myadapter=new ViewHolderAdapter(data,this);
        mListView.setAdapter(myadapter);
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                myadapter.setCurrentItem(position);
//                myadapter.notifyDataSetChanged();//通知界面进行更新
//
//            }
//        });

        //动态添加ListView
        data.add("新的一项");
        myadapter.notifyDataSetChanged();
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch(scrollState){
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        //滑动停止的时候
                        Log.d("TEST","SCROLL_STATE_IDLE");
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        //正在滚动
                        Log.d("TEST","SCROLL_STATE_TOUCH_SCROLL");
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        //手指抛开以后,Listview依靠惯性滑动的时候会回调这个方法
                        Log.d("TEST","SCROLL_STATE_FLING");
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //滚动的时候会一直调用
                Log.d("TEST","正在滚动");
            }
        });
    }


}
