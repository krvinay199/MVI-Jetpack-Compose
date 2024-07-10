package com.example.mvisampleapp.data.model

data class AnimalItem(
    val diet: String?,
    val image: String?,
    val lifespan: String?,
    val location: String?,
    val name: String?,
    val speed: Speed?,
    val taxonomy: Taxonomy?
)

data class Taxonomy(
    val family: String?,
    val kingdom: String?,
    val order: String?
)

data class Speed(
    val imperial: String?,
    val metric: String?
)