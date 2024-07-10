package com.example.mvisampleapp.data.repository

import com.example.mvisampleapp.data.service.AnimalApi

class AnimalRepository(private val api: AnimalApi) {

    suspend fun getAnimals() = api.getAnimals()

}