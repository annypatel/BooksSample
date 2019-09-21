package sample

import androidx.annotation.StringRes
import sample.base.ui.Error
import sample.base.ui.Result
import sample.base.ui.Start
import sample.base.ui.Value
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

/**
 * Matcher for checking [result][Result] is [value][Value] or not.
 */
fun <T, R : Result<T>> isValue(value: T): Matcher<in R?> {
    val matcher = object : TypeSafeMatcher<Value<T>>() {

        override fun describeTo(description: Description) {
            description.appendText("Value")
                .appendValue(value)
        }

        override fun matchesSafely(item: Value<T>): Boolean {
            return item.value == value
        }
    }

    @Suppress("UNCHECKED_CAST")
    return matcher as Matcher<R?>
}

/**
 * Matcher for checking [result][Result] is [error][Error] or not.
 */
fun <T, R : Result<T>> isError(@StringRes message: Int): Matcher<R?> {
    val matcher = object : TypeSafeMatcher<Error<T>>() {

        override fun describeTo(description: Description) {
            description.appendText("Error")
                .appendValue(message)
        }

        override fun matchesSafely(item: Error<T>): Boolean {
            return item.message == message
        }
    }
    @Suppress("UNCHECKED_CAST")
    return matcher as Matcher<R?>
}

/**
 * Matcher for checking [result][Result] is [Start] or not.
 */
fun <R : Result<*>> isStart(): Matcher<in R?> = instanceOf(Start::class.java)