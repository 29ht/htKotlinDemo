package com.example.htkotlinmvvmdemo1.ui.activity

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.*
import com.amap.api.services.core.ServiceSettings
import com.amap.api.services.weather.LocalWeatherForecastResult
import com.amap.api.services.weather.LocalWeatherLiveResult
import com.amap.api.services.weather.WeatherSearch
import com.amap.api.services.weather.WeatherSearchQuery
import com.caspar.base.base.BaseActivity
import com.example.htkotlinmvvmdemo1.R
import com.example.htkotlinmvvmdemo1.bean.response.Hotels
import com.example.htkotlinmvvmdemo1.config.Constant
import com.example.htkotlinmvvmdemo1.databinding.ActivityGaodeMapBinding
import kotlinx.coroutines.launch


/**
 * 高德地图基础功能
 * @author ht 2021/12/16 14:00
 */
class GaodeMapActivity : BaseActivity<ActivityGaodeMapBinding>(),
    AMapLocationListener, WeatherSearch.OnWeatherSearchListener {

    private var hotels = ArrayList<Hotels>()
    private var mLocation: AMapLocation? = null
    private var mLocationClient: AMapLocationClient? = null
    private var mLocationOption: AMapLocationClientOption? = null
    private var isOne = false
    override fun initIntent() {
        if (intent.extras != null) {
            hotels = intent.extras?.getParcelableArrayList(Constant.NWWSWEBVIEWURL)!!
        }

    }

    override fun initView(savedInstanceState: Bundle?) {
        ServiceSettings.updatePrivacyShow(this, true, true)
        ServiceSettings.updatePrivacyAgree(this, true)
        mBindingView.mapView.onCreate(savedInstanceState) // 此方法必须重写
        basIcs()
        marKer()
        clickEvent()
    }

    /*
 * 点击事件
 */
    private fun clickEvent() {
        //导航地图
        mBindingView.tvNavigation.setOnClickListener {
            mBindingView.mapView.map.mapType = AMap.MAP_TYPE_NAVI
        }
        //夜景地图
        mBindingView.tvNightView.setOnClickListener {
            mBindingView.mapView.map.mapType = AMap.MAP_TYPE_NIGHT
        }
        //白昼地图（即普通地图）
        mBindingView.tvDay.setOnClickListener {
            mBindingView.mapView.map.mapType = AMap.MAP_TYPE_NORMAL
        }
        //卫星图
        mBindingView.tvSatellite.setOnClickListener {
            mBindingView.mapView.map.mapType = AMap.MAP_TYPE_SATELLITE
        }
        mBindingView.tvWeather.setOnClickListener {
            //获取当前定位城市天气
            lifecycleScope.launch {
                searchWeather(mLocation?.city.toString())
            }

        }
        //切换中英文
        mBindingView.tvLanguage.text = getString(R.string.switch_english)
        mBindingView.tvLanguage.setOnClickListener {
            if (mBindingView.tvLanguage.text.toString() == getString(R.string.switch_english)) {
                mBindingView.mapView.map.setMapLanguage("en")
                mBindingView.tvLanguage.text = getString(R.string.switch_chinese)
            } else {
                mBindingView.mapView.map.setMapLanguage("zh_cn")
                mBindingView.tvLanguage.text = getString(R.string.switch_english)
            }

        }
    }

    /**
     * 地图基础设置
     */
    private fun basIcs() {

        mBindingView.title.tvCenter.text = getString(R.string.gaod_map)

        if (mLocationClient == null) {
            //初始化定位
            mLocationClient = AMapLocationClient(this)
            //初始化定位参数
            mLocationOption = AMapLocationClientOption()
            //设置定位回调监听
            mLocationClient!!.setLocationListener(this)
            //设置为高精度定位模式
            mLocationOption!!.locationMode = AMapLocationMode.Hight_Accuracy
            //设置定位参数
            mLocationClient!!.setLocationOption(mLocationOption)
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mLocationClient!!.startLocation() //启动定位

        }

        val myLocationStyle = MyLocationStyle()
        //连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER)

        //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.interval(2000)
        //设置定位蓝点的Style
        myLocationStyle.isMyLocationShowing
        mBindingView.mapView.map.myLocationStyle = myLocationStyle

        // 显示实时交通状况
        mBindingView.mapView.map.isTrafficEnabled = true
        //true：显示室内地图；false：不显示；
        mBindingView.mapView.map.showIndoorMap(true)

        //设置默认定位按钮是否显示，非必需设置。
        mBindingView.mapView.map.uiSettings.isMyLocationButtonEnabled = true
        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        mBindingView.mapView.map.isMyLocationEnabled = true

    }

    /**
     * 地图绘制
     */
    private fun marKer() {

        //绘制marker
        if (hotels.size > 0) {
            mBindingView.mapView.map.moveCamera(
                CameraUpdateFactory.changeLatLng(
                    LatLng(
                        hotels[0].Laitude!!,
                        hotels[0].Longitude!!
                    )
                )
            )
            for (map in 0 until hotels.size) {
                mBindingView.mapView.map.addMarker(
                    MarkerOptions()
                        .position(LatLng(hotels[map].Laitude!!, hotels[map].Longitude!!))
                        .title(hotels[map].HotelName)
                        .snippet(hotels[map].HotelAddress + "\n" + "维度:" + hotels[map].Laitude + "/" + "经度：" + hotels[map].Longitude)
                        .icon(
                            BitmapDescriptorFactory.fromBitmap(
                                BitmapFactory.decodeResource(
                                    resources,
                                    R.mipmap.icon_site
                                )
                            )
                        )
                        .draggable(true)
                )
            }
        }


        // 绘制曲线
        mBindingView.mapView.map.addPolyline(
            PolylineOptions()
                .add(LatLng(43.828, 87.621), LatLng(45.808, 126.55))
                .geodesic(true).color(Color.RED)
        )
    }


    //检索参数为城市和天气类型，实况天气为WEATHER_TYPE_LIVE、天气预报为WEATHER_TYPE_FORECAST
    private fun searchWeather(text: String) {
        val mQuery = WeatherSearchQuery(text, WeatherSearchQuery.WEATHER_TYPE_LIVE)
        val mWeatherSearch = WeatherSearch(this)
        mWeatherSearch.setOnWeatherSearchListener(this)
        mWeatherSearch.query = mQuery
        //异步搜索
        mWeatherSearch.searchWeatherAsyn()
    }

    /**
     * 定位回调
     */
    override fun onLocationChanged(p0: AMapLocation?) {
        mLocation = p0
        if (hotels.size == 0 && p0 != null && !isOne) {
            mBindingView.mapView.map.moveCamera(
                CameraUpdateFactory.changeLatLng(
                    LatLng(
                        p0.latitude,
                        p0.longitude
                    )
                )
            )
            isOne = true
        }

    }

    /**
     * 获取天气查询结果
     */
    override fun onWeatherLiveSearched(p0: LocalWeatherLiveResult?, p1: Int) {
        if (p1 == 1000) {
            if (p0 != null) {
                val weatherLive = p0.liveResult
                toast("今天天气： " + weatherLive.weather + "\n今天温度:  " + weatherLive.getTemperature() + "°")
            } else {
                toast("获取天气数据出错：$p0")
            }
        }
    }

    override fun onWeatherForecastSearched(p0: LocalWeatherForecastResult?, p1: Int) {

    }

    override fun onDestroy() {
        super.onDestroy()
        mBindingView.mapView.onDestroy()
        if (null != mLocationClient) {
            mLocationClient!!.onDestroy()
        }
    }

    override fun onResume() {
        super.onResume()
        mBindingView.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mBindingView.mapView.onPause()
    }


}