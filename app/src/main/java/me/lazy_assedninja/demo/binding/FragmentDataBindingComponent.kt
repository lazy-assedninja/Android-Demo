package me.lazy_assedninja.demo.binding

import androidx.databinding.DataBindingComponent
import androidx.fragment.app.Fragment

/**
 * A Data Binding Component implementation for fragments.
 */
@Suppress("unused")
class FragmentDataBindingComponent(fragment: Fragment) : DataBindingComponent {

    private val adapter = FragmentBindingAdapters(fragment)

    override fun getFragmentBindingAdapters() = adapter
}