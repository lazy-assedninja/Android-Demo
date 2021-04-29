package me.lazy_assedninja.sample.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.lazy_assedninja.sample.ui.demo_list.DemoViewModel
import me.lazy_assedninja.sample.ui.documents_provider.DocumentsProviderViewModel
import me.lazy_assedninja.sample.ui.retrofit.RetrofitViewModel
import me.lazy_assedninja.sample.ui.room.RoomViewModel
import me.lazy_assedninja.sample.ui.saf.DirectoryViewModel
import me.lazy_assedninja.sample.view_model.ViewModelFactory

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DemoViewModel::class)
    abstract fun bindDemoViewModel(demoViewModel: DemoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DirectoryViewModel::class)
    abstract fun bindDirectoryViewModel(directoryViewModel: DirectoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RoomViewModel::class)
    abstract fun bindRoomViewModel(roomViewModel: RoomViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DocumentsProviderViewModel::class)
    abstract fun bindDocumentsProviderViewModel(documentsProviderViewModel: DocumentsProviderViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RetrofitViewModel::class)
    abstract fun bindRetrofitViewModel(retrofitViewModel: RetrofitViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}