package ru.kingofraccoons.data.source.network

import ru.kingofraccoons.data.util.Postman
import ru.kingofraccoons.domain.util.Resource
import ru.kingofraccoons.domain.entity.ListCategories
import ru.kingofraccoons.domain.entity.ListMeals

class FoodService(private val postman: Postman): FoodInterface {
    private val baseUrl = "https://themealdb.com/api/json/v1/1/"
    private val tagCategories = "categories.php"
    private val tagMeals = "search.php?s"

    override suspend fun getCategories(): Resource<ListCategories> {
        return postman.get(baseUrl, tagCategories)
    }

    override suspend fun getMeals(): Resource<ListMeals> {
        return postman.get(baseUrl, tagMeals)
    }
}