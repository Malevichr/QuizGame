package com.ru.malevich.quizgame

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class GameViewModelTest {
    private lateinit var viewModel: GameViewModel

    @Before
    fun setup() {
        viewModel = GameViewModel(repository = FakeRepository())
    }

    /**
     * QGTC-01
     */
    @Test
    fun caseNumber1() {
        var actual: GameUiState = viewModel.init()
        var expected: GameUiState = GameUiState.AskedQuestion(
            question = "q1",
            choices = listOf("c1", "c2", "c3", "c4")
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseFirst()
        expected = GameUiState.ChoiceMade(
            question = "q1",
            choices = listOf(
                ChoiceUiState.NotAvailableToChoose(text = "c1"),
                ChoiceUiState.AvailableToChoose(text = "c2"),
                ChoiceUiState.AvailableToChoose(text = "c3"),
                ChoiceUiState.AvailableToChoose(text = "c4")
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.check()
        expected = GameUiState.AnswerCheckedState(
            question = "q1",
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.Correct(text = "c1"),
                ChoiceUiState.NotAvailableToChoose(text = "c2"),
                ChoiceUiState.NotAvailableToChoose(text = "c3"),
                ChoiceUiState.NotAvailableToChoose(text = "c4")
            )
        )
        assertEquals(expected, actual)

    }

    /**
     * QGTC-02
     */
    @Test
    fun caseNumber2() {
        var actual: GameUiState = viewModel.init()
        var expected: GameUiState = GameUiState.AskedQuestion(
            question = "q1",
            choices = listOf("c1", "c2", "c3", "c4")
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseFirst()
        expected = GameUiState.ChoiceMade(
            question = "q1",
            choices = listOf(
                ChoiceUiState.NotAvailableToChoose(text = "c1"),
                ChoiceUiState.AvailableToChoose(text = "c2"),
                ChoiceUiState.AvailableToChoose(text = "c3"),
                ChoiceUiState.AvailableToChoose(text = "c4")
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseSecond()
        expected = GameUiState.ChoiceMade(
            question = "q1",
            choices = listOf(
                ChoiceUiState.AvailableToChoose(text = "c1"),
                ChoiceUiState.NotAvailableToChoose(text = "c2"),
                ChoiceUiState.AvailableToChoose(text = "c3"),
                ChoiceUiState.AvailableToChoose(text = "c4")
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseThird()
        expected = GameUiState.ChoiceMade(
            question = "q1",
            choices = listOf(
                ChoiceUiState.AvailableToChoose(text = "c1"),
                ChoiceUiState.AvailableToChoose(text = "c2"),
                ChoiceUiState.NotAvailableToChoose(text = "c3"),
                ChoiceUiState.AvailableToChoose(text = "c4")
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseForth()
        expected = GameUiState.ChoiceMade(
            question = "q1",
            choices = listOf(
                ChoiceUiState.AvailableToChoose(text = "c1"),
                ChoiceUiState.AvailableToChoose(text = "c2"),
                ChoiceUiState.AvailableToChoose(text = "c3"),
                ChoiceUiState.NotAvailableToChoose(text = "c4")
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.check()
        expected = GameUiState.AnswerCheckedState(
            question = "q1",
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.Correct(text = "c1"),
                ChoiceUiState.NotAvailableToChoose(text = "c2"),
                ChoiceUiState.NotAvailableToChoose(text = "c3"),
                ChoiceUiState.NotCorrect(text = "c4")
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = GameUiState.AskedQuestion(
            question = "q2",
            choices = listOf("c1", "c2", "c3", "c4")
        )
        assertEquals(expected, actual)
    }
}

private class FakeRepository : GameRepository {
    private val list: List<Any> = listOf<QuestionAndChoices>(
        QuestionAndChoices(
            question = "q1".reversed(),
            listOf("c1", "c2", "c3", "c4"),
            correctIndex = 0
        ),
        QuestionAndChoices(
            question = "q2".reversed(),
            listOf("c1", "c2", "c3", "c4"),
            correctIndex = 0
        ),
    )

    private var index: Int = 0

    override fun questionAndChoices(): QuestionAndChoices {
        return list[index]
    }

    private var userChoiceIndex = -1
    override fun saveUserChoice(index: Int) {
        userChoiceIndex = index
    }

    override fun check(): CorrectAndUserChoiceIndexes {
        return CorrectAndUserChoiceIndexes(
            correctIndex = questionAndChoices().correctIndex(),
            userChoiceIndex = userChoiceIndex
        )
    }

    override fun next() {
        index = ++index % list.size
        userChoiceIndex = -1
    }
}