package com.example.mvisampleapp.data.service

import com.example.mvisampleapp.data.model.AnimalItem
import retrofit2.http.GET

interface AnimalApi {
    @GET(value = "animals.json")
    suspend fun getAnimals() : List<AnimalItem>
}