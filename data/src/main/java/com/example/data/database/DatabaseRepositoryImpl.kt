package com.example.data.database

import com.example.data.mapper.DomainHabitToNetMapper
import com.example.data.mapper.HabitNetToDomainMapper
import com.example.domain.model.Habit
import com.example.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(

    private val domainHabitToNetMapper: DomainHabitToNetMapper,
    private val habitNetToDomainMapper: HabitNetToDomainMapper,
    private val database: Database

) : DatabaseRepository {

    override fun getHabits(): Flow<List<Habit>> = database.habitDao().getAll().map { it.map(habitNetToDomainMapper) }

    override fun getHabitsByUid(uid: String) = database.habitDao().getByUid(uid)
        ?.let { habitNetToDomainMapper.invoke(it) }

    override fun addHabit(habit: Habit) = database.habitDao().insert(domainHabitToNetMapper.invoke(habit))

    override fun deleteHabit(habit: Habit) = database.habitDao().delete(domainHabitToNetMapper.invoke(habit))

    override fun updateHabit(habit: Habit) = database.habitDao().update(domainHabitToNetMapper.invoke(habit))
}