package com.rais.footballleague

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.rais.footballleague.ui.activities.SearchMatchActivity
import com.rais.footballleague.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchActivityTest {

    @Rule
    @JvmField var searchActivity = ActivityTestRule(SearchMatchActivity::class.java)

    @Test
    fun testSearchBehaviour() {
        onView(withId(R.id.search_action)).check(matches(isDisplayed()))
        onView(withId(R.id.search_action)).perform(click())
        onView(withId(R.id.search_src_text)).perform(typeText("Man Un"))
        onView(withId(R.id.rv_search_match)).check(matches(hasDescendant(withText("Man United"))))
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