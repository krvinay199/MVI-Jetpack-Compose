package com.example.mvisampleapp.domain.usecase

import com.example.mvisampleapp.common.Resource
import com.example.mvisampleapp.domain.mapper.toDomainModel
import com.example.mvisampleapp.domain.model.Animal
import com.example.mvisampleapp.domain.repository.AnimalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class AnimalsUseCase(private val repository: AnimalRepository) {

    operator fun invoke(): Flow<Resource<List<Animal>>> = flow {
        try {
            emit(Resource.Loading())

            val response = repository.getAnimals().map {
                it.toDomainModel()
            }

            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))

        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Check your Internet connection"))

        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: " "))

        }
    }
}