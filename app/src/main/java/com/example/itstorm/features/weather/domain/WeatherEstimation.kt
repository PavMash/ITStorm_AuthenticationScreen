package com.example.itstorm.features.weather.domain

data class WeatherEstimation(
    val city : String = "г. N",
    val temperature : Int = 0,
    val verdict : String = ""
)