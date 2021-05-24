package me.lazy_assedninja.sample.repository

import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class DemoRepositoryTest {

    private val repository = mock(DemoRepository::class.java)

    @Test
    fun testNull() {
        assertThat(repository.loadDemo(), notNullValue())
    }
}