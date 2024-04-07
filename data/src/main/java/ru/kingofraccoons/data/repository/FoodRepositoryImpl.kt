package ru.kingofraccoons.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.kingofraccoons.data.network.CustomConnectivityManager
import ru.kingofraccoons.data.source.database.FoodDao
import ru.kingofraccoons.data.source.network.FoodInterface
import ru.kingofraccoons.domain.entity.Category
import ru.kingofraccoons.domain.entity.ListCategories
import ru.kingofraccoons.domain.entity.ListMeals
import ru.kingofraccoons.domain.entity.Meal
import ru.kingofraccoons.domain.repository.FoodRepository
import ru.kingofraccoons.domain.util.Resource

class FoodRepositoryImpl(
    private val foodDao: FoodDao,
    private val foodService: FoodInterface,
    private val customConnectivityManager: CustomConnectivityManager
) : FoodRepository {
    override suspend fun getMealsFromNetwork(): Resource<ListMeals> {
        return foodService.getMeals().also { it.data?.let { it1 -> foodDao.insertListMeal(it1) } }
    }

    override fun getMealsFromDB(): Flow<List<Meal>> {
        return foodDao.getMeals()
    }

    override suspend fun getCategoriesFromNetwork(): Resource<ListCategories> {
        return foodService.getCategories().also {
            it.data?.let { it1 ->
                foodDao.insertListCategories(
                    it1
                )
            }
        }
    }

    override fun getCategoriesFromDB(): Flow<List<Category>> {
        return foodDao.getCategories()
    }

    override fun getNetworkFlow(): StateFlow<Boolean> {
        return customConnectivityManager.connectionAsStateFlow
    }
}