package ru.alexgladkov.odyssey.compose.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.helpers.BottomSheetBundle
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.NavConfiguration
import ru.alexgladkov.odyssey.core.extensions.Closeable
import ru.alexgladkov.odyssey.core.screen.ScreenInteractor
import ru.alexgladkov.odyssey.core.toScreenBundle

@Composable
fun Navigator(startScreen: String? = null, startParams: Any? = null) {
    val rootController = LocalRootController.current
    val navConfiguration = rootController.currentScreen.collectAsState()

    navConfiguration.value.let { config ->
        NavigatorAnimated(config.screen, config, rootController)
    }

    LaunchedEffect(Unit) {
        rootController.drawCurrentScreen(startScreen = startScreen, startParams = startParams)
    }
}

@Composable
private fun NavigatorAnimated(
    screen: ScreenInteractor,
    configuration: NavConfiguration,
    rootController: RootController
) {
    AnimatedHost(
        currentScreen = screen.toScreenBundle(),
        screenToRemove = configuration.screenToRemove?.toScreenBundle(),
        animationType = screen.animationType,
        isForward = screen.isForward,
        modifier = Modifier.fillMaxSize(),
        onScreenRemove = rootController.onScreenRemove
    ) { currentScreen ->
        val render = rootController.screenMap[currentScreen.realKey]
        render?.invoke(currentScreen.params)
            ?: throw IllegalStateException("Screen $currentScreen not found in screenMap")
    }
}

@Composable
fun NavigatorModalSheet(
    bundle: BottomSheetBundle,
    rootController: RootController
) {
    val presentedScreen = rootController.screenMap[bundle.currentKey]
    val modalSheet = rootController.screenMap[bundle.key]

    Box(modifier = Modifier.fillMaxSize()) {
        presentedScreen?.invoke(null)
        modalSheet?.invoke(null)
    }
}