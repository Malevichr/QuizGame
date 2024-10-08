package com.ru.malevich.quizgame.core.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface Screen {
    object Empty : Screen {
        override fun show(containerId: Int, fragmentManager: FragmentManager) = Unit
    }

    fun show(containerId: Int, fragmentManager: FragmentManager)

    abstract class Replace(private val fragment: Class<out Fragment>) : Screen {
        override fun show(containerId: Int, fragmentManager: FragmentManager) {
            fragmentManager.beginTransaction()
                .replace(containerId, newFragment())
                .commit()
        }

        protected open fun newFragment() = fragment.getDeclaredConstructor().newInstance()
    }
}