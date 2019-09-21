package sample.books.data

import sample.books.data.database.BookDao
import sample.books.data.database.BookEntity
import sample.books.data.network.BooksApi
import sample.books.domain.Book
import sample.testRxSchedulers
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Test
import org.threeten.bp.LocalDate

class BooksRepositoryImplShould {

    private val booksApi = mock<BooksApi>()
    private val bookDao = mock<BookDao>()
    private val booksRepository = BooksRepositoryImpl(booksApi, bookDao, testRxSchedulers())

    private val books = listOf(Book("1", "Name", "Author", LocalDate.of(2019, 4, 29), "Url"))
    private val bookEntities = listOf(BookEntity("1", "Name", "Author", LocalDate.of(2019, 4, 29), "Url"))

    @Test
    fun callGetAllOnBookDao_whenGetBooksIsCalled() {
        givenASuccessfulGetAllBooksCall(bookEntities)

        booksRepository.getBooks()
            .test()

        verify(bookDao).getAll()
    }

    @Test
    fun ignoreEmptyResult_whenGetBooksIsCalled() {
        givenASuccessfulGetAllBooksCall(emptyList())

        val observer = booksRepository.getBooks()
            .test()

        observer.assertNoValues()
    }

    @Test
    fun propagateException_whenGetAllBooksIsUnSuccessful() {
        val expectedError = RuntimeException("test")
        givenAnUnsuccessfulGetAllBooksCall(expectedError)

        val observer = booksRepository.getBooks()
            .test()

        observer.assertError(expectedError)
    }

    @Test
    fun callBooksApi_whenFetchBooksIsCalled() {
        givenASuccessfulBooksApiCall(books)
        givenASuccessfulInsertBooksCall()

        booksRepository.fetchBooks()
            .test()

        verify(booksApi).getAllBooks()
    }

    @Test
    fun callInsertOnBookDao_whenBooksApiCallIsSuccessful() {
        givenASuccessfulBooksApiCall(books)
        givenASuccessfulInsertBooksCall()

        booksRepository.fetchBooks()
            .test()

        verify(bookDao).insert(bookEntities)
    }

    @Test
    fun propagateException_whenBooksApiCallIsUnsuccessful() {
        val expectedError = RuntimeException("test")
        givenAnUnsuccessfulBooksApiCall(expectedError)

        val observer = booksRepository.fetchBooks()
            .test()

        observer.assertError(expectedError)
    }

    @Test
    fun propagateException_whenInsertBooksIsUnsuccessful() {
        givenASuccessfulBooksApiCall(books)
        val expectedError = RuntimeException("test")
        givenAnUnsuccessfulInsertBooksCall(expectedError)

        val observer = booksRepository.fetchBooks()
            .test()

        observer.assertError(expectedError)
    }

    private fun givenASuccessfulBooksApiCall(result: List<Book>) {
        whenever(booksApi.getAllBooks()).thenReturn(Single.just(result))
    }

    private fun givenAnUnsuccessfulBooksApiCall(exception: Throwable) {
        whenever(booksApi.getAllBooks()).thenReturn(Single.error(exception))
    }

    private fun givenASuccessfulInsertBooksCall() {
        whenever(bookDao.insert(any())).thenReturn(Completable.complete())
    }

    private fun givenAnUnsuccessfulInsertBooksCall(exception: Throwable) {
        whenever(bookDao.insert(any())).thenReturn(Completable.error(exception))
    }

    private fun givenASuccessfulGetAllBooksCall(books: List<BookEntity>) {
        whenever(bookDao.getAll()).thenReturn(Flowable.just(books))
    }

    private fun givenAnUnsuccessfulGetAllBooksCall(exception: RuntimeException) {
        whenever(bookDao.getAll()).thenReturn(Flowable.error(exception))
    }
}
