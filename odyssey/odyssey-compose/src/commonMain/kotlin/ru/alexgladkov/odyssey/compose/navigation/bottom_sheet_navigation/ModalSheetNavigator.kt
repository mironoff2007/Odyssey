package ru.alexgladkov.odyssey.compose.navigation.bottom_sheet_navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.alexgladkov.odyssey.compose.controllers.ModalSheetBundle
import ru.alexgladkov.odyssey.compose.controllers.ModalSheetController
import ru.alexgladkov.odyssey.compose.helpers.noRippleClickable
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.extensions.Closeable

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ModalSheetNavigator(
    content: @Composable () -> Unit
) {
    val modalSheetController = remember { ModalSheetController() }
    val rootController = LocalRootController.current
    var modalStack: List<ModalSheetBundle> by remember { mutableStateOf(emptyList()) }
    var closeable: Closeable? = null

    rootController.attachModalSheet(modalSheetController)
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        content.invoke()

        modalStack.forEach { bundle ->
            val modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth()
            if (bundle.maxHeight != null)
                modifier.fillMaxHeight(bundle.maxHeight)
            Screamer(bundle.alpha) { modalSheetController.removeTopScreen() }
            Card(
                modifier = modifier,
                shape = RoundedCornerShape(
                    topStart = bundle.cornerRadius.dp,
                    topEnd = bundle.cornerRadius.dp
                )
            ) {
                bundle.content.invoke()
            }
        }
    }

    LaunchedEffect(Unit) {
        closeable = modalSheetController.currentStack.watch {
            modalStack = it
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            closeable?.close()
        }
    }
}

@Composable
private fun Screamer(alpha: Float, onCloseClick: () -> Unit) {
    Box(modifier = Modifier
        .noRippleClickable { onCloseClick.invoke() }
        .fillMaxSize().background(Color.Black.copy(alpha = alpha)))
}