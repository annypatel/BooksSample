package sample.books.domain

/**
 * Represents books that needs to be sorted by [sort][sortBy] order.
 */
data class BooksToSort(
    val books: List<Book>,
    val sortBy: SortBy
)