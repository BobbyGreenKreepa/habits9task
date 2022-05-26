package com.example.habitsclean.dagger

import com.example.data.database.DatabaseRepositoryImpl
import com.example.data.network.NetworkRepositoryImpl
import com.example.domain.repository.DatabaseRepository
import com.example.domain.repository.NetworkRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
internal abstract class HabitModule {

    @Binds
    @Reusable
    abstract fun bindRepository1(networkRepositoryImpl: NetworkRepositoryImpl): NetworkRepository

    @Binds
    @Reusable
    abstract fun bindRepository2(databaseRepositoryImpl: DatabaseRepositoryImpl): DatabaseRepository
}