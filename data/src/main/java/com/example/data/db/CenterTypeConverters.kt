package com.example.data.db

import androidx.room.TypeConverter
import com.example.domain.model.Center
import com.google.gson.Gson

// 리스트를 한 번에 Room에 저장하기 위해 Converter 사용
class CenterTypeConverters {
    @TypeConverter
    fun CenterToJson(value: List<Center>): String = Gson().toJson(value)

    @TypeConverter
    fun JsonToCenter(value: String) = Gson().fromJson(value, Array<Center>::class.java).toList()
}