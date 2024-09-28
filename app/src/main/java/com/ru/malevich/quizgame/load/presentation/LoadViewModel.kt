package com.ru.malevich.quizgame.load.presentation

import com.ru.malevich.quizgame.MyViewModel
import com.ru.malevich.quizgame.RunAsync
import com.ru.malevich.quizgame.load.data.LoadRepository

class LoadViewModel(
    private val repository: LoadRepository,
    private val observable: UiObservable,
    private val runAsync: RunAsync = RunAsync.Base()
) : MyViewModel {
    fun load(isFirstRun: Boolean = true) {
        if (isFirstRun) {
            observable.postUiState(LoadUiState.Progress)
            runAsync.handleAsync({
                Thread.sleep(2000)
                val loadResult = repository.load()
                if (loadResult.isSuccessful())
                    LoadUiState.Success
                else
                    LoadUiState.Error(loadResult.message())
            }) { result ->
                observable.postUiState(result)
            }

        }
    }

    fun startUpdates(observer: (LoadUiState) -> Unit) {
        observable.register(observer)
    }

    fun stopUpdates() {
        observable.unregister()
    }
}
