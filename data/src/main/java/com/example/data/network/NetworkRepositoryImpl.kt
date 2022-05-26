package com.example.data.network

import android.util.Log
import com.example.data.mapper.DomainHabitToNetMapper
import com.example.data.mapper.HabitNetToDomainMapper
import com.example.domain.model.Habit
import com.example.domain.repository.NetworkRepository
import java.net.UnknownHostException
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(

    private val domainHabitToNetMapper: DomainHabitToNetMapper,
    private val habitNetToDomainMapper: HabitNetToDomainMapper,
    private val apiService: ApiService

) : NetworkRepository {

    companion object {

        const val TOKEN = "7af52d9e-b8d1-4e9c-9682-be3e8dbddff2"
        const val PATH = "api/habit"
        const val HABIT_DONE_PATH = "api/habit_done"
        const val HEADER = "Authorization"
        const val HOST_EXCEPTION = "UnknownHostException"
        const val HTTP_EXCEPTION = "HttpException"
    }

    override suspend fun getHabits(): List<Habit> {
        var habitList = emptyList<Habit>()

        try {
            habitList = apiService.getHabits(TOKEN).map(habitNetToDomainMapper)
        } catch (e: UnknownHostException) {
            Log.e(HOST_EXCEPTION, e.message, e)
        } catch (e: retrofit2.HttpException) {
            Log.e(HTTP_EXCEPTION, e.message())
        }

        return habitList
    }

    override suspend fun putHabit(habit: Habit): String? {
        var uid: String? = null

        try {
            uid = apiService.putHabit(TOKEN, domainHabitToNetMapper.invoke(habit)).uid
        } catch (e: UnknownHostException) {
            Log.e(HOST_EXCEPTION, e.message, e)
        } catch (e: retrofit2.HttpException) {
            Log.e(HTTP_EXCEPTION, e.message())
        }

        return uid
    }

    override suspend fun deleteHabit(uid: String?) {
        try {
            apiService.deleteHabit(TOKEN, DeleteHabitRequest(uid))
        } catch (e: UnknownHostException) {
            Log.e(HOST_EXCEPTION, e.message, e)
        } catch (e: retrofit2.HttpException) {
            Log.e(HTTP_EXCEPTION, e.message())
        }
    }

    override suspend fun postHabit(date: Int, uid: String?) {
        try {
            apiService.postHabit(TOKEN, PostHabitRequest(date, uid))
        } catch (e: UnknownHostException) {
            Log.e(HOST_EXCEPTION, e.message, e)
        } catch (e: retrofit2.HttpException) {
            Log.e(HTTP_EXCEPTION, e.message())
        }
    }
}