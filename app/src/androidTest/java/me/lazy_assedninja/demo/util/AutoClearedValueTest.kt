package me.lazy_assedninja.demo.util

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import me.lazy_assedninja.demo.library.testing.SingleFragmentActivity
import me.lazy_assedninja.demo.library.testing.TestFragment
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AutoClearedValueTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(SingleFragmentActivity::class.java)

    private val testValue = "Lazy-assed Ninja"

    private lateinit var testFragment: TestFragment

    @Before
    fun init() {
        activityScenarioRule.scenario.onActivity {
            testFragment = TestFragment()
            it.setFragment(testFragment)
        }
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
    }

    @Test
    fun clearOnReplace() {
        activityScenarioRule.scenario.onActivity {
            it.replaceFragment(TestFragment())
        }
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        try {
            testFragment.testValue
            Assert.fail("should throw if accessed when not set")
        } catch (exception: IllegalStateException) {
            exception.printStackTrace()
        }
    }

    @Test
    fun clearOnReplaceBackStack() {
        activityScenarioRule.scenario.onActivity {
            it.replaceFragment(TestFragment(), true)
        }
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        try {
            testFragment.testValue
            Assert.fail("should throw if accessed when not set")
        } catch (exception: IllegalStateException) {
            exception.printStackTrace()
        }
        activityScenarioRule.scenario.onActivity {
            it.supportFragmentManager.popBackStack()
        }
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()

        assertThat(testFragment.testValue, `is`(testValue))
    }

    @Test
    fun doNotClearForChildFragment() {
        testFragment.childFragmentManager.beginTransaction().add(Fragment(), testValue).commit()
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()

        assertThat(testFragment.testValue, `is`(testValue))
    }

    @Test
    fun doNotClearForDialog() {
        val dialogFragment = DialogFragment()
        dialogFragment.show(testFragment.parentFragmentManager, "dialog_fragment")
        dialogFragment.dismiss()
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()

        assertThat(testFragment.testValue, `is`(testValue))
    }
}