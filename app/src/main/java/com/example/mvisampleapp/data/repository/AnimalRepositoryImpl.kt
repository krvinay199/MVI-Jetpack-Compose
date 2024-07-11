package com.example.mvisampleapp.data.repository

import com.example.mvisampleapp.data.model.AnimalItem
import com.example.mvisampleapp.data.service.AnimalApi
import com.example.mvisampleapp.domain.repository.AnimalRepository

class AnimalRepositoryImpl(private val api: AnimalApi) : AnimalRepository {
    override suspend fun getAnimals(): List<AnimalItem> {
        return api.getAnimals()
    }
}