package com.ru.malevich.quizgame.core.di

import com.ru.malevich.quizgame.core.presentation.MyViewModel
import com.ru.malevich.quizgame.game.di.ProvideGameViewModel
import com.ru.malevich.quizgame.gameover.di.ProvideGameOverViewModel
import com.ru.malevich.quizgame.load.di.ProvideLoadViewModel
import com.ru.malevich.quizgame.main.di.ProvideMainViewModel

interface ProvideViewModel {
    fun <T : MyViewModel> makeViewModel(clazz: Class<T>): T

    abstract class AbstractChainLink(
        protected val core: Core,
        private val nextLink: ProvideViewModel,
        private val viewModelClass: Class<out MyViewModel>,
    ) : ProvideViewModel {
        override fun <T : MyViewModel> makeViewModel(clazz: Class<T>): T {
            return if (clazz == viewModelClass)
                module().viewModel() as T
            else
                nextLink.makeViewModel(clazz)
        }

        protected abstract fun module(): Module<out MyViewModel>
    }

    class Make(
        core: Core,
    ) : ProvideViewModel {
        private var chain: ProvideViewModel

        init {
            chain = Error()
            chain = ProvideGameViewModel(core, chain)
            chain = ProvideGameOverViewModel(core, chain)
            chain = ProvideLoadViewModel(core, chain)
            chain = ProvideMainViewModel(core, chain)
        }

        override fun <T : MyViewModel> makeViewModel(clazz: Class<T>): T =
            chain.makeViewModel(clazz)

    }

    class Error : ProvideViewModel {
        override fun <T : MyViewModel> makeViewModel(clazz: Class<T>): T {
            throw IllegalStateException("unknown class: $clazz")
        }
    }
}