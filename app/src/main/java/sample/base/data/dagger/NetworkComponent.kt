package sample.base.data.dagger

import sample.base.data.NetworkClient
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

/**
 * NetworkComponent exposes [NetworkClient] with [networkClient] provision method, dependencies declared via
 * [Subcomponent.modules] won't exposed outside this component.
 */
@Subcomponent(modules = [InternalNetworkModule::class])
interface NetworkComponent {

    @Named("internal")
    fun networkClient(): NetworkClient

    @Subcomponent.Factory
    interface Factory {

        fun create(): NetworkComponent
    }
}

/**
 * Dagger module for [NetworkComponent]. Defines dependencies required to constructs [NetworkClient]. All the
 * dependencies declared here is internal to [NetworkComponent] and won't be accessible outside.
 */
@Module
object InternalNetworkModule {

    private const val BASE_URL = "http://books.example.com/"

    @Provides
    @JvmStatic
    @Named("internal")
    fun networkClient(retrofit: Retrofit): NetworkClient {
        return NetworkClient(retrofit)
    }

    @Provides
    @JvmStatic
    fun retrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @JvmStatic
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }
}