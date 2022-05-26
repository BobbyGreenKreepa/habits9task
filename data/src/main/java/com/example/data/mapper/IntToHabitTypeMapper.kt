package com.example.data.mapper

import com.example.domain.model.HabitType
import javax.inject.Inject

class IntToHabitTypeMapper @Inject constructor(

) : (Int) -> HabitType {

    override fun invoke(intType: Int) = when (intType) {

        HabitType.USEFUL.value -> HabitType.USEFUL
        else -> HabitType.HARMFUL
    }
}