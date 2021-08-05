package me.lazy_assedninja.library.utils

import android.os.Handler
import android.os.Looper
import me.lazy_assedninja.library.testing.OpenForTesting
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Global executor pools for the whole application.
 *
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 */
@Suppress("unused")
@Singleton
@OpenForTesting
class ExecutorUtils(
    private val diskIO: java.util.concurrent.Executor,
    private val networkIO: java.util.concurrent.Executor,
    private val mainThread: java.util.concurrent.Executor
) : Executor {

    @Inject
    constructor() : this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(3),
        MainThreadExecutor()
    )

    override fun diskIO(): java.util.concurrent.Executor {
        return diskIO
    }

    override fun networkIO(): java.util.concurrent.Executor {
        return networkIO
    }

    override fun mainThread(): java.util.concurrent.Executor {
        return mainThread
    }

    private class MainThreadExecutor : java.util.concurrent.Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}