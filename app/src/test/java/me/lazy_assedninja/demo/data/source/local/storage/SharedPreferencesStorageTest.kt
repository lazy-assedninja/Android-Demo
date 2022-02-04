package me.lazy_assedninja.demo.data.source.local.storage

import android.content.Context
import android.content.SharedPreferences
import me.lazy_assedninja.demo.common.mock
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class SharedPreferencesStorageTest {

    private val context = mock<Context>()
    private val sharedPreferences = mock<SharedPreferences>()
    private val sharedPreferencesEditor = mock<SharedPreferences.Editor>()

    private lateinit var storage: SharedPreferencesStorage

    @Before
    fun init() {
        `when`(context.getSharedPreferences(anyString(), anyInt()))
            .thenReturn(sharedPreferences)
        `when`(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)
        storage = SharedPreferencesStorage(context)
    }

    @Test
    fun setString() {
        `when`(sharedPreferencesEditor.putString(anyString(), anyString()))
            .thenReturn(sharedPreferencesEditor)
        val key = "user_name"
        val data = "Lazy-assed Ninja"
        storage.setString(key, data)

        verify(sharedPreferencesEditor).putString(key, data)
        verify(sharedPreferencesEditor).apply()
    }

    @Test
    fun getString() {
        val key = "user_name"
        storage.getString(key, "")

        verify(sharedPreferences).getString(key, "")
    }
}