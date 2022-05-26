package com.example.data.network

import com.google.gson.annotations.SerializedName

data class PostHabitRequest(

    @SerializedName("date")
    val date: Int,

    @SerializedName("habit_uid")
    val uid: String?
)