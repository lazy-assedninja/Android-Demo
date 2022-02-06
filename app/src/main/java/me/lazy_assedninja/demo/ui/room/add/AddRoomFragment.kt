package me.lazy_assedninja.demo.ui.room.add

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import me.lazy_assedninja.demo.R
import me.lazy_assedninja.demo.data.EventObserver
import me.lazy_assedninja.demo.databinding.AddRoomFragmentBinding
import me.lazy_assedninja.demo.library.ui.BaseBottomSheetDialogFragment
import me.lazy_assedninja.demo.ui.index.MainActivity
import me.lazy_assedninja.demo.util.autoCleared
import javax.inject.Inject

class AddRoomFragment : BaseBottomSheetDialogFragment() {

    var binding by autoCleared<AddRoomFragmentBinding>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: AddRoomViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Creates an instance of RoomComponent by grabbing the factory from the MainComponent
        // and injects this fragment
        (activity as MainActivity).mainComponent.roomComponent().create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddRoomFragmentBinding.inflate(inflater, container, false).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
    }

    private fun initData() {
        viewModel.checkUserData.observe(viewLifecycleOwner, EventObserver {
            checkUserData()
        })
        viewModel.dismissKeyboard.observe(viewLifecycleOwner, EventObserver {
            dismissKeyboard(view?.windowToken)
        })
        viewModel.insertFinished.observe(viewLifecycleOwner, EventObserver {
            dismiss()
        })
    }

    private fun checkUserData() {
        val name = binding.tilUserName.editText?.text?.toString().let {
            if (it.isNullOrEmpty()) {
                binding.tilUserName.error = getString(
                    R.string.error_user_name_can_not_be_empty
                )
                return@checkUserData
            } else {
                binding.tilUserName.error = null
                it
            }
        }
        val email = binding.tilUserEmail.editText?.text?.toString().let {
            if (it.isNullOrEmpty()) {
                binding.tilUserEmail.error = getString(
                    R.string.error_user_email_can_not_be_empty
                )
                return@checkUserData
            } else {
                binding.tilUserEmail.error = null
                it
            }
        }
        val password = binding.tilUserPassword.editText?.text?.toString().let {
            if (it.isNullOrEmpty()) {
                binding.tilUserPassword.error = getString(
                    R.string.error_user_password_can_not_be_empty
                )
                return@checkUserData
            } else {
                binding.tilUserPassword.error = null
                it
            }
        }
        viewModel.addUser(name, email, password)
    }
}