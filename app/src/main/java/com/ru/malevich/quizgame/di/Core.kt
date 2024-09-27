package com.ru.malevich.quizgame.di

import android.content.Context
import com.google.gson.Gson

class Core(
    context: Context,
    val clearViewModel: ClearViewModel
) {
    val gson = Gson()
    val sharedPreferences = context.getSharedPreferences("quizAppData", Context.MODE_PRIVATE)
}