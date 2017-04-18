package com.hb.mytest;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */

public class dymicAdapter extends BaseAdapter {
    private List<String> mData;
    private Context mContext;
    private int mCurrentItem=0;//<span style="font-size:10px;">默认是第一个Item</span>
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout=new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.VERTICAL);
        if(mCurrentItem==position){
            layout.addView(addFocusView(position));

        }
        else{
            layout.addView(addNormalView(position));
        }
        return layout;
    }

    public void setCurrentItem(int currentItem) {
        this.mCurrentItem = currentItem;
    }
    public dymicAdapter(List<String> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }
    private View addFocusView(int i){
        ImageView iv=new ImageView(mContext);
        iv.setImageResource(R.drawable.ic_launcher);
        return iv;
    }
    private View addNormalView(int i){
        LinearLayout layout=new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        ImageView iv=new ImageView(mContext);
        iv.setImageResource(R.drawable.ic_launcher);
        layout.addView(iv,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        TextView tv=new TextView(mContext);
        tv.setText(mData.get(i));
        layout.addView(tv,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        layout.setGravity(Gravity.CENTER);
        return layout;
    }
}
