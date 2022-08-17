package com.example.data.api

import com.example.data.BuildConfig
import com.example.data.model.CenterEntity
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CenterApi {
    // Github에 Key값이 올라가지 않도록 숨김. Key를 Header에 넣어 API 요청
    @Headers("Authorization: ${BuildConfig.DECODING_KEY}")
    @GET("15077586/v1/centers")
    suspend fun getCenterList(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int = 10
    ): CenterEntity
}