package sample.books.ui

import io.reactivex.rxkotlin.Observables.combineLatest
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import sample.base.domain.RxSchedulers
import sample.base.domain.invoke
import sample.base.ui.BaseViewModel
import sample.base.ui.LiveResult
import sample.base.ui.MutableLiveResult
import sample.base.ui.Result
import sample.books.R
import sample.books.domain.BookListItem
import sample.books.domain.BooksToSort
import sample.books.domain.FetchBooksUseCase
import sample.books.domain.FlattenBookGroupsUseCase
import sample.books.domain.GetBooksUseCase
import sample.books.domain.SortBooksUseCase
import sample.books.domain.SortBy
import javax.inject.Inject

/**
 * ViewModel responsible for reacting to user interactions on books screen.
 */
class BooksViewModel @Inject constructor(
    private val fetchBooks: FetchBooksUseCase,
    getBooks: GetBooksUseCase,
    sortBooks: SortBooksUseCase,
    flattenBookGroups: FlattenBookGroupsUseCase,
    val schedulers: RxSchedulers
) : BaseViewModel() {

    private val sortBySubject = PublishSubject.create<SortBy>()

    private val _books = MutableLiveResult<List<BookListItem>>()
    val books: LiveResult<List<BookListItem>>
        get() = _books

    init {
        refreshBooks()

        val booksObservable = getBooks().toObservable()
        val sortByObservable = sortBySubject.startWith(SortBy.Week)
            .distinctUntilChanged()

        combineLatest(booksObservable, sortByObservable, ::BooksToSort)
            .flatMapSingle { sortBooks(it) }
            .flatMapSingle { flattenBookGroups(it) }
            .map { Result.value(it) }
            .onErrorReturn { Result.error(R.string.error_generic) }
            .observeOn(schedulers.main)
            .subscribeBy { _books.value = it }
            .autoDispose()
    }

    fun sortBooksBy(sortBy: SortBy) {
        sortBySubject.onNext(sortBy)
    }

    fun refreshBooks() {
        fetchBooks()
            .observeOn(schedulers.main)
            .doOnSubscribe { _books.value = Result.start() }
            .subscribeBy({ _books.value = Result.error(R.string.error_generic) })
            .autoDispose()
    }
}