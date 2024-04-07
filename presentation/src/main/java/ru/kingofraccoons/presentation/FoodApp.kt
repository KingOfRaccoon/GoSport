package ru.kingofraccoons.presentation

import android.app.Application
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import ru.kingofraccoons.data.network.CustomConnectivityManager
import ru.kingofraccoons.data.repository.FoodRepositoryImpl
import ru.kingofraccoons.data.source.database.FoodDatabase
import ru.kingofraccoons.data.source.network.FoodInterface
import ru.kingofraccoons.data.source.network.FoodService
import ru.kingofraccoons.data.util.Postman
import ru.kingofraccoons.domain.repository.FoodRepository
import ru.kingofraccoons.domain.usecase.GetCategoriesUseCase
import ru.kingofraccoons.domain.usecase.GetMealsUseCase
import ru.kingofraccoons.domain.usecase.GetNetworkStateUseCase
import ru.kingofraccoons.presentation.ui.main.MainViewModel

class FoodApp : Application() {
    private val moduleList = listOf(
        module { single { Postman() } },
        module { single<FoodInterface> { FoodService(get()) } },
        module {
            single {
                Room.databaseBuilder(applicationContext, FoodDatabase::class.java, "item_database")
                    .fallbackToDestructiveMigration()
                    .build()
            }
        },
        module {
            single {
                CustomConnectivityManager(
                    this@FoodApp.applicationContext,
                    CoroutineScope(Dispatchers.Default)
                )
            }
        },
        module {
            single<FoodRepository> {
                FoodRepositoryImpl(
                    get<FoodDatabase>().foodDao(),
                    get(),
                    get()
                )
            }
        },
        module { single { GetMealsUseCase(get<FoodRepository>()) } },
        module { single { GetNetworkStateUseCase(get<FoodRepository>()) } },
        module { single { GetCategoriesUseCase(get<FoodRepository>()) } },
        module { single { MainViewModel(get(), get(), get()) } }
    )

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            androidLogger(Level.ERROR)
            modules(moduleList)
        }
    }
}