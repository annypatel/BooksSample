package sample.books.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Dao for accessing book table in database.
 */
@Dao
interface BookDao {

    /**
     * To insert the books into database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entities: List<BookEntity>): Completable

    /**
     * To get all books from database.
     */
    @Query("SELECT * FROM book")
    fun getAll(): Flowable<List<BookEntity>>
}