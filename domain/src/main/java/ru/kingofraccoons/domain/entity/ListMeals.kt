package ru.kingofraccoons.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ListMeals(
    val meals: List<Meal> = listOf()
)