package me.lazy_assedninja.sample.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import me.lazy_assedninja.sample.api.RetrofitService
import me.lazy_assedninja.sample.db.RoomDb
import me.lazy_assedninja.sample.db.UserDao
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val URI_YOU_BIKE = "https://tcgbusfs.blob.core.windows.net/"

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofitService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(URI_YOU_BIKE)
            .addConverterFactory(GsonConverterFactory.create())
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
}