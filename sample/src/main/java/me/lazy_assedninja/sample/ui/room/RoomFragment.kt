package me.lazy_assedninja.sample.ui.room

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import me.lazy_assedninja.library.ui.BaseFragment
import me.lazy_assedninja.library.utils.ExecutorUtils
import me.lazy_assedninja.sample.R
import me.lazy_assedninja.sample.binding.FragmentDataBindingComponent
import me.lazy_assedninja.sample.databinding.FragmentRoomBinding
import me.lazy_assedninja.sample.ui.index.MainActivity
import me.lazy_assedninja.sample.utils.autoCleared
import javax.inject.Inject

private const val ARG_USER_ID = "user_id"

private const val TAG_ADD_USER = "add_user"

class RoomFragment : BaseFragment() {

    var binding by autoCleared<FragmentRoomBinding>()
    private var adapter by autoCleared<RoomListAdapter>()

    @Inject
    lateinit var executorUtils: ExecutorUtils

    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: RoomViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Grabs the mainComponent from the Activity and injects this Fragment
        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_room,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { activity ->
            binding.fabAddUser.setOnClickListener {
                UserFragment().apply {
                    show(activity.supportFragmentManager, TAG_ADD_USER)
                }
            }

            adapter = RoomListAdapter(dataBindingComponent, executorUtils, {
                // ClickListener
                UserFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_USER_ID, it.id.toString())
                    }
                    show(activity.supportFragmentManager, TAG_ADD_USER)
                }
            }, {
                // LongClickListener
                AlertDialog.Builder(activity).setTitle(R.string.dialog_delete_user)
                    .setMessage(R.string.dialog_make_sure_to_delete_user)
                    .setPositiveButton(R.string.bt_user_confirm)
                    { _: DialogInterface, _: Int -> viewModel.deleteUsers(it) }
                    .setNegativeButton(R.string.bt_user_cancel, null).show()

            })
            binding.roomList.adapter = adapter
        }

        viewModel.loadAllUsers().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        initNavigationUI()
        initData()
    }

    private fun initNavigationUI() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)
    }

    private fun initData() {
        viewModel.loadAllUsers()
    }
}