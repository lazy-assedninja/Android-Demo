package me.lazy_assedninja.demo.domain.room

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import me.lazy_assedninja.demo.data.repository.RoomRepository
import me.lazy_assedninja.demo.di.IODispatcher
import me.lazy_assedninja.demo.library.test.OpenForTesting
import me.lazy_assedninja.demo.ui.room.di.RoomScope
import me.lazy_assedninja.demo.vo.User
import javax.inject.Inject

@OpenForTesting
@RoomScope
class UpdateUser @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val roomRepository: RoomRepository
) {

    suspend operator fun invoke(params: User) = withContext(dispatcher) {
        roomRepository.update(params)
    }
}