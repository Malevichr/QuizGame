package com.ru.malevich.quizgame.core.di

import android.content.Context
import com.ru.malevich.quizgame.core.presentation.RunAsync
import com.ru.malevich.quizgame.load.data.cache.CacheModule

class Core(
    context: Context,
    val clearViewModel: ClearViewModel
) {
    val runAsync: RunAsync = RunAsync.Base()
    val cacheModule: CacheModule = CacheModule.Base(context)
    val size = 10
    val runUiTests = true
    val sharedPreferences = context.getSharedPreferences("quizAppData", Context.MODE_PRIVATE)
}