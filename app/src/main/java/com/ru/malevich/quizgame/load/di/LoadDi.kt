package com.ru.malevich.quizgame.load.di

import com.ru.malevich.quizgame.core.MyViewModel
import com.ru.malevich.quizgame.di.Core
import com.ru.malevich.quizgame.di.Module
import com.ru.malevich.quizgame.di.ProvideViewModel
import com.ru.malevich.quizgame.load.data.LoadRepository
import com.ru.malevich.quizgame.load.data.cloud.QuizService
import com.ru.malevich.quizgame.load.presentation.LoadUiObservable
import com.ru.malevich.quizgame.load.presentation.LoadViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class LoadModule(private val core: Core) : Module<LoadViewModel> {
    override fun viewModel(): LoadViewModel {
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
                repository = LoadRepository.Fake(),
                observable = LoadUiObservable.Base(),
                clearViewModel = core.clearViewModel,
                runAsync = core.runAsync
            )
        else LoadViewModel(
            repository = LoadRepository.Base(
                service = service,
                dao = core.cacheModule.dao(),
                size = core.size,
                clearDatabase = core.cacheModule.clearDatabase()
            ),
            clearViewModel = core.clearViewModel,
            observable = LoadUiObservable.Base(),
            runAsync = core.runAsync
        )
    }


}

class ProvideLoadViewModel(core: Core, nextLink: ProvideViewModel) :
    ProvideViewModel.AbstractChainLink(
        core,
        nextLink,
        LoadViewModel::class.java
    ) {
    override fun module(): Module<out MyViewModel<*>> =
        LoadModule(core)
}