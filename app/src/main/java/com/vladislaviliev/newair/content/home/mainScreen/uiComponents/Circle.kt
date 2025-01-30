package com.vladislaviliev.newair.content.home.mainScreen.uiComponents

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Circle(pollution: String, modifier: Modifier = Modifier) {
    Box(modifier, contentAlignment = Alignment.Center) {
        CircleImage()
        CircleTexts(pollution)
    }
}

@Composable
private fun CircleImage() {
    val size = 180.dp
    Canvas(Modifier.size(size)) {
        val strokeWidth = 8.dp
        drawCircle(
            Color.White,
            size.toPx() / 2 - strokeWidth.toPx() / 2,
            style = Stroke(width = strokeWidth.toPx())
        )
    }
}

@Composable
private fun CircleTexts(pollution: String) {
    Column(Modifier.padding(top = 7.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(pollution, fontSize = 60.sp)
        Text("PM10 μg/m", fontSize = 14.sp)
    }
}