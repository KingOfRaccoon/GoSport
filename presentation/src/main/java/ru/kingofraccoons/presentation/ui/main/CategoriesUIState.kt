package ru.kingofraccoons.presentation.ui.main

import ru.kingofraccoons.domain.entity.ListCategories

sealed class CategoriesUIState(
    open val data: ListCategories?,
    open val selectedCategory: String? = null,
    open val message: String = ""
) {
    data class Success(
        override val data: ListCategories,
        override val selectedCategory: String
    ) : CategoriesUIState(data, selectedCategory)

    data class Loading(override val data: ListCategories? = null) : CategoriesUIState(data)

    data class Error(override val message: String) :
        CategoriesUIState(null, message)
}