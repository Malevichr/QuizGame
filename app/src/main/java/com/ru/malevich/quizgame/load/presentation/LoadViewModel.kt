package com.ru.malevich.quizgame.load.presentation

import com.ru.malevich.quizgame.core.di.ClearViewModel
import com.ru.malevich.quizgame.core.presentation.MyViewModel
import com.ru.malevich.quizgame.core.presentation.RunAsync
import com.ru.malevich.quizgame.load.data.LoadRepository

class LoadViewModel(
    private val repository: LoadRepository,
    observable: LoadUiObservable,
    private val runAsync: RunAsync,
    private val clearViewModel: ClearViewModel
) : MyViewModel.Abstract<LoadUiState>(observable) {
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

}
