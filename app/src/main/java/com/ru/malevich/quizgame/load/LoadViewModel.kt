package com.ru.malevich.quizgame.load

import com.ru.malevich.quizgame.MyViewModel

class LoadViewModel(
    private val repository: LoadRepository,
    private val observable: UiObservable
) : MyViewModel {
    fun load(isFirstRun: Boolean = true) {
        if (isFirstRun) {
            observable.postUiState(LoadUiState.Progress)
            repository.load { loadResult ->
                if (loadResult.isSuccessful())
                    observable.postUiState(LoadUiState.Success)
                else
                    observable.postUiState(LoadUiState.Error(loadResult.message()))

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
