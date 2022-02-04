package me.lazy_assedninja.demo.ui.room

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import me.lazy_assedninja.demo.data.Event
import me.lazy_assedninja.demo.data.Status
import me.lazy_assedninja.demo.domain.room.DeleteUser
import me.lazy_assedninja.demo.domain.room.GetUserListLiveData
import me.lazy_assedninja.demo.ui.room.di.RoomScope
import me.lazy_assedninja.demo.vo.User
import javax.inject.Inject

@RoomScope
class RoomViewModel @Inject constructor(
    getUserListLiveData: GetUserListLiveData,
    private val delete: DeleteUser
) : ViewModel() {

    val users: LiveData<List<User>> = getUserListLiveData().map {
        _isLoading.value = true

        if (it.status == Status.ERROR) it.message?.let { message ->
            _snackBarText.value = Event(message)
        }
        _isLoading.value = false
        it.data!!
    }

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _snackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>>
        get() = _snackBarText

    private val _addUser = MutableLiveData<Event<Boolean>>()
    val addUser: LiveData<Event<Boolean>>
        get() = _addUser

    private val _openDetail = MutableLiveData<Event<Long>>()
    val openDetail: LiveData<Event<Long>>
        get() = _openDetail

    private val _showDeleteDialog = MutableLiveData<Event<User>>()
    val showDeleteDialog: LiveData<Event<User>>
        get() = _showDeleteDialog

    fun addUser(isAdd: Boolean) {
        _addUser.value = Event(isAdd)
    }

    fun openDetail(userID: Long) {
        _openDetail.value = Event(userID)
    }

    fun showDeleteDialog(user: User): Boolean {
        _showDeleteDialog.value = Event(user)
        return true
    }

    fun deleteUser(user: User) = viewModelScope.launch {
        delete(user)
    }
}