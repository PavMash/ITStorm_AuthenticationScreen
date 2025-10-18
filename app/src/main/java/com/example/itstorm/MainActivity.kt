package com.example.itstorm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.example.itstorm.root.DefaultRootComponent
import com.example.itstorm.root.RootUI

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootContext = defaultComponentContext()
        val rootComponent = DefaultRootComponent(
            rootContext,
            applicationContext)

        setContent {
            RootUI(rootComponent = rootComponent)
        }
    }
}