package com.ru.malevich.quizgame

class GameViewModel(private val repository: GameRepository) {
    fun chooseFirst(): GameUiState {
        repository.saveUserChoice(0)
        val data = repository.questionAndChoices()
        return GameUiState.ChoiceMade(
            data.question,
            listOf(
                ChoiceUiState.NotAvailableToChoose(data.listOf[0]),
                ChoiceUiState.AvailableToChoose(data.listOf[1]),
                ChoiceUiState.AvailableToChoose(data.listOf[2]),
                ChoiceUiState.AvailableToChoose(data.listOf[3]),
            )
        )
    }

    fun chooseSecond(): GameUiState {
        repository.saveUserChoice(1)
        val data = repository.questionAndChoices()
        return GameUiState.ChoiceMade(
            data.question,
            listOf(
                ChoiceUiState.AvailableToChoose(data.listOf[0]),
                ChoiceUiState.NotAvailableToChoose(data.listOf[1]),
                ChoiceUiState.AvailableToChoose(data.listOf[2]),
                ChoiceUiState.AvailableToChoose(data.listOf[3]),
            )
        )
    }

    fun chooseThird(): GameUiState {
        repository.saveUserChoice(2)
        val data = repository.questionAndChoices()
        return GameUiState.ChoiceMade(
            data.question,
            listOf(
                ChoiceUiState.AvailableToChoose(data.listOf[0]),
                ChoiceUiState.AvailableToChoose(data.listOf[1]),
                ChoiceUiState.NotAvailableToChoose(data.listOf[2]),
                ChoiceUiState.AvailableToChoose(data.listOf[3]),
            )
        )
    }

    fun chooseForth(): GameUiState {
        repository.saveUserChoice(3)
        val data = repository.questionAndChoices()
        return GameUiState.ChoiceMade(
            data.question,
            listOf(
                ChoiceUiState.AvailableToChoose(data.listOf[0]),
                ChoiceUiState.AvailableToChoose(data.listOf[1]),
                ChoiceUiState.AvailableToChoose(data.listOf[2]),
                ChoiceUiState.NotAvailableToChoose(data.listOf[3]),
            )
        )
    }

    fun check(): GameUiState {
        val correctAndUserChoiceIndexes = repository.check()
        val data = repository.questionAndChoices()
        val listOfStates: List<ChoiceUiState> = List(4) { i ->
            when (i) {
                correctAndUserChoiceIndexes.correctIndex -> ChoiceUiState.Correct(data.listOf[i])
                correctAndUserChoiceIndexes.userChoiceIndex -> ChoiceUiState.NotCorrect(data.listOf[i])
                else -> ChoiceUiState.NotAvailableToChoose(data.listOf[i])
            }
        }
        return GameUiState.AnswerCheckedState(
            data.question,
            listOfStates
        )
    }

    fun next(): GameUiState {
        repository.next()
        return init()
    }

    fun init(): GameUiState {
        val data = repository.questionAndChoices()
        return GameUiState.AskedQuestion(
            data.question,
            data.listOf
        )
    }

}
