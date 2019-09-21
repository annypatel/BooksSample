package sample.books.data.network

import sample.books.domain.Book
import io.reactivex.Single
import retrofit2.http.GET

interface BooksApi {

    @GET("books")
    fun getAllBooks(): Single<List<Book>>
}
