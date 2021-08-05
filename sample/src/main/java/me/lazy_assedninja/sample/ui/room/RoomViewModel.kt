package me.lazy_assedninja.sample.ui.room

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import me.lazy_assedninja.sample.di.IODispatcher
import me.lazy_assedninja.sample.repository.RoomRepository
import me.lazy_assedninja.sample.vo.User
import javax.inject.Inject

class RoomViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val isLoading = ObservableBoolean(false)

    val userID: MutableLiveData<Long> = MutableLiveData()

    fun insertUsers(vararg users: User) {
        viewModelScope.launch(dispatcher) {
            roomRepository.insertUsers(*users)
        }
    }

    fun updateUsers(vararg users: User) {
        viewModelScope.launch(dispatcher) {
            roomRepository.updateUsers(*users)
        }
    }

    fun deleteUsers(vararg users: User) {
        viewModelScope.launch(dispatcher) {
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