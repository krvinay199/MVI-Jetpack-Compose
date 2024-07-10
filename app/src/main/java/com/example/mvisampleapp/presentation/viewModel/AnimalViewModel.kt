package com.example.mvisampleapp.presentation.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mvisampleapp.data.repository.AnimalRepository
import com.example.mvisampleapp.data.service.AnimalApi
import com.example.mvisampleapp.data.service.AnimalService.api
import com.example.mvisampleapp.presentation.MainIntent
import com.example.mvisampleapp.presentation.MainState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class AnimalViewModel(private val repository: AnimalRepository): ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AnimalViewModel(
                    repository = AnimalRepository(api = api)
                )
            }
        }
    }

    val userIntent = Channel<MainIntent> { Channel.UNLIMITED }

    var state = mutableStateOf<MainState>(MainState.Idle)
        private set

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().flowOn(Dispatchers.IO).collect { collector ->
                when (collector) {
                    MainIntent.FetchAnimals -> fetchAnimals()
                }
            }
        }
    }

    private suspend fun fetchAnimals() {
        viewModelScope.launch {
            state.value = MainState.Loading
            state.value = try {
                MainState.Success(
                    repository.getAnimals()
                )
            } catch (e: Exception){
                MainState.Error(e.localizedMessage ?: "API failed")
            }
        }
    }

}