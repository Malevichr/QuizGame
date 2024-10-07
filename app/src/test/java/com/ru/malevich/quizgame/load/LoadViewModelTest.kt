package com.ru.malevich.quizgame.load

import com.ru.malevich.quizgame.core.MyViewModel
import com.ru.malevich.quizgame.core.RunAsync
import com.ru.malevich.quizgame.di.ClearViewModel
import com.ru.malevich.quizgame.load.data.LoadRepository
import com.ru.malevich.quizgame.load.data.LoadResult
import com.ru.malevich.quizgame.load.presentation.LoadUiObservable
import com.ru.malevich.quizgame.load.presentation.LoadUiState
import com.ru.malevich.quizgame.load.presentation.LoadViewModel
import com.ru.malevich.quizgame.load.presentation.UiObservable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LoadViewModelTest {
    private lateinit var viewModel: LoadViewModel
    private lateinit var fragment: FakeFragment
    private lateinit var repository: FakeLoadRepository
    private lateinit var observable: FakeLoadUiObservable
    private lateinit var runAsync: FakeRunAsync

    @Before
    fun setup() {
        repository = FakeLoadRepository()
        observable = FakeLoadUiObservable.Base()
        runAsync = FakeRunAsync()
        viewModel = LoadViewModel(
            repository = repository,
            observable = observable,
            runAsync = runAsync,
            clearViewModel = FakeClear()
        )
        fragment = FakeFragment()

    }

    @Test
    fun caseSameFragment() {
        repository.expectedResult(LoadResult.Success)

        viewModel.load(isFirstRun = true)
        assertEquals(LoadUiState.Progress, observable.postUiStatesListCalled.first())
        assertEquals(1, repository.loadCalledCount)

        viewModel.startUpdates(observer = fragment)
        assertEquals(1, observable.registerCalledCount)
        assertEquals(1, observable.postUiStatesListCalled.size)

        assertEquals(1, fragment.statesList.size)
        assertEquals(LoadUiState.Progress, fragment.statesList.first())

        runAsync.returnResult()

        assertEquals(LoadUiState.Success, observable.postUiStatesListCalled[1])
        assertEquals(2, observable.postUiStatesListCalled.size)

        assertEquals(LoadUiState.Success, fragment.statesList[1])
        assertEquals(2, fragment.statesList.size)
    }

    @Test
    fun caseAnotherFragment() {
        repository.expectedResult(LoadResult.Error(message = "no internet"))

        viewModel.load(isFirstRun = true)
        assertEquals(LoadUiState.Progress, observable.postUiStatesListCalled.first())
        assertEquals(1, observable.postUiStatesListCalled.size)
        assertEquals(1, repository.loadCalledCount)

        viewModel.startUpdates(observer = fragment)
        assertEquals(1, observable.registerCalledCount)

        assertEquals(LoadUiState.Progress, fragment.statesList.first())
        assertEquals(1, fragment.statesList.size)

        viewModel.stopUpdates()
        assertEquals(1, observable.unregisterCalledCount)

        runAsync.returnResult()

        assertEquals(
            LoadUiState.Error(message = "no internet"),
            observable.postUiStatesListCalled[1]
        )
        assertEquals(2, observable.postUiStatesListCalled.size)
        assertEquals(1, fragment.statesList.size)

        val newInstanceFragment = FakeFragment()

        viewModel.load(isFirstRun = false)
        assertEquals(2, observable.postUiStatesListCalled.size)
        assertEquals(1, repository.loadCalledCount)

        viewModel.startUpdates(newInstanceFragment)
        assertEquals(2, observable.registerCalledCount)

        assertEquals(
            LoadUiState.Error(message = "no internet"),
            newInstanceFragment.statesList.first()
        )
        assertEquals(1, fragment.statesList.size)
    }
}

private class FakeFragment : (LoadUiState) -> Unit {
    val statesList = mutableListOf<LoadUiState>()

    override fun invoke(p1: LoadUiState) {
        statesList.add(p1)
    }

}

class FakeLoadRepository : LoadRepository {
    private var loadResult: LoadResult? = null
    fun expectedResult(loadResult: LoadResult) {
        this.loadResult = loadResult
    }

    var loadCalledCount = 0
    override suspend fun load(): LoadResult {
        loadCalledCount++
        return loadResult!!
    }
}

interface FakeUiObservable<T : Any> : UiObservable<T> {
    var registerCalledCount: Int
    var unregisterCalledCount: Int
    val postUiStatesListCalled: MutableList<T>

    abstract class Abstract<T : Any> : FakeUiObservable<T> {
        private var uiStateCached: T? = null
        private var observerCached: ((T) -> Unit)? = null

        override var registerCalledCount = 0
        override fun register(observer: (T) -> Unit) {
            registerCalledCount++
            observerCached = observer
            if (uiStateCached != null) {
                observerCached!!.invoke(uiStateCached!!)
                uiStateCached = null
            }
        }

        override var unregisterCalledCount = 0
        override fun unregister() {
            unregisterCalledCount++
            observerCached = null
        }

        override val postUiStatesListCalled = mutableListOf<T>()
        override fun postUiState(uiState: T) {
            postUiStatesListCalled.add(uiState)
            if (observerCached == null) {
                uiStateCached = uiState
            } else {
                observerCached!!.invoke(uiState)
                uiStateCached = null
            }
        }
    }
}


@Suppress("UNCHECKED_CAST")
class FakeRunAsync : RunAsync {
    private var ui: (Any) -> Unit = {}
    private var result: Any? = null
    override fun <T : Any> handleAsync(
        coroutineScope: CoroutineScope,
        heavyOperation: suspend () -> T,
        updateUi: (T) -> Unit
    ) {
        runBlocking {
            result = heavyOperation.invoke()
            ui = updateUi as (Any) -> Unit
        }

    }

    fun returnResult() {
        ui.invoke(result!!)
    }
}
private class FakeClear : ClearViewModel {
    override fun clear(viewModelClass: Class<out MyViewModel<*>>) {
    }
}

private interface FakeLoadUiObservable : FakeUiObservable<LoadUiState>, LoadUiObservable {
    class Base : FakeUiObservable.Abstract<LoadUiState>(), FakeLoadUiObservable
}