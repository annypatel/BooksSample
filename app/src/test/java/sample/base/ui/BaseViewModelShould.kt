package sample.base.ui

import io.reactivex.Flowable
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class BaseViewModelShould {

    class TestViewModel : BaseViewModel() {

        fun subscribeAndAutoDispose() {
            Flowable.just(1, 2, 3)
                .subscribe()
                .autoDispose()
        }

        // overridden with public modifier to make it accessible in tests
        public override fun onCleared() {
            super.onCleared()
        }
    }

    @Test
    fun addDisposable_whenAutoDisposeIsCalled() {
        val viewModel = TestViewModel()

        viewModel.subscribeAndAutoDispose()

        assertThat(viewModel.disposables.size(), equalTo(1))
    }

    @Test
    fun clearDisposables_whenOnClearedIsCalled() {
        val viewModel = TestViewModel()
        viewModel.subscribeAndAutoDispose()

        viewModel.onCleared()

        assertThat(viewModel.disposables.size(), equalTo(0))
    }
}
