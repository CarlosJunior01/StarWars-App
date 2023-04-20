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
class FavoriteFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        launchFragmentInHiltContainer<FavoriteFragment>()
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
    fun shouldShowSearchBar_whenViewIsCreated() {
        Espresso.onView(
            withId(R.id.search_bar_container)
        ).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowTextFavoritePersons_whenViewIsCreated() {
        Espresso.onView(
            withId(R.id.text_favorite_persons)
        ).check(matches(IsNot(isDisplayed())))
    }

    @Test
    fun shouldShowRecyclePersons_whenViewIsCreated() {
        Espresso.onView(
            withId(R.id.recycler_view_persons)
        ).check(matches(IsNot(isDisplayed())))
    }

    @Test
    fun shouldShowTextFavoriteMovies_whenViewIsCreated() {
        Espresso.onView(
            withId(R.id.text_favorite_movies)
        ).check(matches(IsNot(isDisplayed())))
    }

    @Test
    fun shouldShowRecycleMovies_whenViewIsCreated() {
        Espresso.onView(
            withId(R.id.recycler_view_movies)
        ).check(matches(IsNot(isDisplayed())))
    }

    @Test
    fun shouldShowImageBackground_whenViewIsCreated() {
        Espresso.onView(
            withId(R.id.image_background)
        ).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowTextFavoriteEmpty_whenViewIsCreated() {
        Espresso.onView(
            withId(R.id.text_favorite_empty)
        ).check(matches(isDisplayed()))
    }
}