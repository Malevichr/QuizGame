package com.ru.malevich.quizgame.game.presentation

import com.ru.malevich.quizgame.load.presentation.UiObservable

interface GameUiObservable : UiObservable<GameUiState> {
    class Base : UiObservable.Abstract<GameUiState>(), GameUiObservable
}