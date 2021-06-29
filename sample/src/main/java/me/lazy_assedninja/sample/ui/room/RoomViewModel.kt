package me.lazy_assedninja.sample.ui.room

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.lazy_assedninja.sample.repository.RoomRepository
import me.lazy_assedninja.sample.vo.User
import javax.inject.Inject

class RoomViewModel @Inject constructor(private val roomRepository: RoomRepository) : ViewModel() {

    val isLoading = ObservableBoolean(false)

    val userID: MutableLiveData<Long> = MutableLiveData()

    fun insertUsers(vararg users: User) {
        CoroutineScope(Dispatchers.IO).launch {
            roomRepository.insertUsers(*users)
        }

    }

    fun updateUsers(vararg users: User) {
        CoroutineScope(Dispatchers.IO).launch {
            roomRepository.updateUsers(*users)
        }

    }

    fun deleteUsers(vararg users: User) {
        CoroutineScope(Dispatchers.IO).launch {
            roomRepository.deleteUsers(*users)
        }
    }

    fun getUser(): LiveData<User> = userID.switchMap {
        roomRepository.getUser(it)
    }

    fun loadAllUsers(): LiveData<List<User>> {
        isLoading.set(true)
        return roomRepository.loadAllUsers()
    }
}