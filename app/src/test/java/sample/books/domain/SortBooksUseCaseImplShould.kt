package sample.books.domain

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test

class SortBooksUseCaseImplShould {

    private val sortBooksByName = mock<SortBooksByNameUseCase>()
    private val sortBooksByWeek = mock<SortBooksByWeekUseCase>()

    @Test
    fun callSortBooksByName_whenSortTypeIsName() {
        givenASuccessfulSortBooksByNameCall()
        val booksToSort = BooksToSort(mock(), SortBy.Name)
        val sortBooks = SortBooksUseCaseImpl(sortBooksByName, sortBooksByWeek)

        sortBooks(booksToSort)
            .test()

        verify(sortBooksByName)(booksToSort.books)
        verifyZeroInteractions(sortBooksByWeek)
    }

    @Test
    fun callSortBooksByWeek_whenSortTypeIsWeek() {
        givenASuccessfulSortBooksByWeekCall()
        val booksToSort = BooksToSort(mock(), SortBy.Week)
        val sortBooks = SortBooksUseCaseImpl(sortBooksByName, sortBooksByWeek)

        sortBooks(booksToSort)
            .test()

        verify(sortBooksByWeek)(booksToSort.books)
        verifyZeroInteractions(sortBooksByName)
    }

    @Test
    fun propagateException_whenSortBooksByNameFails() {
        val expectedError = Throwable()
        givenAnUnsuccessfulSortBooksByNameCall(expectedError)
        val booksToSort = BooksToSort(mock(), SortBy.Name)
        val sortBooks = SortBooksUseCaseImpl(sortBooksByName, sortBooksByWeek)

        val observer = sortBooks(booksToSort)
            .test()

        observer.assertError(expectedError)
    }

    @Test
    fun propagateException_whenSortBooksByWeekFails() {
        val expectedError = Throwable()
        givenAnUnsuccessfulSortBooksByWeekCall(expectedError)
        val booksToSort = BooksToSort(mock(), SortBy.Week)
        val sortBooks = SortBooksUseCaseImpl(sortBooksByName, sortBooksByWeek)

        val observer = sortBooks(booksToSort)
            .test()

        observer.assertError(expectedError)
    }

    private fun givenASuccessfulSortBooksByNameCall() {
        whenever(sortBooksByName(any()))
            .thenReturn(Single.just(emptyList()))
    }

    private fun givenASuccessfulSortBooksByWeekCall() {
        whenever(sortBooksByWeek(any()))
            .thenReturn(Single.just(emptyList()))
    }

    private fun givenAnUnsuccessfulSortBooksByNameCall(error: Throwable) {
        whenever(sortBooksByName(any()))
            .thenReturn(Single.error(error))
    }

    private fun givenAnUnsuccessfulSortBooksByWeekCall(error: Throwable) {
        whenever(sortBooksByWeek(any()))
            .thenReturn(Single.error(error))
    }
}