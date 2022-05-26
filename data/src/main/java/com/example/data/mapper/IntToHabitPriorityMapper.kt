package com.example.data.mapper

import com.example.domain.model.HabitPriority
import javax.inject.Inject

class IntToHabitPriorityMapper @Inject constructor(

) : (Int) -> HabitPriority{

    override fun invoke(intPriority: Int) = when (intPriority) {

        HabitPriority.HIGH.value -> HabitPriority.HIGH
        HabitPriority.MEDIUM.value -> HabitPriority.MEDIUM
        else -> HabitPriority.LOW
    }
}