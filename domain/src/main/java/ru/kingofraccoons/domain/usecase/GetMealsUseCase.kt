package ru.kingofraccoons.domain.usecase

import ru.kingofraccoons.domain.repository.FoodRepository

class GetMealsUseCase(private val foodRepository: FoodRepository) {
    suspend fun getMealsFromNetwork() = foodRepository.getMealsFromNetwork()
    fun getMealsFromDB() = foodRepository.getMealsFromDB()
}