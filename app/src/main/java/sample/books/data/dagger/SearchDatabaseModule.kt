package sample.books.data.dagger

import sample.books.data.database.BookDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module for exposing [BookDatabase] to application component.
 */
@Module(subcomponents = [BookDatabaseComponent::class])
object BookDatabaseModule {

    @Provides
    @Singleton
    @JvmStatic
    fun bookDatabase(factory: BookDatabaseComponent.Factory): BookDatabase {
        return factory.create().bookDatabase()
    }
}