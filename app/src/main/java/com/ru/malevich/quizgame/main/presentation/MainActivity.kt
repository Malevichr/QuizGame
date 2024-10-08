package com.ru.malevich.quizgame.main.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ru.malevich.quizgame.R
import com.ru.malevich.quizgame.core.di.ProvideViewModel
import com.ru.malevich.quizgame.core.presentation.MyViewModel
import com.ru.malevich.quizgame.core.presentation.Navigate
import com.ru.malevich.quizgame.core.presentation.Screen

class MainActivity : AppCompatActivity(), Navigate, ProvideViewModel {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)
        val viewModel = makeViewModel(MainViewModel::class.java)
        val screen = viewModel.firstScreen(savedInstanceState == null)

        navigate(screen)
    }

    override fun navigate(screen: Screen) {
        screen.show(R.id.container, supportFragmentManager)
    }

    override fun <T : MyViewModel> makeViewModel(clazz: Class<T>): T {
        return (application as ProvideViewModel).makeViewModel(clazz)
    }
}