package ru.kingofraccoons.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.kingofraccoons.domain.entity.Category
import ru.kingofraccoons.domain.entity.ListCategories
import ru.kingofraccoons.domain.entity.ListMeals
import ru.kingofraccoons.domain.entity.Meal
import ru.kingofraccoons.domain.util.Resource

interface FoodRepository {
    suspend fun getMealsFromNetwork(): Resource<ListMeals>
    fun getMealsFromDB(): Flow<List<Meal>>

    suspend fun getCategoriesFromNetwork(): Resource<ListCategories>
    fun getCategoriesFromDB(): Flow<List<Category>>
}