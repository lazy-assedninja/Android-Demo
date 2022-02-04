package me.lazy_assedninja.demo.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.documentfile.provider.DocumentFile
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import me.lazy_assedninja.demo.ui.demo.DemoListAdapter
import me.lazy_assedninja.demo.ui.retrofit.RetrofitListAdapter
import me.lazy_assedninja.demo.ui.room.RoomListAdapter
import me.lazy_assedninja.demo.ui.saf.directory.DirectoryListAdapter
import me.lazy_assedninja.demo.vo.User
import me.lazy_assedninja.demo.vo.YouBike

/**
 * Data Binding adapters specific to the app.
 */
@Suppress("unused")
object BindingAdapters {

    @JvmStatic
    @BindingAdapter("show_or_hide")
    fun showOrHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("demo_items")
    fun setDemoItems(listView: RecyclerView, items: List<String>?) {
        (listView.adapter as DemoListAdapter).submitList(items)
    }

    @JvmStatic
    @BindingAdapter("directory_items")
    fun setDirectoryItems(listView: RecyclerView, items: List<DocumentFile>?) {
        (listView.adapter as DirectoryListAdapter).submitList(items)
    }

    @JvmStatic
    @BindingAdapter("room_items")
    fun setRoomItems(listView: RecyclerView, items: List<User>?) {
        (listView.adapter as RoomListAdapter).submitList(items)
    }

    @JvmStatic
    @BindingAdapter("retrofit_items")
    fun setRetrofitItems(listView: RecyclerView, items: List<YouBike>?) {
        (listView.adapter as RetrofitListAdapter).submitList(items)
    }

    @JvmStatic
    @BindingAdapter("switch_status")
    fun setSwitchStatus(switchMaterial: SwitchMaterial, isChecked: Boolean) {
        switchMaterial.isChecked = isChecked
    }
}