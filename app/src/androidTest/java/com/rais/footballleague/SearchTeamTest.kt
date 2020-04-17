package com.rais.footballleague

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.rais.footballleague.ui.activities.SearchTeamActivity
import com.rais.footballleague.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SearchTeamTest {

    @Rule
    @JvmField var mainActivity = ActivityTestRule(SearchTeamActivity::class.java)

    @Test
    fun testSearchBehaviour() {
        Espresso.onView(ViewMatchers.withId(R.id.search_action))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.search_action)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.search_src_text))
            .perform(ViewActions.typeText("Man Un"))
        Espresso.onView(ViewMatchers.withId(R.id.rv_search_team))
            .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText("Man United"))))
    }

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingresource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingresource)
    }

}