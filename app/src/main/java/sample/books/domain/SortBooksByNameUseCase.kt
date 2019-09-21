package sample.books.domain

import sample.base.domain.RxSchedulers
import sample.base.domain.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

/**
 * Use case for sort books by the first character of its name.
 */
interface SortBooksByNameUseCase : SingleUseCase<List<Book>, List<BookGroup>>

/**
 * Implementation of [SortBooksByNameUseCase].
 */
class SortBooksByNameUseCaseImpl @Inject constructor(
    private val schedulers: RxSchedulers
) : SortBooksByNameUseCase {

    override fun invoke(input: List<Book>): Single<List<BookGroup>> {
        return Single.just(input)
            .subscribeOn(schedulers.computation)
            .map { books ->
                books.sortedBy { it.name }
                    .groupBy { it.name.substring(0, 1) }
                    .map { BookGroup(it.key, it.value) }
            }
    }
}