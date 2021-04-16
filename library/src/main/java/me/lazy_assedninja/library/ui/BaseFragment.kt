package me.lazy_assedninja.library.ui

import android.content.Context
import android.os.IBinder
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    protected fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }
}