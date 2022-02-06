package me.lazy_assedninja.demo.common

import org.mockito.ArgumentCaptor
import org.mockito.Mockito

/**
 * a kotlin friendly mock that handles generics
 */
inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

@Suppress("unused")
inline fun <reified T> argumentCaptor(): ArgumentCaptor<T> = ArgumentCaptor.forClass(T::class.java)