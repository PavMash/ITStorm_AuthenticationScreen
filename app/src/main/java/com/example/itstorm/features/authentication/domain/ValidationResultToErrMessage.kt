package com.example.itstorm.features.authentication.domain

fun ValidationResult.toErrMessage(): String =
    when(this) {
        ValidationResult.WrongLogin ->
            "Неверный логин"
        ValidationResult.NonCyrillicLogin ->
            "Логин пользователя должен быть на кириллице"
        ValidationResult.NoLatinLettersInPw ->
            "Пароль должен содержать хотя бы одну латинскую букву"
        ValidationResult.NoDigitsInPw ->
            "Пароль должен содержать хотя бы одну цифру"
        ValidationResult.ShortPw ->
            "Пароль должен содержать не менее 6 символов"
        ValidationResult.Valid ->
            ""
    }