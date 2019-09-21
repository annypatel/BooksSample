package sample.books.domain

import sample.base.domain.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

/**
 * Use case for sorting books.
 */
interface SortBooksUseCase : SingleUseCase<BooksToSort, List<BookGroup>>

/**
 * Implementation of [SortBooksUseCase], which uses [SortBooksByNameUseCase] and [SortBooksByWeekUseCase] for sorting
 * books based on given sort option.
 */
class SortBooksUseCaseImpl @Inject constructor(
    private val sortBooksByName: SortBooksByNameUseCase,
    private val sortBooksByWeek: SortBooksByWeekUseCase
) : SortBooksUseCase {

    override fun invoke(input: BooksToSort): Single<List<BookGroup>> {
        return when (input.sortBy) {
            SortBy.Name -> sortBooksByName(input.books)
            SortBy.Week -> sortBooksByWeek(input.books)
        }
    }
}