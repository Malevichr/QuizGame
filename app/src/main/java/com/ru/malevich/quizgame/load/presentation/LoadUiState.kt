package com.ru.malevich.quizgame.load.presentation

import com.ru.malevich.quizgame.R
import com.ru.malevich.quizgame.game.NavigateToGame
import com.ru.malevich.quizgame.views.error.ErrorUiState
import com.ru.malevich.quizgame.views.error.UpdateError
import com.ru.malevich.quizgame.views.visibilitybutton.UpdateVisibility
import com.ru.malevich.quizgame.views.visibilitybutton.VisibilityUiState

interface LoadUiState {
    fun show(
        errorTextView: UpdateError,
        retryButton: UpdateVisibility,
        progressBar: UpdateVisibility
    )

    fun navigate(navigateToGame: NavigateToGame) = Unit

    abstract class Abstract(
        private val errorUiState: ErrorUiState,
        private val retryVisibility: VisibilityUiState,
        private val progressVisibility: VisibilityUiState
    ) : LoadUiState {
        override fun show(
            errorTextView: UpdateError,
            retryButton: UpdateVisibility,
            progressBar: UpdateVisibility
        ) {
            errorTextView.update(errorUiState)
            retryButton.update(retryVisibility)
            progressBar.update(progressVisibility)
        }
    }

    data class Error(private val message: String) : Abstract(
        errorUiState = ErrorUiState.Show(R.string.no_internet_connection, message),
        retryVisibility = VisibilityUiState.Visible,
        progressVisibility = VisibilityUiState.Gone
    )

    object Progress : Abstract(
        errorUiState = ErrorUiState.Hide,
        retryVisibility = VisibilityUiState.Gone,
        progressVisibility = VisibilityUiState.Visible
    )

    object Success : Abstract(
        errorUiState = ErrorUiState.Hide,
        retryVisibility = VisibilityUiState.Gone,
        progressVisibility = VisibilityUiState.Gone
    ) {
        override fun navigate(navigateToGame: NavigateToGame) {
            navigateToGame.navigateToGame()
        }
    }

}
