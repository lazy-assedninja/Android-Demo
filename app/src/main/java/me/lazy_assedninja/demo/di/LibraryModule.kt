package me.lazy_assedninja.demo.di

import dagger.Module
import dagger.Provides
import me.lazy_assedninja.demo.library.util.AppExecutors
import javax.inject.Singleton

/**
 * Definition of dependencies, tell Dagger how to provide classes.
 */
@Suppress("unused")
@Module
class LibraryModule {

    @Singleton
    @Provides
    fun provideAppExecutor(): AppExecutors {
        return AppExecutors()
    }
}