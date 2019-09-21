package sample.books.data.dagger

import sample.books.data.BooksRepositoryImpl
import sample.books.data.database.BookDao
import sample.books.data.database.BookDatabase
import sample.books.domain.BooksRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * Module to inject data repositories into application component for books feature.
 */
@Module(includes = [BookDatabaseModule::class])
abstract class BooksDataModule {

    @Binds
    abstract fun booksRepository(repository: BooksRepositoryImpl): BooksRepository

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun bookDao(database: BookDatabase): BookDao {
            return database.bookDao()
        }
    }
}