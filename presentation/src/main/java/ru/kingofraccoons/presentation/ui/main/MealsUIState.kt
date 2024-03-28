package ru.kingofraccoons.presentation.ui.main

import ru.kingofraccoons.domain.entity.ListMeals

sealed class MealsUIState(open val data: ListMeals?, open val message: String = "") {
    data class Success(override val data: ListMeals) : MealsUIState(data)

    data class Loading(override val data: ListMeals? = null) : MealsUIState(data)

    data class Error(override val message: String) :
        MealsUIState(null, message)
}