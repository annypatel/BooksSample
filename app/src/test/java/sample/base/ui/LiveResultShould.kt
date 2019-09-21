package sample.base.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LiveResultShould {

    @get:Rule val instantExecutor = InstantTaskExecutorRule()
    private val owner = mock<LifecycleOwner>()
    private val lifecycle = LifecycleRegistry(owner)

    private val liveResult = MutableLiveResult<String>()
    private val mockedStart = mock<() -> Unit>()
    private val mockedValue = mock<(String) -> Unit>()
    private val mockedError = mock<(Int) -> Unit>()
    private val mockedComplete = mock<() -> Unit>()

    @Before
    fun setup() {
        whenever(owner.lifecycle).thenReturn(lifecycle)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    @Test
    fun callStart_whenStart() {
        liveResult.value = Result.start()

        liveResult.observe(owner, mockedStart, mockedValue, mockedError, mockedComplete)

        verify(mockedStart)()
        verifyZeroInteractions(mockedValue)
        verifyZeroInteractions(mockedError)
        verifyZeroInteractions(mockedComplete)
    }

    @Test
    fun callValue_whenValue() {
        val expectedValue = "xyz"
        liveResult.value = Result.value(expectedValue)

        liveResult.observe(owner, mockedStart, mockedValue, mockedError, mockedComplete)

        verify(mockedValue)(expectedValue)
        verifyZeroInteractions(mockedStart)
        verifyZeroInteractions(mockedError)
        verifyZeroInteractions(mockedComplete)
    }

    @Test
    fun callError_whenError() {
        val expectedError = android.R.string.ok
        liveResult.value = Result.error(expectedError)

        liveResult.observe(owner, mockedStart, mockedValue, mockedError, mockedComplete)

        verify(mockedError)(expectedError)
        verifyZeroInteractions(mockedStart)
        verifyZeroInteractions(mockedValue)
        verifyZeroInteractions(mockedComplete)
    }

    @Test
    fun callComplete_whenComplete() {
        liveResult.value = Result.complete()

        liveResult.observe(owner, mockedStart, mockedValue, mockedError, mockedComplete)

        verify(mockedComplete)()
        verifyZeroInteractions(mockedStart)
        verifyZeroInteractions(mockedValue)
        verifyZeroInteractions(mockedError)
    }
}