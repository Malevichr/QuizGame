package com.ru.malevich.quizgame.load

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LoadViewModelTest {
    private lateinit var viewModel: LoadViewModel
    private lateinit var fragment: FakeFragment
    private lateinit var repository: FakeLoadRepository
    private lateinit var observable: FakeUiObservable

    @Before
    fun setup() {
        repository = FakeLoadRepository()
        observable = FakeUiObservable()
        viewModel = LoadViewModel(
            repository = repository,
            observable = observable
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


        repository.returnResult()
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

        repository.returnResult()
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

private class FakeLoadRepository : LoadRepository {
    private var loadResult: LoadResult? = null
    private var loadResultCallback: (LoadResult) -> Unit = {}
    fun expectedResult(loadResult: LoadResult) {
        this.loadResult = loadResult
    }

    var loadCalledCount = 0
    override fun load(resultCallback: (LoadResult) -> Unit) {
        loadCalledCount++
        loadResultCallback = resultCallback
    }

    fun returnResult() {
        loadResultCallback.invoke(loadResult!!)
    }
}

private class FakeUiObservable : UiObservable {
    private var uiStateCached: LoadUiState? = null
    private var observerCached: ((LoadUiState) -> Unit)? = null

    var registerCalledCount = 0
    override fun register(observer: (LoadUiState) -> Unit) {
        registerCalledCount++
        observerCached = observer
        if (uiStateCached != null) {
            observerCached!!.invoke(uiStateCached!!)
            uiStateCached = null
        }
    }

    var unregisterCalledCount = 0
    override fun unregister() {
        unregisterCalledCount++
        observerCached = null
    }

    val postUiStatesListCalled = mutableListOf<LoadUiState>()
    override fun postUiState(uiState: LoadUiState) {
        postUiStatesListCalled.add(uiState)
        if (observerCached == null) {
            uiStateCached = uiState
        } else {
            observerCached!!.invoke(uiState)
            uiStateCached = null
        }
    }
}