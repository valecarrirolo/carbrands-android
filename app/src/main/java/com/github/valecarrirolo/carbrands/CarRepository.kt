package com.github.valecarrirolo.carbrands

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CarRepository @Inject constructor(
    private val localDataSource: LocalDataSource
) {
    private val favoriteMap: MutableStateFlow<Map<String, Boolean>> = MutableStateFlow(emptyMap())

    private fun getCarList(): List<CarBrand> = localDataSource.getCarList()

    val carWrapperListFlow: Flow<List<CarWrapper>> = favoriteMap.map { currFavoriteMap ->
        getCarList().map { carBrand ->
            CarWrapper(carBrand, currFavoriteMap[carBrand.name] ?: false)
        }
    }

    fun setCarFavorite(carBrand: String, isFavorite: Boolean) {
        favoriteMap.value = favoriteMap.value + (carBrand to isFavorite)
    }
}