package ru.kingofraccoons.domain.usecase

import ru.kingofraccoons.domain.repository.FoodRepository

class GetNetworkStateUseCase(private val foodRepository: FoodRepository) {
    fun getNetworkState() = foodRepository.getNetworkFlow()
}