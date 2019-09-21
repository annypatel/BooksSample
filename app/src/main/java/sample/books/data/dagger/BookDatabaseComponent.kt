package sample.books.data.dagger

import android.app.Application
import androidx.room.Room
import sample.books.data.database.BookDatabase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Named

/**
 * Dagger component that exposes [BookDatabase] with [bookDatabase] provision method, dependencies declared via
 * [Subcomponent.modules] won't be exposed outside this component.
 */
@Subcomponent(modules = [InternalBookDatabaseModule::class])
interface BookDatabaseComponent {

    @Named("internal")
    fun bookDatabase(): BookDatabase

    @Subcomponent.Factory
    interface Factory {

        fun create(): BookDatabaseComponent
    }
}

/**
 * Dagger module for [BookDatabaseComponent]. Defines dependencies required to constructs [BookDatabase]. All
 * the dependencies declared here is internal to [BookDatabaseComponent] and won't be accessible outside.
 */
@Module
object InternalBookDatabaseModule {

    @Provides
    @JvmStatic
    @Named("internal")
    fun bookDatabase(app: Application): BookDatabase {
        return Room.databaseBuilder(app, BookDatabase::class.java, BookDatabase.NAME)
            .build()
    }
}