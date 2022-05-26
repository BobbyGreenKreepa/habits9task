package com.example.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.data.HabitNet
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Query("SELECT * FROM HabitNet")
    fun getAll(): Flow<List<HabitNet>>

    @Query("SELECT * FROM HabitNet WHERE uid LIKE :uid")
    fun getByUid(uid: String): HabitNet?

    @Insert
    fun insert(habit: HabitNet?)

    @Update
    fun update(habit: HabitNet?)

    @Delete
    fun delete(habit: HabitNet?)
}