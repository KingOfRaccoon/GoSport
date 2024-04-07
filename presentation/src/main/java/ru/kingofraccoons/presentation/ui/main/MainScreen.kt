package ru.kingofraccoons.presentation.ui.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import org.koin.compose.koinInject
import ru.kingofraccoons.domain.entity.Meal
import ru.kingofraccoons.presentation.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel = koinInject()) {
    val listState = rememberLazyListState()
    val categories = viewModel.getCategories().collectAsState(CategoriesUIState.Loading())
    val meals = viewModel.getMeals().collectAsState(MealsUIState.Loading())
    val title = viewModel.getTitle().collectAsState(initial = "Москва")
    val isNetwork = viewModel.getNetworkFlow().collectAsState()

    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        Modifier.fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = title.value, color = MaterialTheme.colorScheme.onBackground)
                        Icon(
                            Icons.Default.KeyboardArrowDown,
                            null,
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                },
                Modifier.fillMaxWidth(),
                actions = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_qr_code),
                        contentDescription = null,
                        Modifier.padding(end = 16.dp),
                        MaterialTheme.colorScheme.tertiary
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                contentColor = MaterialTheme.colorScheme.secondaryContainer,
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ) {
                NavigationBarItem(
                    true,
                    {},
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_menu),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    label = {
                        Text(text = "Меню", color = MaterialTheme.colorScheme.onPrimary)
                    }
                )

                NavigationBarItem(
                    false,
                    {},
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_profile),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    },
                    label = {
                        Text(
                            text = "Профиль",
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                )

                NavigationBarItem(
                    false,
                    {},
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_basket),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    },
                    label = {
                        Text(
                            text = "Корзина",
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                )
            }
        }
    ) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(it),
            listState
        ) {
            item {
                if (isNetwork.value)
                    RowBanners(
                        listOf(
                            "https://i.ibb.co/vqNthbK/Rectangle-38.png",
                            "https://i.ibb.co/jLkF1hY/Rectangle-39.png"
                        )
                    )
            }

            stickyHeader {
                RowTags(categories.value, viewModel::setSelectedCategory, viewModel::loadCategories)
            }

            when (val mealsUIState = meals.value) {
                is MealsUIState.Error -> {
                    item {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Проблема с загрузкой блюд",
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Button(onClick = viewModel::loadMeals) {
                                Text(text = "Попробуйте снова")
                            }
                        }
                    }
                }

                is MealsUIState.Loading -> {}
                is MealsUIState.Success -> ColumnMeals(mealsUIState)
            }
        }
    }
}

fun LazyListScope.ColumnMeals(meals: MealsUIState.Success) {
    items(meals.data.meals, { it.idMeal }) {
        ItemMeal(it)
    }
}

@Composable
fun ItemMeal(meal: Meal) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        AsyncImage(
            meal.strMealThumb, null,
            Modifier
                .weight(1f)
                .aspectRatio(1f)
                .padding(end = 22.dp)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop
        )

        Column(
            Modifier
                .weight(1f), Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = meal.strMeal,
                style = TextStyle(fontWeight = FontWeight.Black, fontSize = 18.sp)
            )
            Text(text = meal.ingredients().joinToString { it })

            OutlinedButton(
                onClick = { },
                Modifier.align(Alignment.End),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary)
            ) {
                Text(text = "От " + meal.idMeal, color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}

@Composable
fun RowBanners(images: List<String>) {
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        LazyRow(
            Modifier
                .fillMaxWidth()
        ) {
            items(images) {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    Modifier
                        .width(this@BoxWithConstraints.maxWidth * 0.9f)
                        .aspectRatio(2.3f)
                        .clip(RoundedCornerShape(10.dp))
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowTags(
    categories: CategoriesUIState,
    setSelectedCategory: (String) -> Unit,
    reloadTags: () -> Unit
) {
    when (categories) {
        is CategoriesUIState.Error -> {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Проблема с загрузкой категорий",
                    color = MaterialTheme.colorScheme.onBackground
                )

                Button(onClick = reloadTags) {
                    Text(text = "Попробуйте снова")
                }
            }
        }

        is CategoriesUIState.Loading -> {}
        is CategoriesUIState.Success -> {
            LazyRow(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .background(MaterialTheme.colorScheme.background),
            ) {
                items(categories.data.categories, { it.idCategory }) {
                    ElevatedFilterChip(
                        selected = false,
                        onClick = { setSelectedCategory(it.strCategory) },
                        label = {
                            Text(
                                text = it.strCategory,
                                Modifier.padding(8.dp),
                                if (categories.selectedCategory == it.strCategory)
                                    MaterialTheme.colorScheme.onPrimary
                                else
                                    MaterialTheme.colorScheme.onSurface
                            )
                        },
                        elevation = FilterChipDefaults.elevatedFilterChipElevation(4.dp),
                        colors = FilterChipDefaults.elevatedFilterChipColors(
                            if (categories.selectedCategory == it.strCategory)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.surface
                        ),
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}