package sample.books.domain

import sample.base.domain.CompletableUseCase
import io.reactivex.Completable
import javax.inject.Inject

/**
 * Use case for fetching books.
 */
interface FetchBooksUseCase : CompletableUseCase<Unit>

/**
 * Implementation of [FetchBooksUseCase] that fetches books using [BooksRepository].
 */
class FetchBooksUseCaseImpl @Inject constructor(
    private val booksRepository: BooksRepository
) : FetchBooksUseCase {

    override fun invoke(input: Unit): Completable {
        return booksRepository.fetchBooks()
    }
}