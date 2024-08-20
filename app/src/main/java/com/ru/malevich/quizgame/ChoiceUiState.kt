package com.ru.malevich.quizgame

import android.graphics.Color
import androidx.appcompat.widget.AppCompatButton
import java.io.Serializable

interface ChoiceUiState : Serializable {
    fun update(button: AppCompatButton)
    abstract class Abstract(
        private val text: String,
        private val color: String,
        private val isClickable: Boolean,
        private val isEnabled: Boolean
    ) : ChoiceUiState {
        override fun update(button: AppCompatButton) {
            button.text = text
            button.isClickable = isClickable
            button.isEnabled = isEnabled
            button.setBackgroundColor(Color.parseColor(color))
        }
    }

    data class NotAvailableToChoose(private val text: String) : Abstract(
        text = text,
        color = "#5F6E79",
        isClickable = false,
        isEnabled = false
    )

    data class AvailableToChoose(private val text: String) : Abstract(
        text = text,
        color = "#58B6FF",
        isClickable = true,
        isEnabled = true
    )

    data class Correct(private val text: String) : Abstract(
        text = text,
        color = "#2EE521",
        isClickable = false,
        isEnabled = true
    )

    data class NotCorrect(private val text: String) : Abstract(
        text = text,
        color = "#EE2929",
        isClickable = false,
        isEnabled = true
    )
}