package ru.kingofraccoons.data.repository

import kotlinx.coroutines.flow.Flow
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
    private val foodService: FoodInterface
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

//    override suspend fun getMeals(): Flow<Resource<ListMeals>> {
//        return combine(foodDao.getMeals(), MutableStateFlow(foodService.getMeals())){ local, remote ->
//            if (remote is Resource.Success)
//                remote.also { foodDao.insertListMeal(it.data) }
//            else
//                Resource.Success(ListMeals(local))
//        }
//    }
//
//    override suspend fun getCategories(): Flow<Resource<ListCategories>> {
//        return combine(foodDao.getCategories(), MutableStateFlow(foodService.getCategories())){ local, remote ->
//            if (remote is Resource.Success && local != remote.data.categories)
//                remote.also { foodDao.insertListCategories(it.data) }
//            else
//                Resource.Success(ListCategories(local))
//        }
//    }
}