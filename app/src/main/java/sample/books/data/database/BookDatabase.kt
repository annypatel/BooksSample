package sample.books.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Room database for books feature that maintains offline cache for books.
 */
@Database(
    version = BookDatabase.VERSION,
    entities = [
        BookEntity::class
    ]
)
@TypeConverters(LocalDateConverter::class)
abstract class BookDatabase : RoomDatabase() {

    companion object {
        const val VERSION = 1
        const val NAME = "book.db"
    }

    abstract fun bookDao(): BookDao
}