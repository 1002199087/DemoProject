package com.temporary.demoproject.qmuidemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.temporary.adapter.BaiduMapAdapter;
import com.temporary.demoproject.R;
import com.temporary.overlayutil.OverlayManager;
import com.temporary.overlayutil.TransitRouteOverlay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/*30.313781 , 120.1027*/

public class BaiduMapLoactionActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.baidu_mapview)
    MapView mBaiduMV;
    @BindView(R.id.baidumap_recyclerview)
    RecyclerView mBaidumapRV;

    private BaiduMap mBaiduMap;

    // 获取定位信息
    private LocationClient mLocationClient;
    private MyLocationListener mLocationListener = new MyLocationListener();

    private BaiduMapAdapter baiduMapAdapter;

    private UiSettings mUiSettings;

    // 路线规划
    private RoutePlanSearch mRoutePlanSearch;
    private String mCurrentCity;
    private String mCurrentAddress;
    private String mToAddress;

    private BitmapDescriptor mSTBitmapDescriptor;
    private BitmapDescriptor mTLBitmapDescriptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_baidu_map_loaction);
        ButterKnife.bind(this);

        checkPermission();

        mBaiduMap = mBaiduMV.getMap();

        mUiSettings = mBaiduMap.getUiSettings();
        mUiSettings.setOverlookingGesturesEnabled(false);// 禁止俯视功能

        // 获取定位信息
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(mLocationListener);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode.Battery_Saving：低功耗；
        //LocationMode.Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认gcj02
        //gcj02：国测局坐标；
        //bd09ll：百度经纬度坐标；
        //bd09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

        option.setScanSpan(5000);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //可选，7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明

        initTopbar();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mBaidumapRV.setLayoutManager(linearLayoutManager);
        mBaidumapRV.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));
        mBaidumapRV.setItemAnimator(new DefaultItemAnimator());


        //120.218897,30.297523
        // 116.66528,39.915156
        // 25.492958553074473 , 111.16592665694401
        LatLng oneLatLng = new LatLng(30.313849d, 120.1027d);
        LatLng twoLatLng = new LatLng(30.249865d, 120.189864d);
        LatLng threeLatLng = new LatLng(30.297523, 120.218897);
        LatLng fourLatLng = new LatLng(25.492958553074473, 111.16592665694401);

        final List<LatLng> points = new ArrayList<LatLng>();
        points.add(oneLatLng);
        points.add(twoLatLng);
        points.add(threeLatLng);
        points.add(fourLatLng);

        baiduMapAdapter = new BaiduMapAdapter(
                getApplicationContext(), points);
        baiduMapAdapter.setOnItemClick(new BaiduMapAdapter.OnItemClickListener() {
            @Override
            public void click(int position) {
                selectMarker(baiduMapAdapter.getLatLngs().get(position));
            }
        });
        mBaidumapRV.setAdapter(baiduMapAdapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showLocationMarker();
            }
        }, 1000);

        mRoutePlanSearch = RoutePlanSearch.newInstance();
        mRoutePlanSearch.setOnGetRoutePlanResultListener(routePlanResultListener);

        mSTBitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.icon_st);
        mTLBitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.icon_en);
    }

    /**
     * 换乘
     */
    private void getTransitRouteResult() {
        Log.e("wyy", "BaiduMapLoactionActivity getTransitRouteResult mCurrentCity = " + mCurrentCity
                + " , mCurrentAddress = " + mCurrentAddress
                + " , mToAddress = " + mToAddress);
        TransitRoutePlanOption option = new TransitRoutePlanOption();
        option.city(mCurrentCity);
        option.from(PlanNode.withCityNameAndPlaceName(mCurrentCity, mCurrentAddress));
        option.to(PlanNode.withCityNameAndPlaceName(mCurrentCity, mToAddress));
        option.policy(TransitRoutePlanOption.TransitPolicy.EBUS_TIME_FIRST);
        mRoutePlanSearch.transitSearch(option);
    }

    private OnGetRoutePlanResultListener routePlanResultListener = new OnGetRoutePlanResultListener() {
        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
            TransitRouteOverlay transitRouteOverlay = new MyTransitRouteOverlay(mBaiduMap);
            transitRouteOverlay.setData(transitRouteResult.getRouteLines().get(1));
            for (int i = 0; i < transitRouteResult.getRouteLines().get(1).getAllStep().size(); i++) {
                Log.e("wyy", "onGetTransitRouteResult " + i + " , "
                        + transitRouteResult.getRouteLines().get(1).getAllStep().get(i).getInstructions());
            }
            transitRouteOverlay.addToMap();
            transitRouteOverlay.zoomToSpan();
        }

        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

        }

        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

        }

        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

        }
    };

    private class MyTransitRouteOverlay extends TransitRouteOverlay {

        /**
         * 构造函数
         *
         * @param baiduMap 该TransitRouteOverlay引用的 BaiduMap 对象
         */
        public MyTransitRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return mSTBitmapDescriptor;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return mTLBitmapDescriptor;
        }

    }

    private void checkPermission() {
        /*if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.LOCATION_HARDWARE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.LOCATION_HARDWARE}, 1);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }*/

    }

    private class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            double latitude = bdLocation.getLatitude();    //获取纬度信息
            double longitude = bdLocation.getLongitude();    //获取经度信息
            float radius = bdLocation.getRadius();    //获取定位精度，默认值为0.0f

            String coorType = bdLocation.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            int errorCode = bdLocation.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明

            mCurrentCity = bdLocation.getCity();

            LatLng latLng = new LatLng(latitude, longitude);

            getAddressFromLatLng(latLng, 0);

            mLocationClient.stop();
        }
    }

    private void initTopbar() {
        int actionBarHeight = QMUIDisplayHelper.getActionBarHeight(getApplicationContext());
        Log.e("wyy", "BaiduMapLoactionActivity initTopbar actionBarHeight = " + actionBarHeight);

        QMUIStatusBarHelper.translucent(this);
        mTopbar.setTitle(getResources().getString(R.string.qmui_baidumap_location_button));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTopbar.addRightImageButton(R.mipmap.icon_topbar_overflow, R.id.topbar_right_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        QMUIBottomSheet.BottomListSheetBuilder builder =
                                new QMUIBottomSheet.BottomListSheetBuilder(
                                       getApplicationContext());
                        builder.addItem("开始定位");
                        builder.setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder
                                .OnSheetItemClickListener() {
                            @Override
                            public void onClick(final QMUIBottomSheet dialog, View itemView,
                                                int position, String tag) {
                                switch (position) {
                                    case 0: {// 开始定位
                                        mLocationClient.start();
                                        break;
                                    }
                                }
                                dialog.dismiss();
                            }
                        });
                        builder.build().show();
                    }
                });
    }

    // 显示地图标志
    private void showLocationMarker() {
        for (int i = 0; i < baiduMapAdapter.getLatLngs().size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(baiduMapAdapter.getLatLngs().get(i));
            markerOptions.icon(BitmapDescriptorFactory
                    .fromResource(R.mipmap.baidumap_location_64));
            markerOptions.animateType(MarkerOptions.MarkerAnimateType.jump);
            Marker marker = (Marker) mBaiduMap.addOverlay(markerOptions);// 添加图层图标
            Bundle bundle = new Bundle();
            bundle.putInt("id", i);
            marker.setExtraInfo(bundle);
        }

        // 在地图上显示多点坐标
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng p : baiduMapAdapter.getLatLngs()) {
            builder = builder.include(p);
        }
        LatLngBounds latlngBounds = builder.build();
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(latlngBounds,
                mBaiduMV.getWidth(), mBaiduMV.getHeight());
        mBaiduMap.animateMapStatus(u);
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                int id = marker.getExtraInfo().getInt("id");
                selectMarker(baiduMapAdapter.getLatLngs().get(id));
                getAddressFromLatLng(baiduMapAdapter.getLatLngs().get(id), 1);
                //getLatLngFromAddress(baiduMapAdapter.getLatLngs().get(id));
                return false;
            }
        });
    }

    // 选中标志
    private void selectMarker(LatLng latLng) {
        MapStatusUpdate mapStatusUpdate =
                MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.animateMapStatus(mapStatusUpdate);
    }

    /**
     * 根据经纬度获取位置信息
     *
     * @param latLng
     * @param i      0:获取当前定位的地址和城市 1：获取点击图标的地址
     */
    private void getAddressFromLatLng(final LatLng latLng, final int i) {
        final GeoCoder geocoder = GeoCoder.newInstance();
        geocoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                Toast.makeText(getApplicationContext(),
                        reverseGeoCodeResult.getAddress(),
                        Toast.LENGTH_SHORT).show();
                switch (i) {
                    case 0: {
                        mCurrentAddress = reverseGeoCodeResult.getAddress();
                        Log.e("wyy", "BaiduMapLoactionActivity onGetReverseGeoCodeResult mCurrentAddress = " + mCurrentAddress);
                        //mCurrentCity = getCityFromAddress(reverseGeoCodeResult.getAddress());
                        break;
                    }
                    case 1: {
                        mToAddress = reverseGeoCodeResult.getAddress();
                        getTransitRouteResult();
                        break;
                    }
                }
            }
        });
        geocoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
    }

    // 根据位置信息获取经纬度
    private void getLatLngFromAddress(LatLng latLng) {
        GeoCoder geoCoder = GeoCoder.newInstance();
        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                Log.e("wyy", "BaiduMapLoactionActivity onGetGeoCodeResult ttt "
                        + geoCodeResult.getLocation().latitude + " , "
                        + geoCodeResult.getLocation().longitude);
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

            }
        });
        // 浙江省杭州市江干区天城路
        // 北京市通州区新华大街83号
        String addressBase = "广西桂林灌阳县灌阳镇解放东路46号";
        String addressCity = null;
        try {
            Geocoder tmpGeocoder = new Geocoder(getApplicationContext(), Locale.CHINA);
            List<Address> addresses = tmpGeocoder.getFromLocationName(addressBase, 1);
            for (Address address : addresses) {
                addressCity = address.getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        geoCoder.geocode(new GeoCodeOption().address(addressBase).city(addressCity));
    }

    // 从地址中获取城市
    private String getCityFromAddress(String address) {
        try {
            String city = null;
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.CHINA);
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            for (Address a : addresses) {
                city = a.getLocality();
            }
            return city;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addAnyLocation(List<LatLng> latLngs) {
        final List<OverlayOptions> overlayOptions = new ArrayList<>();
        OverlayManager overlayManager = new OverlayManager(mBaiduMap) {
            @Override
            public List<OverlayOptions> getOverlayOptions() {
                return overlayOptions;
            }

            @Override
            public boolean onMarkerClick(Marker marker) {
                return true;
            }

            @Override
            public boolean onPolylineClick(Polyline polyline) {
                return true;
            }
        };
        for (LatLng latLng : latLngs) {
            MarkerOptions options = new MarkerOptions().position(latLng);
            overlayOptions.add(options);
        }
        overlayManager.addToMap();
        overlayManager.zoomToSpan();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
            mLocationClient.unRegisterLocationListener(mLocationListener);
        }
    }
}
