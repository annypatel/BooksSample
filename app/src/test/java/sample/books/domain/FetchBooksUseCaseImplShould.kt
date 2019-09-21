package sample.books.domain

import sample.base.domain.invoke
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Test

class FetchBooksUseCaseImplShould {

    private val booksRepository = mock<BooksRepository>()
    private val fetchBooks = FetchBooksUseCaseImpl(booksRepository)

    @Test
    fun callFetchBooksOnRepository_whenFetchBooksIsCalled() {
        givenASuccessfulBooksRepositoryCall()

        fetchBooks().test()

        verify(booksRepository).fetchBooks()
    }

    @Test
    fun propagateException_whenFetchBooksIsUnsuccessful() {
        val expectedError = RuntimeException("test")
        givenAnUnsuccessfulBooksRepositoryCall(expectedError)

        val observer = fetchBooks().test()

        observer.assertError(expectedError)
    }

    private fun givenASuccessfulBooksRepositoryCall() {
        whenever(booksRepository.fetchBooks()).thenReturn(Completable.complete())
    }

    private fun givenAnUnsuccessfulBooksRepositoryCall(exception: Throwable) {
        whenever(booksRepository.fetchBooks()).thenReturn(Completable.error(exception))
    }
}