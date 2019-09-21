package sample.books.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate

/**
 * A database entity for books.
 */
@Entity(tableName = "book")
data class BookEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "publish_date")
    val publishDate: LocalDate,

    @ColumnInfo(name = "cover_image_url")
    val coverImageUrl: String
)