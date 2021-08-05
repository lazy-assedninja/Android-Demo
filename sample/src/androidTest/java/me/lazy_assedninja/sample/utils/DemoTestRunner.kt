package me.lazy_assedninja.sample.utils

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import me.lazy_assedninja.sample.application.TestApplication

/**
 * Custom runner to disable dependency injection.
 */
class DemoTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, TestApplication::class.java.name, context)
    }
}