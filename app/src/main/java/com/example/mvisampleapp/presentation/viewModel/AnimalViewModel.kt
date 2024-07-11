package com.example.mvisampleapp.presentation.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mvisampleapp.common.Resource
import com.example.mvisampleapp.data.repository.AnimalRepositoryImpl
import com.example.mvisampleapp.data.service.AnimalService.api
import com.example.mvisampleapp.domain.model.Animal
import com.example.mvisampleapp.domain.usecase.AnimalsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AnimalViewModel(private val animalsUseCase: AnimalsUseCase) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AnimalViewModel(
                    animalsUseCase = AnimalsUseCase(repository = AnimalRepositoryImpl(api))
                )
            }
        }
    }

    val userIntentChannel = Channel<MainIntent> { Channel.UNLIMITED }
    private val userIntent = userIntentChannel.receiveAsFlow()


    var state = mutableStateOf<AnimalsState>(AnimalsState.Idle)
        private set

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.flowOn(Dispatchers.IO).collect { collector ->
                when (collector) {
                    MainIntent.FetchAnimals -> fetchAnimals()
                }
            }
        }
    }

    private fun fetchAnimals() {
        animalsUseCase.invoke().onStart {
            state.value = AnimalsState.Loading
        }.catch {
            state.value = AnimalsState.Error("Unknown Error")
        }.map { resoure ->
            when (resoure) {
                is Resource.Error -> {
                    state.value = AnimalsState.Error("Unknown Error")
                }

                is Resource.Loading -> {
                    state.value = AnimalsState.Loading
                }

                is Resource.Success -> {
                    state.value = AnimalsState.Success(
                        animals = resoure.data!!
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}

sealed interface AnimalsState {
    data object Idle : AnimalsState
    data object Loading : AnimalsState
    data class Success(val animals: List<Animal>) : AnimalsState
    data class Error(val error: String) : AnimalsState
}