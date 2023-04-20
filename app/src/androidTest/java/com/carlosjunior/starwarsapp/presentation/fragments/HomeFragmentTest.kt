package com.carlosjunior.starwarsapp.presentation.fragments

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.repeatedlyUntil
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.carlosjunior.starwarsapp.R
import com.carlosjunior.starwarsapp.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        launchFragmentInHiltContainer<HomeFragment>()
    }

    @Test
    fun shouldShowInitialScreen_whenViewIsCreated() {
        Espresso.onView(
            withId(R.id.view_flipper_fragment)
        ).perform(repeatedlyUntil(swipeUp(), hasDescendant(withId(R.id.initial_screen)), 2))
            .check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowHomeScreen_whenViewIsCreated() {
        Espresso.onView(
            withId(R.id.view_flipper_fragment)
        ).perform(repeatedlyUntil(swipeUp(), hasDescendant(withId(R.id.home_screen)), 2))
            .check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowErrorScreen_whenViewIsCreated() {
        Espresso.onView(
            withId(R.id.view_flipper_fragment)
        ).perform(repeatedlyUntil(swipeUp(), hasDescendant(withId(R.id.error_screen)), 2))
            .check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowAnimationView_whenViewIsCreated() {
        Espresso.onView(
            withId(R.id.view_flipper_fragment)
        ).perform(repeatedlyUntil(swipeUp(), hasDescendant(withId(R.id.animation_view)), 2))
            .check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowImageStarWarsIntroLogo_whenViewIsCreated() {
        Espresso.onView(
            withId(R.id.view_flipper_fragment)
        ).perform(repeatedlyUntil(swipeUp(), hasDescendant(withId(R.id.img_star_wars_logo)), 2))
            .check(matches(isDisplayed()))
    }
}