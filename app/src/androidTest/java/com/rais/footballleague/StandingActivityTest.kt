package com.rais.footballleague

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule

import com.rais.footballleague.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StandingActivityTest {

    @Rule
    @JvmField var searchActivity = ActivityTestRule(MainActivity::class.java)

    @Test
    fun teamStandingBehaviour() {
        onView(withText("English Premier League")).perform(click())
        onView(withText("Standing")).perform(click())
        onView(ViewMatchers.withId(R.id.rv_standing)).check(matches(
            ViewMatchers.hasDescendant(
                withText("Man United")
            )
        ))

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