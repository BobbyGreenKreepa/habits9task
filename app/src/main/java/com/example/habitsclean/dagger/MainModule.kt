package com.example.habitsclean.dagger

import android.content.Context
import androidx.room.Room
import com.example.data.database.Database
import com.example.data.network.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class MainModule {

    companion object {

        const val DATABASE_NAME = "database"
        const val BASE_URL = "https://droid-test-server.doubletapp.ru/"
    }

    @Provides
    fun providesApiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor{ chain ->

                val request: Request = chain.request()
                var response = chain.proceed(request)
                var connectionTryCounter = 0

                while (!(response.isSuccessful) && connectionTryCounter < 5) {
                    connectionTryCounter++
                    response = chain.proceed(request)
                }
                response
            }
            .addInterceptor(httpLoggingInterceptor)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideDataBase(context: Context): Database {
        return Room.databaseBuilder(
            context,
            Database::class.java, DATABASE_NAME
        ).build()
    }
}