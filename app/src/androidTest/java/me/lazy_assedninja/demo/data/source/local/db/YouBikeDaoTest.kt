package me.lazy_assedninja.demo.data.source.local.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.lazy_assedninja.demo.common.TestUtil.createYouBike
import me.lazy_assedninja.demo.common.getOrAwaitValue
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class YouBikeDaoTest : DbTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val youBike = createYouBike(
        1,
        "500101001",
        "YouBike2.0_捷運科技大樓站",
        "28",
        "17",
        "大安區",
        "2000-00-00 00:00:00",
        "25.02605",
        "121.5436",
        "復興南路二段235號前",
        "6",
        "1",
        "2000-00-00 00:00:00",
        "2000-00-00 00:00:00",
        "2000-00-00 00:00:00",
        "2000-00-00 00:00:00"
    )
    private val newYouBike = createYouBike(
        1,
        "500101001",
        "YouBike2.0_捷運科技大樓站",
        "28",
        "17",
        "大安區",
        "2000-00-00 00:00:00",
        "25.02605",
        "121.5436",
        "復興南路二段235號前",
        "6",
        "1",
        "2000-00-00 00:00:00",
        "2000-00-00 00:00:00",
        "2000-00-00 00:00:00",
        "2000-00-00 00:00:00"
    )

    @Before
    fun init() {
        db.youBikeDao().insertAll(listOf(youBike))
    }

    @Test
    fun insertAllAndGetLiveDataOfYouBikes() {
        val loaded = db.youBikeDao().observerYouBikes().getOrAwaitValue()
        assertThat(loaded.size, `is`(1))
        assertThat(loaded[0], `is`(youBike))
    }

    @Test
    fun replaceWhenInsertOnConflictAndGetLiveDataOfYouBikes() {
        db.youBikeDao().insertAll(listOf(newYouBike))

        val loaded = db.youBikeDao().observerYouBikes().getOrAwaitValue()
        assertThat(loaded.size, `is`(1))
        assertThat(loaded[0], `is`(youBike))
    }

    @Test
    fun deleteAllAndGetLiveDataOfYouBikes() {
        db.youBikeDao().deleteAll()

        val loaded = db.youBikeDao().observerYouBikes().getOrAwaitValue()
        assertThat(loaded.size, `is`(0))
    }
}