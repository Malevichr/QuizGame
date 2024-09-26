package com.ru.malevich.quizgame.load

import android.view.View
import com.ru.malevich.quizgame.R
import com.ru.malevich.quizgame.game.NavigateToGame
import com.ru.malevich.quizgame.views.error.ErrorUiState
import com.ru.malevich.quizgame.views.error.UpdateError
import com.ru.malevich.quizgame.views.visibilitybutton.UpdateVisibility

interface LoadUiState {
    fun show(
        errorTextView: UpdateError,
        retryButton: UpdateVisibility,
        progressBar: UpdateVisibility
    )

    fun navigate(navigateToGame: NavigateToGame) = Unit

    abstract class Abstract(
        private val errorUiState: ErrorUiState,
        private val retryVisibility: Int,
        private val progressVisibility: Int
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
        errorUiState = ErrorUiState.Show(R.string.no_internet_connection),
        retryVisibility = View.VISIBLE,
        progressVisibility = View.GONE
    )

    object Progress : Abstract(
        ErrorUiState.Hide,
        View.GONE,
        View.VISIBLE
    )

    object Success : Abstract(
        ErrorUiState.Hide,
        View.GONE,
        View.GONE
    ) {
        override fun navigate(navigateToGame: NavigateToGame) {
            navigateToGame.navigateToGame()
        }
    }

}
