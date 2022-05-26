package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.HabitNet

@Database(entities = [HabitNet::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun habitDao(): HabitDao
}