package com.example.codeidapp.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "weather_data")
@Parcelize
data class WeatherEntity(

    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: Int,

) : Parcelable