package espresso.fake

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.runner.AndroidJUnit4
import com.geekbrains.tests.R
import com.geekbrains.tests.view.search.FakeMainActivity
import com.geekbrains.tests.view.search.SearchResultAdapter
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityRecycleViewTest {
    private lateinit var scenario: ActivityScenario<FakeMainActivity>
    @Before
    fun setUp(){
        scenario = ActivityScenario.launch(FakeMainActivity::class.java)
    }

    @Test
    fun activity_search_Scroll_to(){
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText("algol"), closeSoftKeyboard())
        onView(withId(R.id.searchEditText)).perform(pressImeActionButton())
        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.scrollTo<SearchResultAdapter.SearchResultViewHolder>(
                    hasDescendant(withText("FullName: 42"))
                )
            )
    }

    @Test
    fun activity_PerformClickAtPosition(){
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText("algol"), closeSoftKeyboard())
        onView(withId(R.id.searchEditText)).perform(pressImeActionButton())
        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<SearchResultAdapter.SearchResultViewHolder>(
                    0,
                    click()
                )
            )
    }
    @Test
    fun activity_PerformOnItemClick(){
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText("algol"), closeSoftKeyboard())
        onView(withId(R.id.searchEditText)).perform(pressImeActionButton())
        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.scrollTo<SearchResultAdapter.SearchResultViewHolder>(
                    hasDescendant(withText("FullName: 50"))
                )
            )
        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItem<SearchResultAdapter.SearchResultViewHolder>(
                   hasDescendant(withText("FullName: 42")),
                    click()
                )
            )
        }

    @After
    fun setOff(){
        scenario.close()
    }
}