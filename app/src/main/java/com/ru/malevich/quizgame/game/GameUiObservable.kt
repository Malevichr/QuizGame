package com.ru.malevich.quizgame.game

import com.ru.malevich.quizgame.load.presentation.UiObservable

interface GameUiObservable : UiObservable<GameUiState> {
    class Base : UiObservable.Abstract<GameUiState>(), GameUiObservable
}