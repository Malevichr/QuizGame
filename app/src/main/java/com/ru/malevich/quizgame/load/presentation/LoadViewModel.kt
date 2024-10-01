package com.ru.malevich.quizgame.load.presentation

import com.ru.malevich.quizgame.MyViewModel
import com.ru.malevich.quizgame.RunAsync
import com.ru.malevich.quizgame.di.ClearViewModel
import com.ru.malevich.quizgame.load.data.LoadRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class LoadViewModel(
    private val repository: LoadRepository,
    private val observable: UiObservable,
    private val runAsync: RunAsync = RunAsync.Base(),
    private val clearViewModel: ClearViewModel
) : MyViewModel {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    fun load(isFirstRun: Boolean = true) {
        if (isFirstRun) {
            observable.postUiState(LoadUiState.Progress)
            runAsync.handleAsync(
                viewModelScope,
                {
                    val loadResult = repository.load()
                    if (loadResult.isSuccessful()) {
                        clearViewModel.clear(this.javaClass)
                        LoadUiState.Success
                    } else
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
