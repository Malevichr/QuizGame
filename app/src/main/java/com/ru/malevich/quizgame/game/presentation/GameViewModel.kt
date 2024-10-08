package com.ru.malevich.quizgame.game.presentation

import com.ru.malevich.quizgame.core.di.ClearViewModel
import com.ru.malevich.quizgame.core.presentation.MyViewModel
import com.ru.malevich.quizgame.core.presentation.RunAsync
import com.ru.malevich.quizgame.game.data.GameRepository
import com.ru.malevich.quizgame.views.choicebutton.ChoiceUiState

class GameViewModel(
    observable: GameUiObservable,
    private val repository: GameRepository,
    private val clearViewModel: ClearViewModel,
    private val runAsync: RunAsync
) : MyViewModel.Abstract<GameUiState>(observable) {

    private val updateUi = { it: GameUiState ->
        observable.postUiState(it)
    }

    fun chooseFirst(): GameUiState {
        repository.saveUserChoice(0)
        return GameUiState.ChoiceMade(
            listOf(
                ChoiceUiState.NotAvailableToChoose,
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.AvailableToChoose,
            )
        )
    }

    fun chooseSecond(): GameUiState {
        repository.saveUserChoice(1)
        return GameUiState.ChoiceMade(
            listOf(
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.NotAvailableToChoose,
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.AvailableToChoose,
            )
        )
    }

    fun chooseThird(): GameUiState {
        repository.saveUserChoice(2)
        return GameUiState.ChoiceMade(
            listOf(
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.NotAvailableToChoose,
                ChoiceUiState.AvailableToChoose,
            )
        )
    }

    fun chooseForth(): GameUiState {
        repository.saveUserChoice(3)
        return GameUiState.ChoiceMade(
            listOf(
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.NotAvailableToChoose,
            )
        )
    }

    fun check() {
        runAsync.handleAsync(
            viewModelScope,
            heavyOperation = {
                val correctAndUserChoiceIndexes = repository.check()

                val listOfStates: List<ChoiceUiState> = List(4) { i ->
                    when (i) {
                        correctAndUserChoiceIndexes.correctIndex -> ChoiceUiState.Correct
                        correctAndUserChoiceIndexes.userChoiceIndex -> ChoiceUiState.NotCorrect
                        else -> ChoiceUiState.NotAvailableToChoose
                    }
                }
                GameUiState.AnswerCheckedState(
                    listOfStates
                )
            }, updateUi
        )
    }

    fun next() {
        if (repository.isLastQuestion()) {
            runAsync.handleAsync(
                viewModelScope,
                heavyOperation = {
                    repository.clearProgress()
                    clearViewModel.clear(GameViewModel::class.java)
                    GameUiState.Finish
                }, updateUi
            )
        }
        else {
            repository.next()
            init()
        }
    }

    fun init(firstRun: Boolean = true) {
        runAsync.handleAsync(
            coroutineScope = viewModelScope,
            heavyOperation = {
                if (firstRun) {
                    val data = repository.questionAndChoices()
                    GameUiState.AskedQuestion(
                        data.question,
                        data.listOf
                    )
                } else
                    GameUiState.Empty
            }, updateUi
        )
    }
}
