package me.lazy_assedninja.demo.ui.room.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.lazy_assedninja.demo.data.Event
import me.lazy_assedninja.demo.domain.room.InsertUser
import me.lazy_assedninja.demo.ui.room.di.RoomScope
import me.lazy_assedninja.demo.vo.User
import javax.inject.Inject

@RoomScope
class AddRoomViewModel @Inject constructor(
    private val insert: InsertUser
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _checkUserData = MutableLiveData<Event<Unit>>()
    val checkUserData: LiveData<Event<Unit>>
        get() = _checkUserData

    private val _dismissKeyboard = MutableLiveData<Event<Unit>>()
    val dismissKeyboard: LiveData<Event<Unit>>
        get() = _dismissKeyboard

    private val _insertFinished = MutableLiveData<Event<Unit>>()
    val insertFinished: LiveData<Event<Unit>>
        get() = _insertFinished

    fun checkUserData() {
        _checkUserData.value = Event(Unit)
    }

    fun addUser(name: String, email: String, password: String) {
        _dismissKeyboard.value = Event(Unit)

        _isLoading.value = true
        viewModelScope.launch {
            insert(User(name = name, email = email, password = password))

            _isLoading.value = false
            _insertFinished.value = Event(Unit)
        }
    }
}