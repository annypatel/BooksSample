package sample.books.domain

/**
 * Represents a item in the list of books.
 */
sealed class BookListItem {

    /** Header of the group of books. */
    data class HeaderItem(val name: String) : BookListItem()

    /** Book item in the list. */
    data class BookItem(val book: Book) : BookListItem()
}