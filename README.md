## πΊ μμ° μμ
https://youtube.com/shorts/5zsBkN5l1ws

## π₯ μ± λ€μ΄λ‘λ
https://bigfile.mail.naver.com/download?fid=usKjWN+TpzdZKxuZK3YwKqUjKogZKog/Kx2mFAE/KAKjKxMrKqtZFobla3e4p4KrKqK9MrF0MqbXF6i4KAJvpxpvKxgrMqM/a6MrMm==

## βοΈ μ¬μ©ν κΈ°μ 
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
- λΉλκΈ°
    - Coroutine Flow
- κ·Έ μΈ
    - TedPermission
    - Clean Architecture
    - Multi Module

## β κ΅¬ν μ¬ν­ μ²΄ν¬

<details markdown="1">
<summary><strong>API νΈμΆ λ° λ°μ΄ν° μ μ₯</strong></summary>
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
                            writeCenterList(it) // DBμ μ μ₯νλ λ©μλ νΈμΆ
                        }
                    }
            }
        }
```
Forλ¬Έμ λλ € 1νμ΄μ§λΆν° 10νμ΄μ§κΉμ§ μμ²­μ λ λ¦¬κ³  <br>
`Flow`λ‘ μλ΅μ λ°μ DBμ μ μ₯νλλ‘ νμ΅λλ€. <br>
λ°μ΄ν°λ₯Ό μ μ₯νλ λ°©μμ `Room`μ μ¬μ©νμ΅λλ€.

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
                // μ μ₯μ μ±κ³΅μ μΌλ‘ μλ£νλ©΄ μ§νλ₯ μ λνλ΄λ κ°μ +1 νλ€.
                _progressCount.value = _progressCount.value?.plus(1)
            }
        }
    }
```
λ°μ΄ν°λ₯Ό μΌλ§λ μ μ₯νλμ§ μ§νλ₯ μ μκΈ° μν΄ progressCountμ κ°μ `+1μ© μΉ΄μ΄νΈ` ν΄μ£Όμμ΅λλ€. <br>

```
    private fun manageProgress() {
        thread(start = true) {
            for (percent in 1..100) {
                // 80%κ° λ λκΉμ§ && μ μ₯μ΄ μλ£λμ§ μμλ€λ©΄
                if(percent == STANDARD_FOR_WAIT && viewModel.progressCount.value!!.compareTo(10) < -1) {
                    // μ μ₯μ΄ μλ£λ  λ κΉμ§ λκΈ° -> μ΄λ λͺ μ΄λ₯Ό λκΈ°νλΌλ μκ΅¬ μ¬ν­μ΄ μμμΌλ―λ‘ 3μ΄λ‘ μ€μ 
                    for(sec in 1..3) {
                        Thread.sleep(1000)
                        // μ μ₯μ΄ μλ£λλ©΄
                        if(viewModel.progressCount.value!!.compareTo(10) == 0) {
                            // 0.4μ΄μ κ±Έμ³ 100%λ₯Ό λ§λ€κ³  νμ΄μ§ μ΄λ
                            // 1% = 0.02μ΄, 20% = 0.4μ΄
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
SplashActivityμμλ μμμ μ€λͺν progressCountκ°μ μ΄μ©ν΄ μ§νλ₯ μ νμνκ³  <br>
κ·Έμ λ°λΌ μκ΅¬μ¬ν­μ λ§κ² λκΈ°ν μ§, 100%κ° λμ΄ λ€μ νλ©΄μΌλ‘ λμ΄κ°μ§ λ±μ κ³μ°ν©λλ€.

<br>
</details>

<details markdown="1">
<summary><strong>Room</strong></summary>
<br>

```
// λ¦¬μ€νΈλ₯Ό ν λ²μ Roomμ μ μ₯νκΈ° μν΄ Converter μ¬μ©
class CenterTypeConverters {
    @TypeConverter
    fun CenterToJson(value: List<Center>): String = Gson().toJson(value)

    @TypeConverter
    fun JsonToCenter(value: String) = Gson().fromJson(value, Array<Center>::class.java).toList()
}
```
μ½λ‘λ μΌν° λ¦¬μ€νΈλ₯Ό ν λ²μ μ μ₯νκΈ° μν΄ TypeConverterλ₯Ό μ¬μ©νμ¬ DBμ μ μ₯νμμ΅λλ€.

<br>
</details>

<details markdown="1">
<summary><strong>Keyκ°</strong></summary>
<br>

```
    defaultConfig {
        // ...
        
        buildConfigField "String", "DECODING_KEY", properties['decoding_key']
    }
```

```
interface CenterApi {
    // Githubμ Keyκ°μ΄ μ¬λΌκ°μ§ μλλ‘ μ¨κΉ. Keyλ₯Ό Headerμ λ£μ΄ API μμ²­
    @Headers("Authorization: ${BuildConfig.DECODING_KEY}")
    @GET("15077586/v1/centers")
    suspend fun getCenterList(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int = 10
    ): CenterEntity
}
```

μΉ μμ Keyκ°μ΄ λΈμΆλμ§ μκ² νκΈ° μν΄ BuildConfigλ₯Ό μ¬μ©νμ΅λλ€.

<br>
</details>

<details markdown="1">
<summary><strong>λ§μ»€</strong></summary>
<br>

```
    private fun setMarker(map: NaverMap, center: Center) {
        val marker = Marker()
        val infoWindow = InfoWindow()

        // μμ΄μ½ μ§μ 
        when(center.centerType) {
            "μ§μ­" -> marker.icon = OverlayImage.fromResource(R.drawable.ic_marker_blue)
            "μ€μ/κΆμ­" -> marker.icon = OverlayImage.fromResource(R.drawable.ic_marker_red)
            else -> marker.icon = OverlayImage.fromResource(R.drawable.ic_marker_green)
        }

        marker.isIconPerspectiveEnabled = true
        marker.alpha = 0.8f
        marker.position = LatLng(center.lat, center.lng)
        marker.zIndex = 10
        marker.map = map
        marker.tag = "μ£Όμ: ${center.address}\n" +
                "μΌν°λͺ: ${center.centerName}\n" +
                "μμ€λͺ: ${center.facilityName}\n" +
                "μ°λ½μ²: ${center.phoneNumber}\n" +
                "μλ°μ΄νΈ: ${center.updatedAt}"

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

μμμ λ°λΌ λ§μ»€λ₯Ό κ΅¬λΆνκ³  ν΄λ¦­ μ΄λ²€νΈλ₯Ό λ¬μ μ λ³΄ μλ΄μ°½ on/off <br>
λ€μ μ ννλ κ²½μ° μ ν ν΄μ , νμ λ°μ΄ν° μΆλ ₯μ κ΅¬ννμ΅λλ€.

<br>
</details>

<details markdown="1">
<summary><strong>νμ¬ μμΉ λ²νΌ</strong></summary>
<br>

```
    private fun moveToCurrentLocation() {
        // μ¬μ©μ νμ¬ μμΉ λ°μμ€κΈ°
        var currentLocation: Location?
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                currentLocation = location

                // νλμ μ , νμ¬ μμΉ νμ
                naverMap.locationOverlay.run {
                    isVisible = true
                    position = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
                }

                // μΉ΄λ©λΌ νμ¬μμΉλ‘ μ΄λ
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
FloatingActionButtonμ μ§μ  μμ±ν΄ ν΄λ¦­ μ νμ¬ μμΉλ‘ μ΄λνλλ‘ κ΅¬ννμ΅λλ€.

<br>
</details>