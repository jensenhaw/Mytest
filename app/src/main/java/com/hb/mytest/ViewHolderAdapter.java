package com.hb.mytest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */

public class ViewHolderAdapter extends BaseAdapter {
    private List<String> mData;
    private LayoutInflater mInflater;
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
        ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.itembean_share,null);
            holder.img= (ImageView) convertView.findViewById(R.id.icon_share_id);
            holder.title= (TextView) convertView.findViewById(R.id.name_apk_share_id);
            convertView.setTag(holder);
        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.img.setBackgroundResource(R.drawable.ic_launcher);
        holder.title.setText(mData.get(position));
        return convertView;
    }

    public ViewHolderAdapter(List<String> mData, Context context) {
        this.mData = mData;
        mInflater=LayoutInflater.from(context);
    }
    public  final class ViewHolder{
        public ImageView img;
        public TextView title;
    }
   }
