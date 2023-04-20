package com.carlosjunior.starwarsapp.presentation.fragments

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.carlosjunior.starwarsapp.R
import com.carlosjunior.starwarsapp.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.core.IsNot
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SearchFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        launchFragmentInHiltContainer<SearchFragment>()
    }

    @Test
    fun shouldShowReturnButton_whenViewIsCreated() {
        Espresso.onView(
            withId(R.id.btn_return_button)
        ).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowStarLogo_whenViewIsCreated() {
        Espresso.onView(
            withId(R.id.img_star_wars_logo)
        ).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowButtonPersons_whenViewIsCreated() {
        Espresso.onView(
            withId(R.id.button_persons)
        ).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowButtonMovies_whenViewIsCreated() {
        Espresso.onView(
            withId(R.id.button_movies)
        ).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowSearchBar_whenViewIsCreated() {
        Espresso.onView(
            withId(R.id.search_bar_container)
        ).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowRecycleSearchPersons_whenViewIsCreated() {
        Espresso.onView(
            withId(R.id.recycler_view_search_persons)
        ).check(matches(IsNot(isDisplayed())))
    }

    @Test
    fun shouldShowRecycleSearchMovies_whenViewIsCreated() {
        Espresso.onView(
            withId(R.id.recycler_view_search_movies)
        ).check(matches(IsNot(isDisplayed())))
    }
}