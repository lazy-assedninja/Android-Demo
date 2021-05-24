package me.lazy_assedninja.sample.ui.room

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import me.lazy_assedninja.library.ui.BaseBottomSheetDialogFragment
import me.lazy_assedninja.sample.R
import me.lazy_assedninja.sample.binding.FragmentDataBindingComponent
import me.lazy_assedninja.sample.databinding.FragmentUserBinding
import me.lazy_assedninja.sample.ui.index.MainActivity
import me.lazy_assedninja.sample.utils.autoCleared
import me.lazy_assedninja.sample.vo.User
import javax.inject.Inject

private const val ARG_USER_ID = "user_id"

class UserFragment : BaseBottomSheetDialogFragment() {

    var binding by autoCleared<FragmentUserBinding>()

    private var dataBindingComponent = FragmentDataBindingComponent(this)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: RoomViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var userID: String

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Obtaining the login graph from MainActivity and instantiate
        // the @Inject fields with objects from the graph
        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user,
            container,
            false,
            dataBindingComponent
        )
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userID = arguments?.getString(ARG_USER_ID)
            ?: "null"

        context?.let { context ->
            binding.toolbar.title =
                if (userID == "null") context.getString(R.string.title_add_user)
                else context.getString(R.string.title_update_user)
            binding.btConfirm.setOnClickListener {
                handleData(it)
            }
        }

        initData()
    }

    private fun initData() {
        viewModel.isLoading.set(true)

        if (userID != "null") {
            viewModel.userID.postValue(userID.toLong())
            viewModel.getUser().observe(viewLifecycleOwner) {
                binding.user = it
                viewModel.isLoading.set(false)
            }
        } else {
            viewModel.isLoading.set(false)
        }
    }

    private fun handleData(view: View) {
        dismissKeyboard(view.windowToken)

        var name = ""
        var email = ""
        var password = ""
        context?.let { context ->
            binding.tilUserName.editText?.let { it ->
                if (it.text.isEmpty()) {
                    binding.tilUserName.error =
                        context.getString(R.string.error_user_name_can_not_be_empty)
                    viewModel.isLoading.set(false)
                    return
                } else {
                    binding.tilUserName.error = null
                    name = it.text.toString()
                }
            }
            binding.tilUserEmail.editText?.let { it ->
                if (it.text.isEmpty()) {
                    binding.tilUserEmail.error =
                        context.getString(R.string.error_user_email_can_not_be_empty)
                    viewModel.isLoading.set(false)
                    return
                } else {
                    binding.tilUserEmail.error = null
                    email = it.text.toString()
                }
            }
            binding.tilUserPassword.editText?.let { it ->
                if (it.text.isEmpty()) {
                    binding.tilUserPassword.error =
                        context.getString(R.string.error_user_password_can_not_be_empty)
                    viewModel.isLoading.set(false)
                    return
                } else {
                    binding.tilUserPassword.error = null
                    password = it.text.toString()
                }
            }
        }

        // Return result
        if (userID == "null") {
            viewModel.insertUsers(User(name = name, email = email, password = password))
        } else {
            viewModel.updateUsers(
                User(
                    id = userID.toLong(),
                    name = name,
                    email = email,
                    password = password
                )
            )
        }

        dismiss()
    }
}