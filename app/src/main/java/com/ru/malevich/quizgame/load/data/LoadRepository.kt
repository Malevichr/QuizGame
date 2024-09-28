package com.ru.malevich.quizgame.load.data

import android.os.NetworkOnMainThreadException
import com.google.gson.Gson
import com.ru.malevich.quizgame.StringCache
import java.net.HttpURLConnection
import java.net.URL

interface LoadRepository {

    fun load(): LoadResult

    class Base(
        private val dataCache: StringCache,
        private val parseQuestionAndChoices: ParseQuestionAndChoices = ParseQuestionAndChoices.Base(
            Gson()
        )
    ) : LoadRepository {
        private val url = "https://opentdb.com/api.php?amount=10&type=multiple"

        override fun load(): LoadResult {
            val connection = URL(url).openConnection() as HttpURLConnection

            try {
                val data = connection.inputStream.bufferedReader().use {
                    it.readText()
                }
                val response = parseQuestionAndChoices.parse(data)

                if (response.response_code == 0) {
                    val list = response.results
                    if (list.isEmpty())
                        return LoadResult.Error("empty data")
                    else {
                        dataCache.save(data)
                        return LoadResult.Success
                    }
                } else
                    return LoadResult.Error("response code is not successful: ${response.response_code}")
            } catch (e: NetworkOnMainThreadException) {
                return LoadResult.Error(e.message ?: "на мейне дебил")
            } catch (e: Exception) {
                e.printStackTrace()
                return LoadResult.Error(e.message ?: "error exception")
            } finally {
                connection.disconnect()
            }
        }

    }
}

class Response(
    val response_code: Int,
    val results: List<QuestionAndChoicesCloud>
)

data class QuestionAndChoicesCloud(
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
)

interface ParseQuestionAndChoices {
    fun parse(source: String): Response
    class Base(
        private val gson: Gson
    ) : ParseQuestionAndChoices {
        override fun parse(source: String): Response {
            return gson.fromJson(source, Response::class.java)
        }
    }
}