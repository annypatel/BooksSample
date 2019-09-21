package sample.books.domain

import sample.base.domain.invoke
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.Test

class GetBooksUseCaseImplShould {

    private val booksRepository = mock<BooksRepository>()
    private val getBooks = GetBooksUseCaseImpl(booksRepository)
    private val mockBooks: List<Book> = listOf(mock(), mock(), mock())

    @Test
    fun callGetBooksOnRepository_whenGetBooksIsCalled() {
        givenASuccessfulBooksRepositoryCall(mockBooks)

        getBooks().test()

        verify(booksRepository).getBooks()
    }

    @Test
    fun propagateException_whenGetBooksIsUnsuccessful() {
        val expectedError = RuntimeException("test")
        givenAnUnsuccessfulBooksRepositoryCall(expectedError)

        val observer = getBooks().test()

        observer.assertError(expectedError)
    }

    private fun givenASuccessfulBooksRepositoryCall(result: List<Book>) {
        whenever(booksRepository.getBooks()).thenReturn(Flowable.just(result))
    }

    private fun givenAnUnsuccessfulBooksRepositoryCall(exception: Throwable) {
        whenever(booksRepository.getBooks()).thenReturn(Flowable.error(exception))
    }
}