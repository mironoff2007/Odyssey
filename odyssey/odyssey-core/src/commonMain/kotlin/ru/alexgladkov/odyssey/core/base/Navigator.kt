package ru.alexgladkov.odyssey.core.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import ru.alexgladkov.odyssey.core.RootController
import ru.alexgladkov.odyssey.core.NavConfiguration
import ru.alexgladkov.odyssey.core.extensions.Closeable
import ru.alexgladkov.odyssey.core.helpers.BottomSheetBundle
import ru.alexgladkov.odyssey.core.local.LocalRootController
import ru.alexgladkov.odyssey.core.screen.ScreenBundle
import ru.alexgladkov.odyssey.core.screen.ScreenInteractor

@Composable
fun Navigator(startParams: Any? = null) {
    val rootController = LocalRootController.current
    var navConfiguration: NavConfiguration? by remember { mutableStateOf(null) }
    var closeable: Closeable? = null

    navConfiguration?.let { config ->
        val screen = config.screen

        NavigatorAnimated(screen, config, rootController)
    }

    LaunchedEffect(Unit) {
        closeable = rootController.currentScreen.watch {
            navConfiguration = it
        }

        rootController.drawCurrentScreen(startParams)
    }

    DisposableEffect(Unit) {
        onDispose {
            closeable?.close()
        }
    }
}

@Composable
private fun NavigatorAnimated(
    screen: ScreenInteractor,
    configuration: NavConfiguration,
    rootController: RootController
) {
    AnimatedHost(
        currentScreen = ScreenBundle(key = screen.key, realKey = screen.realKey, params = screen.params),
        removeScreen = configuration.removeScreen,
        animationType = screen.animationType,
        isForward = screen.isForward,
        modifier = Modifier.fillMaxSize()
    ) { currentScreen ->
        val render = rootController.screenMap[currentScreen.realKey]
        render?.invoke(currentScreen.params)
            ?: throw IllegalStateException("Screen $currentScreen not found in screenMap")
    }
}