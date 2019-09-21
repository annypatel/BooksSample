package sample.books.domain

import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Repository to maintain books.
 */
interface BooksRepository {

    fun getBooks(): Flowable<List<Book>>

    fun fetchBooks(): Completable
}