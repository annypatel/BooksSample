package sample.books.ui.dagger

import androidx.lifecycle.ViewModel
import sample.base.ui.dagger.ViewModelKey
import sample.books.ui.BooksActivity
import sample.books.ui.BooksViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module to declare UI components that have injectable members for books feature.
 */
@Module
abstract class BooksUiModule {

    @ContributesAndroidInjector
    abstract fun contributeBooksActivity(): BooksActivity

    @Binds
    @IntoMap
    @ViewModelKey(BooksViewModel::class)
    abstract fun booksViewModel(viewModel: BooksViewModel): ViewModel
}