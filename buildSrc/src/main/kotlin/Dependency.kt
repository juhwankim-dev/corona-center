object Versions {
    const val KOTLIN_VERSION = "1.5.0"
    const val KOTLINX_COROUTINES = "1.5.0"
    const val BUILD_GRADLE = "4.2.1"
    const val COMPILE_SDK_VERSION = 30
    const val BUILD_TOOLS_VERSION = "30.0.3"
    const val MIN_SDK_VERSION = 23
    const val TARGET_SDK_VERSION = 30

    const val CORE_KTX = "1.7.0"
    const val APP_COMPAT = "1.4.2"
    const val ACTIVITY_KTX = "1.2.3"
    const val FRAGMENT_KTX = "1.3.4"
    const val LIFECYCLE_KTX = "2.3.1"
    const val CONSTRAINT_LAYOUT = "2.1.4"
    const val ROOM = "2.4.0"
    const val LIVE_DATA = "2.4.1"
    const val PAGING = "3.0.0"
    const val NAVIGATION = "2.5.0"
    const val SPLASH_SCREEN = "1.0.0-beta01"

    const val MATERIAL = "1.4.0"

    const val RETROFIT = "2.9.0"
    const val OKHTTP = "4.9.0"
    const val RX_JAVA_ADAPTER = "2.5.0"
    const val KOIN = "3.2.0"
    const val GLIDE = "4.12.0"
    const val HILT = "2.38.1"
    const val NAVER_MAP = "3.15.0"
    const val TED_PERMISSION = "3.3.0"

    const val JUNIT = "4.13.2"
    const val ANDROID_JUNIT = "1.1.3"
    const val ESPRESSO_CORE = "3.4.0"
}

object Kotlin {
    const val KOTLIN_STDLIB      = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}"
    const val COROUTINES_CORE    = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLINX_COROUTINES}"
    const val COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLINX_COROUTINES}"
}

object AndroidX {
    const val CORE_KTX                = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val APP_COMPAT              = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"

    const val ACTIVITY_KTX            = "androidx.activity:activity-ktx:${Versions.ACTIVITY_KTX}"
    const val FRAGMENT_KTX            = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT_KTX}"

    const val LIFECYCLE_VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE_KTX}"
    const val LIFECYCLE_LIVEDATA_KTX  = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE_KTX}"

    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"

    const val ROOM_RUNTIME            = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_KTX                = "androidx.room:room-ktx:${Versions.ROOM}"
    const val ROOM_COMPILER           = "androidx.room:room-compiler:${Versions.ROOM}"

    const val LIVE_DATA_KTX           = "androidx.lifecycle:lifecycle-livedata-ktx:$${Versions.LIVE_DATA}"

    const val PAGING                  = "androidx.paging:paging-runtime:${Versions.PAGING}"

    const val NAVIGATION_FRAGMENT     = "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
    const val NAVIGATION_UI           = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"

    const val SPLASH_SCREEN           = "androidx.core:core-splashscreen:${Versions.SPLASH_SCREEN}"
}

object Google {
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
}

object Libraries {
    const val RETROFIT                   = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_CONVERTER_GSON    = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    const val OKHTTP                     = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
    const val OKHTTP_LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"
    const val RETROFIT_ADAPTER           = "com.squareup.retrofit2:adapter-rxjava2:${Versions.RX_JAVA_ADAPTER}"
    const val KOIN                       = "io.insert-koin:koin-core:${Versions.KOIN}"
    const val KOIN_ANDROID               = "io.insert-koin:koin-android:${Versions.KOIN}"
    const val GLIDE                      = "com.github.bumptech.glide:glide:${Versions.GLIDE}"
    const val HILT                       = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_COMPILER              = "com.google.dagger:hilt-compiler:${Versions.HILT}"
    const val NAVER_MAP                  = "com.naver.maps:map-sdk:${Versions.NAVER_MAP}"
    const val TED_PERMISSION             = "io.github.ParkSangGwon:tedpermission-coroutine:${Versions.TED_PERMISSION}"
}

object UnitTest {
    const val JUNIT         = "junit:junit:${Versions.JUNIT}"
}

object AndroidTest {
    const val ANDROID_JUNIT = "androidx.test.ext:junit:${Versions.ANDROID_JUNIT}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
}