package com.ru.malevich.quizgame

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ru.malevich.quizgame.game.GamePage
import com.ru.malevich.quizgame.gameover.GameOverPage
import com.ru.malevich.quizgame.load.LoadPage
import com.ru.malevich.quizgame.main.presentation.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ScenarioTest {
    private lateinit var gamePage: GamePage
    private fun recreate() {
        activityScenarioRule.scenario.recreate()
    }
    private fun doWithRecreate(operation: () -> Unit) {
        operation.invoke()
        recreate()
        operation.invoke()
    }
    @Before
    fun setup(){
        gamePage = GamePage(
            question = "What color is the sky?", choices = listOf(
            "blue", "green", "red", "yellow",
        ))
    }

    @After
    fun clearPrefs() {
        val sharedPreferences = ApplicationProvider
            .getApplicationContext<Context>()
            .getSharedPreferences("quizAppData", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    /**
     * QGTC-01
     */
    @Test
    fun testCaseNumber1() {
        testCaseNumber4()

        gamePage.clickFirstChoice()
        gamePage.assertFirstChoiceMadeState()
        recreate()
        gamePage.assertFirstChoiceMadeState()


        gamePage.clickCheck()
        gamePage.assertAnswerCheckedStateFirstIsCorrect()
        activityScenarioRule.scenario.recreate()
        gamePage.assertAnswerCheckedStateFirstIsCorrect()

    }
    /**
     * QGTS-02
     */
    @Test
    fun testCaseNumber2(){
        testCaseNumber4()

        gamePage.clickFirstChoice()
        gamePage.assertFirstChoiceMadeState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertFirstChoiceMadeState()

        gamePage.clickSecondChoice()
        gamePage.assertSecondChoiceMadeState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertSecondChoiceMadeState()

        gamePage.clickCheck()
        gamePage.assertAnswerCheckedStateFirstIsCorrectSecondIsIncorrect()
        activityScenarioRule.scenario.recreate()
        gamePage.assertAnswerCheckedStateFirstIsCorrectSecondIsIncorrect()

        gamePage.clickNext()
        gamePage = GamePage(question = "What color is the grass?", choices = listOf(
            "green", "blue", "red", "yellow",
        ))
        gamePage.assertAskedQuestionState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertAskedQuestionState()
    }

    /**
     * QGTC-03
     */
    @Test
    fun testCaseNumber3() {
        // region 2 incorrect
        testCaseNumber4()


        gamePage.clickSecondChoice()
        gamePage.assertSecondChoiceMadeState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertSecondChoiceMadeState()


        gamePage.clickCheck()
        gamePage.assertAnswerCheckedStateFirstIsCorrectSecondIsIncorrect()
        activityScenarioRule.scenario.recreate()
        gamePage.assertAnswerCheckedStateFirstIsCorrectSecondIsIncorrect()

        gamePage.clickNext()
        gamePage = GamePage(
            question = "What color is the grass?", choices = listOf(
                "green", "blue", "red", "yellow",
            )
        )

        gamePage.assertAskedQuestionState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertAskedQuestionState()

        gamePage.clickSecondChoice()
        gamePage.assertSecondChoiceMadeState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertSecondChoiceMadeState()


        gamePage.clickCheck()
        gamePage.assertAnswerCheckedStateFirstIsCorrectSecondIsIncorrect()
        activityScenarioRule.scenario.recreate()
        gamePage.assertAnswerCheckedStateFirstIsCorrectSecondIsIncorrect()

        gamePage.clickNext()
        gamePage.assertDoesNotExist()

        var gameOverPage = GameOverPage(
            incorrects = 2,
            corrects = 0
        )
        gameOverPage.assertInitialState()
        activityScenarioRule.scenario.recreate()
        gameOverPage.assertInitialState()

        gameOverPage.clickNewGame()
        gameOverPage.asserDoesNotExist()
        //endregion

        //region 1 correct 1 incorrect
        setup()
        testCaseNumber4()

        gamePage.assertAskedQuestionState()
        recreate()
        gamePage.assertAskedQuestionState()


        gamePage.clickSecondChoice()
        gamePage.assertSecondChoiceMadeState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertSecondChoiceMadeState()


        gamePage.clickCheck()
        gamePage.assertAnswerCheckedStateFirstIsCorrectSecondIsIncorrect()
        activityScenarioRule.scenario.recreate()
        gamePage.assertAnswerCheckedStateFirstIsCorrectSecondIsIncorrect()

        gamePage.clickNext()
        gamePage = GamePage(
            question = "What color is the grass?", choices = listOf(
                "green", "blue", "red", "yellow",
            )
        )

        gamePage.assertAskedQuestionState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertAskedQuestionState()

        gamePage.clickFirstChoice()
        gamePage.assertFirstChoiceMadeState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertFirstChoiceMadeState()


        gamePage.clickCheck()
        gamePage.assertAnswerCheckedStateFirstIsCorrect()
        activityScenarioRule.scenario.recreate()
        gamePage.assertAnswerCheckedStateFirstIsCorrect()

        gamePage.clickNext()
        gamePage.assertDoesNotExist()

        gameOverPage = GameOverPage(
            incorrects = 1,
            corrects = 1
        )
        gameOverPage.assertInitialState()
        activityScenarioRule.scenario.recreate()
        gameOverPage.assertInitialState()

        gameOverPage.clickNewGame()
        gameOverPage.asserDoesNotExist()
        //endregion

        //region 2 correct
        setup()
        testCaseNumber4()

        gamePage.assertAskedQuestionState()
        recreate()
        gamePage.assertAskedQuestionState()

        gamePage.clickFirstChoice()
        gamePage.assertFirstChoiceMadeState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertFirstChoiceMadeState()


        gamePage.clickCheck()
        gamePage.assertAnswerCheckedStateFirstIsCorrect()
        activityScenarioRule.scenario.recreate()
        gamePage.assertAnswerCheckedStateFirstIsCorrect()

        gamePage.clickNext()
        gamePage = GamePage(
            question = "What color is the grass?", choices = listOf(
                "green", "blue", "red", "yellow",
            )
        )

        gamePage.assertAskedQuestionState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertAskedQuestionState()

        gamePage.clickFirstChoice()
        gamePage.assertFirstChoiceMadeState()
        activityScenarioRule.scenario.recreate()
        gamePage.assertFirstChoiceMadeState()


        gamePage.clickCheck()
        gamePage.assertAnswerCheckedStateFirstIsCorrect()
        activityScenarioRule.scenario.recreate()
        gamePage.assertAnswerCheckedStateFirstIsCorrect()

        gamePage.clickNext()
        gamePage.assertDoesNotExist()

        gameOverPage = GameOverPage(
            incorrects = 0,
            corrects = 2
        )
        gameOverPage.assertInitialState()
        activityScenarioRule.scenario.recreate()
        gameOverPage.assertInitialState()
        //endregion
    }

    /**
     * QGTC-04
     */
    @Test
    fun testCaseNumber4() {
        val loadPage = LoadPage()
        doWithRecreate { loadPage.assertProgressState() }

        loadPage.waitTillError()

        doWithRecreate { loadPage.assertErrorState() }

        loadPage.clickRetry()

        doWithRecreate { loadPage.assertProgressState() }

        loadPage.waitTillGone()

        doWithRecreate { gamePage.assertAskedQuestionState() }
    }

}
