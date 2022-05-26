package com.example.data.mapper

import com.example.data.HabitNet
import com.example.domain.model.Habit
import javax.inject.Inject

class HabitNetToDomainMapper @Inject constructor(

    private val intToHabitPriorityMapper: IntToHabitPriorityMapper,
    private val intToHabitTypeMapper: IntToHabitTypeMapper

) : (HabitNet) -> Habit {

    override fun invoke(net: HabitNet) = Habit(

        title = net.title,
        description = net.description,
        type = intToHabitTypeMapper.invoke(net.type),
        priority = intToHabitPriorityMapper.invoke(net.priority),
        count = net.count,
        frequency = net.frequency,
        color = net.color,
        date = net.date,
        uid = net.uid,
        doneDates = net.doneDates
    )
}