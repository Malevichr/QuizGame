package com.ru.malevich.quizgame.load.presentation

interface LoadUiObservable : UiObservable<LoadUiState> {

    class Base : UiObservable.Abstract<LoadUiState>(), LoadUiObservable
}