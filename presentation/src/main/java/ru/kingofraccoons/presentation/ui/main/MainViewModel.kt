package ru.kingofraccoons.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.kingofraccoons.domain.entity.ListCategories
import ru.kingofraccoons.domain.entity.ListMeals
import ru.kingofraccoons.domain.usecase.GetCategoriesUseCase
import ru.kingofraccoons.domain.usecase.GetMealsUseCase
import ru.kingofraccoons.domain.util.Resource

class MainViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getMealsUseCase: GetMealsUseCase
) : ViewModel() {
    private val _selectedCategoryFlow = MutableStateFlow("")
    private val selectedCategoryFlow = _selectedCategoryFlow.asStateFlow()

    private val _networkCategoriesFlow =
        MutableStateFlow<Resource<ListCategories>>(Resource.Loading())

    private val _networkMealsFlow =
        MutableStateFlow<Resource<ListMeals>>(Resource.Loading())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _networkMealsFlow.emit(getMealsUseCase.getMealsFromNetwork())
        }

        viewModelScope.launch(Dispatchers.IO) {
            _networkCategoriesFlow.emit(getCategoriesUseCase.getCategoriesFromNetwork())
        }
    }

    fun getMeals() =
        combine(
            getMealsUseCase.getMealsFromDB(),
            _networkMealsFlow,
            selectedCategoryFlow
        ) { meals, mealsNetwork, selectedCategory ->
            if (mealsNetwork is Resource.Success && meals != mealsNetwork.data.meals) {
                if (selectedCategory.isNotEmpty())
                    convertResourceToMealsUIState(
                        mealsNetwork,
                        mealsNetwork.data.copy(mealsNetwork.data.meals.filter { it.strCategory == selectedCategory })
                    )
                else
                    convertResourceToMealsUIState(mealsNetwork, mealsNetwork.data)
            } else {
                if (selectedCategory.isNotEmpty())
                    convertResourceToMealsUIState(
                        Resource.Success(ListMeals(meals)),
                        ListMeals(meals.filter { it.strCategory == selectedCategory })
                    )
                else
                    convertResourceToMealsUIState(
                        Resource.Success(ListMeals(meals)),
                        ListMeals(meals)
                    )
            }
        }


    fun getCategories() = combine(
        getCategoriesUseCase.getCategoriesFromDB(),
        _networkCategoriesFlow,
        selectedCategoryFlow
    ) { categories, categoriesNetwork, selectedCategory ->
        if (categoriesNetwork is Resource.Success && categories != categoriesNetwork.data.categories) {
            convertResourceToCategoriesUIState(
                categoriesNetwork,
                categoriesNetwork.data,
                selectedCategory
            )
        } else {
            convertResourceToCategoriesUIState(
                Resource.Success(ListCategories(categories)),
                ListCategories(categories),
                selectedCategory
            )
        }
    }

    private fun convertResourceToMealsUIState(
        resource: Resource<ListMeals>,
        newData: ListMeals
    ): MealsUIState {
        return when (resource) {
            is Resource.Error -> MealsUIState.Error(resource.message)
            is Resource.Loading -> MealsUIState.Loading(resource.data)
            is Resource.Success -> MealsUIState.Success(newData)
        }
    }

    private fun convertResourceToCategoriesUIState(
        resource: Resource<ListCategories>,
        newData: ListCategories,
        selectedCategory: String
    ): CategoriesUIState {
        return when (resource) {
            is Resource.Error -> CategoriesUIState.Error(resource.message)
            is Resource.Loading -> CategoriesUIState.Loading(resource.data)
            is Resource.Success -> CategoriesUIState.Success(newData, selectedCategory)
        }
    }

    fun setSelectedCategory(category: String) {
        _selectedCategoryFlow.update { category }
    }
}