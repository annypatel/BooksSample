package sample.books.data.dagger

import dagger.Binds
import dagger.Module
import sample.books.data.network.BooksApi

/**
 * Module to inject [BooksApi] into application component.
 */
@Module
abstract class BooksApiModule {

    @Binds
    abstract fun bindsBooksApi(booksApi: MockBooksApi): BooksApi
}
