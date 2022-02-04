package me.lazy_assedninja.demo.domain.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import me.lazy_assedninja.demo.data.Resource
import me.lazy_assedninja.demo.data.repository.RoomRepository
import me.lazy_assedninja.demo.library.test.OpenForTesting
import me.lazy_assedninja.demo.ui.room.di.RoomScope
import me.lazy_assedninja.demo.vo.User
import javax.inject.Inject

@OpenForTesting
@RoomScope
class GetUserListLiveData @Inject constructor(
    private val roomRepository: RoomRepository
) {

    operator fun invoke(): LiveData<Resource<List<User>>> {
        return try {
            roomRepository.observerUsers().map {
                if (it.isEmpty()) {
                    Resource.error("No data.", emptyList())
                } else {
                    Resource.success(it)
                }
            }
        } catch (e: Exception) {
            MutableLiveData(Resource.error(e.toString(), emptyList()))
        }
    }
}