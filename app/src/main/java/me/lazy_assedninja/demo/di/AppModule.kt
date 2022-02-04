package me.lazy_assedninja.demo.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import me.lazy_assedninja.demo.data.source.local.db.RoomDb
import me.lazy_assedninja.demo.data.source.local.db.UserDao
import me.lazy_assedninja.demo.data.source.local.db.YouBikeDao
import me.lazy_assedninja.demo.data.source.remote.RetrofitService
import me.lazy_assedninja.demo.util.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val URI_YOU_BIKE = "https://tcgbusfs.blob.core.windows.net/"

/**
 * Definition of dependencies, tell Dagger how to provide classes.
 */
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofitService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(URI_YOU_BIKE)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient()
                    .newBuilder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .build()
            )
            .build()
            .create(RetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(context: Context): RoomDb {
        return Room
            .databaseBuilder(context, RoomDb::class.java, "room.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(roomDatabase: RoomDb): UserDao {
        return roomDatabase.userDao()
    }

    @Singleton
    @Provides
    fun provideYouBikeDao(roomDatabase: RoomDb): YouBikeDao {
        return roomDatabase.youBikeDao()
    }
}