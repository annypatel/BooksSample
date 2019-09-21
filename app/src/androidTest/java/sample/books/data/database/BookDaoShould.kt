package sample.books.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.LocalDate

class BookDaoShould {

    @get:Rule val instantExecutor = InstantTaskExecutorRule()
    private lateinit var database: BookDatabase
    private lateinit var dao: BookDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(getApplicationContext(), BookDatabase::class.java)
            .build()
        dao = database.bookDao()
    }

    @After
    fun cleanup() {
        database.close()
    }

    @Test
    fun insertBooksInDatabase() {
        val expectedBooks = books()

        dao.insert(expectedBooks)
            .subscribe()

        dao.getAll()
            .test()
            .assertValue(expectedBooks)
    }

    @Test
    fun replaceBooksInDatabase_whenConflict() {
        dao.insert(books("Name1"))
            .subscribe()
        val expectedBooks = books("Name2")

        dao.insert(expectedBooks)
            .subscribe()

        dao.getAll()
            .test()
            .assertValue(expectedBooks)
    }

    private fun books(name: String = "Name"): List<BookEntity> {
        return listOf(
            BookEntity(
                id = "1",
                name = name,
                author = "Author",
                publishDate = LocalDate.of(2017, 12, 31),
                coverImageUrl = "http://examp.le"
            )
        )
    }
}