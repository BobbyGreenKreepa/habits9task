package com.example.data.mapper

import com.example.data.HabitNet
import com.example.domain.model.Habit
import javax.inject.Inject

class DomainHabitToNetMapper @Inject constructor(

    private val habitPriorityToIntMapper: HabitPriorityToIntMapper,
    private val habitTypeToIntMapper: HabitTypeToIntMapper

) : (Habit) -> HabitNet{

    override fun invoke(habit: Habit) = HabitNet(

        title = habit.title,
        description = habit.description,
        type = habitTypeToIntMapper.invoke(habit.type),
        priority = habitPriorityToIntMapper.invoke(habit.priority),
        count = habit.count,
        frequency = habit.frequency,
        color = habit.color,
        date = habit.date,
        uid = habit.uid,
        doneDates = habit.doneDates
    )
}