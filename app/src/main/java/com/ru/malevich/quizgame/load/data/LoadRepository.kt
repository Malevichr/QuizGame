package com.ru.malevich.quizgame.load.data

import com.ru.malevich.quizgame.load.data.cache.ClearDatabase
import com.ru.malevich.quizgame.load.data.cache.IncorrectCache
import com.ru.malevich.quizgame.load.data.cache.QuestionAndChoicesDao
import com.ru.malevich.quizgame.load.data.cache.QuestionCache
import com.ru.malevich.quizgame.load.data.cloud.QuizService
import kotlinx.coroutines.delay

interface LoadRepository {

    suspend fun load(): LoadResult

    class Base(
        private val service: QuizService,
        private val dao: QuestionAndChoicesDao,
        private val size: Int,
        private val clearDatabase: ClearDatabase
    ) : LoadRepository {
        override suspend fun load(): LoadResult {
            try {
                val result = service.questionAndChoices(size).execute()
                if (result.isSuccessful) {
                    val body = result.body()!!
                    if (body.responseCode == 0) {
                        val list = body.dataList
                        if (list.isEmpty()) {
                            return LoadResult.Error("empty Data")
                        } else {
                            clearDatabase.clear()
                            val questions: List<QuestionCache> =
                                body.dataList.mapIndexed { index, data ->
                                    val incorrects: List<IncorrectCache> =
                                        data.incorrectAnswers.map {
                                            IncorrectCache(questionId = index, choice = it)
                                        }
                                    dao.saveIncorrects(incorrects)
                                    QuestionCache(index, data.question, data.correctAnswer)
                                }
                            dao.saveQuestions(questions)

                            return LoadResult.Success
                        }
                    } else
                        return LoadResult.Error("error")

                } else
                    return LoadResult.Error("error")
            } catch (e: Exception) {
                return LoadResult.Error(e.message ?: "error")
            }
        }
    }

    class Fake(
    ) : LoadRepository {
        private var count = 0
        override suspend fun load(): LoadResult {
            if (count == 0) {
                delay(1000)
                count++
                return LoadResult.Error("error")
            } else {
                delay(1000)
                count--
                return LoadResult.Success
            }
        }
    }
}

