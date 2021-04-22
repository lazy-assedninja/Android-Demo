package me.lazy_assedninja.sample.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import me.lazy_assedninja.sample.db.RoomDb
import me.lazy_assedninja.sample.db.UserDao
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

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