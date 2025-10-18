package com.example.itstorm.features.weather.domain

data class WeatherEstimation(
    val city : String,
    val temperature : Int,
    val verdict : String
)