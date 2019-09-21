package sample.base.ui

import androidx.lifecycle.ViewModel
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Assert.fail
import org.junit.Test
import javax.inject.Provider

class ViewModelFactoryShould {

    @Test
    fun returnViewModel_whenMapHasViewModel() {
        val factory = ViewModelFactory(mapOf(
            TestViewModel::class.java to Provider<ViewModel> { TestViewModel() }
        ))

        val viewModel = factory.create(TestViewModel::class.java)

        assertThat(viewModel, instanceOf(TestViewModel::class.java))
    }

    @Test
    fun returnChildViewModel_whenMapHasChildViewModel() {
        val factory = ViewModelFactory(mapOf(
            ChildTestViewModel::class.java to Provider<ViewModel> { ChildTestViewModel() }
        ))

        val viewModel = factory.create(TestViewModel::class.java)

        assertThat(viewModel, instanceOf(ChildTestViewModel::class.java))
    }

    @Test
    fun propagateException_whenMapIsEmpty() {
        val factory = ViewModelFactory(mapOf())

        try {
            factory.create(TestViewModel::class.java)
            fail("Exception not thrown")
        } catch (_: IllegalStateException) {
        }
    }

    private open class TestViewModel : ViewModel()
    private class ChildTestViewModel : TestViewModel()
}