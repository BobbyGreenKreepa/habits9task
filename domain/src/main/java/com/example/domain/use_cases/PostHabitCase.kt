package com.example.domain.use_cases

import com.example.domain.repository.DatabaseRepository
import com.example.domain.repository.NetworkRepository
import com.example.domain.model.Habit

import javax.inject.Inject

class PostHabitCase @Inject constructor(

    private val databaseRepository: DatabaseRepository,
    private val networkRepository: NetworkRepository
) {

    suspend fun execute(habit: Habit, date: Int) {

        habit.post(date).also { databaseRepository.updateHabit(habit) }
        networkRepository.postHabit(date, habit.uid)
    }
}