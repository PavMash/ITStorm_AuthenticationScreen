package com.example.itstorm.features.authentication.domain

enum class ValidationResult {
    WrongLogin,
    NonCyrillicLogin,
    NoLatinLettersInPw,
    NoDigitsInPw,
    ShortPw,
    Valid
}