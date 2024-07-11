package com.example.mvisampleapp.domain.mapper

import com.example.mvisampleapp.data.model.AnimalItem
import com.example.mvisampleapp.domain.model.Animal


fun AnimalItem.toDomainModel(): Animal {
    return Animal(
        name = this.name ?: "",
        image = this.image ?: "",
        location = this.location ?: "",
        type = this.diet ?: ""
    )
}