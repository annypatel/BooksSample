package sample.books.domain

import sample.base.domain.FlowableUseCase
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Use case for getting books.
 */
interface GetBooksUseCase : FlowableUseCase<Unit, List<Book>>

/**
 * Implementation of [GetBooksUseCase] that gets books using [BooksRepository].
 */
class GetBooksUseCaseImpl @Inject constructor(
    private val booksRepository: BooksRepository
) : GetBooksUseCase {

    override fun invoke(input: Unit): Flowable<List<Book>> {
        return booksRepository.getBooks()
    }
}