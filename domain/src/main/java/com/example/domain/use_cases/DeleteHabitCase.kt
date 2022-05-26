package com.example.domain.use_cases

import com.example.domain.repository.DatabaseRepository
import com.example.domain.model.Habit
import com.example.domain.repository.NetworkRepository
import javax.inject.Inject

class DeleteHabitCase @Inject constructor(

    private val databaseRepository: DatabaseRepository,
    private val networkRepository: NetworkRepository
) {

    suspend fun execute(habit: Habit) {

        habit.uid.let { networkRepository.deleteHabit(it) }
        databaseRepository.deleteHabit(habit)
    }
}