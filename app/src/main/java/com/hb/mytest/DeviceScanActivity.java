/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hb.mytest;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * 类功能描述：</br>
 *本demo是根据给官方demo进行修改
 * @author yuyahao
 * @version 1.0 </p> 修改时间：</br> 修改备注：</br>
 */
public class DeviceScanActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private LeDeviceListAdapter mLeDeviceListAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private ListView mListView = null;
    private boolean mScanning;
//    private dymicAdapter myadapter;
    private Handler mHandler;

    private static final int REQUEST_ENABLE_BT = 1;
    // 10秒后停止查找搜索.
    private static final long SCAN_PERIOD = 10000;
    private Menu menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
                R.drawable.fish);
       // getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(R.string.title_devices);
        actionBar.setDisplayShowHomeEnabled(true);

        Resources r = getResources();
        Drawable myDrawable = r.getDrawable(R.drawable.fish);
        actionBar.setLogo(myDrawable);
        setContentView(R.layout.activity_main);


        mHandler = new Handler();
 //       setContentView(R.layout.activity_main);
        // 检查当前手机是否支持ble 蓝牙,如果不支持退出程序
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }


//        List<String> data=new ArrayList<String>();
//        for(int i=0;i<50;i++){
//            data.add("Item"+i);
//        }
//        myadapter=new dymicAdapter(data,this);
        mListView = (ListView) findViewById(R.id.listview_share_id);
        if (mListView != null) {
            mListView.setOnItemClickListener(this);
        }
      //  final ViewHolderAdapter myadapter=new ViewHolderAdapter(data,this);

        // 初始化 Bluetooth adapter, 通过蓝牙管理器得到一个参考蓝牙适配器(API必须在以上android4.3或以上和版本)
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // 检查设备上是否支持蓝牙
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
 //       getMenuInflater().inflate(R.menu.main, menu);
//        if (!mScanning) {
//            menu.findItem(R.id.menu_stop).setVisible(false);
//            menu.findItem(R.id.menu_scan).setVisible(true);
//            menu.findItem(R.id.menu_refresh).setActionView(null);
//        } else {
//            menu.findItem(R.id.menu_stop).setVisible(true);
//            menu.findItem(R.id.menu_scan).setVisible(true);
//            menu.findItem(R.id.menu_refresh).setActionView(
//                    R.layout.actionbar_indeterminate_progress);
     //   }


        this.menu = menu;

        int groupId = 1;
        MenuItem item1 = menu.add(groupId, Menu.FIRST + 1, 1, "将第二组item不可见");
        item1.setIcon(R.mipmap.ic_launcher);
        menu.add(groupId, Menu.FIRST + 2, 2, "将第二组item可见");
        menu.add(groupId, Menu.FIRST + 3, 3, "将第二组item不可用");
        MenuItem item4 = menu.add(groupId, Menu.FIRST + 4, 4, "将第二组item可用");
        item4.setIcon(R.mipmap.ic_launcher);
        menu.add(groupId, Menu.FIRST + 5, 5, "将第二组item可单选");
        menu.add(groupId, Menu.FIRST + 6, 6, "将第二组item可多选");

        groupId = 2;
        MenuItem item7 = menu.add(groupId, Menu.FIRST + 7, 7, "menu2_1");
        item7.setIcon(R.mipmap.ic_launcher);
        MenuItem item8 = menu.add(groupId, Menu.FIRST + 8, 8, "menu2_2");
        item8.setIcon(R.mipmap.ic_launcher);

        SubMenu subMenu = menu.addSubMenu(groupId, Menu.FIRST + 9, 9, "sub menu");
        subMenu.add(groupId, Menu.FIRST + 10, 1, "sub1");
        subMenu.add(groupId, Menu.FIRST + 11, 2, "sub2");

        /**
         * 这个监听事件可以对item的点击事件进行拦截，拦截后的item在onOptionsItemSelected()中不会得到执行
         */
        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;//返回true为拦截，false不拦截
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_scan:
//                mLeDeviceListAdapter.clear();
//                scanLeDevice(true);
//                break;
//            case R.id.menu_stop:
//                scanLeDevice(false);
//                break;
//        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 为了确保设备上蓝牙能使用, 如果当前蓝牙设备没启用,弹出对话框向用户要求授予权限来启用
        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }

        // Initializes list view adapter.
        mLeDeviceListAdapter = new LeDeviceListAdapter();
        //setAdapter(mLeDeviceListAdapter);
        mListView.setAdapter(mLeDeviceListAdapter);
        scanLeDevice(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
//        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
//            finish();
//            return;
//        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
  //      scanLeDevice(false);
      //  mLeDeviceListAdapter.clear();
    }


    @Override
        public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        System.out.println("==position=="+position);
        final BluetoothDevice device = mLeDeviceListAdapter.getDevice(position);
        if (device == null) return;
        final Intent intent = new Intent(this, DeviceControlActivity.class);
        intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME, device.getName());
        intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS, device.getAddress());
        if (mScanning) {
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            mScanning = false;
        }
//        final Intent intent = new Intent(this, DeviceControlActivity.class);
        startActivity(intent);
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
        invalidateOptionsMenu();
    }

    // Adapter for holding devices found through scanning.
    private class LeDeviceListAdapter extends BaseAdapter {
        private ArrayList<BluetoothDevice> mLeDevices;
        private LayoutInflater mInflator;

        public LeDeviceListAdapter() {
            super();
            mLeDevices = new ArrayList<BluetoothDevice>();
            mInflator = DeviceScanActivity.this.getLayoutInflater();
        }

        public void addDevice(BluetoothDevice device) {
            if(!mLeDevices.contains(device)) {
                mLeDevices.add(device);
            }
        }

        public BluetoothDevice getDevice(int position) {
            return mLeDevices.get(position);
        }

        public void clear() {
            mLeDevices.clear();
        }

        @Override
        public int getCount() {
            return mLeDevices.size();
        }

        @Override
        public Object getItem(int i) {
            return mLeDevices.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            // General ListView optimization code.
            if (view == null) {
                view = mInflator.inflate(R.layout.listitem_device, null);
                viewHolder = new ViewHolder();
                viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
                viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            BluetoothDevice device = mLeDevices.get(i);
            final String deviceName = device.getName();
            if (deviceName != null && deviceName.length() > 0)
                viewHolder.deviceName.setText(deviceName);
            else
                viewHolder.deviceName.setText(R.string.unknown_device);
            viewHolder.deviceAddress.setText(device.getAddress());

            return view;
        }
    }

    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLeDeviceListAdapter.addDevice(device);
                    mLeDeviceListAdapter.notifyDataSetChanged();
                }
            });
        }
    };

    static class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
    }
}