package com.ru.malevich.quizgame.load

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import com.ru.malevich.quizgame.game.TextUi
import org.hamcrest.Matcher

class ErrorUi(
    private val id: Int,
    text: String,
    containerIdMatcher: Matcher<View>,
    containerTypeMatcher: Matcher<View>
) : TextUi(id, text, containerIdMatcher, containerTypeMatcher) {
    fun waitTillDoesNotExist() {
        onView(isRoot()).perform(waitTillDoesNotExist(id, 4000))
    }

    fun waitTillVisible() {
        onView(isRoot()).perform(waitTillDisplayed(id, 4000))
    }
}
