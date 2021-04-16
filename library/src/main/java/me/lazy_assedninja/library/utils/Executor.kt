package me.lazy_assedninja.library.utils

import java.util.concurrent.Executor

interface Executor {
    fun diskIO(): Executor

    fun networkIO(): Executor

    fun mainThread(): Executor
}