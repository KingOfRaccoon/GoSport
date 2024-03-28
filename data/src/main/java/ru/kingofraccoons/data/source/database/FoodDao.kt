package ru.kingofraccoons.data.source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.kingofraccoons.domain.entity.Category
import ru.kingofraccoons.domain.entity.ListCategories
import ru.kingofraccoons.domain.entity.ListMeals
import ru.kingofraccoons.domain.entity.Meal

@Dao
interface FoodDao {

    @Query("Select * From category")
    fun getCategories(): Flow<List<Category>>

    @Query("Select * From meal")
    fun getMeals(): Flow<List<Meal>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMeal(meal: Meal)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: Category)

    @Transaction
    suspend fun insertListMeal(meals: ListMeals){
        meals.meals.forEach { insertMeal(it) }
    }

    @Transaction
    suspend fun insertListCategories(categories: ListCategories){
        categories.categories.forEach { insertCategory(it) }
    }
}