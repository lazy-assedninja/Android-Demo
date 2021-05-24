package me.lazy_assedninja.sample.testing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.lazy_assedninja.sample.utils.autoCleared

class TestFragment : Fragment() {
    var testValue by autoCleared<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        testValue = "Lazy-assed Ninja"
        return View(context)
    }
}