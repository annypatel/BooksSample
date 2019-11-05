package sample.books.ui

import androidx.annotation.StringRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import sample.books.R

inline fun books(init: BooksRobot.() -> Unit) = BooksRobot().apply(init)

/**
 * Test robot for books screen.
 */
class BooksRobot {

    fun sortByName() {
        onView(withId(R.id.sortByMenu)).perform(click())
        onView(withText(R.string.menu_sort_by_name)).perform(click())
    }

    fun sortByWeek() {
        onView(withId(R.id.sortByMenu)).perform(click())
        onView(withText(R.string.menu_sort_by_week)).perform(click())
    }

    fun headerShouldBe(header: String) {
        onView(withText(header)).check(matches(isDisplayed()))
    }

    fun bookNameShouldBe(name: String) {
        onView(withText(name)).check(matches(isDisplayed()))
    }

    fun bookAuthorShouldBe(author: String) {
        onView(withText(author)).check(matches(isDisplayed()))
    }

    fun errorMessageShouldBe(@StringRes message: Int) {
        onView(withText(message)).check(matches(isDisplayed()))
    }
}