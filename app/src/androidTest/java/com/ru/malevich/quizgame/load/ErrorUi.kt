package com.ru.malevich.quizgame.load

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher

class ErrorUi(
    private val id: Int,
    textResId: Int,
    containerIdMatcher: Matcher<View>,
    containerTypeMatcher: Matcher<View>
) {
    val interaction: ViewInteraction = onView(
        allOf(
            containerIdMatcher,
            containerTypeMatcher,
            withId(id),
            withText(textResId),
            isAssignableFrom(TextView::class.java)
        )
    )

    fun assertVisible() {
        interaction.check(matches(isDisplayed()))
    }

    fun assertNotVisible() {
        interaction.check(matches(not(isDisplayed())))
    }
    fun waitTillDoesNotExist() {
        onView(isRoot()).perform(waitTillDoesNotExist(id, 4000))
    }

    fun waitTillVisible() {
        onView(isRoot()).perform(waitTillDisplayed(id, 4000))
    }
}
