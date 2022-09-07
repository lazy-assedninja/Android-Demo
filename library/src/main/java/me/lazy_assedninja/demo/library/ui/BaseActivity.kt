package me.lazy_assedninja.demo.library.ui

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.os.PersistableBundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

private const val TAG_LIFECYCLE_CALLBACKS = "ActivityLifecycle"

/**
 * A generic AppCompatActivity that provide generic functions.
 */
@Suppress("unused")
abstract class BaseActivity : AppCompatActivity() {

    /**
     * Lifecycle
     */
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        printLifecycleCallbacks("onCreate")
    }

    override fun onStart() {
        super.onStart()

        printLifecycleCallbacks("onStart")
    }

    override fun onResume() {
        super.onResume()

        printLifecycleCallbacks("onResume")
    }

    override fun onPause() {
        super.onPause()

        printLifecycleCallbacks("onPause")
    }

    override fun onStop() {
        super.onStop()

        printLifecycleCallbacks("onStop")
    }


    override fun onRestart() {
        super.onRestart()

        printLifecycleCallbacks("onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()

        printLifecycleCallbacks("onDestroy")
    }

    /**
     * Util
     */
    private fun printLifecycleCallbacks(callbacks: String) = Timber.tag(TAG_LIFECYCLE_CALLBACKS).i("${this.javaClass.simpleName} - $callbacks")

    protected fun dismissKeyboard(windowToken: IBinder?) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }
}