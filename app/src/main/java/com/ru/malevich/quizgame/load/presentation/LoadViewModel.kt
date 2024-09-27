package com.ru.malevich.quizgame.load.presentation

import com.ru.malevich.quizgame.MyViewModel
import com.ru.malevich.quizgame.load.data.LoadRepository

class LoadViewModel(
    private val repository: LoadRepository,
    private val observable: UiObservable
) : MyViewModel {
    fun load(isFirstRun: Boolean = true) {
        if (isFirstRun) {
            observable.postUiState(LoadUiState.Progress)
            Thread {
                Thread.sleep(2000)
                repository.load { loadResult ->
                    if (loadResult.isSuccessful())
                        observable.postUiState(LoadUiState.Success)
                    else
                        observable.postUiState(LoadUiState.Error(loadResult.message()))
                }
            }.start()

        }
    }

    fun startUpdates(observer: (LoadUiState) -> Unit) {
        observable.register(observer)
    }

    fun stopUpdates() {
        observable.unregister()
    }
}
