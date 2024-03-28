package ru.kingofraccoons.data.source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kingofraccoons.domain.entity.Category
import ru.kingofraccoons.domain.entity.Meal

@Database(entities = [Category::class, Meal::class], version = 1, exportSchema = false)
abstract class FoodDatabase: RoomDatabase() {
    abstract fun foodDao(): FoodDao
}