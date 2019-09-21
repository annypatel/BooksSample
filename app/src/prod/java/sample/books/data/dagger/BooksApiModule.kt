package sample.books.data.dagger

import sample.base.data.NetworkClient
import sample.books.data.network.BooksApi
import dagger.Module
import dagger.Provides

/**
 * Module to inject [BooksApi] into application component.
 */
@Module
object BooksApiModule {

    @Provides
    @JvmStatic
    fun providesBooksApi(networkClient: NetworkClient): BooksApi {
        return networkClient.create(BooksApi::class)
    }
}
