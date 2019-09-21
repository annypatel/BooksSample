package sample.books.data.database

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

/**
 * Room converter for [LocalDate].
 */
object LocalDateConverter {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @JvmStatic
    @TypeConverter
    fun toLocalDate(value: String): LocalDate {
        return formatter.parse(value, LocalDate::from)
    }

    @JvmStatic
    @TypeConverter
    fun fromLocalDate(date: LocalDate): String {
        return date.format(formatter)
    }
}