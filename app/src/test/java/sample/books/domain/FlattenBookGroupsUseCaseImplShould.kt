package sample.books.domain

import sample.books.domain.BookListItem.BookItem
import sample.books.domain.BookListItem.HeaderItem
import sample.testRxSchedulers
import org.junit.Test
import org.threeten.bp.LocalDate

class FlattenBookGroupsUseCaseImplShould {

    @Test
    fun flattenBookGroups() {
        val group1 = BookGroup("A", listOf(book("A"), book("AA")))
        val group2 = BookGroup("B", listOf(book("B"), book("BB")))
        val flattenBookGroups = FlattenBookGroupsUseCaseImpl(testRxSchedulers())

        val observer = flattenBookGroups(listOf(group1, group2))
            .test()

        observer.assertValue(
            listOf(
                HeaderItem(group1.name),
                BookItem(group1.books[0]),
                BookItem(group1.books[1]),
                HeaderItem(group2.name),
                BookItem(group2.books[0]),
                BookItem(group2.books[1])
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