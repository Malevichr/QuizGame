package com.ru.malevich.quizgame

import com.ru.malevich.quizgame.views.GameUiState
import com.ru.malevich.quizgame.views.choicebutton.ChoiceUiState
import org.junit.Assert.assertEquals
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
            choices = listOf(
                ChoiceUiState.NotAvailableToChoose,
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.AvailableToChoose
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.check()
        expected = GameUiState.AnswerCheckedState(
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.Correct,
                ChoiceUiState.NotAvailableToChoose,
                ChoiceUiState.NotAvailableToChoose,
                ChoiceUiState.NotAvailableToChoose
            )
        )
        assertEquals(expected, actual)

    }

    /**
     * QGTC-02
     */
    @Test
    fun caseNumber2() {
        var actual: GameUiState = viewModel.init(true)
        var expected: GameUiState = GameUiState.AskedQuestion(
            question = "q1",
            choices = listOf("c1", "c2", "c3", "c4")
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseFirst()
        expected = GameUiState.ChoiceMade(
            choices = listOf(
                ChoiceUiState.NotAvailableToChoose,
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.AvailableToChoose
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseSecond()
        expected = GameUiState.ChoiceMade(
            choices = listOf(
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.NotAvailableToChoose,
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.AvailableToChoose
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseThird()
        expected = GameUiState.ChoiceMade(
            choices = listOf(
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.NotAvailableToChoose,
                ChoiceUiState.AvailableToChoose
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseForth()
        expected = GameUiState.ChoiceMade(
            choices = listOf(
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.AvailableToChoose,
                ChoiceUiState.NotAvailableToChoose
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.check()
        expected = GameUiState.AnswerCheckedState(
            choices = listOf(
                ChoiceUiState.Correct,
                ChoiceUiState.NotAvailableToChoose,
                ChoiceUiState.NotAvailableToChoose,
                ChoiceUiState.NotCorrect
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
    private val list: List<QuestionAndChoices> = listOf(
        QuestionAndChoices(
            question = "q1",
            listOf("c1", "c2", "c3", "c4"),
            correctIndex = 0
        ),
        QuestionAndChoices(
            question = "q2",
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
            correctIndex = questionAndChoices().correctIndex,
            userChoiceIndex = userChoiceIndex
        )
    }

    override fun next() {
        index = ++index % list.size
        userChoiceIndex = -1
    }
}