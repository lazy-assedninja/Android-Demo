package me.lazy_assedninja.demo.library.ui

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import timber.log.Timber

private const val TAG_LIFECYCLE_CALLBACKS = "FragmentLifecycle"

/**
 * A generic Fragment that provide generic functions.
 */
@Suppress("unused")
abstract class BaseFragment : Fragment() {

    /**
     * Lifecycle
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        printLifecycleCallbacks("onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        printLifecycleCallbacks("onCreateView")

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        printLifecycleCallbacks("onViewCreated")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        printLifecycleCallbacks("onViewStateRestored")
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        printLifecycleCallbacks("onSaveInstanceState")
    }

    override fun onDestroyView() {
        super.onDestroyView()

        printLifecycleCallbacks("onDestroyView")
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
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }
}