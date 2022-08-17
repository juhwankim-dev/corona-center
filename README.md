## 📺 시연 영상
https://youtube.com/shorts/5zsBkN5l1ws

## 📥 앱 다운로드
https://bigfile.mail.naver.com/download?fid=usKjWN+TpzdZKxuZK3YwKqUjKogZKog/Kx2mFAE/KAKjKxMrKqtZFobla3e4p4KrKqK9MrF0MqbXF6i4KAJvpxpvKxgrMqM/a6MrMm==

## ⚒️ 사용한 기술
- Language
    - Kotlin
- Design Pattern
    - MVVM
- UI Layout
    - XML
- Network
    - Retrofit2
- Jetpack
    - DataBinding
    - Room
    - LiveData
    - ViewModel
- Map
    - Naver Map
- DI
    - Hilt
- 비동기
    - Coroutine Flow
- 그 외
    - TedPermission
    - Clean Architecture
    - Multi Module

## ✅ 구현 사항 체크

<details markdown="1">
<summary><strong>API 호출 및 데이터 저장</strong></summary>
<br>

```
        for(page in 1..10) {
            viewModelScope.launch {
                getCenterListUseCase(page)
                    .catch { _problem.postValue(Result.fail()) }
                    .collect {
                        if(it.isEmpty()) {
                            _problem.postValue(Result.error("Response is empty", it))
                        } else {
                            _problem.postValue(Result.success(it))
                            writeCenterList(it) // DB에 저장하는 메서드 호출
                        }
                    }
            }
        }
```
For문을 돌려 1페이지부터 10페이지까지 요청을 날리고 <br>
`Flow`로 응답을 받아 DB에 저장하도록 했습니다. <br>
데이터를 저장하는 방식은 `Room`을 사용했습니다.

<br>
</details>

<details markdown="1">
<summary><strong>ProgressBar</strong></summary>
<br>

```
    private fun writeCenterList(centerList: List<Center>) {
        viewModelScope.launch {
            val result = writeCenterListUseCase(centerList)
            if(result.status == Status.ERROR) {
                _problem.postValue(result)
            } else {
                // 저장을 성공적으로 완료하면 진행률을 나타내는 값을 +1 한다.
                _progressCount.value = _progressCount.value?.plus(1)
            }
        }
    }
```
데이터를 얼마나 저장했는지 진행률을 알기 위해 progressCount에 값을 `+1씩 카운트` 해주었습니다. <br>

```
    private fun manageProgress() {
        thread(start = true) {
            for (percent in 1..100) {
                // 80%가 될때까지 && 저장이 완료되지 않았다면
                if(percent == STANDARD_FOR_WAIT && viewModel.progressCount.value!!.compareTo(10) < -1) {
                    // 저장이 완료될 때 까지 대기 -> 이때 몇 초를 대기하라는 요구 사항이 없었으므로 3초로 설정
                    for(sec in 1..3) {
                        Thread.sleep(1000)
                        // 저장이 완료되면
                        if(viewModel.progressCount.value!!.compareTo(10) == 0) {
                            // 0.4초에 걸쳐 100%를 만들고 페이지 이동
                            // 1% = 0.02초, 20% = 0.4초
                            for(i in 1..20) {
                                Thread.sleep(TIME_FOR_1_PER)
                                setProgressInfo(percent + i)
                                startMapActivity()
                                return@thread
                            }
                        }
                    }
                }
                Thread.sleep(TIME_FOR_1_PER)
                setProgressInfo(percent)
            }
            startMapActivity()
        }
    }
```
SplashActivity에서는 위에서 설명한 progressCount값을 이용해 진행률을 파악하고 <br>
그에 따라 요구사항에 맞게 대기할지, 100%가 되어 다음 화면으로 넘어갈지 등을 계산합니다.

<br>
</details>

<details markdown="1">
<summary><strong>Room</strong></summary>
<br>

```
// 리스트를 한 번에 Room에 저장하기 위해 Converter 사용
class CenterTypeConverters {
    @TypeConverter
    fun CenterToJson(value: List<Center>): String = Gson().toJson(value)

    @TypeConverter
    fun JsonToCenter(value: String) = Gson().fromJson(value, Array<Center>::class.java).toList()
}
```
코로나 센터 리스트를 한 번에 저장하기 위해 TypeConverter를 사용하여 DB에 저장하였습니다.

<br>
</details>

<details markdown="1">
<summary><strong>Key값</strong></summary>
<br>

```
    defaultConfig {
        // ...
        
        buildConfigField "String", "DECODING_KEY", properties['decoding_key']
    }
```

```
interface CenterApi {
    // Github에 Key값이 올라가지 않도록 숨김. Key를 Header에 넣어 API 요청
    @Headers("Authorization: ${BuildConfig.DECODING_KEY}")
    @GET("15077586/v1/centers")
    suspend fun getCenterList(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int = 10
    ): CenterEntity
}
```

웹 상에 Key값이 노출되지 않게 하기 위해 BuildConfig를 사용했습니다.

<br>
</details>

<details markdown="1">
<summary><strong>마커</strong></summary>
<br>

```
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
```

색상에 따라 마커를 구분하고 클릭 이벤트를 달아 정보 안내창 on/off <br>
다시 선택하는 경우 선택 해제, 표시 데이터 출력을 구현했습니다.

<br>
</details>

<details markdown="1">
<summary><strong>현재 위치 버튼</strong></summary>
<br>

```
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
```
FloatingActionButton을 직접 생성해 클릭 시 현재 위치로 이동하도록 구현했습니다.

<br>
</details>