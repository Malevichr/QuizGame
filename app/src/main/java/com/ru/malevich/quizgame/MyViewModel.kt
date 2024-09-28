package com.ru.malevich.quizgame

import android.os.Handler
import android.os.Looper

interface MyViewModel

interface RunAsync {
    fun <T : Any> handleAsync(
        heavyOperation: () -> T,
        updateUi: (T) -> Unit
    )

    class Base : RunAsync {
        override fun <T : Any> handleAsync(heavyOperation: () -> T, updateUi: (T) -> Unit) {
            Thread {
                val result = heavyOperation.invoke()
                Handler(Looper.getMainLooper()).post {
                    updateUi.invoke(result)
                }
            }.start()
        }
    }
}