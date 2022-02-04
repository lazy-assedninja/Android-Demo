package me.lazy_assedninja.demo.ui.room

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import me.lazy_assedninja.demo.R
import me.lazy_assedninja.demo.data.EventObserver
import me.lazy_assedninja.demo.databinding.RoomFragmentBinding
import me.lazy_assedninja.demo.library.ui.BaseFragment
import me.lazy_assedninja.demo.ui.index.MainActivity
import me.lazy_assedninja.demo.ui.room.add.AddRoomFragment
import me.lazy_assedninja.demo.ui.room.detail.RoomDetailFragment
import me.lazy_assedninja.demo.library.util.AppExecutors
import me.lazy_assedninja.demo.util.autoCleared
import javax.inject.Inject

private const val ARG_USER_ID = "user_id"

private const val TAG_ADD_USER = "add_user"
private const val TAG_USER_DETAIL = "user_detail"

class RoomFragment : BaseFragment() {

    var binding by autoCleared<RoomFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: RoomViewModel by viewModels {
        viewModelFactory
    }

    private var adapter by autoCleared<RoomListAdapter>()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Creates an instance of RoomComponent by grabbing the factory from the MainComponent
        // and injects this fragment
        (activity as MainActivity).mainComponent.roomComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RoomFragmentBinding.inflate(inflater, container, false).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initData()
    }

    private fun initView() {
        adapter = RoomListAdapter(appExecutors, viewModel)
        binding.rv.adapter = adapter
    }

    private fun initData() {
        viewModel.snackBarText.observe(viewLifecycleOwner, EventObserver { message ->
            view?.let { view ->
                Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
            }
        })
        viewModel.addUser.observe(viewLifecycleOwner, EventObserver { isAdd ->
            if (isAdd) activity?.supportFragmentManager?.let { supportFragmentManager ->
                AddRoomFragment().apply {
                    show(supportFragmentManager, TAG_ADD_USER)
                }
            }
        })
        viewModel.openDetail.observe(viewLifecycleOwner, EventObserver { userID ->
            activity?.supportFragmentManager?.let { supportFragmentManager ->
                RoomDetailFragment().apply {
                    arguments = Bundle().apply {
                        putLong(ARG_USER_ID, userID)
                    }
                    show(supportFragmentManager, TAG_USER_DETAIL)
                }
            }
        })
        viewModel.showDeleteDialog.observe(viewLifecycleOwner, EventObserver { user ->
            activity?.let { activity ->
                AlertDialog.Builder(activity)
                    .setTitle(R.string.dialog_delete_user)
                    .setMessage(R.string.dialog_make_sure_to_delete_user)
                    .setPositiveButton(R.string.bt_user_confirm) { _: DialogInterface, _: Int ->
                        viewModel.deleteUser(user)
                    }
                    .setNegativeButton(R.string.bt_user_cancel, null)
                    .show()
            }
        })
    }
}