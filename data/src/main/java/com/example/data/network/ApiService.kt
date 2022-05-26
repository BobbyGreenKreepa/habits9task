package com.example.data.network

import com.example.data.HabitNet
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {

    @GET(NetworkRepositoryImpl.PATH)
    suspend fun getHabits(
        @Header(NetworkRepositoryImpl.HEADER)
        token: String
    ): List<HabitNet>

    @PUT(NetworkRepositoryImpl.PATH)
    suspend fun putHabit(
        @Header(NetworkRepositoryImpl.HEADER)
        token:String,
        @Body habit: HabitNet
    ): PutHabitResponse

    @HTTP(method = "DELETE", path = NetworkRepositoryImpl.PATH, hasBody = true)
    suspend fun deleteHabit(
        @Header(NetworkRepositoryImpl.HEADER)
        token: String,
        @Body
        request: DeleteHabitRequest
    )

    @POST(NetworkRepositoryImpl.HABIT_DONE_PATH)
    suspend fun postHabit(
        @Header(NetworkRepositoryImpl.HEADER) token:String,
        @Body postHabitRequest: PostHabitRequest
    )
}