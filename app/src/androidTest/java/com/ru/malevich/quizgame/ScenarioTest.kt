package com.ru.malevich.quizgame

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ru.malevich.quizgame.game.GamePage
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ScenarioTest {
    private lateinit var gamePage: GamePage

    @Before
    fun setup(){
        gamePage = GamePage(question = "What color is this sky?", choices = listOf(
            "blue", "green", "red", "yellow",
        ))
    }

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    /**
     * QGTC-01
     */
    @Test
    fun testCaseNumber1() {
        gamePage.assertAskedQuestionState()

        gamePage.clickFirstChoice()
        gamePage.assertFirstChoiceMadeState()

        gamePage.clickCheck()
        gamePage.assertAnswerCheckedStateFirstIsCorrect()
    }
    /**
     * QGTS-02
     */
    @Test
    fun testCaseNumber2(){
        gamePage.assertAskedQuestionState()

        gamePage.clickFirstChoice()
        gamePage.assertFirstChoiceMadeState()

        gamePage.clickSecondChoice()
        gamePage.assertSecondChoiceMadeState()

        gamePage.clickCheck()
        gamePage.assertAnswerCheckedStateFirstIsCorrectSecondIsIncorrect()

        gamePage.clickNext()
        gamePage = GamePage(question = "What color is the grass?", choices = listOf(
            "green", "blue", "red", "yellow",
        ))
        gamePage.assertAskedQuestionState()
    }
}