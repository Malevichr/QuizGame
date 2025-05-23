package com.ru.malevich.quizgame.game

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.ru.malevich.quizgame.R
import org.hamcrest.Matcher

class GamePage(
    question: String,
    choices: List<String>
) {

    private val containerIdMatcher: Matcher<View> = withParent(withId(R.id.rootLayout))
    private val classIdMatcher: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))

    private val questionUi = TextUi(
        R.id.questionTextView,
        text = question,
        containerIdMatcher = containerIdMatcher,
        containerClassIdMatcher = classIdMatcher
    )

    private val choicesUiIdList = listOf(
        R.id.firstChoiceButton,
        R.id.secondChoiceButton,
        R.id.thirdChoiceButton,
        R.id.forthChoiceButton,
    )
    private val choicesUiList = choices.mapIndexed { index, text ->
        ChoiceUi(
            id = choicesUiIdList[index],
            text = text,
            containerIdMatcher = containerIdMatcher,
            containerClassIdMatcher = classIdMatcher
        )
    }
    private val checkUi = ButtonUi(
        id = R.id.checkButton,
        textResId = R.string.check,
        colorHex = "#C553BD",
        containerIdMatcher = containerIdMatcher,
        containerClassIdMatcher = classIdMatcher
    )
    private val nextUi = ButtonUi(
        id = R.id.nextButton,
        textResId = R.string.next,
        colorHex = "#5358C5",
        containerIdMatcher = containerIdMatcher,
        containerClassIdMatcher = classIdMatcher
    )

    fun assertAskedQuestionState() {
        questionUi.assertVisible()
        choicesUiList.forEach {
            it.assertAvailableToChooseState()
        }
        checkUi.assertNotVisible()
        nextUi.assertNotVisible()
    }

    fun clickFirstChoice() {
        choicesUiList.first().click()
    }

    fun assertFirstChoiceMadeState() {
        questionUi.assertVisible()
        choicesUiList.first().assertNotAvailableToChooseState()
        for (i in 1 until choicesUiList.size) {
            choicesUiList[i].assertAvailableToChooseState()
        }
        checkUi.assertVisible()
        nextUi.assertNotVisible()
    }

    fun clickCheck() {
        checkUi.click()
    }

    fun assertAnswerCheckedStateFirstIsCorrect() {
        questionUi.assertVisible()
        choicesUiList.first().assertCorrectState()
        for (i in 1 until choicesUiList.size) {
            choicesUiList[i].assertNotAvailableToChooseState()
        }
        checkUi.assertNotVisible()
        nextUi.assertVisible()
    }

    fun clickSecondChoice() {
        choicesUiList[1].click()
    }

    fun assertSecondChoiceMadeState() {
        questionUi.assertVisible()

        choicesUiList.forEachIndexed { index, choiceUi ->
            if (index == 1)
                choiceUi.assertNotAvailableToChooseState()
            else
                choiceUi.assertAvailableToChooseState()
        }
        checkUi.assertVisible()
        nextUi.assertNotVisible()
    }

    fun assertAnswerCheckedStateFirstIsCorrectSecondIsIncorrect() {
        questionUi.assertVisible()

        choicesUiList.forEachIndexed { index, choiceUi ->
            if (index == 0)
                choiceUi.assertCorrectState()
            else if (index == 1)
                choiceUi.assertIncorrectState()
            else
                choiceUi.assertNotAvailableToChooseState()
        }
        checkUi.assertNotVisible()
        nextUi.assertVisible()
    }

    fun clickNext() {
        nextUi.click()
    }

    fun assertDoesNotExist() {
        onView(containerIdMatcher).check(doesNotExist())
    }
}