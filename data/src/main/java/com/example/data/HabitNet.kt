package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.data.database.DatesConverter
import com.google.gson.annotations.SerializedName

@Entity
@TypeConverters(DatesConverter::class)
class HabitNet(

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("type")
    val type: Int,

    @SerializedName("priority")
    val priority: Int,

    @SerializedName("count")
    val count: Int,

    @SerializedName("frequency")
    val frequency: Int,

    @SerializedName("color")
    val color: Int,

    @SerializedName("date")
    val date: Int,

    @PrimaryKey
    @SerializedName("uid")
    var uid: String = "",

    @SerializedName("done_dates")
    var doneDates: MutableList<Int>
)