package com.ru.malevich.quizgame.load.di

import com.ru.malevich.quizgame.MyViewModel
import com.ru.malevich.quizgame.StringCache
import com.ru.malevich.quizgame.di.Core
import com.ru.malevich.quizgame.di.Module
import com.ru.malevich.quizgame.di.ProvideViewModel
import com.ru.malevich.quizgame.load.data.LoadRepository
import com.ru.malevich.quizgame.load.data.ParseQuestionAndChoices
import com.ru.malevich.quizgame.load.data.QuizResponse
import com.ru.malevich.quizgame.load.data.QuizService
import com.ru.malevich.quizgame.load.presentation.LoadViewModel
import com.ru.malevich.quizgame.load.presentation.UiObservable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class LoadModule(private val core: Core) : Module<LoadViewModel> {
    override fun viewModel(): LoadViewModel {
        val defaultResponseData = QuizResponse(-1, emptyList())
        val defaultGsonString = core.gson.toJson(defaultResponseData)
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://opentdb.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(QuizService::class.java)
        return if (core.runUiTests)
            LoadViewModel(
                LoadRepository.Fake(),
                observable = UiObservable.Base(),
                clearViewModel = core.clearViewModel,
            )
        else LoadViewModel(
            LoadRepository.Base(
                dataCache = StringCache.Base(
                    core.sharedPreferences,
                    "question_data",
                    defaultGsonString
                ),
                parseQuestionAndChoices = ParseQuestionAndChoices.Base(core.gson),
                service = service
            ),
            clearViewModel = core.clearViewModel,
            observable = UiObservable.Base()
        )
    }


}

class ProvideLoadViewModel(core: Core, nextLink: ProvideViewModel) :
    ProvideViewModel.AbstractChainLink(
        core,
        nextLink,
        LoadViewModel::class.java
    ) {
    override fun module(): Module<out MyViewModel> =
        LoadModule(core)
}