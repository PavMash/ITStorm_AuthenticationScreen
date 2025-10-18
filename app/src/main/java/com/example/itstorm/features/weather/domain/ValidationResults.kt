package com.example.itstorm.features.weather.domain

data class ValidationResults(
    val isCityValid: Boolean = false,
    val isTemperatureValid: Boolean = false
)