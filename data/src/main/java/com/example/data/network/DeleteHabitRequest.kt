package com.example.data.network

import com.google.gson.annotations.SerializedName

data class DeleteHabitRequest(

    @SerializedName("uid")
    private val uid: String?
)