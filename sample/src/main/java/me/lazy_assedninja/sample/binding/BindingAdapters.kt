package me.lazy_assedninja.sample.binding

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Data Binding adapters specific to the app.
 */
@Suppress("unused")
object BindingAdapters {

    @JvmStatic
    @BindingAdapter("showOrHide")
    fun showOrHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }
}
