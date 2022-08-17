package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.domain.model.Center

@Database(entities = [Center::class], version = 1)
abstract class CenterDatabase: RoomDatabase() {
    abstract fun centerDao(): CenterDao
}