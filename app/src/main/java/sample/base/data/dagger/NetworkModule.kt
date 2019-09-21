package sample.base.data.dagger

import sample.base.data.NetworkClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module for exposing [NetworkClient] to application component.
 */
@Module(subcomponents = [NetworkComponent::class])
object NetworkModule {

    @Provides
    @Singleton
    @JvmStatic
    fun networkClient(factory: NetworkComponent.Factory): NetworkClient {
        return factory.create().networkClient()
    }
}