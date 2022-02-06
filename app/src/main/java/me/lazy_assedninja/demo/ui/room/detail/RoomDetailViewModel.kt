package me.lazy_assedninja.demo.ui.room.detail

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import me.lazy_assedninja.demo.data.Event
import me.lazy_assedninja.demo.data.Status
import me.lazy_assedninja.demo.domain.room.GetUser
import me.lazy_assedninja.demo.domain.room.UpdateUser
import me.lazy_assedninja.demo.ui.room.di.RoomScope
import me.lazy_assedninja.demo.vo.User
import javax.inject.Inject

@RoomScope
class RoomDetailViewModel @Inject constructor(
    private val update: UpdateUser,
    private val getUser: GetUser
) : ViewModel() {

    private val _userID = MutableLiveData<Long>()

    val user: LiveData<User?> = _userID.switchMap {
        _isLoading.value = true

        liveData(viewModelScope.coroutineContext) {
            val userResult = getUser(it)

            if (userResult.status == Status.SUCCESS) {
                emit(userResult.data!!)
            } else {
                userResult.message?.let {
                    _snackBarText.value = Event(it)
                }
            }
            _isLoading.value = false
        }
    }

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _snackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>>
        get() = _snackBarText

    private val _checkUserData = MutableLiveData<Event<Unit>>()
    val checkUserData: LiveData<Event<Unit>>
        get() = _checkUserData

    private val _dismissKeyboard = MutableLiveData<Event<Unit>>()
    val dismissKeyboard: LiveData<Event<Unit>>
        get() = _dismissKeyboard

    private val _updateFinished = MutableLiveData<Event<Unit>>()
    val updateFinished: LiveData<Event<Unit>>
        get() = _updateFinished

    fun setUserID(userID: Long) {
        if (userID != -1L) _userID.value = userID
    }

    fun checkUserData() {
        _checkUserData.value = Event(Unit)
    }

    fun updateUser(name: String, email: String, password: String) {
        _dismissKeyboard.value = Event(Unit)

        _isLoading.value = true
        viewModelScope.launch {
            _userID.value?.let {
                update(User(id = it, name = name, email = email, password = password))

                _isLoading.value = false
                _updateFinished.value = Event(Unit)
            }
        }
    }
}