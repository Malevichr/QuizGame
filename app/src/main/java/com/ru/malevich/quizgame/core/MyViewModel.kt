package com.ru.malevich.quizgame.core

import com.ru.malevich.quizgame.load.presentation.UiObservable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface MyViewModel<T : Any> {

    interface Async<T : Any> : MyViewModel<T> {
        fun startUpdates(observer: (T) -> Unit)
        fun stopUpdates()
    }

    abstract class Abstract<T : Any>(
        protected val observable: UiObservable<T>
    ) : Async<T> {
        protected val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

        override fun startUpdates(observer: (T) -> Unit) = observable.register(observer)

        override fun stopUpdates() = observable.unregister()
    }
}

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