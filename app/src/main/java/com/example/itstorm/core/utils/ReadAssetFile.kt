package com.example.itstorm.core.utils

import android.content.Context

fun readAssetFile(context: Context, fileName: String): String {
    return context.assets.open(fileName).bufferedReader().use { it.readText() }
}