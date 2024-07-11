package com.example.mvisampleapp.domain.repository

import com.example.mvisampleapp.data.model.AnimalItem

interface AnimalRepository {
    suspend fun getAnimals(): List<AnimalItem>
}