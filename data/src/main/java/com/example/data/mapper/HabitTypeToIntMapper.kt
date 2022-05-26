package com.example.data.mapper

import com.example.domain.model.HabitType
import javax.inject.Inject

class HabitTypeToIntMapper @Inject constructor(

) : (HabitType) -> Int {

    override fun invoke(habitType: HabitType) = habitType.value
}