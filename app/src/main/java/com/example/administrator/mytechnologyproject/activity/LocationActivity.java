package com.example.administrator.mytechnologyproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.administrator.mytechnologyproject.R;

public class LocationActivity extends Activity implements View.OnClickListener{
    private Button button;

    public LocationClient mLocationClient = null;
    MapView mMapView = null;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    public BaiduMap mBaiduMap = null;
    public BitmapDescriptor mCurrentMarker = null;
    public BDLocationListener myListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            setLocation(bdLocation);
            mLocationClient.stop();
        }
    };

    private void setLocation(BDLocation location) {

        mBaiduMap.setMyLocationEnabled(true);
        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(0).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);
        LatLng ll = new LatLng(location.getLatitude(),
                location.getLongitude());
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 20);   //设置地图中心点以及缩放级别
//              MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
        mBaiduMap.animateMapStatus(u);
        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.daohang);
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);
        mBaiduMap.setMyLocationConfigeration(config);

        // 当不需要定位图层时关闭定位图层

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        mLocationClient = new LocationClient(getApplicationContext()); //声明LocationClient类
        setContentView(R.layout.activity_location);
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        mLocationClient.registerLocationListener(myListener);//注册监听函数
        initLocation();
        mLocationClient.stop();
        mLocationClient.start();

//        SetNotifyLocation();
    }

//    }

    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    public void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
       // int span = 1000;
       // option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                mLocationClient.start();
                break;
        }
    }
}
