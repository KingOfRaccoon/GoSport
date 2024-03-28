package ru.kingofraccoons.data.source.network

import ru.kingofraccoons.domain.util.Resource
import ru.kingofraccoons.domain.entity.ListCategories
import ru.kingofraccoons.domain.entity.ListMeals

interface FoodInterface {
    suspend fun getCategories(): Resource<ListCategories>

    suspend fun getMeals(): Resource<ListMeals>
}