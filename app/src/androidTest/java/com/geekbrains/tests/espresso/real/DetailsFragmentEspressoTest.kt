package com.geekbrains.tests.espresso.real

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.R
import com.geekbrains.tests.TEST_RESULT_NUMBER_PLUS_ONE
import com.geekbrains.tests.view.details.DetailsFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsFragmentEspressoTest {
    private lateinit var scenario :FragmentScenario<DetailsFragment>
    @Before
    fun setUp(){
        scenario = launchFragmentInContainer()
    }

    @Test
    fun test_fragment_bundle(){
        val fragmentArgs = bundleOf("TOTAL_COUNT_EXTRA" to 10)
        val scenario = launchFragmentInContainer<DetailsFragment>(fragmentArgs)
        scenario.moveToState(Lifecycle.State.RESUMED)
        val assertion = matches(withText("Number of results: 10"))
        onView(withId(R.id.totalCountTextView)).check(assertion)
    }

    @Test
    fun test_countMethod(){
        onView(withId(R.id.incrementButton)).perform(click())
        onView(withId(R.id.totalCountTextView)).check(matches(withText(TEST_RESULT_NUMBER_PLUS_ONE)))
    }
}