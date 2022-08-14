package com.github.valecarrirolo.carbrands

import android.app.Application
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val application: Application
) {
    fun getCarList(): List<CarBrand> {
        val carJson = application.resources.openRawResource(R.raw.cars)
            .use { it.reader().readText() }
        return Json.decodeFromString(carJson)
    }
}