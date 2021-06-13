package com.example.budget.data

import androidx.room.TypeConverter
import java.util.*

class DBConverters {
    @TypeConverter
    fun fromTimeStamp(value: Long?): Date?{
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimeStamp(date: Date?): Long?{
        return date?.time?.toLong()
    }
}