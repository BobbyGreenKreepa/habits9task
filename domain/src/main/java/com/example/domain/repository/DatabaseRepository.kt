package com.example.domain.repository

import com.example.domain.model.Habit
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    fun getHabits(): Flow<List<Habit>>

    fun getHabitsByUid(uid: String): Habit?

    fun addHabit(habit: Habit)

    fun deleteHabit(habit: Habit)

    fun updateHabit(habit: Habit)
}