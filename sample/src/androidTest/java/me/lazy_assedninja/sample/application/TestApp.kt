package me.lazy_assedninja.sample.application

import android.app.Application

/**
 * We use a separate App for tests to prevent initializing dependency injection.
 *
 * See [me.lazy_assedninja.sample.utils.DemoTestRunner].
 */
class TestApplication : Application()
