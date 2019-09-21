package sample.books.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import sample.books.R
import sample.base.domain.invoke
import sample.books.domain.Book
import sample.books.domain.BookGroup
import sample.books.domain.BookListItem
import sample.books.domain.BooksToSort
import sample.books.domain.FetchBooksUseCase
import sample.books.domain.FlattenBookGroupsUseCase
import sample.books.domain.GetBooksUseCase
import sample.books.domain.SortBooksUseCase
import sample.books.domain.SortBy
import sample.isError
import sample.isStart
import sample.isValue
import sample.testRxSchedulers
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.clearInvocations
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test

class BooksViewModelShould {

    @get:Rule val instantExecutor = InstantTaskExecutorRule()
    private val fetchBooks = mock<FetchBooksUseCase>()
    private val getBooks = mock<GetBooksUseCase>()
    private val sortBooks = mock<SortBooksUseCase>()
    private val flattenBookGroups = mock<FlattenBookGroupsUseCase>()

    private val books: List<Book> = listOf(mock(), mock(), mock())
    private val bookGroups: List<BookGroup> = listOf(mock(), mock(), mock())
    private val bookItems: List<BookListItem> = listOf(mock(), mock(), mock())

    @Test
    fun callGetSortFlattenBooks_whenCreated() {
        givenSuccessfulUseCaseCalls()

        createViewModel(false)

        inOrder(fetchBooks, getBooks, sortBooks, flattenBookGroups) {
            verify(fetchBooks)()
            verify(getBooks)()
            verify(sortBooks)(BooksToSort(books, SortBy.Week))
            verify(flattenBookGroups)(bookGroups)
        }
    }

    @Test
    fun setValueInBooks_whenAllCallsSuccessful() {
        givenSuccessfulUseCaseCalls()

        val viewModel = createViewModel(true)

        assertThat(viewModel.books.value, isValue(bookItems))
    }

    @Test
    fun setErrorInBooks_whenGetBooksIsUnsuccessful() {
        givenAnUnsuccessfulGetBooksCall(RuntimeException())
        givenASuccessfulSortBooksCall(bookGroups)
        givenASuccessfulFlattenBooksCall(bookItems)
        givenASuccessfulFetchBooksCall()

        val viewModel = createViewModel(true)

        assertThat(viewModel.books.value, isError(R.string.error_generic))
    }

    @Test
    fun setErrorInBooks_whenSortBooksIsUnsuccessful() {
        givenASuccessfulGetBooksCall(books)
        givenAnUnsuccessfulSortBooksCall(RuntimeException())
        givenASuccessfulFlattenBooksCall(bookItems)
        givenASuccessfulFetchBooksCall()

        val viewModel = createViewModel(true)

        assertThat(viewModel.books.value, isError(R.string.error_generic))
    }

    @Test
    fun setErrorInBooks_whenFlattenBooksIsUnsuccessful() {
        givenASuccessfulGetBooksCall(books)
        givenASuccessfulSortBooksCall(bookGroups)
        givenAnUnsuccessfulFlattenBooksCall(RuntimeException())
        givenASuccessfulFetchBooksCall()

        val viewModel = createViewModel(true)

        assertThat(viewModel.books.value, isError(R.string.error_generic))
    }

    @Test
    fun callSortFlattenBooks_whenSortBooksByCalled() {
        givenSuccessfulUseCaseCalls()
        val viewModel = createViewModel(true)
        val sortBy = SortBy.Name

        viewModel.sortBooksBy(sortBy)

        verify(getBooks, never())()
        verify(fetchBooks, never())()
        inOrder(sortBooks, flattenBookGroups) {
            verify(sortBooks)(BooksToSort(books, sortBy))
            verify(flattenBookGroups)(bookGroups)
        }
    }

    @Test
    fun callFetchBooks_whenRefreshBooksIsCalled() {
        givenSuccessfulUseCaseCalls()
        val viewModel = createViewModel(true)

        viewModel.refreshBooks()

        verify(fetchBooks)()
    }

    @Test
    fun setStartInBooks_whenRefreshBooksIsCalled() {
        givenSuccessfulUseCaseCalls()
        val viewModel = createViewModel(true)

        viewModel.refreshBooks()

        assertThat(viewModel.books.value, isStart())
    }

    @Test
    fun setErrorInBooks_whenRefreshBooksIsUnsuccessful() {
        givenSuccessfulUseCaseCalls()
        val viewModel = createViewModel(true)
        givenAnUnsuccessfulFetchBooksCall(RuntimeException())

        viewModel.refreshBooks()

        assertThat(viewModel.books.value, isError(R.string.error_generic))
    }

    private fun createViewModel(clearInvocations: Boolean): BooksViewModel {
        val viewModel = BooksViewModel(fetchBooks, getBooks, sortBooks, flattenBookGroups, testRxSchedulers())
        if (clearInvocations)
            clearInvocations(fetchBooks, getBooks, sortBooks, flattenBookGroups)
        return viewModel
    }

    private fun givenSuccessfulUseCaseCalls() {
        givenASuccessfulGetBooksCall(books)
        givenASuccessfulSortBooksCall(bookGroups)
        givenASuccessfulFlattenBooksCall(bookItems)
        givenASuccessfulFetchBooksCall()
    }

    private fun givenASuccessfulFetchBooksCall() {
        whenever(fetchBooks(any())).thenReturn(Completable.complete())
    }

    private fun givenAnUnsuccessfulFetchBooksCall(exception: RuntimeException) {
        whenever(fetchBooks(any())).thenReturn(Completable.error(exception))
    }

    private fun givenASuccessfulFlattenBooksCall(bookItems: List<BookListItem>) {
        whenever(flattenBookGroups(any())).thenReturn(Single.just(bookItems))
    }

    private fun givenAnUnsuccessfulFlattenBooksCall(exception: RuntimeException) {
        whenever(flattenBookGroups(any())).thenReturn(Single.error(exception))
    }

    private fun givenASuccessfulSortBooksCall(bookGroups: List<BookGroup>) {
        whenever(sortBooks(any())).thenReturn(Single.just(bookGroups))
    }

    private fun givenAnUnsuccessfulSortBooksCall(exception: RuntimeException) {
        whenever(sortBooks(any())).thenReturn(Single.error(exception))
    }

    private fun givenASuccessfulGetBooksCall(books: List<Book>) {
        whenever(getBooks()).thenReturn(Flowable.just(books))
    }

    private fun givenAnUnsuccessfulGetBooksCall(exception: RuntimeException) {
        whenever(getBooks()).thenReturn(Flowable.error(exception))
    }
}
