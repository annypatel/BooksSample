package sample.dagger

import android.app.Application
import sample.App
import sample.base.data.dagger.NetworkModule
import sample.base.ui.dagger.ViewModelFactoryModule
import sample.books.data.dagger.BooksApiModule
import sample.books.data.dagger.BooksDataModule
import sample.books.domain.dagger.BooksDomainModule
import sample.books.ui.dagger.BooksUiModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Component providing Application scoped instances.
 */
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelFactoryModule::class,
        BooksDomainModule::class,
        BooksDataModule::class,
        BooksUiModule::class,
        NetworkModule::class,
        BooksApiModule::class,
        RxSchedulersModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance app: Application): AppComponent
    }
}