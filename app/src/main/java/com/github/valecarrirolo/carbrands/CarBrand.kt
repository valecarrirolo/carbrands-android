package com.github.valecarrirolo.carbrands

import kotlinx.serialization.Serializable

@Serializable
data class CarBrand(
    val rank: Long,
    val image: String,
    val origin: String,
    val name: String,
    val type: String
)