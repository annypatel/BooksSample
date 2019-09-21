package sample.books.domain

import sample.base.domain.RxSchedulers
import sample.base.domain.SingleUseCase
import sample.books.domain.BookListItem.BookItem
import sample.books.domain.BookListItem.HeaderItem
import io.reactivex.Single
import io.reactivex.rxkotlin.toFlowable
import javax.inject.Inject

/**
 * Use case for flattening the [BookGroups][BookGroup] into list of [BookListItems][BookListItem].
 */
interface FlattenBookGroupsUseCase : SingleUseCase<List<BookGroup>, List<BookListItem>>

/**
 * Implementation of [FlattenBookGroupsUseCase].
 */
class FlattenBookGroupsUseCaseImpl @Inject constructor(
    private val schedulers: RxSchedulers
) : FlattenBookGroupsUseCase {

    override fun invoke(input: List<BookGroup>): Single<List<BookListItem>> {
        return input.toFlowable()
            .subscribeOn(schedulers.computation)
            .flatMap { group ->
                group.books
                    .mapTo(mutableListOf<BookListItem>(HeaderItem(group.name))) {
                        BookItem(it)
                    }
                    .toFlowable()
            }
            .toList()
    }
}