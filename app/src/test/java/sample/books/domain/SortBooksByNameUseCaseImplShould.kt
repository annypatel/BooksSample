package sample.books.domain

import sample.testRxSchedulers
import org.junit.Test
import org.threeten.bp.LocalDate

class SortBooksByNameUseCaseImplShould {

    @Test
    fun sortBooksByName() {
        val bookA = book("A")
        val bookAB = book("AB")
        val bookAC = book("AC")
        val bookB = book("B")
        val bookC = book("C")
        val books = listOf(bookC, bookAB, bookAC, bookA, bookB)

        val sortBooks = SortBooksByNameUseCaseImpl(testRxSchedulers())
        val observer = sortBooks(books)
            .test()

        observer.assertValue(
            listOf(
                BookGroup("A", listOf(bookA, bookAB, bookAC)),
                BookGroup("B", listOf(bookB)),
                BookGroup("C", listOf(bookC))
            )
        )
    }

    private fun book(name: String): Book {
        return Book(
            id = "",
            name = name,
            author = "",
            publishDate = LocalDate.of(2018, 7, 3),
            coverImageUrl = ""
        )
    }
}