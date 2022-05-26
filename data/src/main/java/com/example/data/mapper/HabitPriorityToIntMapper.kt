package com.example.data.mapper

import com.example.domain.model.HabitPriority
import javax.inject.Inject

class HabitPriorityToIntMapper @Inject constructor(

) : (HabitPriority) -> Int {

    override fun invoke(habitPriority: HabitPriority) = habitPriority.value
}