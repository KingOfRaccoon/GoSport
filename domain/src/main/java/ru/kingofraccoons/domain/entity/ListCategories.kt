package ru.kingofraccoons.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ListCategories(
    val categories: List<Category> = listOf()
)