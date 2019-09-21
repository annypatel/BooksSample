package sample.base.ui.dagger

import androidx.lifecycle.ViewModelProvider
import sample.base.ui.ViewModelFactory
import dagger.Binds
import dagger.Module

/**
 * Module that injects [ViewModelProvider.Factory] into application component.
 */
@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
