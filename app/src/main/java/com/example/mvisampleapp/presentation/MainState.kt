package com.example.mvisampleapp.presentation

import com.example.mvisampleapp.data.model.AnimalItem

sealed class MainState {

    data object Idle: MainState()
    data object Loading: MainState()
    data class Success(val animals: List<AnimalItem>): MainState()
    data class Error(val error: String): MainState()
}