package com.example.domain.repository

import com.example.domain.model.Habit

interface NetworkRepository {

    suspend fun getHabits(): List<Habit>

    suspend fun putHabit(habit: Habit): String?

    suspend fun deleteHabit(uid: String?)

    suspend fun postHabit(date: Int, uid: String?)
}