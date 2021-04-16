package me.lazy_assedninja.sample.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import me.lazy_assedninja.library.di.OpenForTesting
import javax.inject.Inject

/**
 * Binding adapters that work with a fragment instance.
 */
@Suppress("unused")
@OpenForTesting
class FragmentBindingAdapters @Inject constructor(private val fragment: Fragment) {

    @BindingAdapter(value = ["imageUrl", "imageRequestListener"], requireAll = false)
    fun bindImage(imageView: ImageView, url: String?, listener: RequestListener<Drawable?>?) {
        Glide.with(fragment).load(url).listener(listener).into(imageView)
    }
}