package com.vladislaviliev.newair.content.home.mainScreen.uiComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vladislaviliev.newair.R

@Composable
internal fun BackgroundImages(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Image(
            painterResource(R.drawable.ic_sun), null,
            Modifier
                .size(50.dp)
                .align(Alignment.TopStart)
                .offset(20.dp, 30.dp),
            alpha = 0.25f,
        )

        Image(
            painterResource(R.drawable.ic_clouds), null,
            Modifier
                .size(50.dp)
                .align(Alignment.TopEnd)
                .offset((-40).dp, 60.dp),
            alpha = 0.25f,
        )

        Image(
            painterResource(R.drawable.ic_hot_air_balloon), null,
            Modifier
                .size(65.dp)
                .align(Alignment.TopEnd)
                .offset((-20).dp, 150.dp),
            alpha = 0.2f,
        )

        Image(
            painterResource(R.drawable.ic_mountains), null,
            Modifier
                .size(30.dp)
                .align(Alignment.BottomStart)
                .offset(15.dp, 0.dp),
            alpha = 0.25f,
        )

        Image(
            painterResource(R.drawable.ic_fruit_tree), null,
            Modifier
                .size(45.dp)
                .align(Alignment.BottomStart)
                .offset(35.dp, 0.dp),
            alpha = 0.35f,
        )

        Image(
            painterResource(R.drawable.ic_pine), null,
            Modifier
                .size(60.dp)
                .align(Alignment.BottomEnd)
                .offset((-10).dp, 0.dp),
            alpha = 0.35f,
        )

        Image(
            painterResource(R.drawable.ic_poppy), null,
            Modifier
                .size(25.dp)
                .align(Alignment.BottomEnd)
                .offset((-60).dp, 0.dp),
            alpha = 0.5f,
        )
    }
}
