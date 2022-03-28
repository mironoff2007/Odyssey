package ru.alexgladkov.common.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexgladkov.common.compose.theme.Odyssey


@Composable
fun ModalSheetScreen(onCloseClick: () -> Unit) {
    Column(modifier = Modifier.background(Odyssey.color.primaryBackground)) {
        Text(modifier = Modifier.padding(16.dp), text = "Modal Sheet", fontSize = 24.sp, color = Odyssey.color.primaryText)
        ActionCell(text = "Close", icon = Icons.Filled.ArrowDownward) {
            onCloseClick.invoke()
        }
    }
}