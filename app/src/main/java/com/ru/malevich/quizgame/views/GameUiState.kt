package com.ru.malevich.quizgame.views

import com.ru.malevich.quizgame.views.choicebutton.ChoiceUiState
import com.ru.malevich.quizgame.views.choicebutton.UpdateChoiceButton
import com.ru.malevich.quizgame.views.questiontextview.UpdateText
import com.ru.malevich.quizgame.views.visibilitybutton.UpdateVisibility
import com.ru.malevich.quizgame.views.visibilitybutton.VisibilityUiState
import java.io.Serializable

interface GameUiState : Serializable {

    fun update(
        questionTextView: UpdateText,
        firstChoiceButton: UpdateChoiceButton,
        secondChoiceButton: UpdateChoiceButton,
        thirdChoiceButton: UpdateChoiceButton,
        forthChoiceButton: UpdateChoiceButton,
        nextButton: UpdateVisibility,
        checkButton: UpdateVisibility
    )

    object Empty : GameUiState {
        override fun update(
            questionTextView: UpdateText,
            firstChoiceButton: UpdateChoiceButton,
            secondChoiceButton: UpdateChoiceButton,
            thirdChoiceButton: UpdateChoiceButton,
            forthChoiceButton: UpdateChoiceButton,
            nextButton: UpdateVisibility,
            checkButton: UpdateVisibility
        ) = Unit

    }
    data class AskedQuestion(
        private val question: String,
        private val choices: List<String>
    ) : GameUiState {
        override fun update(
            questionTextView: UpdateText,
            firstChoiceButton: UpdateChoiceButton,
            secondChoiceButton: UpdateChoiceButton,
            thirdChoiceButton: UpdateChoiceButton,
            forthChoiceButton: UpdateChoiceButton,
            nextButton: UpdateVisibility,
            checkButton: UpdateVisibility
        ) {
            questionTextView.update(question)
            firstChoiceButton.update(ChoiceUiState.Initial(choices[0]))
            secondChoiceButton.update(ChoiceUiState.Initial(choices[1]))
            thirdChoiceButton.update(ChoiceUiState.Initial(choices[2]))
            forthChoiceButton.update(ChoiceUiState.Initial(choices[3]))
            nextButton.update(VisibilityUiState.Gone)
            checkButton.update(VisibilityUiState.Invisible)
        }
    }

    data class ChoiceMade(
        private val choices: List<ChoiceUiState>
    ) : GameUiState {
        override fun update(
            questionTextView: UpdateText,
            firstChoiceButton: UpdateChoiceButton,
            secondChoiceButton: UpdateChoiceButton,
            thirdChoiceButton: UpdateChoiceButton,
            forthChoiceButton: UpdateChoiceButton,
            nextButton: UpdateVisibility,
            checkButton: UpdateVisibility
        ) {
            firstChoiceButton.update(choices[0])
            secondChoiceButton.update(choices[1])
            thirdChoiceButton.update(choices[2])
            forthChoiceButton.update(choices[3])
            checkButton.update(VisibilityUiState.Visible)
        }
    }

    data class AnswerCheckedState(
        private val choices: List<ChoiceUiState>
    ) : GameUiState {
        override fun update(
            questionTextView: UpdateText,
            firstChoiceButton: UpdateChoiceButton,
            secondChoiceButton: UpdateChoiceButton,
            thirdChoiceButton: UpdateChoiceButton,
            forthChoiceButton: UpdateChoiceButton,
            nextButton: UpdateVisibility,
            checkButton: UpdateVisibility
        ) {
            firstChoiceButton.update(choices[0])
            secondChoiceButton.update(choices[1])
            thirdChoiceButton.update(choices[2])
            forthChoiceButton.update(choices[3])
            nextButton.update(VisibilityUiState.Visible)
            checkButton.update(VisibilityUiState.Gone)
        }
    }
}

