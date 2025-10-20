package com.example.itstorm.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.example.itstorm.features.authentication.presentation.view.AuthenticationUI
import com.example.itstorm.root.flow.app.AppFlowUI

@Composable
fun RootUI(rootComponent: RootComponent) {
    Children(stack = rootComponent.stack,){ child ->
        when(val instance = child.instance){
            is RootComponent.Child.Authentication -> AuthenticationUI(instance.component)
            is RootComponent.Child.AppFlow -> AppFlowUI(instance.component)
        }
    }
}