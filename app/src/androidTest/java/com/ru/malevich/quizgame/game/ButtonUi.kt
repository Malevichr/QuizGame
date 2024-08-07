package com.ru.malevich.quizgame.game

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.ru.malevich.quizgame.ButtonColorMatcher
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher

class ButtonUi(
    id: Int,
    textResId: Int,
    colorHex: String,
    containerIdMatcher: Matcher<View>,
    containerClassIdMatcher: Matcher<View>
) : AbstractButton(
    onView(
        allOf(
            containerIdMatcher,
            containerClassIdMatcher,
            withId(id),
            withText(textResId),
            ButtonColorMatcher(colorHex)

        )
    )
) {
    fun assertNotVisible() {
        interaction.check(matches(not(isDisplayed())))
    }

    fun assertVisible() {
        interaction.check(matches(isDisplayed()))
    }
}

abstract class AbstractButton(
    protected val interaction: ViewInteraction
) {
    fun click() {
        interaction.perform(androidx.test.espresso.action.ViewActions.click())
    }
}