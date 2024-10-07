package com.ru.malevich.quizgame

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ru.malevich.quizgame.load.data.cache.IncorrectCache
import com.ru.malevich.quizgame.load.data.cache.QuestionAndChoicesDao
import com.ru.malevich.quizgame.load.data.cache.QuestionAndChoicesDatabase
import com.ru.malevich.quizgame.load.data.cache.QuestionCache
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomTest {
    private lateinit var dao: QuestionAndChoicesDao
    private lateinit var db: QuestionAndChoicesDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            QuestionAndChoicesDatabase::class.java
        ).build()
        dao = db.dao()
    }

    @Test
    fun test() = runBlocking {
        dao.saveQuestions(
            listOf(
                QuestionCache(1, "1", "one"),
                QuestionCache(2, "2", "two"),
            )
        )
        dao.saveIncorrects(
            listOf(
                IncorrectCache(questionId = 1, choice = "1 incorrect one"),
                IncorrectCache(questionId = 1, choice = "1 incorrect two"),

                IncorrectCache(questionId = 2, choice = "2 incorrect one"),
                IncorrectCache(questionId = 2, choice = "2 incorrect two"),
                IncorrectCache(questionId = 2, choice = "2 incorrect three"),
            )
        )
        var actual: Any = dao.question(1)
        var expected: Any = QuestionCache(1, "1", "one")
        assertEquals(actual, expected)

        actual = dao.question(2)
        expected = QuestionCache(2, "2", "two")
        assertEquals(actual, expected)

        actual = dao.incorects(1)
        expected = listOf(
            IncorrectCache(questionId = 1, choice = "1 incorrect one"),
            IncorrectCache(questionId = 1, choice = "1 incorrect two"),
        )
        assertEquals(actual, expected)

        actual = dao.incorects(2).toSet()
        expected = listOf(
            IncorrectCache(questionId = 2, choice = "2 incorrect one"),
            IncorrectCache(questionId = 2, choice = "2 incorrect three"),
            IncorrectCache(questionId = 2, choice = "2 incorrect two"),
        ).toSet()
        assertEquals(actual, expected)
    }

    @After
    fun closeDb() {
        db.close()
    }

}