package sample.books.domain.dagger

import sample.books.domain.FetchBooksUseCase
import sample.books.domain.FetchBooksUseCaseImpl
import sample.books.domain.FlattenBookGroupsUseCase
import sample.books.domain.FlattenBookGroupsUseCaseImpl
import sample.books.domain.GetBooksUseCase
import sample.books.domain.GetBooksUseCaseImpl
import sample.books.domain.SortBooksByNameUseCase
import sample.books.domain.SortBooksByNameUseCaseImpl
import sample.books.domain.SortBooksByWeekUseCase
import sample.books.domain.SortBooksByWeekUseCaseImpl
import sample.books.domain.SortBooksUseCase
import sample.books.domain.SortBooksUseCaseImpl
import dagger.Binds
import dagger.Module

/**
 * Module to inject domain use-cases into application component for books feature.
 */
@Module
abstract class  BooksDomainModule {

    @Binds
    abstract fun getBooksUseCase(getBooks: GetBooksUseCaseImpl): GetBooksUseCase

    @Binds
    abstract fun fetchBooksUseCase(fetchBooks: FetchBooksUseCaseImpl): FetchBooksUseCase

    @Binds
    abstract fun sortBooksUseCase(sortBooks: SortBooksUseCaseImpl): SortBooksUseCase

    @Binds
    abstract fun sortBooksByNameUseCase(sortBooksByName: SortBooksByNameUseCaseImpl): SortBooksByNameUseCase

    @Binds
    abstract fun sortBooksByWeekUseCase(sortBooksByWeek: SortBooksByWeekUseCaseImpl): SortBooksByWeekUseCase

    @Binds
    abstract fun flattenBookGroupsUseCase(flattenBookGroups: FlattenBookGroupsUseCaseImpl): FlattenBookGroupsUseCase
}