package me.lazy_assedninja.demo.library.ui

import android.content.Context
import android.content.DialogInterface
import android.os.IBinder
import android.view.inputmethod.InputMethodManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * A generic BottomSheetDialogFragment that provide generic functions.
 */
@Suppress("unused")
abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    protected fun dismissKeyboard(windowToken: IBinder?) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        dismissKeyboard(view?.windowToken)
    }
}