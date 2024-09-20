package com.ru.malevich.quizgame.gameover

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.ru.malevich.quizgame.R
import com.ru.malevich.quizgame.views.statstextview.StatsTextView
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

class StatsUi(
    incorrects: Int,
    corrects: Int,
    containerIdMatcher: Matcher<View>,
    containerTypeMatcher: Matcher<View>
) {
    private val interaction: ViewInteraction =
        onView(
            allOf(
                containerIdMatcher,
                containerTypeMatcher,
                withId(R.id.statsTextView),
                withText(
                    "Game over\nCorrects: $corrects\nIncorrects: $incorrects"
                ),
                isAssignableFrom(StatsTextView::class.java)
            )
        )

    fun assertVisible() {
        interaction.check(matches(isDisplayed()))
    }

}
