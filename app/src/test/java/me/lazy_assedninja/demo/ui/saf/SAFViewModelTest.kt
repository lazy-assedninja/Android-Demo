package me.lazy_assedninja.demo.ui.saf

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.lazy_assedninja.demo.util.LiveDataUtil.getValue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SAFViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private var viewModel = SAFViewModel()

    @Test
    fun testNull() {
        assertThat(viewModel.chooseFile, notNullValue())
        assertThat(viewModel.chooseFolder, notNullValue())
    }

    @Test
    fun chooseFile() {
        viewModel.chooseFile()

        assertThat(getValue(viewModel.chooseFile).peekContent(), `is`(true))
    }

    @Test
    fun chooseFolder() {
        viewModel.chooseFolder()

        assertThat(getValue(viewModel.chooseFolder).peekContent(), `is`(true))
    }
}