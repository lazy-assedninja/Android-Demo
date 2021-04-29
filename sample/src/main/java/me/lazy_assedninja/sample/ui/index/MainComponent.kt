package me.lazy_assedninja.sample.ui.index

import dagger.Subcomponent
import me.lazy_assedninja.sample.content_provider.MyDocumentsProvider
import me.lazy_assedninja.sample.di.ActivityScope
import me.lazy_assedninja.sample.ui.demo_list.DemoFragment
import me.lazy_assedninja.sample.ui.documents_provider.DocumentsProviderFragment
import me.lazy_assedninja.sample.ui.retrofit.RetrofitDetailFragment
import me.lazy_assedninja.sample.ui.retrofit.RetrofitFragment
import me.lazy_assedninja.sample.ui.room.UserFragment
import me.lazy_assedninja.sample.ui.room.RoomFragment
import me.lazy_assedninja.sample.ui.saf.DirectoryFragment
import me.lazy_assedninja.sample.ui.saf.SAFFragment

// Scope annotation that the LoginComponent uses Classes annotated with @ActivityScope
// will have a unique instance in this Component
@ActivityScope
// Definition of a Dagger SubComponent
@Subcomponent
interface MainComponent {

    // Factory to create instances of MainComponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

    // Classes that can be injected by this Component
    fun inject(documentsProvider: MyDocumentsProvider)
    fun inject(activity: MainActivity)
    fun inject(demoFragment: DemoFragment)
    fun inject(safFragment: SAFFragment)
    fun inject(directoryFragment: DirectoryFragment)
    fun inject(roomFragment: RoomFragment)
    fun inject(userFragment: UserFragment)
    fun inject(documentsProviderFragment: DocumentsProviderFragment)
    fun inject(retrofitFragment: RetrofitFragment)
    fun inject(retrofitDetailFragment: RetrofitDetailFragment)
}