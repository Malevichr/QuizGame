package com.ru.malevich.quizgame.gameover

import com.ru.malevich.quizgame.views.statstextview.StatsUiState
import org.junit.Assert.assertEquals
import org.junit.Test


class GameOverViewModelTest {
    @Test
    fun test() {
        val repository = FakeGameOverRepository()
        val viewModel = GameOverViewModel(repository = repository)

        val actual: StatsUiState = viewModel.statsUiState()
        val expected = StatsUiState.Base(2, 3)
        assertEquals(expected, actual)
    }
}

private class FakeGameOverRepository : GameOverRepository {
    override fun stats(): Pair<Int, Int> = Pair(2, 3)

}