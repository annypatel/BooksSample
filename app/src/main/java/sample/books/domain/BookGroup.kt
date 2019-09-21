package sample.books.domain

/**
 * Represents a group of [books][Book] with its name.
 */
data class BookGroup(
    val name: String,
    val books: List<Book>
)