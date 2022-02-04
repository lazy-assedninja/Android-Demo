package me.lazy_assedninja.demo.ui.room.di

import dagger.Subcomponent
import me.lazy_assedninja.demo.ui.room.RoomFragment
import me.lazy_assedninja.demo.ui.room.add.AddRoomFragment
import me.lazy_assedninja.demo.ui.room.detail.RoomDetailFragment

/**
 * Components that inherit and extend the object graph of a parent component.
 */
@RoomScope
@Subcomponent(modules = [RoomModule::class])
interface RoomComponent {

    // Factory to create instances of RoomComponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): RoomComponent
    }

    // Classes that can be injected by this Component
    fun inject(roomFragment: RoomFragment)
    fun inject(roomDetailFragment: RoomDetailFragment)
    fun inject(addRoomFragment: AddRoomFragment)
}