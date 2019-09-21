package sample.books.data

import sample.base.domain.RxSchedulers
import sample.books.data.database.BookDao
import sample.books.data.database.BookEntity
import sample.books.data.network.BooksApi
import sample.books.domain.Book
import sample.books.domain.BooksRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Implementation of [BooksRepository] that maintains books.
 */
class BooksRepositoryImpl @Inject constructor(
    private val booksApi: BooksApi,
    private val bookDao: BookDao,
    private val schedulers: RxSchedulers
) : BooksRepository {

    override fun getBooks(): Flowable<List<Book>> {
        return bookDao.getAll()
            .subscribeOn(schedulers.database)
            .filter { it.isNotEmpty() }
            .map(::toBooks)
    }

    override fun fetchBooks(): Completable {
        return booksApi.getAllBooks()
            .subscribeOn(schedulers.io)
            .flatMapCompletable {
                bookDao.insert(toBookEntities(it))
                    .subscribeOn(schedulers.database)
            }
    }

    private fun toBooks(entities: List<BookEntity>): List<Book> {
        return entities.map {
            Book(
                id = it.id,
                name = it.name,
                author = it.author,
                publishDate = it.publishDate,
                coverImageUrl = it.coverImageUrl
            )
        }
    }

    private fun toBookEntities(books: List<Book>): List<BookEntity> {
        return books.map {
            BookEntity(
                id = it.id,
                name = it.name,
                author = it.author,
                publishDate = it.publishDate,
                coverImageUrl = it.coverImageUrl
            )
        }
    }
}
