package com.ru.malevich.quizgame.views.error

import android.view.View
import java.io.Serializable

interface ErrorUiState : Serializable {
    fun update(errorText: UpdateError)

    object Hide : ErrorUiState {
        override fun update(errorText: UpdateError) {
            errorText.updateVisibility(View.GONE)
        }
    }

    data class Show(private val textResId: Int, private val message: String = "") : ErrorUiState {
        override fun update(errorText: UpdateError) {
            if (message == "")
                errorText.updateTextResId(textResId)
            else
                errorText.updateText(message)

            errorText.updateVisibility(View.VISIBLE)
        }
    }
}