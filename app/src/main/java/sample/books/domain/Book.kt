package sample.books.domain

import org.threeten.bp.LocalDate

data class Book(
    val id: String,
    val name: String,
    val author: String,
    val publishDate: LocalDate,
    val coverImageUrl: String
)
