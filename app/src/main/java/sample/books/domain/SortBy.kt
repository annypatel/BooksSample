package sample.books.domain

/**
 * Represents book sorting options.
 */
sealed class SortBy {

    /** Sort by name of the book. */
    object Name : SortBy()

    /** Sort by week of published date. */
    object Week : SortBy()
}