package com.example.newsapp.core.presentation

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import com.example.newsapp.ui.theme.Grey1
import com.example.newsapp.ui.theme.Grey2

@Composable
fun ShimmerPlaceholder(
    shape: Shape = RectangleShape,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var size by remember { mutableStateOf<Size?>(null) }

    SubcomposeLayout { constraints ->
        // Subcompose the content
        val placeables = subcompose("content", content).map { it.measure(constraints) }

        // Measure the height of the first placeable
        val heightPx = if (placeables.isNotEmpty()) placeables[0].height else 0
        val widthPx = if (placeables.isNotEmpty()) placeables[0].width else 0

        size = Size(
            height = heightPx.toFloat(),
            width = widthPx.toFloat()
        )

        // Return zero-sized layout since we are not drawing anything
        layout(0, 0) {}
    }

    when {
        isLoading && size == null -> { }
        isLoading && size != null -> {
            Box(
                modifier = modifier
                    .size(
                        width = with(LocalDensity.current) { size!!.width.toDp() },
                        height = with(LocalDensity.current) { size!!.height.toDp() }
                    )
                    .shimmerEffect(shape)
            )
        }
        else -> {
            Box(
                modifier = modifier
            ) {
                content()
            }
        }
    }
}

fun Modifier.shimmerEffect(shape: Shape = RectangleShape): Modifier = composed {

    var size by remember {
        mutableStateOf(IntSize.Zero)
    }

    val transition = rememberInfiniteTransition(label = "")

    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(800)
        ),
        label = ""
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(Grey1, Grey2, Grey1),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        ),
        shape = shape
    )
        .onGloballyPositioned {
            size = it.size
        }
}