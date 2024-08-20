package com.ru.malevich.quizgame

import android.view.View
import com.ru.malevich.quizgame.databinding.ActivityMainBinding
import java.io.Serializable

interface GameUiState : Serializable {
    fun update(binding: ActivityMainBinding)

    abstract class Abstract(
        private val questionText: String,
        private val choiceStateList: List<ChoiceUiState>,
        private val checkVisibility: Int,
        private val nextVisibility: Int
    ) : GameUiState {
        override fun update(binding: ActivityMainBinding) = with(binding) {
            questionTextView.text = questionText
            choiceStateList[0].update(firstChoiceButton)
            choiceStateList[1].update(secondChoiceButton)
            choiceStateList[2].update(thirdChoiceButton)
            choiceStateList[3].update(forthChoiceButton)
            checkButton.visibility = checkVisibility
            nextButton.visibility = nextVisibility
        }
    }

    data class AskedQuestion(
        private val question: String,
        private val choices: List<String>
    ) : Abstract(
        questionText = question,
        choiceStateList = choices.map {
            ChoiceUiState.AvailableToChoose(it)
        },
        checkVisibility = View.INVISIBLE,
        nextVisibility = View.GONE
    )
    data class ChoiceMade(
        private val question: String,
        private val choices: List<ChoiceUiState>
    ) : Abstract(
        questionText = question,
        choiceStateList = choices,
        checkVisibility = View.VISIBLE,
        nextVisibility = View.GONE
    )

    data class AnswerCheckedState(
        private val question: String,
        private val choices: List<ChoiceUiState>
    ) : Abstract(
        questionText = question,
        choiceStateList = choices,
        checkVisibility = View.GONE,
        nextVisibility = View.VISIBLE
    )
}

