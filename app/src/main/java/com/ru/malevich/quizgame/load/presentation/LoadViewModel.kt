package com.ru.malevich.quizgame.load.presentation

import com.ru.malevich.quizgame.R
import com.ru.malevich.quizgame.core.di.ClearViewModel
import com.ru.malevich.quizgame.core.presentation.MyViewModel
import com.ru.malevich.quizgame.core.presentation.RunAsync
import com.ru.malevich.quizgame.load.data.BackendError
import com.ru.malevich.quizgame.load.data.LoadRepository
import com.ru.malevich.quizgame.load.data.NoInternetConnectionException

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
                heavyOperation = {
                    try {
                        val loadResult = repository.load()
                        clearViewModel.clear(this.javaClass)
                        LoadUiState.Success
                    } catch (e: Exception) {
                        when (e) {
                            is NoInternetConnectionException -> LoadUiState.ErrorRes()
                            is BackendError -> LoadUiState.Error(e.message)
                            else -> LoadUiState.ErrorRes(R.string.service_unavailable)
                        }
                    }
                },
                updateUi = { result ->
                    observable.postUiState(result)
                }
            )

        }
    }

}
