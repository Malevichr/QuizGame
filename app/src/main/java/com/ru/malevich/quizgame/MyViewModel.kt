package com.ru.malevich.quizgame

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface MyViewModel

interface RunAsync {
    fun <T : Any> handleAsync(
        coroutineScope: CoroutineScope,
        heavyOperation: suspend () -> T,
        updateUi: (T) -> Unit
    )

    class Base : RunAsync {

        override fun <T : Any> handleAsync(
            coroutineScope: CoroutineScope,
            heavyOperation: suspend () -> T,
            updateUi: (T) -> Unit
        ) {
            coroutineScope.launch(Dispatchers.IO) {
                val result = heavyOperation.invoke()
                withContext(Dispatchers.Main) {
                    updateUi.invoke(result)
                }
            }
        }
    }
}