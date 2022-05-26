package com.example.domain.model

import java.io.Serializable

data class Habit(

    val title: String,
    val description: String,
    val type: HabitType,
    val priority: HabitPriority,
    val count: Int,
    val frequency: Int,
    val color: Int,
    var date: Int,
    var uid: String = "",
    val doneDates: MutableList<Int> = mutableListOf()

) : Serializable {

    fun post(date : Int) = doneDates.add(date)
    fun getCountDone(today : Int) : Int {

        val lastUpdateDay = today - today.rem(frequency)
        return doneDates.filter { it >= lastUpdateDay }.size
    }
}