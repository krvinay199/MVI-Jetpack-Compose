package com.example.mvisampleapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvisampleapp.data.repository.AnimalRepository
import com.example.mvisampleapp.data.service.AnimalApi

class ViewModelFactory(private val api: AnimalApi): ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
         fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>
        ): T {
            if (modelClass.isAssignableFrom(AnimalViewModel::class.java)) {
                return AnimalViewModel(AnimalRepository(api)) as T
            }

            throw IllegalArgumentException("Unknown class name")
        }
}