package com.example.domain.use_cases

import com.example.domain.repository.DatabaseRepository
import com.example.domain.repository.NetworkRepository
import javax.inject.Inject

class LoadRemoteDataCase @Inject constructor(

    private val databaseRepository: DatabaseRepository,
    private val networkRepository: NetworkRepository

) {

    suspend fun execute() {

        val remoteHabits = networkRepository.getHabits()
        remoteHabits.forEach {

            val habit = it.uid?.let { uid -> databaseRepository.getHabitsByUid(uid) }
            if (habit == null)
                databaseRepository.addHabit(it)
        }

    }
}