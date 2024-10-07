package com.ru.malevich.quizgame.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ru.malevich.quizgame.R
import com.ru.malevich.quizgame.core.MyViewModel
import com.ru.malevich.quizgame.di.ProvideViewModel

class MainActivity : AppCompatActivity(), Navigate, ProvideViewModel {
    override fun onCreate(savedInstanceState: Bundle?) {

//        StrictMode.setThreadPolicy(
//            StrictMode.ThreadPolicy.Builder()
//                .detectDiskReads()
//                .detectDiskWrites()
//                .detectNetwork()
//                .penaltyLog()
//                .permitNetwork() // Разрешает сетевой доступ в StrictMode
//                .build()
//        )
//        StrictMode.setVmPolicy(
//            StrictMode.VmPolicy.Builder()
//                .detectLeakedSqlLiteObjects()
//                .detectLeakedClosableObjects()
//                .penaltyLog()
//                .penaltyDeath()
//                .build()
//        )
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
            navigateToLoad()
    }

    override fun navigate(screen: Screen) {
        screen.show(R.id.container, supportFragmentManager)
    }

    override fun <S : Any, T : MyViewModel<S>> makeViewModel(clazz: Class<T>): T {
        return (application as ProvideViewModel).makeViewModel(clazz)
    }
}