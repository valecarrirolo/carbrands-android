package com.github.valecarrirolo.carbrands

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CarBrandsViewModel @Inject constructor(private val repository: CarRepository) : ViewModel() {
    val carWrapperStateFlow: StateFlow<List<CarWrapper>> = repository.carWrapperListFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun setCarFavorite(car: CarWrapper, isFavorite: Boolean) {
        repository.setCarFavorite(car.car.name, isFavorite)
    }
}