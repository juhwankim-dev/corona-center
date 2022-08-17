package com.example.presentation.views

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.UiThread
import com.example.domain.model.Center
import com.example.presentation.R
import com.example.presentation.config.BaseActivity
import com.example.presentation.databinding.ActivityMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapActivity : BaseActivity<ActivityMapBinding>(R.layout.activity_map), OnMapReadyCallback {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    private val viewModel: MapViewModel by viewModels()
    private val centerList = mutableListOf<Center>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        initView()
        initEvent()
    }

    private fun initView() {
        // 네이버맵 동적으로 불러오기
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    @UiThread
    override fun onMapReady(map: NaverMap) {
        naverMap = map

        // 내장 위치 추적 기능 사용
        naverMap.locationSource = locationSource
        moveToCurrentLocation()

        centerList.forEach {
            setMarker(map, it)
        }
    }

    private fun setMarker(map: NaverMap, center: Center) {
        val marker = Marker()
        val infoWindow = InfoWindow()

        // 아이콘 지정
        when(center.centerType) {
            "지역" -> marker.icon = OverlayImage.fromResource(R.drawable.ic_marker_blue)
            "중앙/권역" -> marker.icon = OverlayImage.fromResource(R.drawable.ic_marker_red)
            else -> marker.icon = OverlayImage.fromResource(R.drawable.ic_marker_green)
        }

        marker.isIconPerspectiveEnabled = true
        marker.alpha = 0.8f
        marker.position = LatLng(center.lat, center.lng)
        marker.zIndex = 10
        marker.map = map
        marker.tag = "주소: ${center.address}\n" +
                "센터명: ${center.centerName}\n" +
                "시설명: ${center.facilityName}\n" +
                "연락처: ${center.phoneNumber}\n" +
                "업데이트: ${center.updatedAt}"

        marker.setOnClickListener {
            val cameraUpdate = CameraUpdate.scrollTo(LatLng(center.lat, center.lng))
            map.moveCamera(cameraUpdate)

            if(marker.infoWindow == null) {
                infoWindow.open(marker)
            } else {
                infoWindow.close()
            }

            true
        }

        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                return infoWindow.marker?.tag as CharSequence? ?: ""
            }
        }
    }

    private fun moveToCurrentLocation() {
        // 사용자 현재 위치 받아오기
        var currentLocation: Location?
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                currentLocation = location

                // 파랑색 점, 현재 위치 표시
                naverMap.locationOverlay.run {
                    isVisible = true
                    position = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
                }

                // 카메라 현재위치로 이동
                val cameraUpdate = CameraUpdate.scrollTo(
                    LatLng(
                        currentLocation!!.latitude,
                        currentLocation!!.longitude
                    )
                )
                naverMap.moveCamera(cameraUpdate)
            }
            .addOnCanceledListener {
                Log.d(TAG, "onCanceledListener")
            }
            .addOnFailureListener {
                Log.d(TAG, "onFailureListener: ${it.message}")
            }
    }

    private fun initEvent() {
        with(viewModel) {
            readCenterList().observe(this@MapActivity) {
                centerList.addAll(it)
            }
        }

        binding.fabLocationSearching.setOnClickListener {
            moveToCurrentLocation()
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private const val TAG = "MapActivity_Corona"
    }
}