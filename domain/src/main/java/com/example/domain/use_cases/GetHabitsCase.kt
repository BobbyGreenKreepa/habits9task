package com.example.domain.use_cases

import com.example.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetHabitsCase @Inject constructor(

    private val databaseRepository: DatabaseRepository
) {

    fun execute() = databaseRepository.getHabits()
}