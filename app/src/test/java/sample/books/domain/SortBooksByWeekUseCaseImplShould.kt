package sample.books.domain

import sample.testRxSchedulers
import org.junit.Test
import org.threeten.bp.LocalDate

class SortBooksByWeekUseCaseImplShould {

    @Test
    fun sortBooksByWeek() {
        val book11 = book(LocalDate.of(2019, 4, 16))
        val book12 = book(LocalDate.of(2019, 4, 17))
        val book13 = book(LocalDate.of(2019, 4, 18))
        val book2 = book(LocalDate.of(2019, 4, 27))
        val book3 = book(LocalDate.of(2019, 4, 29))
        val books = listOf(book3, book12, book13, book11, book2)

        val sortBooks = SortBooksByWeekUseCaseImpl(testRxSchedulers())
        val observer = sortBooks(books)
            .test()

        observer.assertValue(
            listOf(
                BookGroup("28 Apr, 2019", listOf(book3)),
                BookGroup("21 Apr, 2019", listOf(book2)),
                BookGroup("14 Apr, 2019", listOf(book13, book12, book11))
            )
        )
    }

    private fun book(publishedDate: LocalDate): Book {
        return Book(
            id = "",
            name = "",
            author = "",
            publishDate = publishedDate,
            coverImageUrl = ""
        )
    }
}