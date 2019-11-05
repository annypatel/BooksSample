package sample.dagger

import android.app.Application
import sample.base.ui.dagger.ViewModelFactoryModule
import sample.books.data.dagger.BooksDataModule
import sample.books.data.network.BooksApi
import sample.books.domain.dagger.BooksDomainModule
import sample.books.ui.dagger.BooksUiModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Component providing Application scoped instances for espresso tests.
 */
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelFactoryModule::class,
        BooksDomainModule::class,
        BooksDataModule::class,
        BooksUiModule::class,
        TestRxSchedulersModule::class
    ]
)
interface TestAppComponent : AppComponent {

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance app: Application,
            @BindsInstance booksApi: BooksApi
        ): TestAppComponent
    }
}