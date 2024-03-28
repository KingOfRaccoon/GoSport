package ru.kingofraccoons.domain.usecase

import ru.kingofraccoons.domain.repository.FoodRepository

class GetCategoriesUseCase(private val foodRepository: FoodRepository) {
    suspend fun getCategoriesFromNetwork() = foodRepository.getCategoriesFromNetwork()
    fun getCategoriesFromDB() = foodRepository.getCategoriesFromDB()
}