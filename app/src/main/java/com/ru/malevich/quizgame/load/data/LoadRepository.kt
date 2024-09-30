package com.ru.malevich.quizgame.load.data

import com.google.gson.Gson
import com.ru.malevich.quizgame.StringCache

interface LoadRepository {

    suspend fun load(): LoadResult

    class Base(
        private val dataCache: StringCache,
        private val parseQuestionAndChoices: ParseQuestionAndChoices = ParseQuestionAndChoices.Base(
            Gson()
        ),
        private val service: QuizService
    ) : LoadRepository {

        override suspend fun load(): LoadResult {
            try {
                val result = service.questionAndChoices().execute()
                if (result.isSuccessful) {
                    val body = result.body()!!
                    if (body.responseCode == 0) {
                        val list = body.dataList
                        if (list.isEmpty()) {
                            return LoadResult.Error("empty Data")
                        } else {
                            val data = parseQuestionAndChoices.toString(body)
                            dataCache.save(data)
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
}


interface ParseQuestionAndChoices {
    fun toString(data: Any): String
    fun parse(source: String): QuizResponse
    class Base(
        private val gson: Gson
    ) : ParseQuestionAndChoices {
        override fun toString(data: Any): String {
            return gson.toJson(data)
        }

        override fun parse(source: String): QuizResponse {
            return gson.fromJson(source, QuizResponse::class.java)
        }
    }
}