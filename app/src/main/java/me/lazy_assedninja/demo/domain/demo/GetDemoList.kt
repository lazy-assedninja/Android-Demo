package me.lazy_assedninja.demo.domain.demo

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import me.lazy_assedninja.demo.data.Resource
import me.lazy_assedninja.demo.data.repository.DemoRepository
import me.lazy_assedninja.demo.di.IODispatcher
import me.lazy_assedninja.demo.library.test.OpenForTesting
import me.lazy_assedninja.demo.ui.demo.di.DemoScope
import javax.inject.Inject

@OpenForTesting
@DemoScope
class GetDemoList @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val demoRepository: DemoRepository
) {

    suspend operator fun invoke(): Resource<List<String>> = withContext(dispatcher) {
        return@withContext Resource.success(demoRepository.getDemoList())
    }
}