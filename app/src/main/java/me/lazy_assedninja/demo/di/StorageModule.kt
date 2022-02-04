package me.lazy_assedninja.demo.di

import dagger.Binds
import dagger.Module
import me.lazy_assedninja.demo.data.source.local.storage.SharedPreferencesStorage
import me.lazy_assedninja.demo.data.source.local.storage.Storage

/**
 * Definition of dependencies, tell Dagger how to provide classes.
 */
@Suppress("unused")
@Module
abstract class StorageModule {

    // Makes Dagger provide SharedPreferencesStorage when a Storage type is requested
    @Binds
    abstract fun provideStorage(storage: SharedPreferencesStorage): Storage
}