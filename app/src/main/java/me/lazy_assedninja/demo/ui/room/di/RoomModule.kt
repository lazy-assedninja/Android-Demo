package me.lazy_assedninja.demo.ui.room.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.lazy_assedninja.demo.di.ViewModelKey
import me.lazy_assedninja.demo.ui.room.RoomViewModel
import me.lazy_assedninja.demo.ui.room.add.AddRoomViewModel
import me.lazy_assedninja.demo.ui.room.detail.RoomDetailViewModel

/**
 * Definition of dependencies, tell Dagger how to provide classes.
 */
@Suppress("unused")
@Module
abstract class RoomModule {

    @Binds
    @IntoMap
    @ViewModelKey(RoomViewModel::class)
    abstract fun bindRoomViewModel(roomViewModel: RoomViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RoomDetailViewModel::class)
    abstract fun bindRoomDetailViewModel(roomDetailViewModel: RoomDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddRoomViewModel::class)
    abstract fun bindAddRoomViewModel(addRoomViewModel: AddRoomViewModel): ViewModel
}