package sample.books.ui

import android.content.Intent
import androidx.test.rule.ActivityTestRule
import sample.books.R
import sample.books.data.network.BooksApi
import sample.books.domain.Book
import sample.dagger.TestAppComponentRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.LocalDate

class BooksActivityShould {

    private val booksApi = mock<BooksApi>()
    @get:Rule
    val componentRule = TestAppComponentRule(booksApi)
    @get:Rule
    val activityRule = ActivityTestRule(BooksActivity::class.java, false, false)

    @Test
    fun displayBookGroupedByName_whenGetBooksIsSuccessful() {
        val book = book()
        givenASuccessfulBooksApiCall(listOf(book))
        activityRule.launchActivity(Intent())

        books {
            sortByName()

            headerShouldBe("E")
            bookNameShouldBe(book.name)
            bookAuthorShouldBe(book.author)
        }
    }

    @Test
    fun displayBookGroupedByWeek_whenGetBooksIsSuccessful() {
        val book = book()
        givenASuccessfulBooksApiCall(listOf(book))
        activityRule.launchActivity(Intent())

        books {
            sortByWeek()

            headerShouldBe("Jul 1, 2018")
            bookNameShouldBe(book.name)
            bookAuthorShouldBe(book.author)
        }
    }

    @Test
    fun displayErrorToast_whenGetBooksIsUnsuccessful() {
        givenAnUnsuccessfulBooksApiCall(Throwable())
        activityRule.launchActivity(Intent())

        books {
            errorMessageShouldBe(R.string.error_generic)
        }
    }

    private fun givenASuccessfulBooksApiCall(result: List<Book>) {
        whenever(booksApi.getAllBooks()).thenReturn(Single.just(result))
    }

    private fun givenAnUnsuccessfulBooksApiCall(exception: Throwable) {
        whenever(booksApi.getAllBooks()).thenReturn(Single.error(exception))
    }

    private fun book(): Book {
        return Book(
            id = "",
            name = "Eat, Move, Sleep",
            author = "Tom Rath",
            publishDate = LocalDate.of(2018, 7, 3),
            coverImageUrl = "http://examp.le"
        )
    }
}