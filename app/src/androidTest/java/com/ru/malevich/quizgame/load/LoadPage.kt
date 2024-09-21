package com.ru.malevich.quizgame.load

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.ru.malevich.quizgame.R
import com.ru.malevich.quizgame.game.ButtonUi
import org.hamcrest.Matcher

class LoadPage {
    private val containerIdMatcher: Matcher<View> = withParent(withId(R.id.loadContainer))
    private val containerTypeMatcher: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))

    private val progress = ProgressUi(
        containerIdMatcher = containerIdMatcher,
        containerTypeMatcher = containerTypeMatcher,
        )
    private val retryButton = ButtonUi(
        R.id.retryButton,
        R.string.retry,
        "#5358C5",
        containerIdMatcher,
        containerTypeMatcher
    )
    private val errorText = ErrorUi(
        R.id.error,
        R.string.no_internet_connection,
        containerIdMatcher,
        containerTypeMatcher
    )

    fun assertProgressState() {
        progress.assertVisible()
        retryButton.assertNotVisible()
        errorText.assertNotVisible()
    }

    fun waitTillError() {
        errorText.waitTillVisible()
    }

    fun assertErrorState() {
        progress.assertNotVisible()
        retryButton.assertVisible()
        errorText.assertVisible()
    }

    fun clickRetry() {
        retryButton.click()
    }

    fun waitTillGone() {
        errorText.waitTillDoesNotExist()
    }

}
