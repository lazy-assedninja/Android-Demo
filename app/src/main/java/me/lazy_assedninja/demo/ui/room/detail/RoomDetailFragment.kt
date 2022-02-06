package me.lazy_assedninja.demo.ui.room.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import me.lazy_assedninja.demo.R
import me.lazy_assedninja.demo.data.EventObserver
import me.lazy_assedninja.demo.databinding.RoomDetailFragmentBinding
import me.lazy_assedninja.demo.library.ui.BaseBottomSheetDialogFragment
import me.lazy_assedninja.demo.ui.index.MainActivity
import me.lazy_assedninja.demo.util.autoCleared
import javax.inject.Inject

private const val ARG_USER_ID = "user_id"

class RoomDetailFragment : BaseBottomSheetDialogFragment() {

    var binding by autoCleared<RoomDetailFragmentBinding>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: RoomDetailViewModel by viewModels {
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
        binding = RoomDetailFragmentBinding.inflate(inflater, container, false).apply {
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
        viewModel.setUserID(arguments?.getLong(ARG_USER_ID) ?: -1L)

        viewModel.snackBarText.observe(viewLifecycleOwner, EventObserver { message ->
            view?.let { view ->
                Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
            }
        })
        viewModel.checkUserData.observe(viewLifecycleOwner, EventObserver {
            checkUserData()
        })
        viewModel.dismissKeyboard.observe(viewLifecycleOwner, EventObserver {
            dismissKeyboard(view?.windowToken)
        })
        viewModel.updateFinished.observe(viewLifecycleOwner, EventObserver {
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
        viewModel.updateUser(name, email, password)
    }
}