package com.ru.malevich.quizgame.core.presentation

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class LoggedApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("mvlc", "Application: onCreate")
    }
}

abstract class LoggedFragment(
    fragmentName: String,
    private val log: MyLog = MyLog.Fragment(fragmentName)
) : Fragment() {
    init {
        log.log("init")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        log.log("onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log.log("onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        log.log("onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        log.log("onViewCreated")
    }

    override fun onStart() {
        log.log("onStart")
        super.onStart()
    }

    override fun onResume() {
        log.log("onResume")
        super.onResume()
    }

    override fun onPause() {
        log.log("onPause")
        super.onPause()
    }

    override fun onStop() {
        log.log("onStop")
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        log.log("onSaveInstanceState")
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        log.log("onViewStateRestored")
        super.onViewStateRestored(savedInstanceState)
    }
}

interface MyLog {
    fun log(operationName: String)

    abstract class Abstract(
        private val typeName: String,
        private val className: String
    ) : MyLog {
        override fun log(operationName: String) {
            Log.d("Mlvc", "$typeName $className : $operationName")
        }
    }

    class Fragment(fragmentName: String) : Abstract("Fragment", fragmentName)

    class Application(applicationName: String) : Abstract("Application", applicationName)

    class Activity(activityName: String) : Abstract("Activity", activityName)
}
