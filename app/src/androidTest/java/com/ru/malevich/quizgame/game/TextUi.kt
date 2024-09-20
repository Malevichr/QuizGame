package com.ru.malevich.quizgame.game

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher

open class TextUi(
    id: Int,
    text: String,
    containerIdMatcher: Matcher<View>,
    containerClassIdMatcher: Matcher<View>
) {
    protected val interaction: ViewInteraction = onView(
        allOf(
            containerIdMatcher,
            containerClassIdMatcher,
            withId(id),
            withText(text),
            isAssignableFrom(TextView::class.java)
        )
    )

    fun assertVisible() {
        interaction.check(matches(isDisplayed()))
    }

    fun assertNotVisible() {
        interaction.check(matches(not(isDisplayed())))
    }
}
